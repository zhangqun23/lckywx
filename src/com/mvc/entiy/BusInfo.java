package com.mvc.entiy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="bus_info")
public class BusInfo {
private Integer bus_id;//主键Id
private String bus_owner;//车主
private String bus_license;//车主驾照编号
private String bus_tel;//联系电话
private String bus_model;//车型
private String bus_num;//车牌号
private Integer bus_sit;//承载人数
private Date bus_manu_time;//车辆出厂年份
private Integer aircon_if;//是否有空调(0:无空调;1:有空调)
private Integer bus_sta;//班车状态是否为空闲车辆(0:运营中;1:空闲)
private Integer bus_rent_sta;//空闲状态是否已经租赁(0:无租赁;1:已租赁)
private Integer line_id;//待考虑外键
private Boolean is_delete;//是否删除0表示未删除，1表示删除
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public Integer getBus_id() {
	return bus_id;
}
public void setBus_id(Integer bus_id) {
	this.bus_id = bus_id;
}
@Column(length=16)
public String getBus_owner() {
	return bus_owner;
}
public void setBus_owner(String bus_owner) {
	this.bus_owner = bus_owner;
}
@Column(length=32)
public String getBus_license() {
	return bus_license;
}
public void setBus_license(String bus_license) {
	this.bus_license = bus_license;
}
@Column(length=32)
public String getBus_tel() {
	return bus_tel;
}
public void setBus_tel(String bus_tel) {
	this.bus_tel = bus_tel;
}
@Column(length=32)
public String getBus_model() {
	return bus_model;
}
public void setBus_model(String bus_model) {
	this.bus_model = bus_model;
}
@Column(length=32)
public String getBus_num() {
	return bus_num;
}
public void setBus_num(String bus_num) {
	this.bus_num = bus_num;
}
@Column(columnDefinition="INT not null default 0")
public Integer getBus_sit() {
	return bus_sit;
}
public void setBus_sit(Integer bus_sit) {
	this.bus_sit = bus_sit;
}
public Date getBus_manu_time() {
	return bus_manu_time;
}
public void setBus_manu_time(Date bus_manu_time) {
	this.bus_manu_time = bus_manu_time;
}
@Column(columnDefinition="INT not null default 0")
public Integer getAircon_if() {
	return aircon_if;
}
public void setAircon_if(Integer aircon_if) {
	this.aircon_if = aircon_if;
}
@Column(columnDefinition="INT not null default 0")
public Integer getBus_sta() {
	return bus_sta;
}
public void setBus_sta(Integer bus_sta) {
	this.bus_sta = bus_sta;
}
@Column(columnDefinition="INT not null default 0")
public Integer getBus_rent_sta() {
	return bus_rent_sta;
}
public void setBus_rent_sta(Integer bus_rent_sta) {
	this.bus_rent_sta = bus_rent_sta;
}
@Column(columnDefinition="INT not null default 0")
public Integer getLine_id() {
	return line_id;
}
public void setLine_id(Integer line_id) {
	this.line_id = line_id;
}
@Column(columnDefinition="INT not null default 0")
public Boolean getIs_delete() {
	return is_delete;
}
public void setIs_delete(Boolean is_delete) {
	this.is_delete = is_delete;
}


}
