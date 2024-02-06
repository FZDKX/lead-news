package com.fzdkx.jwt;

import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * @author 发着呆看星
 * @create 2024/2/6
 */

public class PasswordTest {
    @Test
    public void testPassword(){
        // 盐
        String salt = "123abc";
        // 加盐后加密的密码
        String pswd = DigestUtils.md5DigestAsHex(("admin" + salt).getBytes());
        System.out.println(pswd);
    }
}
