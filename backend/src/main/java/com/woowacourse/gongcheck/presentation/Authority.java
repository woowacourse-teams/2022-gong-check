package com.woowacourse.gongcheck.presentation;

public enum Authority {
    GUEST, HOST;

    public boolean isHost() {
        return this == HOST;
    }
}
