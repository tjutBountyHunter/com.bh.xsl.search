package com.xsl.search.service.message;

import com.xsl.search.service.es.EsServer;
import com.xsl.search.service.mapper.ItemMapper;
import com.xsl.search.service.common.pojo.SearchItem;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 监听商品添加消息，接收消息后，将对应的商品信息同步到索引库
 * <p>Title: ItemAddMessageListener</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 * @version 1.0
 */
public class ItemAddMessageListener implements MessageListener {

	@Autowired
	private ItemMapper itemMapper;

	private EsServer esServer;


	@Override
	public void onMessage(Message message){

		try {

			System.out.println("Add_Item_MQ");
			// 创建client
			esServer = new EsServer();
			TransportClient client = esServer.getClient();

			BulkRequestBuilder bulkBuilder = client.prepareBulk();

			//从消息中取商品id
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			Long itemId = new Long(text);
			//等待事务提交
			Thread.sleep(1000);
			//根据商品id查询商品信息
			SearchItem searchItem = itemMapper.getItemById(itemId);
			if(searchItem == null){
				throw new Exception("添加任务过程中查询数据库失败");
			}
			//向文档对象中添加域
			bulkBuilder.add(client.prepareIndex("test", "item",searchItem.getId().toString())
					.setSource(
							XContentFactory.jsonBuilder()
									.startObject()
									.field("cid", searchItem.getCid())
									.field("descr", searchItem.getDescr())
									.field("money", searchItem.getMoney())
									.field("state", searchItem.getState())
									.field("send_id", searchItem.getSendid())
									.field("update_date", searchItem.getUpdatedate())
									.field("create_date", searchItem.getCreatedate())
									.field("deadline", searchItem.getDeadline())
									.field("number", searchItem.getNumber())
									.field("name", searchItem.getName())
									.field("url", searchItem.getUrl())
									.field("task_num", searchItem.getTask_name())
									.field("task_id",searchItem.getTaskid())
									.field("level",searchItem.getLevel())
									.endObject()
					)
			);
			//提交
			BulkResponse response = bulkBuilder.get();
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
