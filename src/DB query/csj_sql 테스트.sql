SELECT
    C.NAME     AS "챕터명",
    C.LENGTH   AS "강의길이",
    C.REGDATE  AS "등록일",
    C.DOC      AS "자료",
    C.VIDEO    AS "영상링크",
    M.PROGRESS AS "진행률",
    M.STATE    AS "학습상태"
FROM
    CHAPTER C
JOIN
    MY_CHAPTER M ON C.CHPTR_ID = M.CHPTR_ID
WHERE
    M.USER_ID = 'user1'
ORDER BY
    C.CHPTR_ID ASC; -- 챕터 순서대로 정렬

--유저, 강의로 강의 목록 조회.
		select
	        c.name, c.length, c.regdate, c.doc, c.video,
	        nvl(m.progress, 0) as progress,
	        nvl(m.state, 0) as state
	    from
	        chapter c
	    left join
	        my_chapter m on c.chptr_id = m.chptr_id
	        and m.user_id = 'user1'
	    where
	        c.lect_id = 'L1'
	    order by
	        c.num asc  ;

--	<!--메인화면용 강의리스트, 리뷰정보까지 조회-->

	    select
	        l.lect_id as lectId,
	        l.name,
	        l.inst_id as instId,
	        l.price,
	        l.usercount,
	        count(r.review_id) as reviewcount,
	        nvl(avg(r.score), 0) as avgscore
	    from lecture l
	    left outer join review r on l.lect_id = r.lect_id
	    where l.approval = 1  -- 승인된 강의만 출력
	    group by l.lect_id, l.name, l.inst_id, l.price, l.usercount;


select * from chapter
where lect_id='L1';

--Chapter 추가데이터
-- L1
--INSERT INTO CHAPTER (CHPTR_ID, NUM, NAME, LENGTH, DOC, DOCREGDATE, VIDEO, REGDATE, LECT_ID)
--VALUES ('CH' || SEQ_CHAPTER_ID.NEXTVAL,
--        (SELECT NVL(MAX(NUM), 0) + 1 FROM CHAPTER WHERE LECT_ID = 'L1'),
--        '자바 환경설정', 15, 'java_1.ppt', TO_DATE('2025-01-10','YYYY-MM-DD'), 'www.video_l1_1.com', SYSDATE, 'L1');
--
--INSERT INTO CHAPTER (CHPTR_ID, NUM, NAME, LENGTH, DOC, DOCREGDATE, VIDEO, REGDATE, LECT_ID)
--VALUES ('CH' || SEQ_CHAPTER_ID.NEXTVAL,
--        (SELECT NVL(MAX(NUM), 0) + 1 FROM CHAPTER WHERE LECT_ID = 'L1'),
--        '변수와 자료형', 20, 'java_2.ppt', TO_DATE('2025-01-11','YYYY-MM-DD'), 'www.video_l1_2.com', SYSDATE, 'L1');
--
--INSERT INTO CHAPTER (CHPTR_ID, NUM, NAME, LENGTH, DOC, DOCREGDATE, VIDEO, REGDATE, LECT_ID)
--VALUES ('CH' || SEQ_CHAPTER_ID.NEXTVAL,
--        (SELECT NVL(MAX(NUM), 0) + 1 FROM CHAPTER WHERE LECT_ID = 'L1'),
--        '제어문 마스터', 30, 'java_3.ppt', TO_DATE('2025-01-12','YYYY-MM-DD'), 'www.video_l1_3.com', SYSDATE, 'L1');
----L2
--INSERT INTO CHAPTER (CHPTR_ID, NUM, NAME, LENGTH, DOC, DOCREGDATE, VIDEO, REGDATE, LECT_ID)
--VALUES ('CH' || SEQ_CHAPTER_ID.NEXTVAL,
--        (SELECT NVL(MAX(NUM), 0) + 1 FROM CHAPTER WHERE LECT_ID = 'L2'),
--        '파이썬 기초 문법', 20, 'py_01.ppt', TO_DATE('2025-02-01','YYYY-MM-DD'), 'www.video_l2_1.com', SYSDATE, 'L2');
--
--INSERT INTO CHAPTER (CHPTR_ID, NUM, NAME, LENGTH, DOC, DOCREGDATE, VIDEO, REGDATE, LECT_ID)
--VALUES ('CH' || SEQ_CHAPTER_ID.NEXTVAL,
--        (SELECT NVL(MAX(NUM), 0) + 1 FROM CHAPTER WHERE LECT_ID = 'L2'),
--        '리스트와 튜플', 25, 'py_02.ppt', TO_DATE('2025-02-02','YYYY-MM-DD'), 'www.video_l2_2.com', SYSDATE, 'L2');
--
--INSERT INTO CHAPTER (CHPTR_ID, NUM, NAME, LENGTH, DOC, DOCREGDATE, VIDEO, REGDATE, LECT_ID)
--VALUES ('CH' || SEQ_CHAPTER_ID.NEXTVAL,
--        (SELECT NVL(MAX(NUM), 0) + 1 FROM CHAPTER WHERE LECT_ID = 'L2'),
--        '함수와 모듈', 30, 'py_03.ppt', TO_DATE('2025-02-03','YYYY-MM-DD'), 'www.video_l2_3.com', SYSDATE, 'L2');


/*
	사용처 : 동영상 시청용 데이터 조회
	입력값 : 학생id, LectId
	결과값 : 해당 lect에 대한 수강 진척 리스트.
*/
		select
	        m.user_id as stuId, c.chptr_id as chptrId, c.name, c.length as videoLength, c.video, c.num,
	        nvl(m.progtime, 0) as progtime,
			nvl(m.progress, 0) as progress,
	        nvl(m.state, 0) as state
	    from
	        chapter c
	    left join
	        my_chapter m on c.chptr_id = m.chptr_id
	        and m.user_id = 'user1'
	    where
	        c.lect_id = 'L1'
	    order by
	        c.num asc;

update chapter
	set  video='M-FtY9ksEUbvo'
	where chptr_id='CH14';


commit;

INSERT INTO MY_CHAPTER(MY_CHAPTER_ID, CHPTR_ID, USER_ID, LASTDATE, PROGRESS, PROGTIME, STATE)
 VALUES ('MC' || SEQ_MY_CHAPTER_ID.NEXTVAL, 'CH1', 'user1', SYSDATE, 50, 50, 0);

 select * from INSTRUCTOR;


 --출석체크 기능 테스트.
 select * from attendance
 order by ATTDATE;

 select * from attendance
 where attdate between to_date('2026-01-14') and SYSDATE
 order by ATTDATE;

 insert INTO ATTENDANCE (ATTEND_ID, USER_ID, ATTDATE)
 VALUES  (SEQ_ATTENDANCE_ID.NEXTVAL,'user1', SYSDATE );


 select * from CHAPTER
 where lect_id = 'L1';
 select * from MY_CHAPTER
 order by lastdate;
