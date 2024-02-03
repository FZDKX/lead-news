package com.fzdkx.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 * 自定义JWT工具类
 */
@Component
public class JWTUtils {

    // TOKEN密钥
    @Value("${token.secretKey}")
    private String TOKEN_SECRET_KEY = "71077C2DA03D669EF10B78C351849A11";

    // 过期时间(单位小时) ，默认为2小时
    @Value("${token.timeOut}")
    private Integer TIME_OUT = 2;

    // 校验Token
    private final JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET_KEY)).build();

    public String getToken(Long id) {
        // 获取当前时间
        Date now = new Date();
        // 获取过期时间
        // 1s == 1000ms
        int MS = 1000;
        // 时间基本单位 小时
        Long BASIC_TIME = (long) (60 * 60 * 1000);
        Date expire = new Date(now.getTime() + BASIC_TIME * TIME_OUT);

        // 创建JWT头信息
        HashMap<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("typ", "JWT");
        headerClaims.put("alg", "HS256");

        // 创建JWT
        return JWT.create()
                // 设置头信息
                .withHeader(headerClaims)
                // 设置签发时间
                .withIssuedAt(now)
                // 设置过期时间
                .withExpiresAt(expire)
                // 设置签发人
                .withIssuer("fzdkx")
                // 自定义信息，可以获取，userID
                .withClaim("userId", id)
                // 自定义信息，可以获取，tokenID
                .withClaim("tokenId", UUID.randomUUID().toString())
                // 设置使用的算法和密钥
                .sign(Algorithm.HMAC256(TOKEN_SECRET_KEY));
    }

    public boolean verifyToken(String token) {
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            // 如果不报错，代表验签成功
            return true;
        } catch (JWTVerificationException e) {
            // 如果保存代表验签失败
            System.err.println("验签失败");
        }
        return false;
    }

    private Long getUserID(String token) {
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            // 不报错，验签成功
            return verify.getClaim("userId").asLong();
        } catch (JWTVerificationException e) {
            // 报错验签失败
            return null;
        }
    }
}
