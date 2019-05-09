package com.xsl.search.export;

import com.xsl.search.export.vo.SearchResult;
import com.xsl.search.export.vo.TaskInfoVo;
import com.xsl.search.export.vo.TaskSearchReqVo;

import java.util.List;

public interface SearchResource {
	SearchResult search_item(String keyword, int page, int rows , int sort_type)  throws Exception;
	SearchResult search_hunter(String keyword, int page, int rows , int sort_type) throws Exception;

	int test(TaskInfoVo taskInfoVo);

	List<TaskInfoVo> searchTask(TaskSearchReqVo taskSearchReqVo);
}
