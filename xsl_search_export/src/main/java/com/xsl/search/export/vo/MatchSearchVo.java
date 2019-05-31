package com.xsl.search.export.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 说明：
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:28
 * @Description:
 */
public class MatchSearchVo implements Serializable {
	private String matchid;

	private String matchname;

	private String matchaddress;

	private String matchposterurl;

	private String matchinfo;

	public String getMatchid() {
		return matchid;
	}

	public void setMatchid(String matchid) {
		this.matchid = matchid;
	}

	public String getMatchname() {
		return matchname;
	}

	public void setMatchname(String matchname) {
		this.matchname = matchname;
	}

	public String getMatchaddress() {
		return matchaddress;
	}

	public void setMatchaddress(String matchaddress) {
		this.matchaddress = matchaddress;
	}

	public String getMatchposterurl() {
		return matchposterurl;
	}

	public void setMatchposterurl(String matchposterurl) {
		this.matchposterurl = matchposterurl;
	}

	public String getMatchinfo() {
		return matchinfo;
	}

	public void setMatchinfo(String matchinfo) {
		this.matchinfo = matchinfo;
	}
}
