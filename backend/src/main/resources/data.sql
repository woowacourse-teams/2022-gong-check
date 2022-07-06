INSERT INTO member (space_password, created_at) VALUES ('1234', current_timestamp());
INSERT INTO space (member_id, name, created_at) VALUES (1, '잠실', current_timestamp());
INSERT INTO job (space_id, name, created_at) VALUES (1, '청소', current_timestamp());
INSERT INTO section (job_id, name, created_at) VALUES (1, '트랙룸', current_timestamp());
INSERT INTO task (section_id, name, created_at) VALUES (1, '책상 청소', current_timestamp());
INSERT INTO task (section_id, name, created_at) VALUES (1, '빈백 털기', current_timestamp());
INSERT INTO section (job_id, name, created_at) VALUES (1, '굿샷 강의장', current_timestamp());
INSERT INTO task (section_id, name, created_at) VALUES (2, '책상 청소', current_timestamp());
INSERT INTO task (section_id, name, created_at) VALUES (2, '의자 청소', current_timestamp());
