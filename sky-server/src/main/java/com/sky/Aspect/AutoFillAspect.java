package com.sky.Aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //切入点(mapper下的所以类的所以方法 同时 注解为AutoFill)
    @Pointcut("@annotation(com.sky.annotation.AutoFill)")  // &&  execution(* com.sky.mapper.*.*(..))
    public void autoJoinPoint(){}

    //前置通知：在通知中进行公共字段的赋值
    @Before("autoJoinPoint()")
    public void autoFill(JoinPoint joinPoint){  // joinPoint能够获取目标方法的信息
        log.info("开始公共字段自动填充...");

        //获取到当前被拦截的方法上的数据库操作类型

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();  //获得数据库操作类型

        //获取到当前被拦截的方法的参数-实体参数
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }

        Object entity = args[0];  //约定第一个参数为实体对象

        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currenId = BaseContext.getCurrentId();

        //根据当前不同的操作类型，为对应的属性赋值
        if(operationType == OperationType.INSERT){
            //为4个公共属性赋值
            try {
                //需要指定参数类型
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class );
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //通过反射为对象属性赋值
                setCreateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currenId);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currenId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(operationType == OperationType.UPDATE){
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //通过反射为对象属性赋值
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currenId);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
