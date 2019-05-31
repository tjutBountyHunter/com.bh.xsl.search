package com.xsl.search.export.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 说明：搜索请求Vo
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:38
 * @Description:
 */
public class GxzdSearchReqVo implements Serializable {
	private String keyWord;
	private Integer size;
	private List<String> ids;

	public GxzdSearchReqVo(String keyWord, Integer size, List<String> ids) {
		this.keyWord = keyWord;
		this.size = size;
		this.ids = ids;
	}

	public GxzdSearchReqVo(String keyWord, Integer size) {
		this.keyWord = keyWord;
		this.size = size;
	}

	public GxzdSearchReqVo() {
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
