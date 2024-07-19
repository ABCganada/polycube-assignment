package kr.co.polycube.backendtest.log;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class Logging {

    @Pointcut("execution(* kr.co.polycube.backendtest..controller..*(..))")
    private void allRequest() {}

    @Pointcut("execution(* kr.co.polycube.backendtest.users.controller..*.*(..))")
    private void userRequest() {}

    @Before("userRequest()")
    public void logUserAgent(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String userAgent = request.getHeader("User-Agent");
            System.out.println("======= Method: " + joinPoint.getSignature().getName() + " =======");
            System.out.println("Client Agent: " + userAgent);
        }
    }

    @Before("allRequest()")
    public void logUrl() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String url = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
            System.out.println(">> Request URL : " + url);
        }
    }
}
