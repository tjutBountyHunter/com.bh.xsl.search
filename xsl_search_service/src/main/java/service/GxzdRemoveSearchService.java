 package service;

import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;

 /**
  * 说明：高校组队系统——删除索引
  *
  * @Auther: 11432_000
  * @Date: 2019/5/25 17:46
  * @Description:
  */
 public interface GxzdRemoveSearchService {

     /** 删除用户 */
     boolean removeMatchUser(String hunterId)throws RuntimeException;
     /** 删除比赛 */
     boolean removeMatch(String matchId)throws RuntimeException;
     /** 删除队伍 */
     boolean removeMatchTeam(String teamId)throws RuntimeException;
 }
