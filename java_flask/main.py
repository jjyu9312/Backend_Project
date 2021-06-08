import numpy as np
import pandas as pd
from flask import Flask, request, jsonify, render_template
import pickle
from tensorflow.keras.preprocessing.text import Tokenizer
from konlpy.tag import Okt
from tensorflow.keras.preprocessing.sequence import pad_sequences


app = Flask(__name__)

@app.before_first_request
def load_model_to_app():
    app.predictor = load_model('./static/model/model.h5')

# 메인 페이지 라우팅
@app.route('/')
def home():
    return render_template('index.html')

@app.route('/predict', methods=['POST'])
def predict():
    df = pd.DataFrame(data = 'title':f{["sentence"]})
    X_test = []
    temp_X = okt.morphs(stred, stem=True) # 토큰화
    stopwords = ['의', '가', '이', '은', '들', '는', '좀', '잘', '걍', '과', '도', '를', '으로', '자', '에', '와', "무조건", "밖"
                 '한', '하다','대박', '최고', '에서', "국내", "여행", "가야", "갈", "할", "필수", "꼭"]
    temp_X = [word for word in temp_X if not word in stopwords] # 불용어 제거
    X_test.append(temp_X)
    X_test = tokenizer.texts_to_sequences(X_test)
    X_test = pad_sequences(X_test, maxlen=20)
    predict = app.predictor.predict(X_test)
    predict_labels = int(np.argmax(predict, axis=1))

    return render_template('index.html', pred = predict_labels)


# Rest 등록
# api.add_resource(RestMl, '/retrainModel')
#
# if __name__ == '__main__':
#     # 모델 로드
#     # ml/model.py 선 실행 후 생성
#     # Flask 서비스 스타트
#     app.run(host='0.0.0.0', port=8000, debug=True)
