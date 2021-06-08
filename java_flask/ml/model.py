import pickle
import requests
import json
import pandas as pd
import numpy as np
import os
import time
import scipy.io
import joblib
from pathlib import Path
from sklearn.preprocessing import LabelEncoder
from sklearn.model_selection import train_test_split
from konlpy.tag import *
from tqdm import tqdm
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.layers import Embedding, Dense, LSTM
from tensorflow.keras.models import Sequential
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.models import model_from_json

import tensorflow as tf
tf.debugging.set_log_device_placement(True)

# 텐서를 CPU에 할당
with tf.device('/CPU:0'):
  a = tf.constant([[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]])
  b = tf.constant([[1.0, 2.0], [3.0, 4.0], [5.0, 6.0]])

c = tf.matmul(a, b)
print(c)


def export_model():

    sea = pd.read_csv("C:/Users/byl93/여기어떼 Dropbox/데이터/바다s.csv", encoding="CP949")
    mountain = pd.read_csv("C:/Users/byl93/여기어떼 Dropbox/데이터/산s.csv", encoding="CP949")
    history = pd.read_csv("C:/Users/byl93/여기어떼 Dropbox/데이터/역사s.csv", encoding="CP949")
    leisure = pd.read_csv("C:/Users/byl93/여기어떼 Dropbox/데이터/체험s.csv", encoding="utf-8")

    youtube_data = pd.concat([sea, mountain, history, leisure], axis = 0)

    encoder = LabelEncoder()
    encoder.fit(youtube_data['tag'].unique())
    print(encoder.classes_)
    encoder.transform(['바다', '문화', '산', '레저'])
    youtube_data['labeling'] = encoder.transform(youtube_data['tag'])

    def split_train_test(data, test_ratio):
        shuffled_indices = np.random.permutation(len(data))
        test_set_size = int(len(data) * test_ratio)
        test_indices = shuffled_indices[:test_set_size]
        train_indices = shuffled_indices[test_set_size:]

        return data.iloc[train_indices], data.iloc[test_indices]

    train_data, test_data = split_train_test(youtube_data, 0.3)

    stopwords = ['의', '가', '이', '은', '들', '는', '좀', '잘', '걍', '과', '도', '를', '으로', '자', '에', '와', '한', '하다']

    okt = Okt()

    X_train = []
    X_test = []
    try:
        for sentence in tqdm(train_data['title']):
            temp_X = []
            temp_X = okt.morphs(sentence, stem=True) # 토큰화
            temp_X = [word for word in temp_X if not word in stopwords] # 불용어 제거
            X_train.append(temp_X)
    except:
        pass
    try:
        for sentence in tqdm(test_data['title']):
            temp_X = []
            temp_X = okt.morphs(sentence, stem=True) # 토큰화
            temp_X = [word for word in temp_X if not word in stopwords] # 불용어 제거
            X_test.append(temp_X)
    except:
        pass

    max_words = 50000
    tokenizer = Tokenizer(num_words = max_words)
    tokenizer.fit_on_texts(X_train)
    X_train = tokenizer.texts_to_sequences(X_train)
    X_test = tokenizer.texts_to_sequences(X_test)

    y_train = []
    y_test = []
    for i in range(len(train_data['labeling'])):
        if train_data['labeling'].iloc[i] == 0:
            y_train.append([0, 0, 0, 1])
        elif train_data['labeling'].iloc[i] == 1:
            y_train.append([0, 0, 1, 0])
        elif train_data['labeling'].iloc[i] == 2:
            y_train.append([0, 1, 0, 0])
        elif train_data['labeling'].iloc[i] == 3:
            y_train.append([1, 0, 0, 0])

    for i in range(len(test_data['labeling'])):
        if test_data['labeling'].iloc[i] == 0:
            y_test.append([0, 0, 0, 1])
        elif test_data['labeling'].iloc[i] == 1:
            y_test.append([0, 0, 1, 0])
        elif test_data['labeling'].iloc[i] == 2:
            y_test.append([0, 1, 0, 0])
        elif test_data['labeling'].iloc[i] == 3:
            y_test.append([1, 0, 0, 0])

    y_train = np.array(y_train)
    y_test = np.array(y_test)


    max_len = 20 # 전체 데이터의 길이를 20로 맞춘다
    X_train = pad_sequences(X_train, maxlen=max_len)
    X_test = pad_sequences(X_test, maxlen=max_len)

    # model = Sequential()
    # model.add(Embedding(max_words, 100))
    # model.add(LSTM(256))
    # model.add(Dense(4, activation='softmax'))

    # model.compile(optimizer='rmsprop', loss='categorical_crossentropy', metrics=['accuracy'])
    # hist = model.fit(X_train, y_train, epochs=8, batch_size=10, validation_split=0.01)

    json_file = open("./LSTMmodel.json", "r")
    loaded_model_json = json_file.read()
    json_file.close()
    model = model_from_json(loaded_model_json)

    model.load_weights("./LSTMmodel.h5")
    # print("Loaded model from disk")

    # evaluate
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    model.fit(X_train, y_train, epochs=10, batch_size=20, validation_split=0.01)

    # 모델 저장(첫 학습, 재 학습 구분)
    return joblib.dump(model, "../model/model.pkl")


if __name__ == "__main__":
    export_model()
    print('성공')
