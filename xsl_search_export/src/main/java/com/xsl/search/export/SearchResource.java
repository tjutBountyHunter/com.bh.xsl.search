package com.xsl.search.export;

import com.xsl.search.export.vo.SearchResult;
import com.xsl.search.export.vo.TaskInfoVo;
import com.xsl.search.export.vo.TaskSearchReqVo;

import java.util.List;

public interface SearchResource {
	int addTaskToEs(TaskInfoVo taskInfoVo);

	List<TaskInfoVo> searchTask(TaskSearchReqVo taskSearchReqVo);
}
