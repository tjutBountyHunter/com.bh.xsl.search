package com.search.dao;

import com.search.common.SearchResult;
import com.search.common.pojo.SearchHunter;
import com.search.common.pojo.SearchItem;
import com.search.es.EsServer;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.functionScoreQuery;

public class SearchHunterDao {

    private EsServer esServer;

    public SearchResult search(String keyword, int page, int rows , int search_type) throws Exception {
        SearchResult result = new SearchResult();
        esServer = new EsServer();
        TransportClient client = esServer.getClient();
        SearchRequestBuilder requestBuilder = client.prepareSearch("test2")
                .setTypes("hunter").setQuery(functionScoreQuery(QueryBuilders.matchPhraseQuery("descr", keyword)));
        switch (search_type){
            case 0:
                requestBuilder = client.prepareSearch("test")
                        .setTypes("item")
                        .setQuery(functionScoreQuery(QueryBuilders.multiMatchQuery(keyword,"descr")))
                        .addSort("credit", SortOrder.DESC);
                break;

            case 1:
                requestBuilder = client.prepareSearch("test")
                        .setTypes("item")
                        .setQuery(functionScoreQuery(QueryBuilders.multiMatchQuery(keyword,"descr")))
                        .addSort("task_acc_num", SortOrder.DESC);
                break;
        }


        SearchResponse searchResponse = requestBuilder.setFrom(page*rows).setSize(rows).execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        List<SearchHunter> hunterList = new ArrayList<>(page*rows);
        for(SearchHit hit:hits){
            Map<String,Object> hit_source = hit.getSourceAsMap();

            SearchHunter hunter = new SearchHunter();

            SearchItem item = new SearchItem();

            hunter.setId(Integer.parseInt(hit.getId()));
            hunter.setLevel((Integer) hit_source.get("level"));
            hunter.setEmpirical((Integer) hit_source.get("empirical"));
            hunter.setTaskaccnum((Integer) hit_source.get("task_acc_num"));
            hunter.setTaskfailnum((Integer) hit_source.get("task_fail_num"));
            hunter.setCredit((Integer) hit_source.get("credit"));
            hunter.setDescr((String) hit_source.get("descr"));

            String date = hit_source.get("last_time").toString();
            date = date.replace("Z", " UTC");//注意是空格+UTC
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
            Date d = format.parse(date);

            hunter.setLasttime(d);

            hunterList.add(hunter);
        }
        result.setHunterList(hunterList);
        result.setRecordCount(hits.getTotalHits());
        result.setTotalPages(1+(int)hits.getTotalHits()/rows);
        return result;
    }
}
