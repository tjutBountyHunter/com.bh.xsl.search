package com.xsl.search.service.controller.resourceImpl;

import com.xsl.search.export.SearchResource;
import com.xsl.search.export.vo.SearchResult;
import com.xsl.search.export.vo.TaskInfoVo;
import com.xsl.search.export.vo.TaskSearchReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.SearchService;

import java.util.List;

@Controller
public class SearchResourceImpl implements SearchResource {
	@Autowired
	private SearchService searchService;

	@Override
	public SearchResult search_item(String keyword, int page, int rows, int sort_type) throws Exception {
		return null;
	}

	@Override
	public SearchResult search_hunter(String keyword, int page, int rows, int sort_type) throws Exception {
		return null;
	}

	@Override
	public int test(TaskInfoVo taskInfoVo) {
		return searchService.addTaskToEs(taskInfoVo);
	}

	@Override
	public List<TaskInfoVo> searchTask(TaskSearchReqVo taskSearchReqVo) {
		String keyword = taskSearchReqVo.getKeyword();
		List<String> taskIds = taskSearchReqVo.getTaskIds();
		int size = taskSearchReqVo.getSize();
		List<TaskInfoVo> taskInfoVos = searchService.searchTask(keyword, taskIds, size);
		return taskInfoVos;
	}
}
