

CREATE SEQUENCE t_board_no_seq;



/* Create Tables */

CREATE TABLE t_board
(
	no number NOT NULL,
	title varchar2(100) NOT NULL,
	name varchar2(20) NOT NULL,
	passwd varchar2(128) NOT NULL,
	content clob NOT NULL,
	regdate date DEFAULT sysdate NOT NULL,
	readcount number(6) DEFAULT 0 NOT NULL,
	PRIMARY KEY (no)
);


select * from t_board;

select no, 
	title, 
	name, 
	regdate, 
	readcount 
from t_board
order by no desc;

select 
	title, 
	name, 
	regdate,
	content,
	readcount 
from t_board
where no=1;

delete t_board where no = 1;

SELECT B.*
FROM (SELECT rownum as rnum, A.*
         FROM (SELECT no, 
         			  title, 
         			  name,
         			  to_char(regdate, 'YYYY/MM/DD') AS regdate,
         			  readcount
         	   FROM t_board
	 	 	   ORDER BY no desc) A) B
WHERE rnum BETWEEN ? and ?;

trunc(t_board);
