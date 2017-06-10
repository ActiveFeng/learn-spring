package learn.spring.aop.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启动AspectJ 自动代理
 * 
 * @author ActiveFeng
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

	/**
	 * 配置bean loginAspect
	 * 
	 * @return LoginAspect
	 */
	@Bean
	public LoginAspect loginAspect() {
		return new LoginAspect();
	}

}
