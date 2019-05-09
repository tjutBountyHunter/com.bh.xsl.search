package service;

import com.xsl.search.export.vo.SearchResult;
import com.xsl.search.export.vo.TaskInfoVo;

import java.util.List;

public interface SearchService {
	SearchResult search_item(String keyword, int page, int rows, int sort_type)  throws Exception;
	SearchResult search_hunter(String keyword, int page, int rows, int sort_type) throws Exception;

	int addTaskToEs(TaskInfoVo taskInfoVo);

	List<TaskInfoVo> searchTask(String keyword, List<String> taskIds, int size);
}
