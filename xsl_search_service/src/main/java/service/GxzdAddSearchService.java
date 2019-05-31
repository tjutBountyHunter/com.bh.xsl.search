package service;

import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;
import com.xsl.search.service.common.XslResult;

import java.util.List;

/**
 * 说明： 高校组队系统——添加索引
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:46
 * @Description:
 */
public interface GxzdAddSearchService {

	/** 添加用户 */
	XslResult addMatchUser(MatchUserSearchVo matchUserSearchVo)throws RuntimeException;
	/** 添加比赛 */
	XslResult addMatch(MatchSearchVo matchSearchVo)throws RuntimeException;
	/** 添加队伍 */
	XslResult addMatchTeam(MatchTeamSearchVo matchTeamSearchVo)throws RuntimeException;

}
