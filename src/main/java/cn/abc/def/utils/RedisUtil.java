package cn.abc.def.utils;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RedisUtil {

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	public void hset(String key, String field, Object o) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			stringRedisTemplate.boundHashOps(key).put(field, objectMapper.writeValueAsString(o));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public String hget(String key, String field) {
		BoundHashOperations<String, String, String> bho = stringRedisTemplate.boundHashOps(key);
		if (bho == null)
			return null;
		else
			return (String) bho.get(field);
	}

	public <T> T hget(String key, String field, Class<T> clazz) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String text = hget(key, field);
			if (text == null) {
				return null;
			}
			T result = objectMapper.readValue(text, clazz);
			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void hdel(String key, String... field) {
		stringRedisTemplate.boundHashOps(key).delete(field);
	}

	public void del(String key) {
		stringRedisTemplate.delete(key);
	}
	
	public void clearRedis() {
		del("pmList");
	}
}
