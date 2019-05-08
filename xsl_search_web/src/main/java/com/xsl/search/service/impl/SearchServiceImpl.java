package com.xsl.search.service.impl;

import com.xsl.search.export.SearchService;
import com.xsl.search.export.vo.SearchResult;
import com.xsl.search.service.dao.SearchHunterDao;
import com.xsl.search.service.dao.SearchItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 搜索任务和猎人
 * <p>Title: SearchServiceImpl</p>
 * <p>Description: </p>
 * @version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchItemDao searchItemDao;

    @Autowired
    private SearchHunterDao searchHunterDao;

    @Override
    public SearchResult search_item(String keyword, int page, int rows , int search_type) throws Exception{
        SearchResult searchResult = searchItemDao.search(keyword,page,rows,search_type);
        return searchResult;
    }

    @Override
    public SearchResult search_hunter(String keyword, int page, int rows ,int search_type) throws Exception{
        SearchResult searchResult = searchHunterDao.search(keyword,page,rows,search_type);
        return searchResult;
    }

}
