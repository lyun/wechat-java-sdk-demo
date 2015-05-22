package com.usefulwww.wechat.demo;

import java.io.IOException;
import java.io.InputStream;

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
 * Servlet implementation class CustomMessage
 */
@WebServlet("/CustomMessage")
public class CustomMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final Wechat wechat = new Wechat();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String appId="wx9a3ef4ece8e2715f";
		String appSecret="54fc774782eaa1b485a16b76d872284e";

		wechat.setOptions(appId, appSecret);
		
		String openId="ommWbs0VWp_K1wm5tVkeV_OEEmtI";
		String content="测试客服消息";
		
		sendCustomMsg4Txt(openId, content);
	}
	
	public  boolean sendCustomMsg4Txt(String openId,String content) {
		Message message = new Message();
		message.setToUserName(openId);
		message.setMsgType(MessageType.text.toString());
		message.setContent(content);
		
		return wechat.send(message);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	

}
