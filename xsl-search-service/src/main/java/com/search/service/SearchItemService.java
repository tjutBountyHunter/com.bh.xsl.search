package com.search.service;

import com.search.common.XslResult;

import java.net.UnknownHostException;

public interface SearchItemService{
	XslResult importAllItems() throws UnknownHostException;
	XslResult importAllHunter() throws UnknownHostException;
}
