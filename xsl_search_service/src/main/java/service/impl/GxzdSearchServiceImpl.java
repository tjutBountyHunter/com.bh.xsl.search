package service.impl;

import com.xsl.search.export.GxzdSearchService;
import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;
import com.xsl.search.service.common.util.EsServer;
import com.xsl.search.service.common.util.GsonSingle;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:48
 * @Description:
 */
@Service
public class GxzdSearchServiceImpl implements GxzdSearchService {

	@Override
	/** 查用户 */
	public List<MatchUserSearchVo> searchMatchUser(String keyWord, List<String> ids, Integer size) throws RuntimeException {
		//组合查询条件
		BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery();
		if (ids != null && ids.size() > 0){
			queryBuilders.must(QueryBuilders.matchQuery("hunterid", ids));
		}
		if(!StringUtils.isEmpty(keyWord)){
			queryBuilders.must(QueryBuilders.boolQuery()
					.should(QueryBuilders.matchQuery("name", keyWord))
					.should(QueryBuilders.matchQuery("major", keyWord))
					.should(QueryBuilders.termQuery("phone",keyWord))
					.should(QueryBuilders.matchQuery("school",keyWord))
					.should(QueryBuilders.matchQuery("signature",keyWord))
			);
		}
		//获取查询结果
		SearchHits user = search(queryBuilders, size, "gxzd-user", "user", null);
		List<MatchUserSearchVo> result = new ArrayList<>();
		for(SearchHit hit: user) {
			String source = hit.getSourceAsString();
			MatchUserSearchVo matchUserSearchVo = GsonSingle.getGson().fromJson(source, MatchUserSearchVo.class);
			result.add(matchUserSearchVo);
		}
		return result;
	}

	@Override
	/** 查比赛 */
	public List<MatchSearchVo> searchMatch(String keyWord, List<String> ids, Integer size) throws RuntimeException {
		//组合查询条件
		BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery();
		if (ids != null && ids.size() > 0){
			queryBuilders.must(QueryBuilders.matchQuery("matchid", ids));
		}
		if(!StringUtils.isEmpty(keyWord)){
			queryBuilders.must(QueryBuilders.boolQuery()
					.should(QueryBuilders.matchQuery("matchinfo", keyWord))
					.should(QueryBuilders.matchQuery("matchname", keyWord))
					.should(QueryBuilders.matchQuery("matchaddress",keyWord))
					.should(QueryBuilders.termQuery("matchid",keyWord))
			);
		}
		//获取查询结果
		SearchHits user = search(queryBuilders, size, "gxzd-match", "match", null);
		List<MatchSearchVo> result = new ArrayList<>();
		for(SearchHit hit: user) {
			String source = hit.getSourceAsString();
			MatchSearchVo matchSearchVo = GsonSingle.getGson().fromJson(source, MatchSearchVo.class);
			result.add(matchSearchVo);
		}
		return result;
	}

	@Override
	/** 查队伍 */
	public List<MatchTeamSearchVo> searchMatchTeam(String keyWord, List<String> ids, Integer size) throws RuntimeException {
		//组合查询条件
		BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery();
		if (ids != null && ids.size() > 0){
			queryBuilders.must(QueryBuilders.matchQuery("teamid", ids));
		}
		if(!StringUtils.isEmpty(keyWord)){
			queryBuilders.must(QueryBuilders.boolQuery()
					.should(QueryBuilders.matchQuery("matchname", keyWord))
					.should(QueryBuilders.matchQuery("teamname",keyWord))
					.should(QueryBuilders.matchQuery("teamslogan",keyWord))
					.should(QueryBuilders.termQuery("teamid",keyWord))
			);
		}
		//获取查询结果
		SearchHits user = search(queryBuilders, size, "gxzd-team", "team", null);
		List<MatchTeamSearchVo> result = new ArrayList<>();
		for(SearchHit hit: user) {
			String source = hit.getSourceAsString();
			MatchTeamSearchVo matchTeamSearchVo = GsonSingle.getGson().fromJson(source, MatchTeamSearchVo.class);
			result.add(matchTeamSearchVo);
		}
		return result;
	}


	/** 获取查询结果 */
	private SearchHits search(BoolQueryBuilder queryBuilders, Integer size,String index,String type,String sortBy){
		TransportClient client = EsServer.getClient();
		//拼接查询条件
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
		searchRequestBuilder
				.setTypes(type)
				.setQuery(queryBuilders)
				.setSize(size);
		if (!StringUtils.isEmpty(sortBy)) {
			searchRequestBuilder.addSort(sortBy, SortOrder.DESC);
		}
		//執行查詢
		SearchResponse sr = searchRequestBuilder.execute().actionGet();;
		SearchHits resource = sr.getHits();
		return resource;
	}

}
