package com.xsl.search.service.controller;

//import org.apache.commons.lang3.StringUtils;

import com.xsl.search.export.SearchResource;
import com.xsl.search.export.vo.SearchResult;
import com.xsl.search.export.vo.TaskInfoVo;
import com.xsl.search.service.common.XslResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.SearchService;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping(value="/item", method=RequestMethod.GET)
	@ResponseBody
	public XslResult search_item(@RequestParam String query,
								 @RequestParam(defaultValue="0")Integer page,
								 @RequestParam(defaultValue="10")Integer rows,
								 @RequestParam(defaultValue="0")Integer sort_type) {
		//查询条件不能为空
		if (query.isEmpty()) {
			return XslResult.build(400, "查询条件不能为空");
		}
		SearchResult searchResult = null;
		try {
			System.out.println(query);
			searchResult = searchService.search_item(query,page,rows,sort_type);
		} catch (Exception e) {
			e.printStackTrace();
			return XslResult.build(500, e.toString());
		}
		return XslResult.ok(searchResult);
	}

	@RequestMapping(value="/hunter", method=RequestMethod.GET)
	@ResponseBody
	public XslResult search_hunter(@RequestParam String query,
								 @RequestParam(defaultValue="0")Integer page,
								 @RequestParam(defaultValue="10")Integer rows,
								 @RequestParam(defaultValue="0")Integer sort_type) {
		//查询条件不能为空
		if (query.isEmpty()) {
			return XslResult.build(400, "查询条件不能为空");
		}
		SearchResult searchResult = null;
		try {
			System.out.println(query);
			searchResult = searchService.search_hunter(query,page,rows,sort_type);
		} catch (Exception e) {
			e.printStackTrace();
			return XslResult.build(500, e.toString());
		}
		return XslResult.ok(searchResult);
	}


	@RequestMapping(value="/test")
	@ResponseBody
	public XslResult search_hunter(TaskInfoVo taskInfoVo) {
		int test = searchService.addTaskToEs(taskInfoVo);
		return XslResult.ok(test);
	}

	@RequestMapping(value="/test2")
	@ResponseBody
	public List<TaskInfoVo> search(String keyword, String[] taskIds, int size) {
		List<TaskInfoVo> test = searchService.searchTask(keyword, Arrays.asList(taskIds), size);
		return test;
	}



}
