package service;


import com.xsl.search.export.vo.ResBaseVo;

import java.net.UnknownHostException;

public interface SearchItemService{
	ResBaseVo importAllItems() throws UnknownHostException;
	ResBaseVo importAllHunter() throws UnknownHostException;
}
