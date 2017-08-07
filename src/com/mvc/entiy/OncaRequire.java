package com.mvc.entiy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="onca_require")
public class OncaRequire {
private Integer onre_id;//网约车id，主键	
private String onre_linkman;//网约车联系人
private String onre_tel;//联系方式
private Integer onre_persons;//网约车承载人数
private Float onre_rent;//租金要求
private Date onre_date;//租赁时间
private String onre_start;//始发地
private String onre_end;//目的地
private String onre_remark;//备注
private User user;//用户id

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
@Column(name = "onre_id",unique = true, nullable = false, length = 11)
public Integer getOnre_id() {
	return onre_id;
}
public void setOnre_id(Integer onre_id) {
	this.onre_id = onre_id;
}

@Column(name = "onre_linkman", length = 36)
public String getOnre_linkman() {
	return onre_linkman;
}
public void setOnre_linkman(String onre_linkman) {
	this.onre_linkman = onre_linkman;
}

@Column(name = "onre_tel", length = 36)
public String getOnre_tel() {
	return onre_tel;
}
public void setOnre_tel(String onre_tel) {
	this.onre_tel = onre_tel;
}

@Column(name = "onre_persons", nullable = false)
public Integer getOnre_persons() {
	return onre_persons;
}
public void setOnre_persons(Integer onre_persons) {
	this.onre_persons = onre_persons;
}

@Column(columnDefinition = "float(10,1) not null default '0.0'")
public Float getOnre_rent() {
	return onre_rent;
}
public void setOnre_rent(Float onre_rent) {
	this.onre_rent = onre_rent;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "onre_date")
public Date getOnre_date() {
	return onre_date;
}
public void setOnre_date(Date onre_date) {
	this.onre_date = onre_date;
}

@Column(name = "onre_start", length = 36)
public String getOnre_start() {
	return onre_start;
}
public void setOnre_start(String onre_start) {
	this.onre_start = onre_start;
}

@Column(name = "onre_end", length = 36)
public String getOnre_end() {
	return onre_end;
}
public void setOnre_end(String onre_end) {
	this.onre_end = onre_end;
}

@Column(name = "onre_remark", length = 256)
public String getOnre_remark() {
	return onre_remark;
}
public void setOnre_remark(String onre_remark) {
	this.onre_remark = onre_remark;
}
@ManyToOne
@JoinColumn(name="user_id")
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}

}
