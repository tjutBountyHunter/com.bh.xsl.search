package service.impl;

import com.xsl.search.export.GxzdRemoveSearchResource;
import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;
import com.xsl.search.service.common.util.EsServer;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.stereotype.Service;
import service.GxzdRemoveSearchService;

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
	public boolean removeMatchUser(String hunterId) throws RuntimeException {
		try {
			TransportClient client = EsServer.getClient();
			//提交删除申请
			client.prepareDelete("gxzd-user","user",hunterId).get();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeMatch(String matchId) throws RuntimeException {
		try {
			TransportClient client = EsServer.getClient();
			client.prepareDelete("gxzd-match", "match",matchId).get();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeMatchTeam(String teamId) throws RuntimeException {
		try {
			TransportClient client = EsServer.getClient();
			client.prepareDelete("gxzd-team", "team",teamId).get();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
