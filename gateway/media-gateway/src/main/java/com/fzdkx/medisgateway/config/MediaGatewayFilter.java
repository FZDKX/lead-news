package com.fzdkx.medisgateway.config;

import com.fzdkx.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @author 发着呆看星
 * @create 2024/2/4
 * 自定义网关全局过滤器
 */
@Order(0)
@Component
public class MediaGatewayFilter implements GlobalFilter {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private final String MEDIA_TOKEN_PREFIX = "token:media:";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求与响应
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        // 判断是否是登录请求
        // 如果是登录请求，放行
        if (request.getURI().getPath().contains("/login")) {
            return chain.filter(exchange);
        }
        // 如果不是，则获取Token
        String token = request.getHeaders().getFirst("token");
        // 如果Token不存在
        if (!StringUtils.hasLength(token)) {
            // 返回响应码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 处理结束
            return response.setComplete();
        }
        // 判断Token是否有效
        Boolean flag = redisTemplate.hasKey(MEDIA_TOKEN_PREFIX + token);
        // Token无效
        if (Boolean.FALSE.equals(flag)) {
            // 返回响应码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 处理结束
            return response.setComplete();
        }
        // Token有效，尝试Token续签
        renew(token);
        // 将用户ID保存至请求头中
        String userId = tokenUtils.getUserId(token);
//        HttpHeaders headers = request.getHeaders();
//        headers.add("userId",userId);
        ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> {
            httpHeaders.add("userId", userId);
        }).build();
        exchange.mutate().request(serverHttpRequest);
        // 放行
        return chain.filter(exchange);
    }

    /**
     * Token续签
     */
    public void renew(String token) {
        Long expire = redisTemplate.getExpire(MEDIA_TOKEN_PREFIX + token, TimeUnit.HOURS);
        // 如果Token过期，但Redis中依然保存，那么就续签
        if (expire < tokenUtils.getTIME_OUT()) {
            redisTemplate.expire(MEDIA_TOKEN_PREFIX + token, tokenUtils.getTIME_OUT() * 2, TimeUnit.HOURS);
        }
    }
}
