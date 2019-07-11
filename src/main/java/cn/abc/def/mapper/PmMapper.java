package cn.abc.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.abc.def.entity.PrivateMessage;

@Repository
public interface PmMapper {
	
	void save(PrivateMessage privateMessage);
	
	List<PrivateMessage> findPm(@Param("senderUid") Integer senderUid, @Param("receiverUid") Integer receiverUid);
	
	/**
	 * 根据ID查找最新的私信
	 * @param id
	 * @param senderUid
	 * @param receiverUid
	 * @return
	 */
	List<PrivateMessage> findNewPmById(@Param("id") Integer id, @Param("senderUid") Integer senderUid, 
										@Param("receiverUid") Integer receiverUid);
	
	Integer deletePm(@Param("senderUid") Integer senderUid, @Param("receiverUid") Integer receiverUid);
}
