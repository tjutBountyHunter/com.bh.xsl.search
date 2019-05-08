package com.xsl.search.service.mapper;

import java.util.List;

public interface ItemMapper {

	List<SearchItem> getItemList();
	SearchItem getItemById(long itemId);

}
