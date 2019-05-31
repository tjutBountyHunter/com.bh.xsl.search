package service;

import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;
import com.xsl.search.service.common.XslResult;

import java.util.List;

/**
 * 说明：高校组队系统——删除索引
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:46
 * @Description:
 */
public interface GxzdRemoveSearchService {

	/** 删除用户 */
	XslResult removeMatchUser(MatchUserSearchVo matchUserSearchVo)throws RuntimeException;
	/** 删除比赛 */
	XslResult removeMatch(MatchSearchVo matchSearchVo)throws RuntimeException;
	/** 删除队伍 */
	XslResult removeMatchTeam(MatchTeamSearchVo matchTeamSearchVo)throws RuntimeException;
}
