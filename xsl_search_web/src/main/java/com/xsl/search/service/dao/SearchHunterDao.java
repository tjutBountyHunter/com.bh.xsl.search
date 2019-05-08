package com.xsl.search.service.dao;

import com.xsl.search.export.vo.HunterTransfer;
import com.xsl.search.export.vo.SearchItem;
import com.xsl.search.export.vo.SearchResult;
import service.EsServer;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.functionScoreQuery;


@Repository
public class SearchHunterDao {

    private EsServer esServer;




    //猎人描述搜索，传入参数：关键词，第几页，一页有多少行，排序方式（0:按信用倒序 2:按任务成功数倒序）


    public SearchResult search(String keyword, int page, int rows , int search_type) throws Exception {
        SearchResult result = new SearchResult();
        esServer = new EsServer();
        TransportClient client = esServer.getClient();
        SearchRequestBuilder requestBuilder = client.prepareSearch("test2")
                .setTypes("hunter").setQuery(functionScoreQuery(QueryBuilders.matchPhraseQuery("descr", keyword)));
        switch (search_type){
            case 0:
                requestBuilder = client.prepareSearch("test2")
                        .setTypes("hunter")
                        .setQuery(functionScoreQuery(QueryBuilders.multiMatchQuery(keyword,"descr")))
                        .addSort("credit", SortOrder.DESC);
                break;

            case 1:
                requestBuilder = client.prepareSearch("test2")
                        .setTypes("hunter")
                        .setQuery(functionScoreQuery(QueryBuilders.multiMatchQuery(keyword,"descr")))
                        .addSort("task_acc_num", SortOrder.DESC);
                break;
        }


        SearchResponse searchResponse = requestBuilder.setFrom(page*rows).setSize(rows).execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        List<HunterTransfer> hunterList = new ArrayList<>(page*rows);
        for(SearchHit hit:hits){
            Map<String,Object> hit_source = hit.getSourceAsMap();

            HunterTransfer hunter = new HunterTransfer();

            SearchItem item = new SearchItem();

            hunter.setId(Integer.parseInt(hit.getId()));
            hunter.setLevel((Integer) hit_source.get("level"));
            hunter.setEmpirical((Integer) hit_source.get("empirical"));
            hunter.setTaskaccnum((Integer) hit_source.get("task_acc_num"));
            hunter.setTaskfailnum((Integer) hit_source.get("task_fail_num"));
            hunter.setCredit((Integer) hit_source.get("credit"));
            hunter.setDescr((String) hit_source.get("descr"));
            hunter.setState((Integer) hit_source.get("state"));
            hunter.setUrl((String) hit_source.get("url"));

            //日期格式转换
            SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式

            String date = hit_source.get("last_time").toString();
            date = date.replace("Z", " UTC");//注意是空格+UTC
            Date lasttime = format.parse(date);
            String lasttime_str = format0.format(lasttime);
            hunter.setLasttime(lasttime_str);

            hunterList.add(hunter);
        }
        result.setHunterList(hunterList);
        result.setRecordCount(hits.getTotalHits());
        result.setTotalPages(1+(int)hits.getTotalHits()/rows);
        return result;
    }
}
