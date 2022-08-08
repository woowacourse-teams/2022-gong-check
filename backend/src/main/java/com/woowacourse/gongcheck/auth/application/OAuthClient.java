package com.woowacourse.gongcheck.auth.application;

import com.woowacourse.gongcheck.auth.application.response.SocialProfileResponse;

public interface OAuthClient {
    SocialProfileResponse requestSocialProfileByCode(String code);
}
