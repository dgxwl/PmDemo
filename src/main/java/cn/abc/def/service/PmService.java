package cn.abc.def.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.abc.def.entity.PrivateMessage;
import cn.abc.def.mapper.PmMapper;

@Service
public class PmService implements IPmService {

	@Resource
	private PmMapper pmMapper;

	public void save(PrivateMessage privateMessage) {
		pmMapper.save(privateMessage);
	}

	public List<PrivateMessage> findPm(Integer senderUid, Integer receiverUid) {
		return pmMapper.findPm(senderUid, receiverUid);
	}

	public List<PrivateMessage> findNewPm(Integer id, Integer senderUid, Integer receiverUid) {
		
		return pmMapper.findNewPmById(id, senderUid, receiverUid);
	}

	
}
