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
 * 用户旅游交易表
 * zq
 * */
@Entity
@Table(name="travel_trade")
public class TravelTrade {
private Integer trtr_id;//交易ID
private String trtr_num;//交易订单号
private String trtr_tel;//客户联系方式
private Float trtr_price;//交易额
private Integer trtr_state;//交易状态0未支付1已支付2申请退款3退款中4、退款完成
private Integer is_refund;//是否申请了退款0表示没有退款1表示退款
private Travel travel;//对应的旅游信息
private Customer customer;//对应的顾客信息
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
public Integer getTrtr_id() {
	return trtr_id;
}
public void setTrtr_id(Integer trtr_id) {
	this.trtr_id = trtr_id;
}
@Column(length=32)
public String getTrtr_num() {
	return trtr_num;
}
public void setTrtr_num(String trtr_num) {
	this.trtr_num = trtr_num;
}
@Column(length=32)
public String getTrtr_tel() {
	return trtr_tel;
}
public void setTrtr_tel(String trtr_tel) {
	this.trtr_tel = trtr_tel;
}
@Column(columnDefinition = "float(10,2) not null default '0.00'")
public Float getTrtr_price() {
	return trtr_price;
}
public void setTrtr_price(Float trtr_price) {
	this.trtr_price = trtr_price;
}
@Column(columnDefinition = "INT not null default 0")
public Integer getTrtr_state() {
	return trtr_state;
}
public void setTrtr_state(Integer trtr_state) {
	this.trtr_state = trtr_state;
}
@Column(columnDefinition = "INT not null default 0")
public Integer getIs_refund() {
	return is_refund;
}
public void setIs_refund(Integer is_refund) {
	this.is_refund = is_refund;
}
@ManyToOne
@JoinColumn(name="travel_id")
public Travel getTravel() {
	return travel;
}
public void setTravel(Travel travel) {
	this.travel = travel;
}
@ManyToOne
@JoinColumn(name="customer_id")
public Customer getCustomer() {
	return customer;
}
public void setCustomer(Customer customer) {
	this.customer = customer;
}

}
