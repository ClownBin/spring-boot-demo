package com.example.demo.annotation.aop;

import com.example.demo.annotation.aop.RoleCheck;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
* 支持spel表达式 注解适配
*/

@Aspect
@Component
@Slf4j
public class RoleCheckAop {

    /** 角色管理类，提供用户权限 校验接口 */
    @Autowired
    OneboxClusterRoleInfoService oneboxClusterRoleInfoService;

    private ExpressionParser parser = new SpelExpressionParser();

    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Pointcut("@annotation(com.example.demo.annotation.aop.RoleCheck)")
    public void clusterRoleCheckPointCut(){

    }

    @Around("clusterRoleCheckPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = ((MethodSignature)point.getSignature()).getMethod();
        RoleCheck clusterRoleCheck = method.getAnnotation(RoleCheck.class);

        Object[] args = point.getArgs();

        long creatorId = parse(clusterRoleCheck.creatorId(), method, args);
        long clusterId = parse(clusterRoleCheck.clusterId(), method, args);

        if (creatorId < 0){
            return new Result<>(ResultCode.ERROR.code(), "Invalid creator id.");
        }

        if (clusterId < 0){
            return new Result<>(ResultCode.ERROR.code(), "Invalid cluster id.");
        }

        if (!oneboxClusterRoleInfoService.checkCreatorUserRoleAdmin(clusterId, creatorId)){
            return new Result<>(ResultCode.ERROR.code(), "Current user have no auth to operate cluster role.");
        }

        Object result = point.proceed();

        return result;
    }

    /**
     * 解析spring EL表达式
     *
     */
    private Long parse(String key, Method method, Object[] args) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < params.length; i ++) {
            context.setVariable(params[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, Long.class);
    }
}
