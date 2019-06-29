package com.xsl.search.service.controller.resourceImpl;

import com.xsl.search.export.GxzdAddSearchResource;
import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.GxzdAddSearchService;

/**
 * @ClassName GxzdAddSearchResourceImpl
 * @Description TODO
 * @Author 11432
 * @DATE 2019/6/1 16:05
 */
@Controller
public class GxzdAddSearchResourceImpl implements GxzdAddSearchResource {

    @Autowired
    private GxzdAddSearchService gxzdAddSearchService;

    @Override
    public boolean addMatchUser(MatchUserSearchVo matchUserSearchVo) throws RuntimeException {
        return gxzdAddSearchService.addMatchUser(matchUserSearchVo);
    }

    @Override
    public boolean addMatch(MatchSearchVo matchSearchVo) throws RuntimeException {
        return gxzdAddSearchService.addMatch(matchSearchVo);
    }

    @Override
    public boolean addMatchTeam(MatchTeamSearchVo matchTeamSearchVo) throws RuntimeException {
        return gxzdAddSearchService.addMatchTeam(matchTeamSearchVo);
    }
}
