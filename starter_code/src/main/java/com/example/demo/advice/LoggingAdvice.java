package com.example.demo.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class LoggingAdvice {
    Logger Log = LoggerFactory.getLogger(LoggingAdvice.class);

    /**
     * Following is the definition for a Pointcut to select
     * all the methods available. So advice will be called
     * for all the methods.
     */
    @Pointcut("execution(* com.example.demo.controllers.*.*(..))")
    public void appPointcut() {
    }

    /**
     * This is the method which I would like to execute
     * around a selected method execution.
     * 
     * @Around annotation has a point cut argument. Our pointcut just says, ‘Apply
     *         this advice any method which is annotated with"ApplicationLogger"
     */
    @Around("appPointcut()")
    public void applicationLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        Object[] array = joinPoint.getArgs();
        // Around Advice
        // before (Request)
        Log.info("Method Request invoked " + className + " : " + methodName + "() " + "arguments :"
                + mapper.writeValueAsString(array));

        Object object = joinPoint.proceed();
        // after (Response)
        Log.info(className + " : " + methodName + "() " + "Response :"
                + mapper.writeValueAsString(object));
    }

}
