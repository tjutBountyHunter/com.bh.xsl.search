package com.xsl.search.service.mapper;


import java.util.List;


public interface HunterMapper{

    List<SearchHunter> getHunterList();
    SearchHunter getHunterById(long itemId);

}
