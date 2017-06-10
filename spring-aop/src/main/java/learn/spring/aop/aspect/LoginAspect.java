package learn.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.alibaba.fastjson.JSON;

/**
 * 测试登录接口配置 切点
 * 
 * @author ActiveFeng
 *
 */
@Aspect
public class LoginAspect {

	/**
	 * 配置切点 * learn.spring.aop.controller.LoginController.login(..)
	 * 	说明  * 返回值任意类型   learn.spring.aop.controller包名
	 * 	LoginController 类名  login(..) 方法  .. 使用任意参数
	 * 
	 *  也可以配置正则使用
	 *  * learn.spring.aop.controller.LoginController.*(..)
	 *  表示所有方法
	 *  
	 */
	@Pointcut("execution(* learn.spring.aop.controller.LoginController.login(..))")
	public void CollectOperateLog() {
	}

	@Before("CollectOperateLog()")
	public void Before(JoinPoint joinPoint) {
		System.out.println("LoginAspect @Before 方法调用前。。。" + joinPoint.getSignature().getName());
	}

	@After("CollectOperateLog()")
	public void After(JoinPoint joinPoint) {
		System.out.println("LoginAspect @After 方法调用后。。。" + joinPoint.getSignature().getName());
	}

	@AfterReturning(value = "CollectOperateLog()", argNames = "result", returning = "result")
	public void AfterReturning(JoinPoint joinPoint, Object result) {
		System.out.println("LoginAspect AfterReturning 方法调用返回结果后。。。" + result);
	}

	
	@AfterThrowing(value = "CollectOperateLog()", throwing = "e")
	public void AfterThrowing(JoinPoint joinPoint, Throwable e) {
		System.out.println("LoginAspect @AfterThrowing 方法调用抛出异常后。。。" + e.getMessage());
	}

	/**
	 * Around 环绕包裹方法的整个过程 除了Around其他的都是是不可以修改目标方法的参数的
	 * 
	 * @param point
	 * @return  Object 连接点返回的参数往外返回
	 * @throws Throwable
	 */
	@Around("CollectOperateLog()")
	public Object Around(ProceedingJoinPoint point) throws Throwable {
		Object obj = null;
		Object[] objs = point.getArgs();

		System.out.println("LoginAspect @Arround 方法调用前" + JSON.toJSONString(objs));

		try {
			obj = point.proceed();
		} catch (Throwable e) {
			System.out.println("LoginAspect @Arround 方法调用失败 " + e.getMessage());
			throw e;
		}
		System.out.println("LoginAspect @Arround 方法调用后");
		
		return obj;
	}

}
