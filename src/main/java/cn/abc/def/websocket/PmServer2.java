package cn.abc.def.websocket;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.internal.toplink.embedded.websocket.WebSocket;

import cn.abc.def.controller.BaseController;

/**
 * @@ServerEndpoint注解参数configurator配置为自定义的可获取session的类
 * @author Administrator
 *
 */
@ServerEndpoint(value = "/wspm_img.do", configurator = GetHttpSessionConfigurator.class)
public class PmServer2 extends BaseController {

	//保存用户id和对应的websocket session
	private static Map<Integer, Session> sessionMap = new HashMap<>();
	private Integer userId;
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		//获取httpSession
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		userId = getUid(httpSession);
		sessionMap.put(userId, session);
	}
	
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}
	
	List<Byte> lb = new LinkedList<Byte>();
	@OnMessage
	public void onMessage(WebSocket cs, Session session, byte[] data, boolean last) {
		if (!last) {System.out.println(last + ", " + data.length);
			for (byte b : data) {
				lb.add(b);
			}
		} else {System.out.println(last + ", " + data.length);
			byte[] allBytes = new byte[lb.size()];
			for (int i = 0; i < lb.size(); i++) {
				allBytes[i] = lb.get(i);
			}
			
			try (FileOutputStream fos = new FileOutputStream("D:/haha.png");
					BufferedOutputStream bos = new BufferedOutputStream(fos)) {
				bos.write(allBytes);
			} catch (Exception e) {
				e.printStackTrace();
			}
			lb.clear();
			System.out.println("成功接收图片");
		}
	}
	
	@OnMessage
	public void onMessage(Session session, String message) {
		JSONObject json = JSON.parseObject(message);
		try {
			Integer toUser = json.getInteger("toUser");
			String content = json.getString("content");
			Session toSession = sessionMap.get(toUser);
			if (toSession == null) {
				session.getBasicRemote().sendText("[系统消息: 对方不在线]");
			} else {
				toSession.getBasicRemote().sendText(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		sessionMap.remove(userId);
	}
}
