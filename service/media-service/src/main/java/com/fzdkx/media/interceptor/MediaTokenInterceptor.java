package com.fzdkx.media.interceptor;

import com.fzdkx.model.media.bean.MediaUser;
import com.fzdkx.utils.MediaThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author 发着呆看星
 * @create 2024/2/6
 */
@Slf4j

public class MediaTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String userId = request.getHeader("userId");
        if (StringUtils.hasLength(userId)){
            MediaUser user = new MediaUser();
            user.setId(Long.valueOf(userId));
            MediaThreadLocalUtil.setUser(user);
            log.info("用户信息已设置在ThreadLocal中，id：{}",userId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("正在请求ThreadLocal中的用户信息");
        MediaThreadLocalUtil.clear();
    }
}
