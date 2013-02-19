/**
 * 商品管理
 * @date 2011-12-05
 * */
package com.winekee.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winekee.common.bean.Actmanage;
import com.winekee.common.bean.Goods;
import com.winekee.common.bean.GoodsComment;
import com.winekee.common.util.Page;

public interface IGoodsService_ms 
{
	
	/**
	 * 查看配置信息
	 * @param String htmlpath htmlpath信息
	 * @return List 商品信息
	 * */
	public List findHtmlpageByhtmlpath(String htmlpaths);
	
	/**
	 * 查询特价专栏
	 * @param list 商品id
	 * @return 商品列表
	 */
	public List findGoodsByIds(List list);
	
	/**
	 * 查看商品信息
	 * @param int id 商品编号
	 * @return Goods 商品信息
	 * */
	public Goods findGoodsById(int id) ;
	
	public List<Goods> getGoodsByIds(List ids);
	
	/**
	 * 首页获取商品评论滚动内容 默认查看前50条显示的评论
	 * @param null
	 * @return List<GoodsComment> 滚动封装List
	 * */
	public List<GoodsComment> findGoodsComment() ;
		
	/**
	 * 查看商品列表信息
	 * @param String keyword 搜索关键字 , String situation 场合,String nature 个性,Sting intervalprice 价格 ,
	 * @param String category 种类, String country 产地, String grapcetype葡萄品种,
	 * @param String specification 规格, String pagenum
	 * @return List 商品信息
	 * */
	public Page goodslist(String ksort,String keyword , String situation, String nature, String intervalprice,
			String category, String country, String grapcetype, String specification, String pagenum);

	/**
	 * 查看商品信息
	 * @param int id 商品编号
	 * @return Goods 商品信息(用于满赠送)
	 * */
	public Goods findGoodsByGift(int goodsid2);

	/**
	 * 重新加载一条商品信息
	 * @param int id 商品编号
	 * @return boolean  成功= true .
	 * */
	public boolean initHtm(int id);

	/**
	 * 重新加载所有商品信息
	 * */
	public void initAllHtm();

	/**
	 * 提交用户对商品的评论信息
	 * @param GoodsComment gc
	 * @return void
	 * */
	public void commentAdd(GoodsComment gc);
	/**
	 * 重新加载段范围内商品信息
	 * @param int id 商品编号
	 * @return boolean  成功= true .
	 * */
	public boolean initHtm(int id1, int id2);
	/**
	 * 查看酒具列表信息
	 * @param Sting intervalprice 价格 , String category 种类 , String pagenum 页面
	 * @return List 商品信息
	 * */
	public Page toollist(String intervalprice, String category, String pagenum,String ksort);

	/**
	 * 查看一个活动内容
	 * @param int id1 商品编号,type 活动类型
	 * @return Actmanage
	 * */
	public Actmanage findOneActManageConfirm(String goodsid , String type);

	/**
	 *  限时抢购提交订单
	 *  @param HttpServletRequest request, Actmanage act 活动表 , int goodsnum 购买限时抢购商品数量
	 *  @return int orderid
	 * */
	public int saveQiangOrder(HttpServletRequest request, Actmanage act , int goodsnum);
	/**
	 *  聚便宜提交订单
	 *  @param HttpServletRequest request, Actmanage act 活动表 , int goodsnum 购买聚便宜商品数量
	 *  @return int orderid
	 * */
	public int saveJpyOrder(HttpServletRequest request, Actmanage act , int goodsnum);
	
	/**
	 * 重新加载导入商品信息
	 * @param int id 商品编号
	 * @return boolean  成功= true .
	 * */
	public boolean initOtherHtm(int id) ;
	
	/**
	 * 重新加载段范围内商品信息
	 * @param int id 商品编号
	 * @return boolean  成功= true .
	 * */
	@SuppressWarnings("unchecked")
	public boolean initGoodsOtherHtm(int id1, int id2) ;
	
	/**
	 * 一淘 FullIndex.xml:全量更新每24个小时发生一次
	 */
	public void initFullIndexXml();
	
	/**
	 * 查看商品列表信息
	 * @param  String keyword1 搜索关键字1 ,String keyword2 搜索关键字2,String goodsparentcategory 大类 ,
	 * @param String category  小种类,  String pagenum
	 * @return List 商品信息
	 * */
	public Page exchangeList(String ksort,String  goodsparentcategory ,String  goodscategory ,String  keyword1 ,String  keyword2 ,String  pagenum) ;
	
	/**
	 *  秒杀活动提交订单
	 *  @param HttpServletRequest request, Actmanage act 活动表 , int goodsnum 购买秒杀活动商品数量
	 *  @return int orderid
	 * */
	public int saveMsOrder(HttpServletRequest request, Actmanage act , int goodsnum);
}
