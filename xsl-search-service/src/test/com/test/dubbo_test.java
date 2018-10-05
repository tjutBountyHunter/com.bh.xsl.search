package com.test;

import com.xsl.search.export.SearchService;
import com.xsl.search.service.common.SearchResult;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class dubbo_test {
    private SearchService searchService;

    @Test
    public void search(){
        ApplicationContext context=new ClassPathXmlApplicationContext("file:/Users/jiangzeyu/eclipse-workspace/xsl-search-parent/xsl-search-service/src/test/com/test/dubbo.xml");
        searchService =  (SearchService) context.getBean("searchService");
        SearchResult searchResult = null;
        try {
            searchResult = searchService.search_hunter("快递",0,10,1);
        }catch (Exception e){
            e.printStackTrace();
        }

//        for (SearchHunter item: searchResult.getHunterList()) {
//            System.out.print(item.getId());
//            System.out.print("\n");
//        }
    }
}
