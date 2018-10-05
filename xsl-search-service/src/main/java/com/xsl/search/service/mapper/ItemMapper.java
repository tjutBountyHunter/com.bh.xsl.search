package com.xsl.search.service.mapper;

import com.xsl.search.service.common.pojo.SearchItem;

import java.util.List;

public interface ItemMapper {

	List<SearchItem> getItemList();
	SearchItem getItemById(long itemId);

}
