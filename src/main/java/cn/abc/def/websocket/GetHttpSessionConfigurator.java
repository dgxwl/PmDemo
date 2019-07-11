package cn.abc.def.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * 继承javax.websocket.server.ServerEndpointConfig.Configurator类,
 * 并重写modifyHandshake()方法, 可获取HttpSession
 * @author Administrator
 *
 */
public class GetHttpSessionConfigurator extends Configurator {

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		//从request中获取session
		HttpSession session = (HttpSession) request.getHttpSession();
		//将session保存到EndpointConfig中
		sec.getUserProperties().put(HttpSession.class.getName(), session);
	}
}
