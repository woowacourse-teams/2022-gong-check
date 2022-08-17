package com.woowacourse.gongcheck.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Host
    H001("Space 비밀번호와 입력받은 번호가 일치하지 않는 경우"),
    H002("entranceCode 가 유효하지 않은 코드인 경우"),
    H003("entranceCode 에서 hostId 추출 시 유효하지 않은 hostId인 경우"),
    H004("Host 조회 시, 입력 받은 id의 Host가 존재하지 않는 경우"),
    H005("Host 조회 시, 입력 받은 githubId의 Host가 존재하지 않는 경우"),

    // Job
    J001("Job 조회 시, 입력 받은 host, id에 해당하는 Job 이 존재하지 않는 경우"),
    J002("Job 조회 시, 입력 받은 id에 해당하는 Job 이 존재하지 않는 경우"),

    // SPACE
    SP01("공간 생성 시, 공간 이름이 이미 존재하는 경우"),
    SP02("공간 수정 시, 공간 이름이 이미 존재하는 경우"),
    SP03("비밀번호가 4자리로 이루어지지 않은 경우"),
    SP04("공간 조회 시, 입력 받은 host, id에 해당하는 공간이 존재하지 않는 경우"),

    // AUTHORITY
    A001("호스트 권한이 없는 토큰으로 호스트용 접근 경로로 접근할 경우"),
    A002("Guest 의 토큰이 만료된 경우"),
    A003("헤더에 토큰값이 정상적으로 존재하지 않을 경우"),

    // TASK
    T001("RunningTask 가 이미 존재하는데 또 생성하려는 경우"),
    T002("RunningTask 생성할 Task 가 존재하지 않는 경우"),
    T003("Task 조회 시, 입력 받은 host, id에 해당하는 Task 가 존재하지 않는 경우"),

    // RUNNING_TASK
    R001("RunningTask 조회 시, RunningTask 가 아직 생성되지 않은 경우"),
    R002("RunningTask 체크 시, RunningTask 가 존재하지 않는 경우"),
    R003("체크리스트가 다 체크되지 않았지만, 제출한 경우"),

    // SUBMISSION,
    S001("현재 제출할 수 있는 RunningTask 가 존재하지 않는데 제출한 경우"),
    S002("제출 시 제출자 이름이 공백인 경우"),
    S003("제출 시 제출자 이름이 10글자 이상인 경우"),

    // SECTION
    SE01("Sectoin 조회 시, Section이 존재하지 않는 경우"),

    // IMAGE
    IM01("이미지 업로드 시, 이미지 파일이 null 인 경우"),
    IM02("이미지 업로드 시, 이미지 파일이 빈값인 경우"),
    IM03("이미지 업로드 시, 이미지 파일 이름이 빈값인 경우"),
    IM04("이미지 업로드 시, 이미지 파일 확장자가 잘못된 경우"),
    IM05("이미지 업로드 시, 파일이 잘못된 경우"),

    // DESCRIPTION
    D001("설명 길이가 128자 이상인 경우"),

    // NAME
    N001("이름이 공백인 경우"),
    N002("이름이 10글자 이상인 경우"),

    // INFRASTRUCTURE
    I001("슬랙 메시지 전송에 실패한 경우"),
    I002("해싱용 시크릿키가 32자 미만인 경우"),
    I003("해싱 인코딩 실패할 경우"),
    I004("해싱 디코딩 실패한 경우"),
    I005("토큰이 값이 올바르지 않아 추출할 수 없는 경우"),
    I006("깃허브 엑세스 토큰이 null 인 경우"),
    I007("깃허브 사용자 프로필을 가져올 수 없는 경우"),

    // ERROR
    E001("예상치 못한 예외가 발생한 경우"),

    // DTO Valid Error
    V001("요청에 대한 DTO 필드값 일부가 null 인 경우");

    private final String description;

    ErrorCode(final String description) {
        this.description = description;
    }
}
