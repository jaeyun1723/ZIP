package com.ssafy.home.util;

import com.ssafy.home.exception.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JWTUtil {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.access-token.expiretime}")
    private long accessTokenExpireTime;

    @Value("${jwt.refresh-token.expiretime}")
    private long refreshTokenExpireTime;

    //	Token 발급
    //		key : Claim에 셋팅될 key 값
    //		value : Claim에 셋팅 될 data 값
    //		subject : payload에 sub의 value로 들어갈 subject값
    //		expire : 토큰 유효기간 설정을 위한 값
    //		jwt 토큰의 구성 : header + payload + signature

    public String createAccessToken(String memberId) {
        return create(memberId, "access-token", accessTokenExpireTime);
    }

    //	AccessToken에 비해 유효기간을 길게 설정
    public String createRefreshToken(String memberId) {
        return create(memberId, "refresh-token", refreshTokenExpireTime);
    }

    public String create(String memberId, String subject, long expireTime) {
//		Payload 설정 : 생성일 (IssuedAt), 유효기간 (Expiration),
//		토큰 제목 (Subject), 데이터 (Claim) 등 정보 세팅.
        Claims claims = Jwts.claims()
                .setSubject(subject)    // 토큰 제목 설정
                .setIssuedAt(new Date()) // 생성일 설정
                .setExpiration(new Date(System.currentTimeMillis() + expireTime)); // 만료일 설정
        claims.put("memberId", memberId);

        String jwt = Jwts.builder()
                .setHeaderParam("type", "JWT").setClaims(claims) // Header 설정 : 토큰 타입, 해쉬 알고리즘 정보
                .signWith(SignatureAlgorithm.HS256, this.generateKey()) // Signature 설정 : secret key 활용한 암호화.
                .compact(); // 직렬화

        return jwt;
    }

    //	Signature 설정에 들어갈 key 생성
    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = salt.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (log.isInfoEnabled()) {
                e.printStackTrace();
            } else {
                log.error("Making JWT Key Error :: ", e.getMessage());
            }
        }
        return key;
    }

    //	전달 받은 토큰이 제대로 생성된 것인지 확인
    //	문제가 있다면 UnauthorizedException
    public boolean checkToken(String token) {
        try {
            if (token.startsWith("Bearer "))
                token = token.substring("Bearer ".length());
            else throw new UnAuthorizedException();
            // Json Web Signature? 서버에서 인증을 근거로 인증정보를 서버의 private key로 서명 한것을 토큰화 한것
            // setSigningKey : JWS 서명 검증을 위한  secret key 세팅
            // parseClaimsJws : 파싱하여 원본 jws 만들기
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey())
                    .parseClaimsJws(token);
            // Claims 는 Map의 구현체 형태
            log.debug("claims: {}", claims);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public String getMemberId(String authorization) {
        Jws<Claims> claims = null;
        if (authorization.startsWith("Bearer "))
            authorization = authorization.substring("Bearer ".length());
        try {
            claims = Jwts.parser().setSigningKey(this.generateKey())
                    .parseClaimsJws(authorization);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new UnAuthorizedException();
        }
        Map<String, Object> value = claims.getBody();
        log.info("value: {}", value);
        return (String) value.get("memberId");
    }
}
