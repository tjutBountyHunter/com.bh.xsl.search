package com.search.mapper;


import com.search.common.pojo.SearchHunter;

import java.util.List;

public interface HunterMapper{

    List<SearchHunter> getItemList();
    SearchHunter getItemById(long itemId);

}
