package com.xsl.search.export.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 说明：
 *
 * @Auther: 11432_000
 * @Date: 2019/5/25 17:30
 * @Description:
 */
public class MatchTeamSearchVo implements Serializable {

	private String teamid;

	private String teamname;

	private String teamslogan;

	private String teamlogourl;

	private String matchname;


	public String getTeamid() {
		return teamid;
	}

	public void setTeamid(String teamid) {
		this.teamid = teamid;
	}

	public String getTeamname() {
		return teamname;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public String getTeamslogan() {
		return teamslogan;
	}

	public void setTeamslogan(String teamslogan) {
		this.teamslogan = teamslogan;
	}

	public String getTeamlogourl() {
		return teamlogourl;
	}

	public void setTeamlogourl(String teamlogourl) {
		this.teamlogourl = teamlogourl;
	}

	public String getMatchname() {
		return matchname;
	}

	public void setMatchname(String matchname) {
		this.matchname = matchname;
	}
}
