package service;

import com.xsl.search.export.vo.GxzdSearchReqVo;
import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;
import com.xsl.search.service.common.XslResult;

import java.util.List;

/**
 * 说明：高校组队系统——搜索
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:46
 * @Description:
 */
public interface GxzdSearchService {


	/** 查用户 */
	List<MatchUserSearchVo> searchMatchUser(String keyWord, List<String> ids,Integer size)throws RuntimeException;
	/** 查比赛 */
	List<MatchSearchVo> searchMatch(String keyWord, List<String> ids,Integer size)throws RuntimeException;
	/** 查队伍 */
	List<MatchTeamSearchVo> searchMatchTeam(String keyWord, List<String> ids,Integer size)throws RuntimeException;

}
