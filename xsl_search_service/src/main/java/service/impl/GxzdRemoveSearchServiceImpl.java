package service.impl;

import com.xsl.search.export.GxzdRemoveSearchService;
import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;
import com.xsl.search.service.common.XslResult;
import com.xsl.search.service.common.util.EsServer;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.stereotype.Service;

/**
 * 说明：
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:59
 * @Description:
 */
@Service
public class GxzdRemoveSearchServiceImpl implements GxzdRemoveSearchService {

	@Override
	public boolean removeMatchUser(MatchUserSearchVo matchUserSearchVo) throws RuntimeException {
		try {
			TransportClient client = EsServer.getClient();
			//提交删除申请
			client.prepareDelete("gxzd-user","user",matchUserSearchVo.getHunterid()).get();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeMatch(MatchSearchVo matchSearchVo) throws RuntimeException {
		try {
			TransportClient client = EsServer.getClient();
			client.prepareDelete("gxzd-match", "match",matchSearchVo.getMatchid()).get();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeMatchTeam(MatchTeamSearchVo matchTeamSearchVo) throws RuntimeException {
		try {
			TransportClient client = EsServer.getClient();
			client.prepareDelete("gxzd-team", "team",matchTeamSearchVo.getTeamid()).get();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
