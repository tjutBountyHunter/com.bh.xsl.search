package com.search.mapper;


import com.search.common.pojo.SearchHunter;

import java.util.List;


public interface HunterMapper{

    List<SearchHunter> getHunterList();
    SearchHunter getHunterById(long itemId);

}
