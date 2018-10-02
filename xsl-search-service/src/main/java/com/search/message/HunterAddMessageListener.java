package com.search.message;

import com.search.common.pojo.SearchHunter;
import com.search.es.EsServer;
import com.search.mapper.HunterMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class HunterAddMessageListener implements MessageListener {

    @Autowired
    private HunterMapper hunterMapper;

    private EsServer esServer;


    @Override
    public void onMessage(Message message){

        try {

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
            SearchHunter searchHunter = hunterMapper.getHunterById(itemId);
            //向文档对象中添加域
            bulkBuilder.add(client.prepareIndex("test2", "hunter",searchHunter.getId().toString())
                    .setSource(
                            XContentFactory.jsonBuilder()
                                    .startObject()
                                    .field("level", searchHunter.getLevel())
                                    .field("descr", searchHunter.getDescr())
                                    .field("empirical", searchHunter.getEmpirical())
                                    .field("task_acc_num",searchHunter.getTaskaccnum())
                                    .field("task_fail_num", searchHunter.getTaskfailnum())
                                    .field("credit", searchHunter.getCredit())
                                    .field("lasttime", searchHunter.getLasttime())
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
