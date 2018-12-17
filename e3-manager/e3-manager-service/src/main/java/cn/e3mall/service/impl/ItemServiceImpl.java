package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.ImageUrlSplit;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;

/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Override
	public TbItem getItemById(long itemId) {
		//根据主键查询
		//TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(itemId);
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//取分页结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		//取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}

	@Override
	public E3Result addItem(TbItem item, String desc) {
		// 1、生成商品id
		long itemId = IDUtils.genItemId();
		// 2、补全TbItem对象的属性
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 3、向商品表插入数据
		itemMapper.insert(item);
		// 4、创建一个TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 5、补全TbItemDesc的属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 6、向商品描述表插入数据
		itemDescMapper.insert(itemDesc);
		// 7、E3Result.ok()
		return E3Result.ok();
	}

	@Override
	public E3Result deleteItems(Long[] ids) {
		//是否全部成功
		boolean flagAllSuccess=true;
		StringBuffer msg = new StringBuffer();
		for (Long id:ids) {
			E3Result e3Result = deleteItem(id);
			if (200 == e3Result.getStatus()) {
				msg.append("删除商品"+id+"成功！");
			}else if (300 == e3Result.getStatus()){
				msg.append("删除商品"+id+"失败！");
				flagAllSuccess=false;
			}
		}
		//如果全部成功，就返回status 200
		if (flagAllSuccess) {
				return E3Result.ok();
		} else { //有未成功的就返回 status 300
			E3Result result =new E3Result();
			result.setStatus(300);
			result.setMsg(msg.toString());
			return result;
		}
	}

	private E3Result deleteItem(Long id) {
		//删除图片
		//根据id取出图片信息
		TbItem tbItem = itemMapper.selectByPrimaryKey(id);
		String mulImage = tbItem.getImage();
		String[] mulPath= ImageUrlSplit.getMulUrl(mulImage);
		//FastDFS删除图片
		try {
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			for (String imagePath:mulPath) {
				int deleteDone = fastDFSClient.deleteFile(imagePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//删除tb_item_desc
		int itemDescDeleteDone = itemDescMapper.deleteByPrimaryKey( id);
		//删除tb_item
		int itemDeleteDone=itemMapper.deleteByPrimaryKey(id);
		E3Result result = E3Result.ok();
		return result;
	}
}
