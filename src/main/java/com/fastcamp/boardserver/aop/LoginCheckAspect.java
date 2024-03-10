package com.fastcamp.boardserver.aop;

import com.fastcamp.boardserver.aop.LoginCheck.UserType;
import com.fastcamp.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Log4j2
@Component
@Aspect
public class LoginCheckAspect {

    @Around("@annotation(com.fastcamp.boardserver.aop.LoginCheck) && @annotation(LoginCheck)")
    public Object adminLoginCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck)
        throws Throwable {
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest()
            .getSession();
        String accountId = null;
        int idIndex = 0;
        UserType type = loginCheck.type();

        switch (type){
            case ADMIN -> {
                accountId = SessionUtil.getLoginAdminId(session);
            }
            case USER -> {
                accountId = SessionUtil.getLoginMemberId(session);
            }
        }

        if(accountId == null){
            log.error(joinPoint.toString() + "계정을 찾지 못했습니다.");
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 ID 값을 확인해주세요.") {};
        }

        Object[] args = joinPoint.getArgs();
        args[idIndex] = accountId;

        return joinPoint.proceed(args);
    }

}
