package com.xsl.search.service.controller.resourceImpl;

import com.xsl.search.export.GxzdRemoveSearchResource;
import org.springframework.beans.factory.annotation.Autowired;
import service.GxzdRemoveSearchService;

/**
 * @ClassName GxzdRemoveSearchResourceImpl
 * @Description TODO
 * @Author 11432
 * @DATE 2019/6/1 16:08
 */
public class GxzdRemoveSearchResourceImpl implements GxzdRemoveSearchResource {

    @Autowired
    private GxzdRemoveSearchService gxzdRemoveSearchService;

    @Override
    public boolean removeMatchUser(String hunterId) throws RuntimeException {
        return gxzdRemoveSearchService.removeMatchUser(hunterId);
    }

    @Override
    public boolean removeMatch(String matchId) throws RuntimeException {
        return gxzdRemoveSearchService.removeMatch(matchId);    }

    @Override
    public boolean removeMatchTeam(String teamId) throws RuntimeException {
        return gxzdRemoveSearchService.removeMatchTeam(teamId);    }
}
