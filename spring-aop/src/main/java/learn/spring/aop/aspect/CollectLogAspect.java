package learn.spring.aop.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import learn.spring.aop.aspect.annotation.AroundCollectLog;

/**
 * 
 * 使用Around 环绕型通知 方便监控整个过程
 * 
 * @author ActiveFeng
 *
 */
@Aspect
@Component
public class CollectLogAspect {

	/**
	 *
	 *  
	 */
	@Pointcut("@annotation(learn.spring.aop.aspect.annotation.AroundCollectLog)")
	public void CollectOperateLog() {
	}

	/**
	 * Around 环绕包裹方法的整个过程 这里一般拦截service层 可以捕获异常 做处理
	 * 
	 * @param point
	 * @return
	 * @throws Throwable
	 */
	@Around("CollectOperateLog()")
	public Object Around(ProceedingJoinPoint point) throws Throwable {
		Object obj = null;

		// 获取连接点位置的类的名字
		String targetName = point.getTarget().getClass().getName();

		// 获取连接点位置执行的方法名
		String methodName = point.getSignature().getName();

		// 获取方法入参
		Object[] arguments = point.getArgs();
		Class<?> targetClass = null;
		try {
			targetClass = Class.forName(targetName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Method[] methods = targetClass.getMethods();
		String operationType = "";
		String operationName = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				@SuppressWarnings("rawtypes")
				Class[] clazzs = method.getParameterTypes();
				if (clazzs != null && clazzs.length == arguments.length
						&& method.getAnnotation(AroundCollectLog.class) != null) {
					operationType = method.getAnnotation(AroundCollectLog.class).operationType();
					operationName = method.getAnnotation(AroundCollectLog.class).operationName();
					break;
				}
			}
		}

		System.out.println("类名：" + targetName + "，方法名：" + methodName + "，操作类型：" + operationType + "，操作名称："
				+ operationName + "，request：" + getHttpServletRequest());

		try {
			obj = point.proceed();
		} catch (Throwable e) {
			// TODO 这里可以做错误日志保存
			throw e;
		}
		// TODO 这里可以做操作记录保存

		return obj;
	}

	public HttpServletRequest getHttpServletRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		return request;
	}

}
