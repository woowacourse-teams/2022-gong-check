DROP TABLE host IF EXISTS;
DROP TABLE space IF EXISTS;
DROP TABLE job IF EXISTS;
DROP TABLE running_task IF EXISTS;
DROP TABLE task IF EXISTS;
DROP TABLE section IF EXISTS;
DROP TABLE submission IF EXISTS;

CREATE TABLE host
(
    id             BIGINT     NOT NULL AUTO_INCREMENT,
    space_password VARCHAR(4) NOT NULL,
    github_id      BIGINT     NOT NULL UNIQUE,
    image_url      VARCHAR    NOT NULL,
    created_at     TIMESTAMP  NOT NULL,
    updated_at     TIMESTAMP NULL,
    PRIMARY KEY (id)
);

CREATE TABLE space
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    host_id    BIGINT      NOT NULL,
    name       VARCHAR(20) NOT NULL,
    img_url    VARCHAR NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP NULL,
    PRIMARY KEY (id)
);

CREATE TABLE job
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    space_id   BIGINT      NOT NULL,
    name       VARCHAR(20) NOT NULL,
    slack_url  VARCHAR NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP NULL,
    PRIMARY KEY (id)
);

CREATE TABLE task
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    section_id BIGINT      NOT NULL,
    name       VARCHAR(50) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP NULL,
    PRIMARY KEY (id)
);

CREATE TABLE section
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    job_id     BIGINT      NOT NULL,
    name       VARCHAR(20) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP NULL,
    PRIMARY KEY (id)
);

CREATE TABLE running_task
(
    task_id    BIGINT    NOT NULL,
    is_checked BOOLEAN   NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (task_id),
    FOREIGN KEY (task_id) REFERENCES task (id)
);

CREATE TABLE submission
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    job_id     BIGINT      NOT NULL,
    author     VARCHAR(20) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    PRIMARY KEY (id)
);
