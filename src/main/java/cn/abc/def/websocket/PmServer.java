package cn.abc.def.websocket;

import java.io.IOException;
import java.util.HashMap;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.abc.def.controller.BaseController;

/**
 * @@ServerEndpoint注解参数configurator配置为自定义的可获取session的类
 * @author Administrator
 *
 */
@ServerEndpoint(value = "/wspm.do", configurator = GetHttpSessionConfigurator.class)
public class PmServer extends BaseController {

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
	
	@OnMessage
	public void onMessage(Session session, String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Integer toUser = Integer.parseInt(mapper.readTree(message).get("toUser").toString());
			String content = mapper.readTree(message).get("content").toString();
			content = content.substring(1, content.length() - 1);
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
