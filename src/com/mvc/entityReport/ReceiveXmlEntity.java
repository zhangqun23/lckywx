package com.mvc.entityReport;


/**
 * 接收到的微信xml实体类
 * @author pamchen-1
 *
 */
public class ReceiveXmlEntity {
	private String ToUserName; //消息接收方微信号，一般为公众平台账号微信号
	private String FromUserName; //消息发送方微信号
	private String CreateTime; //消息创建时间
	private String MsgType; //消息类型；文本消息为text,图片消息为image,语音消息为voice,视频消息为video
	private String MsgId; //消息ID号
	private String Event;
	private String EventKey;
	private String Ticket;
	private String Latitude;
	private String Longitude;
	private String Precision;
	private String PicUrl; //图片链接地址，可以用HTTP GET获取
	private String MediaId; //媒体ID
	private String Title;
	private String Description;
	private String Url;
	private String Location_X;
	private String Location_Y;
	private String Scale;
	private String Label;
	private String Content; //消息内容
	private String Format; //语音格式，这里为amr
	private String Recognition;
	
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String locationX) {
		Location_X = locationX;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String locationY) {
		Location_Y = locationY;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
