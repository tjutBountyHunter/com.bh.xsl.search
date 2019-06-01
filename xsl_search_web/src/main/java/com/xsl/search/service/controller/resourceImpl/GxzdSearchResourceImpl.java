package com.xsl.search.service.controller.resourceImpl;

import com.xsl.search.export.GxzdSearchResource;
import com.xsl.search.export.vo.MatchSearchVo;
import com.xsl.search.export.vo.MatchTeamSearchVo;
import com.xsl.search.export.vo.MatchUserSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import service.GxzdSearchService;

import java.util.List;

/**
 * @ClassName GxzdSearchResourceImpl
 * @Description TODO
 * @Author 11432
 * @DATE 2019/6/1 16:08
 */
public class GxzdSearchResourceImpl implements GxzdSearchResource {

    @Autowired
    private GxzdSearchService gxzdSearchService;

    @Override
    public List<MatchUserSearchVo> searchMatchUser(String keyWord, List<String> ids, Integer size) throws RuntimeException {
        return gxzdSearchService.searchMatchUser(keyWord,ids,size);
    }

    @Override
    public List<MatchSearchVo> searchMatch(String keyWord, List<String> ids, Integer size) throws RuntimeException {
        return gxzdSearchService.searchMatch(keyWord,ids,size);
    }

    @Override
    public List<MatchTeamSearchVo> searchMatchTeam(String keyWord, List<String> ids, Integer size) throws RuntimeException {
        return gxzdSearchService.searchMatchTeam(keyWord,ids,size);
    }
}
