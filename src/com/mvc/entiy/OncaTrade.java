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
@Table(name="onca_trade")
public class OncaTrade {
private Integer octr_id;//网约车交易id，主键
private Float octr_money;//交易金额
private Date octr_date;//交易时间
private String octr_evaluate;//交易评价
private Integer is_over;//0代表交易中，1代表交易结束
private Integer is_evaluate;//0代表未评价，1代表已评价
private OncaRequire oncaRequire;//外键
private OnlineCar onlineCar;//外键

@Id
@GeneratedValue(strategy=GenerationType.AUTO)
@Column(name = "octr_id",unique = true, nullable = false, length = 11)
public Integer getOctr_id() {
	return octr_id;
}
public void setOctr_id(Integer octr_id) {
	this.octr_id = octr_id;
}

@Column(columnDefinition = "float(10,1) not null default '0.0'")
public Float getOctr_money() {
	return octr_money;
}
public void setOctr_money(Float octr_money) {
	this.octr_money = octr_money;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "octr_date")
public Date getOctr_date() {
	return octr_date;
}
public void setOctr_date(Date octr_date) {
	this.octr_date = octr_date;
}

@Column(name = "octr_evaluate", length = 256)
public String getOctr_evaluate() {
	return octr_evaluate;
}
public void setOctr_evaluate(String octr_evaluate) {
	this.octr_evaluate = octr_evaluate;
}

@Column(name = "is_over", nullable = false)
public Integer getIs_over() {
	return is_over;
}
public void setIs_over(Integer is_over) {
	this.is_over = is_over;
}

@Column(name = "is_evaluate", nullable = false)
public Integer getIs_evaluate() {
	return is_evaluate;
}
public void setIs_evaluate(Integer is_evaluate) {
	this.is_evaluate = is_evaluate;
}

@ManyToOne
@JoinColumn(name="onre_id")
public OncaRequire getOncaRequire() {
	return oncaRequire;
}
public void setOncaRequire(OncaRequire oncaRequire) {
	this.oncaRequire = oncaRequire;
}
@ManyToOne
@JoinColumn(name="onca_id")
public OnlineCar getOnlineCar() {
	return onlineCar;
}
public void setOnlineCar(OnlineCar onlineCar) {
	this.onlineCar = onlineCar;
}

}
