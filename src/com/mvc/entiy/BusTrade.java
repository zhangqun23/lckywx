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
@Table(name="bus_trade")
public class BusTrade {
	
private Integer butr_id;//主键
private Date butr_time;//交易创建时间
private Float butr_depo;//押金
private Float butr_money;//交易金额
private Integer butr_rent_if;//续租状态
private Date butr_rent_time;//续租时间
private Integer invoice_id;//是否开发票
private String invoice_num;//发票号
private  Integer butr_state;//交易状态
private BusNeed busNeed;//班车需求fk
private BusInfo busInfo;//班车fk
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public Integer getButr_id() {
	return butr_id;
}
public void setButr_id(Integer butr_id) {
	this.butr_id = butr_id;
}
public Date getButr_time() {
	return butr_time;
}
public void setButr_time(Date butr_time) {
	this.butr_time = butr_time;
}
@Column(columnDefinition = "float(10,1) not null default '0.0'")
public Float getButr_depo() {
	return butr_depo;
}
public void setButr_depo(Float butr_depo) {
	this.butr_depo = butr_depo;
}
@Column(columnDefinition = "float(10,1) not null default '0.0'")
public Float getButr_money() {
	return butr_money;
}
public void setButr_money(Float butr_money) {
	this.butr_money = butr_money;
}
@Column(columnDefinition="INT not null default 0")
public Integer getButr_rent_if() {
	return butr_rent_if;
}
public void setButr_rent_if(Integer butr_rent_if) {
	this.butr_rent_if = butr_rent_if;
}

public Date getButr_rent_time() {
	return butr_rent_time;
}
public void setButr_rent_time(Date butr_rent_time) {
	this.butr_rent_time = butr_rent_time;
}
@Column(columnDefinition="INT not null default 0")
public Integer getInvoice_id() {
	return invoice_id;
}
public void setInvoice_id(Integer invoice_id) {
	this.invoice_id = invoice_id;
}
@Column(length=32)
public String getInvoice_num() {
	return invoice_num;
}
public void setInvoice_num(String invoice_num) {
	this.invoice_num = invoice_num;
}
@Column(columnDefinition="INT not null default 0")
public Integer getButr_state() {
	return butr_state;
}
public void setButr_state(Integer butr_state) {
	this.butr_state = butr_state;
}
@ManyToOne
@JoinColumn(name="bune_id")
public BusNeed getBusNeed() {
	return busNeed;
}
public void setBusNeed(BusNeed busNeed) {
	this.busNeed = busNeed;
}
@ManyToOne
@JoinColumn(name="bus_id")
public BusInfo getBusInfo() {
	return busInfo;
}
public void setBusInfo(BusInfo busInfo) {
	this.busInfo = busInfo;
}

}
