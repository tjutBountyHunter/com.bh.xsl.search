package com.xsl.search.service.impl;

import com.xsl.search.export.vo.ResBaseVo;
import com.xsl.search.export.vo.SearchHunter;
import com.xsl.search.export.vo.SearchItem;
import com.xsl.search.service.es.EsServer;
import com.xsl.search.service.mapper.HunterMapper;
import com.xsl.search.service.mapper.ItemMapper;
import com.xsl.search.export.SearchItemService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;

//import cn.e3mall.common.utils.E3Result;

/**
 * 索引库维护Service
 * <p>Title: SearchItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private HunterMapper hunterMapper;

	private EsServer esServer;
	
	@Override
	public ResBaseVo importAllItems() throws UnknownHostException {
		// 创建client
		esServer = new EsServer();
		TransportClient client = esServer.getClient();
		BulkRequestBuilder bulkBuilder = client.prepareBulk();
		try {
			//查询商品列表
			List<SearchItem> itemList = itemMapper.getItemList();
			Calendar nowTime = Calendar.getInstance();

			//遍历商品列表
			for (SearchItem searchItem : itemList) {
				System.out.print(searchItem.getCreatedate());
				bulkBuilder.add(client.prepareIndex("test", "item",searchItem.getId()+"")
								.setSource(
										 XContentFactory.jsonBuilder()
										.startObject()
												 .field("cid", searchItem.getCid())
												 .field("descr", searchItem.getDescr())
												 .field("send_id", searchItem.getSendid())
												 .field("money",searchItem.getMoney())
												 .field("state", searchItem.getState())
												 .field("create_date", searchItem.getCreatedate())
												 .field("number", searchItem.getNumber())
												 .field("update_date",searchItem.getUpdatedate())
												 .field("deadline",searchItem.getDeadline())
												 .field("url",searchItem.getUrl())
												 .field("name",searchItem.getName())
												 .field("task_name",searchItem.getTask_name())
												 .field("task_id",searchItem.getTaskid())
												 .field("level",searchItem.getLevel())
										.endObject()
								)
				);
			}
			//提交
			BulkResponse response = bulkBuilder.get();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
			client.close();
			return ResBaseVo.build(500, "数据导入发生异常");
		}
		return ResBaseVo.ok();
	}

	@Override
	public ResBaseVo importAllHunter() throws UnknownHostException {
		// 创建client
		esServer = new EsServer();
		TransportClient client = esServer.getClient();
		BulkRequestBuilder bulkBuilder = client.prepareBulk();
		try {
			//查询商品列表
			List<SearchHunter> hunterList = hunterMapper.getHunterList();
			Calendar nowTime = Calendar.getInstance();

			//遍历商品列表
			for (SearchHunter searchHunter : hunterList) {
				bulkBuilder.add(client.prepareIndex("test2", "hunter",searchHunter.getId()+"")
						.setSource(
								XContentFactory.jsonBuilder()
										.startObject()
										.field("level", searchHunter.getLevel())
										.field("descr", searchHunter.getDescr())
										.field("empirical", searchHunter.getEmpirical())
										.field("task_acc_num",searchHunter.getTaskaccnum())
										.field("task_fail_num", searchHunter.getTaskfailnum())
										.field("credit", searchHunter.getCredit())
										.field("last_time", searchHunter.getLasttime())
										.field("state",searchHunter.getState())
										.field("url",searchHunter.getUrl())
										.endObject()
						)
				);
			}
			//提交
			BulkResponse response = bulkBuilder.get();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
			client.close();
			return ResBaseVo.build(500, "数据导入发生异常");
		}
		return ResBaseVo.ok();
	}

}
