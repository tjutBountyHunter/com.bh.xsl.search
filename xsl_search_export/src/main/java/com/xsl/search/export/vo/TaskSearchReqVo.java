package com.xsl.search.export.vo;

import java.util.List;

public class TaskSearchReqVo extends ReqBaseVo {
	private String keyword;

	private List<String> taskIds;

	private int size;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<String> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
