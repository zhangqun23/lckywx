package com.mvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="access_token")
public class AccessToken implements Serializable  {

	private Integer acto_id;
	private String acto_num;
	private Long acto_time;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "acto_id",unique = true, nullable = false, length = 10)
	public Integer getActo_id() {
		return acto_id;
	}
	public void setActo_id(Integer acto_id) {
		this.acto_id = acto_id;
	}
	@Column(name="acto_num",length = 256)
	public String getActo_num() {
		return acto_num;
	}
	public void setActo_num(String acto_num) {
		this.acto_num = acto_num;
	}
	public Long getActo_time() {
		return acto_time;
	}
	public void setActo_time(Long acto_time) {
		this.acto_time = acto_time;
	}
	
}
