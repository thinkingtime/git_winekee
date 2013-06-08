package com.founder.eastlaser.item.query;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.founder.eastlaser.common.base.BaseQuery;

@Component
public class ServItemQuery extends BaseQuery{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String title;
	
	private String descript;
	
	private Long personId;
	
	private Date begintime;
	
	private Date endtime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	
}