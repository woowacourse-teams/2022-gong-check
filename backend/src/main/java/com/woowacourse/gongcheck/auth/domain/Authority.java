package com.woowacourse.gongcheck.auth.domain;

public enum Authority {
    GUEST, HOST;

    public boolean isHost() {
        return this == HOST;
    }
}
