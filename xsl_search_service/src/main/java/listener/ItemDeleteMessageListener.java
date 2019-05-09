package listener;

import com.xsl.search.service.common.util.EsServer;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemDeleteMessageListener implements MessageListener {


    private EsServer esServer;

    @Override
    public void onMessage(Message message) {

        try {
            System.out.println("Delete_Item_MQ");
            TransportClient client = EsServer.getClient();



            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            String itemId = textMessage.getText();
            System.out.print(textMessage.getText());
            //等待事务提交
            Thread.sleep(1000);
            //根据商品id查询商品信息
            DeleteResponse response = client.prepareDelete("test","item",itemId).get();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
