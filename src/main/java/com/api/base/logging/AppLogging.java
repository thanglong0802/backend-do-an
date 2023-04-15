/**
 * 
 */
package com.api.base.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.api.base.utils.enumerate.MessageCode;

/**
 * @author BacDV
 *
 */
@Aspect
@Component
public class AppLogging {
    private static final Logger logger = LoggerFactory.getLogger(AppLogging.class);

    @Autowired
    private HttpServletRequest req;

    @Autowired
    private HttpServletResponse res;

    @Autowired
    private MessageSource messageSource;

    @Pointcut("within(com.api.base.controller..*)")
    public void controllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are
        // in the
        // advices.
    }

    @Pointcut("within(@org.springframework.stereotype.Repository *)"
            + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are
        // in the
        // advices.
    }

    @Around("controllerPointcut()")
    public Object incomingRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        String requestUri = req.getRequestURI();
        String method = req.getMethod();

        long executeTime = System.currentTimeMillis() - startTime;
        Object[] params = { method, res.getStatus(), requestUri, executeTime + "(ms)" };

        logger.info(messageSource.getMessage(MessageCode.LOG_W001.name(), params, LocaleContextHolder.getLocale()));

        return result;
    }

    @AfterThrowing(pointcut = "controllerPointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        String requestUri = req.getRequestURI();
        String method = req.getMethod();

        Object[] params = { method, HttpStatus.BAD_REQUEST.value(), requestUri};
        logger.info(messageSource.getMessage(MessageCode.LOG_W002.name(), params, LocaleContextHolder.getLocale()));
    }
}
