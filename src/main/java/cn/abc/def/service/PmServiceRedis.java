package cn.abc.def.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.abc.def.entity.PrivateMessage;
import cn.abc.def.redis.RedisCache;

/**
 * Redis实现
 * @author Administrator
 *
 */
@Service
public class PmServiceRedis implements IPmService {
	
	@Resource
	private RedisCache redisUtil;
	
	private volatile int id = 1;

	@Override
	public void save(PrivateMessage privateMessage) {
		privateMessage.setId(id);
		redisUtil.hset("pmList", id + "", privateMessage);
		id++;
	}

	@Override
	public List<PrivateMessage> findPm(Integer senderUid, Integer receiverUid) {
		List<PrivateMessage> pmList = new ArrayList<>();
		int i = 1;
		PrivateMessage pm = redisUtil.hget("pmList", i+"", PrivateMessage.class);
		while (pm != null) {
			if ((senderUid.equals(pm.getSenderUid()) && receiverUid.equals(pm.getReceiverUid()))
					|| (senderUid.equals(pm.getReceiverUid()) && receiverUid.equals(pm.getSenderUid()))) {
					pmList.add(pm);
				}
			i++;
			pm = redisUtil.hget("pmList", i+"", PrivateMessage.class);
		}
		
		return pmList;
	}

	@Override
	public List<PrivateMessage> findNewPm(Integer id, Integer senderUid, Integer receiverUid) {
		List<PrivateMessage> pmList = new ArrayList<>();
		int i = 1;
		PrivateMessage pm = redisUtil.hget("pmList", i+"", PrivateMessage.class);
		while (pm != null) {
			if (pm.getId() > id
					&& ((senderUid.equals(pm.getSenderUid()) && receiverUid.equals(pm.getReceiverUid()))
						|| (senderUid.equals(pm.getReceiverUid()) && receiverUid.equals(pm.getSenderUid())))) {
					pmList.add(pm);
				}
			i++;
			pm = redisUtil.hget("pmList", i+"", PrivateMessage.class);
		}
		
		return pmList;
	}

}
