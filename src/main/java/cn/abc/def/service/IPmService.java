package cn.abc.def.service;

import java.util.List;
import cn.abc.def.entity.PrivateMessage;

public interface IPmService {

	void save(PrivateMessage privateMessage);
	
	List<PrivateMessage> findPm(Integer senderUid, Integer receiverUid);
	
	List<PrivateMessage> findNewPm(Integer id, Integer senderUid, Integer receiverUid);
}
