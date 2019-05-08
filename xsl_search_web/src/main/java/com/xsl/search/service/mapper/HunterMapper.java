package com.xsl.search.service.mapper;


import com.xsl.search.export.vo.SearchHunter;

import java.util.List;


public interface HunterMapper{

    List<SearchHunter> getHunterList();
    SearchHunter getHunterById(long itemId);

}
