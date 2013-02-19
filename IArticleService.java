/***
 * 文章管理
 */
package com.winekee.web.service;

import com.winekee.common.bean.Article;

public interface IArticleService {
	/**
	 * 查询文章详细信息
	 * @param id 文章编号
	 * @return 文章内容
	 */
	public Article findOneArticleById(String id);
}
