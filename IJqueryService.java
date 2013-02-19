/**
 * jquery调用处理内容
 * @date 2011-12-28
 * */
package com.winekee.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.winekee.common.bean.MemberAddress;

public interface IJqueryService
{
	
	/**
	 * 修改购物车数量
	 * @param session HttpSession , request HttpServletRequest
	  * @return 购物车商品信息
	 */
	public String cartGoodsnumChange(HttpServletRequest request , HttpServletResponse response);
	
	
	/**
	 * 获取会员地址列表
	 * @param int memberid 会员id
	 * @return List 地址列表
	 * */
	public List<MemberAddress> getMemberAddrList(String memberid);
	
	/**
	 * 根据id获取收货地址
	 * @param id
	 * @return
	 */
	public MemberAddress getMemberAddrById(int addressId);
	
	/**
	 * 更新用户地址
	 */
	public void updateMemberAddr(MemberAddress address);
	
	/**
	 * 新增用户地址
	 * @param address
	 * @return
	 */
	public void addMemberAddr(MemberAddress address);
	
	/**
	 * 设置默认收货地址
	 * @param addrMap
	 * @return
	 */
	public boolean setDefaultMemberAddr(MemberAddress address);
	
	/**
	 * 删除收货地址
	 * @param addressid
	 * @return
	 */
	public void deleteMemberAddr(MemberAddress address);
	
	/**
	 * 根据红酒卡号获取卡对象
	 * @param cardNum
	 * @return
	 */
	//public Card getCardByCardNum(String cardNum);
	
	/**
	 * 新增收藏夹信息
	 * @param memberid 会员编号，goodsid 商品编号，username 用户名称
	 */
	public void favoriteAdd(String memberid , String goodsid , String username , String goodsname);
	
	/**
	 * 根据用户名查找用户是否注册
	 * @param String username
	 * @return String 0=存在此用户 ，1=此用户未注册
	 */
	public String getMemberByUsername(String username);
	
	/**
	 * 判断商品是否已加入收藏夹
	 * @param memberid 用户编号, goodsid 商品编号
	 * @return 是否已加入
	 */
	public boolean findOneFavorite(String memberid , String goodsid );

	/**
	 * 获取商品评论列表
	 * @param String index 页码, String goodsid 商品编号
	 * @return 封装商品评论列表信息
	 * */
	public StringBuilder comment(int index, String goodsid) ;
	
	/**
	 * 获取用户最近浏览记录+保存用户当前浏览商品信息
	 * @param HttpServletRequest request , HttpServletResponse response
	 * @return StringBuilder
	 */
	public StringBuilder goodsHistory(HttpServletRequest request , HttpServletResponse response);
	
	/**
	 * 根据商品id查询商品信息
	 * @param ids
	 * @return
	 */
	public List findGoodsByIds(String ids);


	/**
	 * 商品评论提交
	 * @param String verifycode, String session_code, String goodsid , String content
	 * @return StringBuilder
	 * */
	public StringBuilder commentAdd(String verifycode, String session_code, String goodsid , String content);

	/**
	 * 优惠券抵扣
	 * @param HttpServletRequest request
	 * @retun String 优惠券抵扣结果
	 * */
	public String findCardByCardNum(HttpServletRequest request , HttpServletResponse response);
	
	/**
	 * 查询出当前限时抢购商品数量
	 * @param String id
	 * @return int 限时抢购商品数量
	 */
	public int findoneActManageBuynum(String id);
	
	/**
	 * 根据eventid查找用户是否已经报名品酒会
	 * @param String memberid 用户ID , String eventid 品酒会ID
	 * @return boolean true 已报名
	 */
	public boolean getMemberByEventId(String memberid, String eventid);
	
	/**
	 * 人气酒top10票选:2012-12
	 * @param HttpServletRequest request
	 * @return String result 投票结果 ：-1 尚未登录 1 已参加过投票活动 0 投票成功
	 * */
	public String top10Vote(HttpServletRequest request);
	
	/**
	 * 国家月信息展示
	 * @param request
	 * @return
	 */
	public String countrymonth(HttpServletRequest request);

	/**
	 * 查找单一商品 综合评分 和 销售总价
	 * @param String goodsid
	 * @return String
	 * */
	public String findGoodsRateSalecount(String goodsid);
	
	/**
	 * 红酒客官网活动报名：type1
	 * @param HttpServletRequest request
	 * @param int eventid  活动id
	 * @param String memo  备注信息
	 * @return String result 报名结果 ：-1 尚未登录
	 * */
	public String activity(HttpServletRequest request, int eventid, String memo);

	/**
	 * ‘立即兑换’ 商品时判断用户葡萄是否足够兑换商品
	 * @param HttpServletRequest request
	 * @return String
	 * */
	public String confirmGoodExchange(HttpServletRequest request);
	
	/**
	 * 发送激活邮件
	 * @param request
	 * @return
	 */
	public String sendActiveEmail(HttpServletRequest request);
	
	/**
	 * 红酒客官网活动报名：type2
	 * @param HttpServletRequest request
	 * @param int eventid  活动id
	 * @return String result 报名结果 ：-1 尚未登录
	 * */
	public String activity2(HttpServletRequest request, int eventid);
	
	/**
	 * 修改购物车数量后，当有换购商品时，获取换购商品显示html
	 * @param request
	 * @return
	 */
	public String getCoudHtml(HttpServletRequest request);


	/**
	 * 携程抵用券抵扣
	 * @param request
	 * @return 提示信息
	 */
	public String checkCard(HttpServletRequest request);
}
