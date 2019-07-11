package cn.abc.def.controller;

import javax.servlet.http.HttpSession;
import cn.abc.def.entity.User;

public class BaseController {
	
	public Integer getUid(HttpSession session) {
		Integer id = null;
		User user = (User) session.getAttribute("user");
		if (user != null) {
			id = user.getId();
		}
		return id;
	}
}
