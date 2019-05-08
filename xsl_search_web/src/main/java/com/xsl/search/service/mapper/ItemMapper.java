package com.xsl.search.service.mapper;

import com.xsl.search.export.vo.SearchItem;

import java.util.List;

public interface ItemMapper {

	List<SearchItem> getItemList();
	SearchItem getItemById(long itemId);

}
