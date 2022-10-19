DROP TABLE IF EXISTS host;
DROP TABLE IF EXISTS space;
DROP TABLE IF EXISTS job;
DROP TABLE IF EXISTS running_task;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS section;
DROP TABLE IF EXISTS submission;

CREATE TABLE host
(
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    space_password VARCHAR(4)   NOT NULL,
    github_id      BIGINT       NOT NULL UNIQUE,
    image_url      VARCHAR(255) NOT NULL,
    created_at     TIMESTAMP    NOT NULL,
    updated_at     TIMESTAMP    NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE space
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    host_id    BIGINT       NOT NULL,
    name       VARCHAR(10)  NOT NULL,
    img_url    VARCHAR(255) NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE job
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    space_id   BIGINT       NOT NULL,
    name       VARCHAR(10)  NOT NULL,
    slack_url  VARCHAR(255) NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE section
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    job_id      BIGINT       NOT NULL,
    name        VARCHAR(10)  NOT NULL,
    description VARCHAR(128) NULL,
    image_url   VARCHAR(255) NULL,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE task
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    section_id  BIGINT       NOT NULL,
    name        VARCHAR(10)  NOT NULL,
    description VARCHAR(128) NULL,
    image_url   VARCHAR(255) NULL,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE running_task
(
    task_id    BIGINT    NOT NULL,
    is_checked BOOLEAN   NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (task_id),
    FOREIGN KEY (task_id) REFERENCES task (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE submission
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    job_id     BIGINT      NOT NULL,
    author     VARCHAR(10) NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
