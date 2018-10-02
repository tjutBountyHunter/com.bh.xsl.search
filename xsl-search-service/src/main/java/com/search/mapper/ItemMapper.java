package com.search.mapper;

import com.search.common.pojo.SearchItem;

import java.util.List;

public interface ItemMapper {

	List<SearchItem> getItemList();
	SearchItem getItemById(long itemId);

}
