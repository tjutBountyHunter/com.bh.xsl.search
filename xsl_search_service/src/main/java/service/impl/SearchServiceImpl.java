package service.impl;

import com.xsl.search.export.vo.SearchResult;
import com.xsl.search.export.vo.TaskInfoVo;
import com.xsl.search.service.common.util.GsonSingle;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import com.xsl.search.service.common.util.EsServer;
import org.springframework.util.StringUtils;
import service.SearchService;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索任务和猎人
 * <p>Title: SearchServiceImpl</p>
 * <p>Description: </p>
 * @version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Override
    public SearchResult search_item(String keyword, int page, int rows , int search_type) throws Exception{
//        SearchResult searchResult = searchItemDao.search(keyword,page,rows,search_type);
//        return searchResult;
        return null;
    }

    @Override
    public SearchResult search_hunter(String keyword, int page, int rows ,int search_type) throws Exception{
//        SearchResult searchResult = searchHunterDao.search(keyword,page,rows,search_type);
//        return searchResult;

        return null;
    }

    @Override
    public int addTaskToEs(TaskInfoVo taskInfo) {
        // 创建client
        TransportClient client = EsServer.getClient();
        BulkRequestBuilder bulkBuilder = client.prepareBulk();

        try {
            //向文档对象中添加域
            bulkBuilder.add(client.prepareIndex("task_info", "task",taskInfo.getTaskId())
                    .setSource(
                            XContentFactory.jsonBuilder()
                                    .startObject()
                                    .field("cid", taskInfo.getCid())
                                    .field("content", taskInfo.getContent())
                                    .field("createDate", taskInfo.getCreateDate())
                                    .field("deadLineDate", taskInfo.getDeadLineDate())
                                    .field("masterId", taskInfo.getMasterId())
                                    .field("masterlevel", taskInfo.getMasterlevel())
                                    .field("money", taskInfo.getMoney())
                                    .field("name", taskInfo.getName())
                                    .field("sex", taskInfo.getSex())
                                    .field("sourcetype", taskInfo.getSourcetype())
                                    .field("state", taskInfo.getState())
                                    .field("taskId", taskInfo.getTaskId())
                                    .field("taskTitle",taskInfo.getTaskTitle())
                                    .field("txUrl",taskInfo.getTxUrl())
                                    .field("updatedate",taskInfo.getUpdatedate())
                                    .endObject()
                    )
            );
            //提交
            BulkResponse response = bulkBuilder.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public  List<TaskInfoVo> searchTask(String keyword, List<String> taskIds, int size){
        TransportClient client = EsServer.getClient();

        //组合查询
        BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery()
                                           .must(QueryBuilders.rangeQuery("state").from(0).to(1))
                                           .must(QueryBuilders.commonTermsQuery("taskId", taskIds));
        if(!StringUtils.isEmpty(keyword)){
            queryBuilders.must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("taskTitle", keyword)).should(QueryBuilders.matchQuery("content", keyword)));
        }

        //執行查詢
        SearchResponse sr = client.prepareSearch("task_info")
                .setTypes("task")
                .setQuery(queryBuilders)
                .addSort("createDate.keyword", SortOrder.DESC)
                .setSize(size)
                .execute()
                .actionGet();

        SearchHits resource = sr.getHits();

        List<TaskInfoVo> result = new ArrayList<>();
        for(SearchHit hit: resource) {
            String source = hit.getSourceAsString();
            TaskInfoVo taskInfoVo = GsonSingle.getGson().fromJson(source, TaskInfoVo.class);
            result.add(taskInfoVo);
        }
        return result;
    }


}
