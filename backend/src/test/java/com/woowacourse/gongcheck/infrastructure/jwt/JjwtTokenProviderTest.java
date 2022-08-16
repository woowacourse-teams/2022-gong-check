package com.woowacourse.gongcheck.infrastructure.jwt;

import static com.woowacourse.gongcheck.auth.domain.Authority.GUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongcheck.exception.InfrastructureException;
import com.woowacourse.gongcheck.exception.UnauthorizedException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.junit.jupiter.api.Test;

class JjwtTokenProviderTest {

    private final String key = "Z29uZy1jaGVjay1nb25nLWNoZWNrLWdvbmctY2hlY2stZ29uZy1jaGVjay1nb25nLWNoZWNrLWdvbmctY2hlY2stZ29uZy1jaGVjay1nb25nLWNoZWNrCg==";
    private final JjwtTokenProvider tokenProvider = new JjwtTokenProvider(key, 360000);

    @Test
    void 토큰을_생성한다() {
        String token = tokenProvider.createToken("1", GUEST);

        assertThat(token).isNotNull();
    }

    @Test
    void 토큰이_만료되면_예외가_발생한다() {
        String expiredToken = Jwts.builder()
                .setSubject("1")
                .setExpiration(new Date(new Date().getTime() - 1))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(key)), SignatureAlgorithm.HS256)
                .compact();

        assertThatThrownBy(() -> tokenProvider.extractSubject(expiredToken))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("만료된 토큰입니다.");
    }

    @Test
    void 올바르지_않은_토큰이면_예외가_발생한다() {
        String invalidToken = "invalidToken";

        assertThatThrownBy(() -> tokenProvider.extractSubject(invalidToken))
                .isInstanceOf(InfrastructureException.class)
                .hasMessageContaining("올바르지 않은 토큰입니다.");
    }

    @Test
    void 식별값을_반환한다() {
        String subject = "1";
        String token = Jwts.builder()
                .setSubject(subject)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(key)), SignatureAlgorithm.HS256)
                .compact();

        assertThat(tokenProvider.extractSubject(token)).isEqualTo(subject);
    }

    @Test
    void 권한을_반환한다() {
        String token = Jwts.builder()
                .setSubject("1")
                .claim("authority", GUEST)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(key)), SignatureAlgorithm.HS256)
                .compact();

        assertThat(tokenProvider.extractAuthority(token)).isEqualTo(GUEST);
    }

    @Test
    void 권한_반환에_실패하면_예외가_발생한다() {
        String invalidToken = "invalidToken";

        assertThatThrownBy(() -> tokenProvider.extractAuthority(invalidToken))
                .isInstanceOf(InfrastructureException.class)
                .hasMessageContaining("올바르지 않은 토큰입니다.");
    }
}
