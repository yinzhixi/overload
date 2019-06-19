package com.jm.web.log;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jm.bean.LogEntity;
import com.jm.service.LogService;
import com.jm.util.RandomTools;
@Aspect
@Configuration
public class LogAopAction {
    private Logger logger = LoggerFactory.getLogger(LogAopAction.class);
    
    //注入service,用来将日志信息保存在数据库
    @Autowired
    private LogService logService;
    
     //配置接入点,如果不知道怎么配置,可以百度一下规则
     @Pointcut("execution(* com.jm.controller..*.*(..))")  
     public void controllerAspect(){}//定义一个切入点
 
     @Around("controllerAspect()")
     public Object around(ProceedingJoinPoint pjp){
         long start = System.currentTimeMillis();
        // 拦截的实体类，就是当前正在执行的controller
        Object target = pjp.getTarget();
        // 拦截的方法名称。当前正在执行的方法
        String methodName = pjp.getSignature().getName();
        // 拦截的方法参数
        Object[] args = pjp.getArgs();
        // 拦截的放参数类型
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
//            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();
        
        Object object = null;
        // 获得被拦截的方法
        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (SecurityException e1) {
            e1.printStackTrace();
        }
        if (null != method) {
            if (method.isAnnotationPresent(SystemLog.class)) {
                SystemLog systemlog = method.getAnnotation(SystemLog.class);
                LogEntity log = newLogEntity();
                log.setModule(systemlog.module());
                log.setMethod(systemlog.methods());
                try {
                    object = pjp.proceed();
                    long end = System.currentTimeMillis();
                    //将计算好的时间保存在实体中
                    log.setUseTime(""+(end-start));
                    log.setDescription("执行成功！");
                    //保存进数据库
                    logService.saveLog(log);
                } catch (Throwable e) {
                    e.printStackTrace();
                    long end = System.currentTimeMillis();
                    log.setUseTime(""+(end-start));
                    log.setDescription("执行失败");
                    logService.saveLog(log);
                }
            } else {//没有包含注解
                try {
                    object = pjp.proceed();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        } else { //不需要拦截直接执行
            try {
                object = pjp.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return object;
     }
     
     private LogEntity newLogEntity() {
         HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
         //常见日志实体对象
         LogEntity log = new LogEntity();
         log.setId(RandomTools.randomStringUpper());
         //获取登录用户账户
         Subject subject = SecurityUtils.getSubject();
         if(subject != null) {
             String userId = (String)subject.getPrincipal();
             log.setUserId(userId);
         }
         //获取系统时间
         String time = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
         log.setReqTime(time);
         
         //请求参数
         String ip = request.getRemoteAddr();
         String url = request.getRequestURL().toString();
         String param = request.getQueryString();
         String host = request.getRemoteHost();
         int port = request.getRemotePort();
         String userAgent = request.getHeader("User-Agent");
         
         log.setIp(ip);
         log.setUrl(StringUtils.substring(url, 0, 128));
         log.setParam(StringUtils.substring(param, 0, 512));
         log.setHost(host);
         log.setPort(port);
         log.setUserAgent(StringUtils.substring(userAgent, 0, 512));
         return log;
     }
}