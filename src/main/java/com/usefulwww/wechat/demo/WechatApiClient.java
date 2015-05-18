package com.usefulwww.wechat.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.usefulwww.core.wechat.CallBack;
import com.usefulwww.core.wechat.Message;
import com.usefulwww.core.wechat.MessageType;
import com.usefulwww.core.wechat.Wechat;

/**
 * Servlet implementation class ApiClient
 */
public class WechatApiClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public WechatApiClient() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doRequest(request, response);
	}
	
	private final Wechat wechat = new Wechat();
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//配置回调信息
				CallBack callback = new CallBack(){
					@Override
					public String replay(Message send) {
						String type = send.getMsgType();
						String event = send.getEvent();
						String content = send.getContent();
						
						if(MessageType.Event.toString().equals(type)){
							MessageType msgType = MessageType.valueOf(type);
							switch (msgType) {
							case Event:
								if(MessageType.EventSubscribe.toString().equals(event)){
									return replyWelcome(send);
								}
								break;
							case Text:
								if("hello".equals(content)){
									return replyHello(send);	
								} else {
									Message replay = new Message();
									replay.setEvent(MessageType.Text.toString());
									replay.setFromUserName(send.getToUserName());
									replay.setToUserName(send.getFromUserName());
									replay.setContent("未知指令");
									replay.setCreateTime(new Date().getTime());
									return wechat.getReplay4Text(replay);
								}
												
							default:
								break;
							}
							
							return MessageType.Event.toString();
						}
						
						return "error";
					}
				};
				
				InputStream in = request.getInputStream();
				
				String echostr = request.getParameter("echostr");
				String timestamp = request.getParameter("timestamp");
				String token = request.getParameter("token");
				String nonce = request.getParameter("nonce");
				String signature = request.getParameter("signature");
				
				//验证
				boolean isvaild = wechat.vaild(token, signature, timestamp, nonce, echostr);
				
				//待回复的信息
				String replay =wechat.reply(echostr, in, callback);
				
				
		System.out.println(replay);
	}
	
	
	/**
	 * 关注时回复
	 * @param msg
	 * @return
	 */
	private String replyWelcome(Message send){
		Message replay = new Message();
		replay.setEvent(MessageType.Text.toString());
		replay.setFromUserName(send.getToUserName());
		replay.setToUserName(send.getFromUserName());
		replay.setContent("感谢您关注");
		replay.setCreateTime(new Date().getTime());
		return wechat.getReplay4Text(replay);
	}
	
	/**
	 * 测试指令
	 * @param send
	 * @return
	 */
	private String replyHello(Message send){
		Message replay = new Message();
		replay.setEvent(MessageType.Text.toString());
		replay.setFromUserName(send.getToUserName());
		replay.setToUserName(send.getFromUserName());
		replay.setContent("接受到您发送的消息:\n"+send.getContent());
		replay.setCreateTime(new Date().getTime());
		return wechat.getReplay4Text(replay);
	}

}
