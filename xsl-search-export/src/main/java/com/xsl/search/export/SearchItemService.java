package com.xsl.search.export;

import com.xsl.search.service.common.XslResult;

import java.net.UnknownHostException;

public interface SearchItemService{
	XslResult importAllItems() throws UnknownHostException;
	XslResult importAllHunter() throws UnknownHostException;
}
