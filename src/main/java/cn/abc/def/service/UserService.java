package cn.abc.def.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.abc.def.entity.User;
import cn.abc.def.mapper.UserMapper;

@Service
public class UserService implements IUserService {

	@Resource
	private UserMapper userMapper;
	
	public Integer register(User user) {
		User u = userMapper.findUserByUsername(user.getUsername());
		if (u == null) {
			return userMapper.register(user);
		} else {
			throw new RuntimeException("注册失败：该用户已存在");
		}
	}
	
	public User login(String username, String password) {
		User user = userMapper.findUserByUsername(username);
		if (user == null) {
			throw new RuntimeException("用户名错误");
		}
		if (user.getPassword().equals(password)) {
			return user;
		}
		throw new RuntimeException("密码错误");
	}

	public List<User> findAllUser() {
		return userMapper.findAllUser();
	}
}
