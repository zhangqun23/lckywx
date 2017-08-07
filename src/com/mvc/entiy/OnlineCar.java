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


@Entity
@Table(name="online_car")
public class OnlineCar {
private Integer onca_id;//主键
private Integer driver_num;//交易次数
private Float driver_evaluate;//评价
private String onca_number;//车牌号
private String onca_type;//车型
private Integer onca_persons;//汽车承载人数
private String onca_holder;//车辆所有人
private Date onca_register;//车辆注册日期
private Float onca_rent;//租金
private String onca_photo;//汽车与车主照片图片存储到服务器文件夹
private Integer onca_state;//网约车状态0不能预约1可预约
private String onca_remark;//备注
private Integer is_audit;//是否通过审核0表示待审核，1表示已审核
private WorkerInfo workerInfo;//审核人fk
private Date release_time;//信息发布时间
private Boolean is_delete;//是否删除
private Driver driver;//网约车信息登录人的微信号fk
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public Integer getOnca_id() {
	return onca_id;
}

public void setOnca_id(Integer onca_id) {
	this.onca_id = onca_id;
}
@Column(columnDefinition="INT not null default 0")
public Integer getDriver_num() {
	return driver_num;
}
public void setDriver_num(Integer driver_num) {
	this.driver_num = driver_num;
}
@Column(columnDefinition = "float(10,2) not null default '0.00'")
public Float getDriver_evaluate() {
	return driver_evaluate;
}
public void setDriver_evaluate(Float driver_evaluate) {
	this.driver_evaluate = driver_evaluate;
}
@Column(length=32)
public String getOnca_number() {
	return onca_number;
}
public void setOnca_number(String onca_number) {
	this.onca_number = onca_number;
}
@Column(length=36)
public String getOnca_type() {
	return onca_type;
}
public void setOnca_type(String onca_type) {
	this.onca_type = onca_type;
}
@Column(columnDefinition="INT not null default 0")
public Integer getOnca_persons() {
	return onca_persons;
}
public void setOnca_persons(Integer onca_persons) {
	this.onca_persons = onca_persons;
}
@Column(length=32)
public String getOnca_holder() {
	return onca_holder;
}
public void setOnca_holder(String onca_holder) {
	this.onca_holder = onca_holder;
}

public Date getOnca_register() {
	return onca_register;
}
public void setOnca_register(Date onca_register) {
	this.onca_register = onca_register;
}
@Column(columnDefinition = "float(10,1) not null default '0.0'")
public Float getOnca_rent() {
	return onca_rent;
}
public void setOnca_rent(Float onca_rent) {
	this.onca_rent = onca_rent;
}
@Column(length=32)
public String getOnca_photo() {
	return onca_photo;
}
public void setOnca_photo(String onca_photo) {
	this.onca_photo = onca_photo;
}
@Column(columnDefinition="INT not null default 0")
public Integer getOnca_state() {
	return onca_state;
}
public void setOnca_state(Integer onca_state) {
	this.onca_state = onca_state;
}
@Column(length=255)
public String getOnca_remark() {
	return onca_remark;
}
public void setOnca_remark(String onca_remark) {
	this.onca_remark = onca_remark;
}
@Column(columnDefinition="INT not null default 0")
public Integer getIs_audit() {
	return is_audit;
}
public void setIs_audit(Integer is_audit) {
	this.is_audit = is_audit;
}
@ManyToOne
@JoinColumn(name="auditor")
public WorkerInfo getWorkerInfo() {
	return workerInfo;
}
public void setWorkerInfo(WorkerInfo workerInfo) {
	this.workerInfo = workerInfo;
}
public Date getRelease_time() {
	return release_time;
}
public void setRelease_time(Date release_time) {
	this.release_time = release_time;
}
public Boolean getIs_delete() {
	return is_delete;
}
public void setIs_delete(Boolean is_delete) {
	this.is_delete = is_delete;
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
