package listener;

import com.xsl.search.export.vo.SearchHunter;
import com.xsl.search.service.common.util.EsServer;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class HunterUpdateMessageListener implements MessageListener {


    private EsServer esServer;


    @Override
    public void onMessage(Message message){
        try {
            TransportClient client = EsServer.getClient();
            UpdateRequest request = new UpdateRequest();
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            //等待事务提交
            Thread.sleep(1000);
            //根据商品id查询商品信息
            SearchHunter searchHunter = null;
            if(searchHunter == null){
                throw new Exception("更新猎人时查询数据表失败");
            }
            //向文档对象中添加域

            XContentBuilder builder = XContentFactory.jsonBuilder()
                                    .startObject()
                                    .field("level", searchHunter.getLevel())
                                    .field("descr", searchHunter.getDescr())
                                    .field("empirical", searchHunter.getEmpirical())
                                    .field("task_acc_num",searchHunter.getTaskaccnum())
                                    .field("task_fail_num", searchHunter.getTaskfailnum())
                                    .field("credit", searchHunter.getCredit())
                                    .field("last_time", searchHunter.getLasttime())
                                    .endObject();

            //提交
            UpdateResponse response = client.prepareUpdate("test2", "hunter",String.valueOf(itemId)).setDoc(builder).get();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
