package com.mvc.constants;

/**
 * 微信支付相关常量，做到开箱即用 
 */

public class wxPayConstants {

	public static final String APPID = "wx3afdb0aec74f693f";//微信公众号appid
	public static final String APPSECRET = "c5b66a39a2c96849446d1c2d33994a28";//APPSECRET
	public static final String MCH_ID = "1468721802";//商户号
	public static final String PATERNERKEY = "luochuankeyunqichezhan9113622220";//API密钥
	public static final String SIGNTYPE = "MD5";//编码方式
	public static final String TRAVELBODY = "洛客-旅游";//交易名称，必须是“XX-XX”
	public static final String NOTIFY_URL = "http://lckywx.cc/lckywx/returnPay/payReturn.do";//微信支付回调URL，支付完成后，微信会把相关和用户信息发送到该URL
	public static final String FILEPATH = "/usr/java/apiclient_cert.p12"; //退款需要提供证书数据，所以需要根据证书路径读取证书
	public static final String BASEURL="http://lckywx.cc/lckywx";
	//public static final String FILEPATH = "E:\\证书\\apiclient_cert.p12"; //退款需要提供证书数据，所以需要根据证书路径读取证书
	
}
