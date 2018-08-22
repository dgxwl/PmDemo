package cn.abc.def.entity;

import java.io.Serializable;
import java.util.Date;

public class PrivateMessage implements Serializable {
	
	private Integer id;
	private Integer senderUid;
	private Integer receiverUid;
	private String text;
	private Date createTime;
	
	public PrivateMessage() {
		
	}

	public PrivateMessage(Integer id, Integer senderUid, Integer receiverUid, String text, Date createTime) {
		this.id = id;
		this.senderUid = senderUid;
		this.receiverUid = receiverUid;
		this.text = text;
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSenderUid() {
		return senderUid;
	}

	public void setSenderUid(Integer senderUid) {
		this.senderUid = senderUid;
	}

	public Integer getReceiverUid() {
		return receiverUid;
	}

	public void setReceiverUid(Integer receiverUid) {
		this.receiverUid = receiverUid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrivateMessage other = (PrivateMessage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + "," + senderUid + "," + receiverUid + "," + text + "," + createTime.getTime();
	}

	
}
