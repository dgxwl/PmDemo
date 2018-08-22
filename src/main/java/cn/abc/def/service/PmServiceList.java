package cn.abc.def.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.abc.def.entity.PrivateMessage;

/**
 * list实现
 * @author Administrator
 *
 */
@Service
public class PmServiceList implements IPmService {

	private volatile int id = 1;
	private List<PrivateMessage> pmList = new ArrayList<>();

	@Override
	public void save(PrivateMessage privateMessage) {
		privateMessage.setId(id++);
		pmList.add(privateMessage);
	}

	@Override
	public List<PrivateMessage> findPm(Integer senderUid, Integer receiverUid) {
		List<PrivateMessage> sub = new ArrayList<PrivateMessage>();
		for (PrivateMessage pm : pmList) {
			if ((senderUid.equals(pm.getSenderUid()) && receiverUid.equals(pm.getReceiverUid()))
				|| (senderUid.equals(pm.getReceiverUid()) && receiverUid.equals(pm.getSenderUid()))) {
				sub.add(pm);
			}
		}
		return sub;
	}

	@Override
	public List<PrivateMessage> findNewPm(Integer id, Integer senderUid, Integer receiverUid) {
		List<PrivateMessage> sub = new ArrayList<PrivateMessage>();
		for (PrivateMessage pm : pmList) {
			if (pm.getId() > id
				&& ((senderUid.equals(pm.getSenderUid()) && receiverUid.equals(pm.getReceiverUid()))
					|| (senderUid.equals(pm.getReceiverUid()) && receiverUid.equals(pm.getSenderUid())))) {
				sub.add(pm);
			}
		}
		return sub;
	}

}
