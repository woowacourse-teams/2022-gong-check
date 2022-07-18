package com.woowacourse.gongcheck.application;

import com.woowacourse.gongcheck.exception.UnauthorizedException;
import com.woowacourse.gongcheck.presentation.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JjwtTokenProvider implements JwtTokenProvider {

    private static final String AUTHORITY = "authority";

    private final Key key;
    private final long expireTime;

    public JjwtTokenProvider(@Value("${security.jwt.token.secret-key}") final String secretKey,
                             @Value("${security.jwt.token.expire-time}") final long expireTime) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.expireTime = expireTime;
    }

    @Override
    public String createToken(final String subject, Authority authority) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim(AUTHORITY, authority)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String extractSubject(final String token) throws UnauthorizedException {
        return extractBody(token).getSubject();
    }

    @Override
    public Authority extractAuthority(final String token) throws UnauthorizedException {
        String authority = extractBody(token).get(AUTHORITY, String.class);
        return Authority.valueOf(authority);
    }

    private Claims extractBody(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("만료된 토큰입니다.");
        } catch (JwtException e) {
            throw new UnauthorizedException("올바르지 않은 토큰입니다.");
        }
    }
}
