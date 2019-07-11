package cn.abc.def.controller;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.abc.def.entity.User;
import cn.abc.def.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Resource
	private IUserService userService;
	
	
	@RequestMapping("/toRegister.do")
	public String toRegister() {
		return "register";
	}
	
	@RequestMapping("/toLogin.do")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/toUserlist.do")
	public String toUserlist(ModelMap modelMap) {
		List<User> users = userService.findAllUser();
		modelMap.addAttribute("users", users);
		return "userlist";
	}
	
	@RequestMapping(value="/register.do", method=RequestMethod.POST)
	public String register(User user, ModelMap modelMap) {
		try {
			userService.register(user);
			modelMap.addAttribute("message", "注册成功!");
			return "message";
		} catch (RuntimeException e) {
			modelMap.addAttribute("message", e.getMessage());
			return "message";
		}
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(String username, String password, HttpSession session, ModelMap modelMap) {
		try {
			User user = userService.login(username, password);
			session.setAttribute("user", user);
			return "redirect:toUserlist.do";
		} catch (RuntimeException e) {
			modelMap.addAttribute("message", e.getMessage());
			return "message";
		}
		
	}
}
