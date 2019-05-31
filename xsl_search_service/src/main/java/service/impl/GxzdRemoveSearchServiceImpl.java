package service.impl;

import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;
import com.xsl.search.service.common.XslResult;
import com.xsl.search.service.common.util.EsServer;
import org.elasticsearch.client.transport.TransportClient;
import service.GxzdRemoveSearchService;

/**
 * 说明：
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:59
 * @Description:
 */
public class GxzdRemoveSearchServiceImpl implements GxzdRemoveSearchService {

	@Override
	public XslResult removeMatchUser(MatchUserSearchVo matchUserSearchVo) throws RuntimeException {
		TransportClient client = EsServer.getClient();
		//提交删除申请
		client.prepareDelete("gxzd-user","user",matchUserSearchVo.getHunterid()).get();
		return XslResult.ok();
	}

	@Override
	public XslResult removeMatch(MatchSearchVo matchSearchVo) throws RuntimeException {
		TransportClient client = EsServer.getClient();
		client.prepareDelete("gxzd-match", "match",matchSearchVo.getMatchid()).get();
		return XslResult.ok();
	}

	@Override
	public XslResult removeMatchTeam(MatchTeamSearchVo matchTeamSearchVo) throws RuntimeException {
		TransportClient client = EsServer.getClient();
		client.prepareDelete("gxzd-team", "team",matchTeamSearchVo.getTeamid()).get();
		return XslResult.ok();
	}
}
