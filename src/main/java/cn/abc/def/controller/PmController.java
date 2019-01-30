package cn.abc.def.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.abc.def.entity.PrivateMessage;
import cn.abc.def.entity.ResponseResult;
import cn.abc.def.service.IPmService;

@Controller
@RequestMapping("/pm")
public class PmController extends BaseController {

	@Autowired
	@Qualifier("pmServiceRedis")  //选择实现类
	private IPmService pmService;
	
	@RequestMapping("/toPm.do")
	public String toPm() {
		return "pm";
	}
	
	@RequestMapping("/toWspm.do")
	public String toWspm() {
		return "wspm";
	}
	
	@RequestMapping("/sendPm.do")
	@ResponseBody
	public ResponseResult<Void> sendPm(Integer senderUid, Integer receiverUid, String text) {
	
		ResponseResult<Void> rr;
		
		try {
			pmService.save(new PrivateMessage(null, senderUid, receiverUid, text, new Date()));
			rr = new ResponseResult<Void>(1, "OK");
			return rr;
		} catch (Exception e) {
			rr = new ResponseResult<Void>(0, "ERROR");
			return rr;
		}
	}
	
	@RequestMapping("/getPm.do")
	@ResponseBody
	public ResponseResult<List<PrivateMessage>> getPm(Integer senderUid, Integer receiverUid) {
		ResponseResult<List<PrivateMessage>> rr;
		
		try {
			List<PrivateMessage> messages = pmService.findPm(senderUid, receiverUid);
			rr = new ResponseResult<List<PrivateMessage>>(1, "OK", messages);
			return rr;
		} catch (Exception e) {
			e.printStackTrace();
			rr = new ResponseResult<List<PrivateMessage>>(0, "ERROR");
			return rr;
		}
	}
	
	@RequestMapping("/getNewPm.do")
	@ResponseBody
	public ResponseResult<List<PrivateMessage>> getNewPm(Integer id, Integer senderUid, Integer receiverUid) {
		ResponseResult<List<PrivateMessage>> rr;
		
		try {
			List<PrivateMessage> messages = pmService.findNewPm(id, senderUid, receiverUid);
			rr = new ResponseResult<List<PrivateMessage>>(1, "OK", messages);
			return rr;
		} catch (Exception e) {
			e.printStackTrace();
			rr = new ResponseResult<List<PrivateMessage>>(0, "ERROR");
			return rr;
		}
	}
}
