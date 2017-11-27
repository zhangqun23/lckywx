package com.mvc.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.mvc.service.LoginService;
import com.mvc.constants.wxPayConstants;
import com.mvc.entityReport.ReceiveXmlEntity;

@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService {
	
	public String wechatProcess(String xml){
	
			/*xml = "<xml><ToUserName><![CDATA[gh_680bdefc8c5d]]></ToUserName><FromUserName><![CDATA[oIDrpjqASyTPnxRmpS9O_ruZGsfk]]></FromUserName>";
			xml = xml + "<CreateTime>1359028446</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[你好]]></Content></xml>";*/
			
		String result = "";
		
		/** 解析xml数据 */
		ReceiveXmlEntity xmlEntity = getMsgEntity(xml);
		/*if(xmlEntity.getMsgType() == "event" && xmlEntity.getEvent() == "subscribe" || xmlEntity.getEvent() == "unsubscribe"){
			//过滤关注和取消关注事件
		} else {
			
		}*/
		switch(xmlEntity.getMsgType()){
		case "event":
			result = receiveEvent(xmlEntity.getEvent());
			result += xmlEntity.getFromUserName();
			break;
		case "text":
			String content=xmlEntity.getContent();
			if(content.indexOf("票")>-1){result = "购票功能正在完善中，敬请期待！";}
			else if(content.indexOf("从")>-1){result = "购票功能正在完善中，敬请期待！";}
			else if(content.indexOf("到")>-1){result = "购票功能正在完善中，敬请期待！";}
			else if(content.indexOf("货运")>-1){result = "请点击"+wxPayConstants.BASEURL+"完成货运操作";}
			else if(content.indexOf("广告")>-1){result = "请点击"+wxPayConstants.BASEURL+"完成广告操作";}
			else if(content.indexOf("班车")>-1){result = "请点击"+wxPayConstants.BASEURL+"完成班车预定操作";}
			else if(content.indexOf("旅游")>-1){result = "请点击"+wxPayConstants.BASEURL+"完成旅游操作";}
			else {
				result = "对不起，我们暂时不能识别该文本消息";
			}
			break;
		case "image":
			result = "对不起，我们暂时不能识别图片类型的消息";
			break;
		case "location":
			result = "您发送的是位置，经度为" + xmlEntity.getLocation_X() + ",纬度为" + xmlEntity.getLocation_Y() + ",位置为" + xmlEntity.getLabel();
			break;
		case "voice":
			result = "对不起，我们暂时不能识别语音类型的消息";
			break;
		case "video":
			result = "对不起，我们暂时不能识别视频类型的消息";
			break;
		case "link":
			result = "这是一个链接";
			break;
		default :
			result = "对不起，我们暂时不能该类型的消息";
			break;
		
		}
		
		String resultend = formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
		
		return resultend;
	}
	
	public ReceiveXmlEntity getMsgEntity(String strXml){
		ReceiveXmlEntity msg = null; 
		try {
			if (strXml.length() <= 0 || strXml == null)
				return null;
			 
			// 将字符串转化为XML文档对象
			Document document = DocumentHelper.parseText(strXml);
			// 获得文档的根节点
			Element root = document.getRootElement();
			// 遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();
			
			// 遍历所有结点
			msg = new ReceiveXmlEntity();
			//利用反射机制，调用set方法
			//获取该实体的元类型
			Class<?> c = Class.forName("com.mvc.entityReport.ReceiveXmlEntity");
			msg = (ReceiveXmlEntity)c.newInstance();//创建这个实体的对象
			
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				//获取set方法中的参数字段（实体类的属性）
				Field field = c.getDeclaredField(ele.getName());
				//获取set方法，field.getType())获取它的参数数据类型
				Method method = c.getDeclaredMethod("set"+ele.getName(), field.getType());
				//调用set方法
				method.invoke(msg, ele.getText());
			}
		} catch (Exception e) {
			System.out.println("xml 格式异常: "+ strXml);
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 封装文字类的返回消息
	 * @param to
	 * @param from
	 * @param content
	 * @return
	 */
	public String formatXmlAnswer(String to, String from, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}
	
	public String receiveEvent(String object){
		String result = "";
		switch(object){
		case ("subscribe"):
			result = "欢迎关注洛川客运微信公众号，您的OpenId为";
			break;
		}
		return result;
	}

}
