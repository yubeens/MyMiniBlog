package org.jyb.backend.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component // 스프링 빈으로 등록해 다른 곳에서 @Autowired 등으로 사용할 수 있게 함.
public class JwtTokenProvider {

    @Value("${jwt.secret-key}") // properties 값을 주입받음.
    private String secretKey;

    private final long expiration = 1000 * 60 * 60 * 24; // 토큰 유효 시간 설정 (24시간)

    // 1. 토큰 생성
    // - username 을 기반으로 JWT 토큰을 생성
    // - now 는 현재시간, expiryDate 는 만료시간
    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username) // : 토큰 안에 들어갈 사용자 식별 값 설정
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey) // : 비밀 키와 알고리즘으로 서명하여 위조 방지
                .compact(); // : 최종적으로 JWT 문자열을 반환
    }

    // 2. 토큰에서 유저명 추출 - JWT 토큰을 복호화하여 안에 있는 "username"을 꺼내는 기능
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token) // : 토큰의 유효성도 함께 확인하며, 내부 정보도 추출
                .getBody()
                .getSubject();
    }

    // 3. 토큰 유효성 검사 - 토큰이 유효한지(변조,만료) 검사
    // 파싱이 실패하거나 예외가 발생하면 false 반환
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
