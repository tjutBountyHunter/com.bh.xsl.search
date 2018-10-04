package com.search.message;

import com.search.common.pojo.SearchItem;
import com.search.es.EsServer;
import com.search.mapper.ItemMapper;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemUpdateMessageListener implements MessageListener {
    @Autowired
    private ItemMapper itemMapper;


    private EsServer esServer;


    @Override
    public void onMessage(Message message){
        try {

            System.out.println("Update_Item_MQ");
            esServer = new EsServer();
            TransportClient client = esServer.getClient();
            UpdateRequest request = new UpdateRequest();
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            //等待事务提交
            Thread.sleep(1000);
            //根据商品id查询商品信息
            SearchItem searchItem = itemMapper.getItemById(itemId);
            //向文档对象中添加域

            XContentBuilder builder = XContentFactory.jsonBuilder()
                                    .startObject()
                                    .field("cid", searchItem.getCid())
                                    .field("descr", searchItem.getDescr())
                                    .field("money", searchItem.getMoney())
                                    .field("state", searchItem.getState())
                                    .field("send_id", searchItem.getSendid())
                                    .field("update_date", searchItem.getUpdatedate())
                                    .field("createDate", searchItem.getCreatedate())
                                    .field("deadline", searchItem.getDeadline())
                                    .field("num", searchItem.getNum())
                                    .endObject();

            //提交

            UpdateResponse response = client.prepareUpdate("test", "item",String.valueOf(itemId)).setDoc(builder).get();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
