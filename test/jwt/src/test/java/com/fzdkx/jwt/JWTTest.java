package com.fzdkx.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author 发着呆看星
 * @create 2024/2/3
 */
public class JWTTest {
    // TOKEN密钥
    private String TOKEN_SECRET_KEY = "dhbidhiuwdnsuidhwjkdnisudnsjd";
    // 过期时间，默认为 2 小时
    private Integer TIME_OUT = 60 * 60 * 2;
    // 1000ms
    private Integer MS = 1000;
    // 校验Token
    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET_KEY)).build();

    public String getToken(Long id) {
        // 获取当前时间
        Date now = new Date();
        // 获取过期时间
        Date expire = new Date(now.getTime() + (long) MS * TIME_OUT);

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

    private Long getUserID(String token){
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            // 不报错，验签成功
            return verify.getClaim("userId").asLong();
        } catch (JWTVerificationException e) {
            // 报错验签失败
            return null;
        }

    }

    // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDY5NDA4MTcsImV4cCI6MTcwNjk0ODAxNywiaXNzIjoiZnpka3giLCJ1c2VySWQiOjEsInRva2VuSWQiOiJlYTY3YTA0MS05YjBlLTQ0YmYtODUzNi0zMTQzNTNmNzFmYjcifQ.WpjF3f9zMB2CO4QbSv2cftzJnrTO4OS9ydsKiPsGxJ0
    // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDY5NDA3OTYsImV4cCI6MTcwNjk0Nzk5NiwiaXNzIjoiZnpka3giLCJ1c2VySWQiOjEsInRva2VuSWQiOiIwMmQ0MmYzZi0xYjRlLTQ3NGMtOGU0NC0wZjAzMjQ4ZTMzYWYifQ.HVYqdvKrqGw4vYg_3aQiFVh-GIm_LjK7qJX-hfFRnj8
    @Test
    public void testGetToken() {
        String token = getToken(1L);
        System.out.println(token);
    }


    @Test
    public void testVerifyToken(){
        boolean b = verifyToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDY5NDA4MTcsImV4cCI6MTcwNjk0ODAxNywiaXNzIjoiZnpka3giLCJ1c2VySWQiOjEsInRva2VuSWQiOiJlYTY3YTA0MS05YjBlLTQ0YmYtODUzNi0zMTQzNTNmNzFmYjcifQ.WpjF3f9zMB2CO4QbSv2cftzJnrTO4OS9ydsKiPsGxJ0");
        System.out.println(b ? "验签成功" : "验签失败");
    }

    @Test
    public void testGetId(){
        Long id = getUserID("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDY5NDA4MTcsImV4cCI6MTcwNjk0ODAxNywiaXNzIjoiZnpka3giLCJ1c2VySWQiOjEsInRva2VuSWQiOiJlYTY3YTA0MS05YjBlLTQ0YmYtODUzNi0zMTQzNTNmNzFmYjcifQ.WpjF3f9zMB2CO4QbSv2cftzJnrTO4OS9ydsKiPsGxJ0");
        System.out.println(id != null ? id : "验签失败");
    }
}
