package learn.spring.aop.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import learn.spring.aop.aspect.annotation.AroundCollectLog;

@RestController
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password)
			throws Exception {
		System.out.println(username + "," + password);

		if (username.equals("feng") && password.equals("123")) {
			return "success";
		} else {
			throw new Exception("账号密码错误！");
		}
	}

	@GetMapping("/add")
	@AroundCollectLog(operationType = "add", operationName = "新增用户")
	public String add(@RequestParam("username") String username, HttpServletRequest request) throws Exception {
		System.out.println(username);

		if (username.equals("feng")) {
			return "success";
		} else {
			throw new Exception("用户错误！");
		}
	}

}
