package com.taotao.common.pojo;

import java.util.List;

/**
 * EasyUI DataGrid的返回值类型
 * <p>Title: EasyUIDataGridResult</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月13日上午11:56:29
 * @version 1.0
 */
public class EasyUIDataGridResult {

	private long total;
	private List<?> rows;
	
	public EasyUIDataGridResult(long total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
	
}
