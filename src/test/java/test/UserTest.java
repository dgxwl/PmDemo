package test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.abc.def.entity.PrivateMessage;
import cn.abc.def.entity.User;
import cn.abc.def.mapper.UserMapper;
import redis.clients.jedis.Jedis;

public class UserTest {
	@Test
	public void testRegister() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("spring-dao.xml");

		UserMapper userMapper = ac.getBean("userMapper", UserMapper.class);
		userMapper.register(new User(null, "test", "1234"));

		ac.close();
	}

	@Test
	public void testJedis() {
		Jedis jedis = new Jedis("localhost");
//		jedis.auth("password");
		jedis.set("Message", "Hello World!");
		String msg = jedis.get("Message");
		System.out.println(msg);
		jedis.close();
	}

	@Test
	public void testTemplete() {
		// 初始化Spring容器
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-redis.xml");
		StringRedisTemplate template = ctx.getBean("stringRedisTemplate", StringRedisTemplate.class);
		String text = (String)template.boundHashOps("pmList").get("1");
		PrivateMessage pm = null;
		try {
			pm = new ObjectMapper().readValue(text, PrivateMessage.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(pm);
	}
}
