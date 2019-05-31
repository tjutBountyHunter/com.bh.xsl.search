package com.xsl.search.export;

import com.xsl.search.export.vo.GxzdSearchReqVo;
import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;

import java.util.List;

/**
 * 说明：组队---搜索系统
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:16
 * @Description:
 */
public interface SearchMatchTeam {
	/** 查用户 */
	List<MatchUserSearchVo> searchMatchUser(GxzdSearchReqVo reqVo)throws RuntimeException;
	/** 查比赛 */
	List<MatchSearchVo> searchMatch(GxzdSearchReqVo reqVo)throws RuntimeException;
	/** 查队伍 */
	List<MatchTeamSearchVo> searchMatchTeam(GxzdSearchReqVo reqVo)throws RuntimeException;


}
