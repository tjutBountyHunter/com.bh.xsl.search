package service.impl;

import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;
import com.xsl.search.service.common.XslResult;
import com.xsl.search.service.common.util.EsServer;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import service.GxzdAddSearchService;

/**
 * 说明：
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:58
 * @Description:
 */
public class GxzdAddSearchServiceImpl implements GxzdAddSearchService {

	@Override
	/** 添加用户 */
	public XslResult addMatchUser(MatchUserSearchVo matchUserSearchVo) throws RuntimeException {
		// 创建client
		TransportClient client = EsServer.getClient();
		BulkRequestBuilder bulkBuilder = client.prepareBulk();
		try {
			//向文档对象中添加域
			bulkBuilder.add(client.prepareIndex("gxzd-user", "user",matchUserSearchVo.getHunterid())
					.setSource(
							XContentFactory.jsonBuilder()
									.startObject()
									.field("hunterid", matchUserSearchVo.getHunterid())
									.field("userid",matchUserSearchVo.getUserid())
									.field("name",matchUserSearchVo.getName())
									.field("school",matchUserSearchVo.getSchool())
									.field("major",matchUserSearchVo.getMajor())
									.field("phone",matchUserSearchVo.getPhone())
									.field("txUrl",matchUserSearchVo.getTxUrl())
									.field("signature",matchUserSearchVo.getSignature())
									.endObject()
					)
			);
			//提交
			BulkResponse response = bulkBuilder.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return XslResult.ok();
	}

	@Override
	/** 添加比赛 */
	public XslResult addMatch(MatchSearchVo matchSearchVo) throws RuntimeException {
		// 创建client
		TransportClient client = EsServer.getClient();
		BulkRequestBuilder bulkBuilder = client.prepareBulk();
		try {
			//向文档对象中添加域
			bulkBuilder.add(client.prepareIndex("gxzd-match", "match",matchSearchVo.getMatchid())
					.setSource(
							XContentFactory.jsonBuilder()
									.startObject()
									.field("matchid", matchSearchVo.getMatchid())
									.field("matchaddress",matchSearchVo.getMatchaddress())
									.field("matchinfo", matchSearchVo.getMatchinfo())
									.field("matchname", matchSearchVo.getMatchinfo())
									.field("matchposterurl", matchSearchVo.getMatchposterurl())
									.endObject()
					)
			);
			//提交
			BulkResponse response = bulkBuilder.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return XslResult.ok();
	}

	@Override
	/** 添加队伍 */
	public XslResult addMatchTeam(MatchTeamSearchVo matchTeamSearchVo) throws RuntimeException {
		// 创建client
		TransportClient client = EsServer.getClient();
		BulkRequestBuilder bulkBuilder = client.prepareBulk();
		try {
			//向文档对象中添加域
			bulkBuilder.add(client.prepareIndex("gxzd-team", "team",matchTeamSearchVo.getTeamid())
					.setSource(
							XContentFactory.jsonBuilder()
									.startObject()
									.field("teamid", matchTeamSearchVo.getTeamid())
									.field("teamlogourl", matchTeamSearchVo.getTeamlogourl())
									.field("matchname", matchTeamSearchVo.getMatchname())
									.field("teamname", matchTeamSearchVo.getTeamname())
									.field("teamslogan", matchTeamSearchVo.getTeamslogan())
									.endObject()
					)
			);
			//提交
			BulkResponse response = bulkBuilder.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return XslResult.ok();
	}
}
