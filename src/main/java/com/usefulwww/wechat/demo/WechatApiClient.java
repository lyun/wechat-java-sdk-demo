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
					public String reply(Message msg) {
						String type = msg.getMsgType();
						String event = msg.getEvent();
						if(MessageType.event.toString().equals(type)){
							if(MessageType.VIEW.toString().equals(event)){
								
							} else if(MessageType.CLICK.toString().equals(event)){
								
							} else if(MessageType.subscribe.toString().equals(event)){
								return replyWelcome(msg);
							} else if(MessageType.unsubscribe.toString().equals(event)){
								
							}
						} else if(MessageType.text.toString().equals(type)){
							switch (msg.getContent()) {
							case "welcome":
								return replyWelcome(msg);
							case "hello":
							default:
								return replyHello(msg);
								
							}
						}
						return null;
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
	private String replyWelcome(Message msg){
		Message reply = new Message();
		reply.setEvent(MessageType.text.toString());
		reply.setFromUserName(msg.getToUserName());
		reply.setToUserName(msg.getFromUserName());
		reply.setContent("感谢您关注");
		reply.setCreateTime(new Date().getTime());
		return wechat.getRepy4Text(reply);
	}
	
	/**
	 * 测试指令
	 * @param msg
	 * @return
	 */
	private String replyHello(Message msg){
		Message reply = new Message();
		reply.setEvent(MessageType.text.toString());
		reply.setFromUserName(msg.getToUserName());
		reply.setToUserName(msg.getFromUserName());
		reply.setContent("接受到您发送的消息:\n"+msg.getContent());
		reply.setCreateTime(new Date().getTime());
		return wechat.getRepy4Text(reply);
	}
	


}
