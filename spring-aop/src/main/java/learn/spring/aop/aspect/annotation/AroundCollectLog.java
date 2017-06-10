package learn.spring.aop.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AroundCollectLog {
	/** 要执行的操作类型比如：add操作 **/
	public String operationType() default "";

	/** 要执行的具体操作比如：添加用户 **/
	public String operationName() default "";

}
