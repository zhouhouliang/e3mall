package cn.e3mall.controller;


import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 根据商品id返回json格式商品信息
	 * @param itemId 商品id
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}

	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		//调用服务查询商品列表
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	/**
	 *
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "/item/save")
	@ResponseBody
	public E3Result saveItem(TbItem item, String desc) {

		E3Result result = itemService.addItem(item, desc);
		return result;
	}

	@RequestMapping(value = "/rest/item/delete")
	@ResponseBody
	public E3Result deleteItem(String[] ids){
		Long[] idsLong=new Long[ids.length];
		for (int i = 0; i < ids.length; i++) {
			idsLong[i] = Long.parseLong(ids[i]);
		}
		E3Result e3Result = itemService.deleteItems(idsLong);
		return  e3Result;
	}
}
