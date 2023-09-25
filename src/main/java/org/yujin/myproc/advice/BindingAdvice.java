package org.yujin.myproc.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class BindingAdvice {

    @Before("execution(* org.yujin.myproc..*Controller.*(..))")
    public void testBefore() {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info("AOP Before :: request uri => " + request.getRequestURI());
    }

    @After("execution(* org.yujin.myproc..*Controller.*(..))")
    public void testAfter() {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        log.info("AOP After :: request uri => " + request.getRequestURI());
    }


    //Controller 의 메소드가 본 메소드를 aop 하려면
    //public ResponseEntity<?> joinProc(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) throws Exception {
    //와 같이 @Valid 와 BindingResult bindingResult 를 인자로 받아야 한다.
    //그래야 로그인 시에는 이것이 타고, 그외에는 JwtFilter 가 탄다.
    @Around("execution(* org.yujin.myproc..*Controller.*(..))")
    public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();

        log.info("AOP Around :: type => " + type);
        log.info("AOP Around :: method => " + method);

        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : args) {

            if(arg instanceof BindingResult) {

                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()) {

                    Map<String, String> errorMap = new HashMap<>();

                    for(FieldError error : bindingResult.getFieldErrors()) {

                        errorMap.put(error.getField(), error.getDefaultMessage());

                        log.warn(type+"."+method+"() => 필드 : "+error.getField()+", 메시지 : "+error.getDefaultMessage());
                        log.debug(type+"."+method+"() => 필드 : "+error.getField()+", 메시지 : "+error.getDefaultMessage());

                    }

                    return ResponseEntity.badRequest().body(errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
