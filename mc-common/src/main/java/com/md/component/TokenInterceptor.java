package com.md.component;

import cn.hutool.core.util.StrUtil;
import com.md.exception.TokenExpiredException;
import com.md.exception.TokenExpiredSoonException;
import com.md.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/** @author  */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) {
        // 若目标方法不是Controller方法，则直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 若请求头中不存在Token令牌，则返回失效提示
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            throw new TokenExpiredException("Token令牌不存在");
        }
        // 解析Token令牌
        Map<String, Object> verifyResult = JwtUtil.parse(token);
        System.out.println("token解析结果："+verifyResult);
        // 若Token令牌即将过期，则返回过期提示以及一个新的Token令牌
        Object expiringSoon = verifyResult.get("expiringSoon");
        System.out.println(expiringSoon);
        if ((boolean) verifyResult.get("expiringSoon")) {
            throw new TokenExpiredSoonException((String) verifyResult.get("newToken"));
        }
        return true;
    }
}