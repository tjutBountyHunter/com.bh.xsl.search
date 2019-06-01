package com.xsl.search.export;

import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;

/**
 * 说明： 高校组队系统——添加数据
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:46
 * @Description:
 */
public interface GxzdAddSearchService {

	/** 添加用户 */
	boolean addMatchUser(MatchUserSearchVo matchUserSearchVo)throws RuntimeException;
	/** 添加比赛 */
	boolean addMatch(MatchSearchVo matchSearchVo)throws RuntimeException;
	/** 添加队伍 */
	boolean addMatchTeam(MatchTeamSearchVo matchTeamSearchVo)throws RuntimeException;

}
