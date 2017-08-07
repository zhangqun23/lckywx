package com.mvc.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/*
 * 顾客信息表
 * zq
 * */
@Entity
@Table(name="customer")
public class Customer {
private Integer customer_id;//id
private String customer_name;//顾客名字
private String customer_idcard;//身份证号
private Boolean is_insurance;//是否购买保险
private User user;//微信用户
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public Integer getCustomer_id() {
	return customer_id;
}
public void setCustomer_id(Integer customer_id) {
	this.customer_id = customer_id;
}
@Column(length=32)
public String getCustomer_name() {
	return customer_name;
}
public void setCustomer_name(String customer_name) {
	this.customer_name = customer_name;
}
@Column(length=32)
public String getCustomer_idcard() {
	return customer_idcard;
}
public void setCustomer_idcard(String customer_idcard) {
	this.customer_idcard = customer_idcard;
}
public Boolean getIs_insurance() {
	return is_insurance;
}
public void setIs_insurance(Boolean is_insurance) {
	this.is_insurance = is_insurance;
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
