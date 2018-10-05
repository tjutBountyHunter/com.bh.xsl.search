package com.xsl.search.service.mapper;


import com.xsl.search.service.common.pojo.SearchHunter;

import java.util.List;


public interface HunterMapper{

    List<SearchHunter> getHunterList();
    SearchHunter getHunterById(long itemId);

}
