INSERT INTO host (space_password, created_at)
VALUES ('1234', current_timestamp());

INSERT INTO space (host_id, name, img_url, created_at)
VALUES (1, '잠실', 'https://velog.velcdn.com/images/cks3066/post/258f92c1-32be-4acb-be30-1eb64635c013/image.jpg',
        current_timestamp());
INSERT INTO space (host_id, name, img_url, created_at)
VALUES (1, '선릉', 'https://velog.velcdn.com/images/cks3066/post/28a9d0e5-d585-42e4-bc9e-458e439e2f4f/image.jpg',
        current_timestamp());

INSERT INTO job (space_id, name, created_at)
VALUES (1, '청소', current_timestamp());
INSERT INTO job (space_id, name, created_at)
VALUES (1, '마감', current_timestamp());
INSERT INTO job (space_id, name, created_at)
VALUES (2, '청소', current_timestamp());
INSERT INTO job (space_id, name, created_at)
VALUES (2, '마감', current_timestamp());

INSERT INTO section (job_id, name, created_at)
VALUES (1, '트랙룸', current_timestamp());
INSERT INTO section (job_id, name, created_at)
VALUES (1, '굿샷 강의장', current_timestamp());
INSERT INTO section (job_id, name, created_at)
VALUES (1, '톱오브스윙방', current_timestamp());

INSERT INTO task (section_id, name, created_at)
VALUES (1, '칠판 닦기', current_timestamp());
INSERT INTO task (section_id, name, created_at)
VALUES (1, '빈백 털기', current_timestamp());
INSERT INTO task (section_id, name, created_at)
VALUES (1, '책상 닦기', current_timestamp());

INSERT INTO task (section_id, name, created_at)
VALUES (2, '칠판 닦기', current_timestamp());
INSERT INTO task (section_id, name, created_at)
VALUES (2, '책상 닦기', current_timestamp());
INSERT INTO task (section_id, name, created_at)
VALUES (2, '의자 닦기', current_timestamp());

INSERT INTO task (section_id, name, created_at)
VALUES (3, '칠판 닦기', current_timestamp());
INSERT INTO task (section_id, name, created_at)
VALUES (3, '책상 닦기', current_timestamp());
INSERT INTO task (section_id, name, created_at)
VALUES (3, '의자 닦기', current_timestamp());
