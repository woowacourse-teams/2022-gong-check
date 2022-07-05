package com.woowacourse.gongcheck.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;


@ExtendWith(MockitoExtension.class)
class AuthorizationTokenExtractorTest {

    private final HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);

    @Test
    void Authorization_헤더가_비어있으면_빈_값을_반환한다() {
        when(httpRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        assertThat(AuthorizationTokenExtractor.extractToken(httpRequest)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "jwt.token.here", "Bearer ", "Digest jwt.token.here"})
    void 토큰이_지정된_형식이_아닌_경우_빈_값을_반환한다(final String header) {
        when(httpRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(header);
        assertThat(AuthorizationTokenExtractor.extractToken(httpRequest)).isEmpty();
    }

    @Test
    void 토큰을_정상적으로_추출한다() {
        when(httpRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer jwt.token.here");
        assertThat(AuthorizationTokenExtractor.extractToken(httpRequest)).isEqualTo(Optional.of("jwt.token.here"));
    }
}
