package com.usefulwww.wechat.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
    }
	
	public  boolean sendCustomMsg4Txt(String openId,String content) {
		Message message = new Message();
		message.setToUserName(openId);
		message.setMsgType(MessageType.text.toString());
		message.setContent(content);
		
		return wechat.send(message);
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String appId=request.getParameter("appId");
		String appSecret=request.getParameter("appSecret");
		
		/*
		final ServletContext context = request.getServletContext();
		String rootDir = context.getRealPath("/");
		rootDir = rootDir.endsWith(String.valueOf(File.separatorChar)) == false ? rootDir
				+ File.separatorChar
				: rootDir;
		try {
			Properties p = new Properties();
			p.load(new FileReader(new File(rootDir
					+ "/WEB-INF/classes/config.properties")));
			appId = p.getProperty("appId");
			appSecret = p.getProperty("appSecret");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		wechat.setOptions(appId, appSecret);
		
		String openId=request.getParameter("openId");
		String content="测试客服消息";
		
		sendCustomMsg4Txt(openId, content);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
