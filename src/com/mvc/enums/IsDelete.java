package com.mvc.enums;

/**
 * 合同类型
 * 
 * @author wangrui
 * @date 2016-09-17
 *
 */
public enum IsDelete {
	// 0:未删除；1：删除
	NO(0), YES(1);

	public int value;

	private IsDelete(int value) {
		this.value = value;
	}
}
