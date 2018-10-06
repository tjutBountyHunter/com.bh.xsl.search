package com.xsl.search.service.dao;

import com.xsl.search.service.common.SearchResult;
import com.xsl.search.service.common.pojo.ItemTransfer;
import com.xsl.search.service.es.EsServer;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.functionScoreQuery;

/**
 * 商品搜索dao
 * <p>Title: SearchDao</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 * @version 1.0
 */
@Repository
public class SearchItemDao {

	/**
	 *根据查询条件查询索引库
	 * <p>Title: search</p>
	 * <p>Description: </p>
	 * @param query
	 * @return
	 */
    private EsServer esServer;

    //任务描述搜索，传入参数：关键词，第几页，一页有多少行，排序方式（0:按热度排序，1:按悬赏金额倒叙，2:按悬赏金额正序，3:按发布时间倒序）
	public SearchResult search(String keyword, int page, int rows ,int search_type) throws Exception {

        SearchResult result = new SearchResult();
        esServer = new EsServer();
        TransportClient client = esServer.getClient();
        SearchRequestBuilder requestBuilder = client.prepareSearch("test")
                .setTypes("item").setQuery(functionScoreQuery(QueryBuilders.matchPhraseQuery("descr", keyword)));
        switch (search_type){
            case 0:
                Map<String, Object> params = new HashMap<>(4);
                Calendar nowTime = Calendar.getInstance();
                params.put("now_millis", nowTime.getTimeInMillis());
                String inlineScript = "long data_millis = doc['create_date'].date.millis;" +
                        "long now_millis  = params.now_millis;" +
                        "long difference = now_millis - data_millis + 28800000;" +
                        "double difference_of_hour = difference/3600000;" +
                        "long num=doc['number'].value;" +
                        "double score = 100*(_score+Math.log(num+1))/(difference_of_hour+5);" +
                        "return score;";
                Script script = new Script( ScriptType.INLINE, "painless",inlineScript, params);
                ScriptScoreFunctionBuilder scriptBuilder = ScoreFunctionBuilders.scriptFunction(script);

                requestBuilder = client.prepareSearch("test")
                        .setTypes("item")
                        .setQuery(functionScoreQuery(QueryBuilders.multiMatchQuery(keyword,"descr"),scriptBuilder));
                break;
            case 1:
                requestBuilder = client.prepareSearch("test")
                        .setTypes("item")
                        .setQuery(functionScoreQuery(QueryBuilders.multiMatchQuery(keyword,"descr")))
                        .addSort("money", SortOrder.DESC);
                break;

            case 2:
                requestBuilder = client.prepareSearch("test")
                        .setTypes("item")
                        .setQuery(functionScoreQuery(QueryBuilders.multiMatchQuery(keyword,"descr")))
                        .addSort("money", SortOrder.ASC);
                break;
            case 3:
                requestBuilder = client.prepareSearch("test")
                        .setTypes("item")
                        .setQuery(functionScoreQuery(QueryBuilders.multiMatchQuery(keyword,"descr")))
                        .addSort("create_date", SortOrder.DESC);
            case 4:
                requestBuilder = client.prepareSearch("test")
                        .setTypes("item")
                        .setQuery(functionScoreQuery(QueryBuilders.multiMatchQuery(keyword,"descr")))
                        .addSort("_id", SortOrder.DESC);
                break;

        }


        SearchResponse searchResponse = requestBuilder.setFrom(page*rows).setSize(rows).execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        List<ItemTransfer> itemList = new ArrayList<>(page*rows);
        for(SearchHit hit:hits){
            Map<String,Object> hit_source = hit.getSourceAsMap();

            ItemTransfer item = new ItemTransfer();
			item.setId(Integer.parseInt(hit.getId()));
			item.setCid((Integer) hit_source.get("cid"));
			item.setNumber((Integer) hit_source.get("number"));
            item.setMoney((Double) hit_source.get("money"));
			item.setSendid((Integer) hit_source.get("send_id"));
			item.setDescr((String) hit_source.get("descr"));
            item.setState((Integer) hit_source.get("state"));
            item.setName((String) hit_source.get("name"));
            item.setUrl((String) hit_source.get("url"));
            item.setTask_name((String) hit_source.get("task_name"));
            item.setTaskid((String) hit_source.get("task_id"));
            item.setLevel((Integer) hit_source.get("level"));


            SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



            String date = hit_source.get("create_date").toString();
            System.out.println("date: "+date);
            date = date.replace("Z", " UTC");//注意是空格+UTC
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
            Date create_date = format.parse(date);
            String create_date_str = format0.format(create_date);
            item.setCreatedate(create_date_str);


            date = hit_source.get("update_date").toString();
            date = date.replace("Z", " UTC");//注意是空格+UTC
            Date update_date = format.parse(date);
            String update_date_str = format0.format(update_date);
            item.setUpdatedate(update_date_str);


            date = hit_source.get("deadline").toString();
            date = date.replace("Z", " UTC");//注意是空格+UTC
            Date deadline = format.parse(date);
            String deadline_str = format1.format(deadline);
            item.setDeadline(deadline_str);

            itemList.add(item);
        }
        result.setItemList(itemList);
        result.setRecordCount(hits.getTotalHits());
        result.setTotalPages(1+(int)hits.getTotalHits()/rows);
        return result;
	}

}
