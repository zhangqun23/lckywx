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
@Table(name="truck")
public class Truck {
private Integer trck_id;//货车id，主键
private Float trck_load;//货车载重，以吨为单位
private Float trck_left_load;//剩余载重
private Integer is_freeze;//0代表未冷冻，1代表冷冻
private String trck_splace;//始发地
private String trck_eplac;//目的地
private String trck_price;//价格
private Integer trck_num;//交易次数
private String trck_score;//评分
private Date trck_time;//预计交易时间
private Integer trck_check;//0代表未通过审核，1代表已通过审核
private Driver driver;//外键

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
@Column(name = "trck_id",unique = true, nullable = false, length = 11)
public Integer getTrck_id() {
	return trck_id;
}
public void setTrck_id(Integer trck_id) {
	this.trck_id = trck_id;
}

@Column(columnDefinition = "float(10,2) not null default '0.00'")
public Float getTrck_load() {
	return trck_load;
}
public void setTrck_load(Float trck_load) {
	this.trck_load = trck_load;
}

@Column(columnDefinition = "float(10,2) not null default '0.00'")
public Float getTrck_left_load() {
	return trck_left_load;
}
public void setTrck_left_load(Float trck_left_load) {
	this.trck_left_load = trck_left_load;
}

@Column(name = "is_freeze", nullable = false)
public Integer getIs_freeze() {
	return is_freeze;
}
public void setIs_freeze(Integer is_freeze) {
	this.is_freeze = is_freeze;
}

@Column(name = "trck_splace", length = 32)
public String getTrck_splace() {
	return trck_splace;
}
public void setTrck_splace(String trck_splace) {
	this.trck_splace = trck_splace;
}

@Column(name = "trck_eplac", length = 32)
public String getTrck_eplace() {
	return trck_eplac;
}
public void setTrck_eplace(String trck_eplace) {
	this.trck_eplac = trck_eplace;
}

@Column(name = "trck_price", length = 32)
public String getTrck_price() {
	return trck_price;
}
public void setTrck_price(String trck_price) {
	this.trck_price = trck_price;
}

@Column(name = "trck_num", length = 11)
public Integer getTrck_num() {
	return trck_num;
}
public void setTrck_num(Integer trck_num) {
	this.trck_num = trck_num;
}

@Column(columnDefinition = "float(10,2) not null default '0.00'")
public String getTrck_score() {
	return trck_score;
}
public void setTrck_score(String trck_score) {
	this.trck_score = trck_score;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "trck_time")
public Date getTrck_time() {
	return trck_time;
}
public void setTrck_time(Date trck_time) {
	this.trck_time = trck_time;
}

@Column(name = "trck_check", nullable = false)
public Integer getTrck_check() {
	return trck_check;
}
public void setTrck_check(Integer trck_check) {
	this.trck_check = trck_check;
}

@ManyToOne
@JoinColumn(name="driver_id")
public Driver getDriver() {
	return driver;
}
public void setDriver(Driver driver) {
	this.driver = driver;
}
}
