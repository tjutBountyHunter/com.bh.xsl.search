package listener;

import com.xsl.search.export.vo.TaskInfoVo;
import com.xsl.search.service.common.util.GsonSingle;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import com.xsl.search.service.common.util.EsServer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.io.Serializable;

public class AddTaskListener implements MessageListener {

    private EsServer esServer;

    @Override
    public void onMessage(Message message) {
        // 创建client
        TransportClient client = EsServer.getClient();
        BulkRequestBuilder bulkBuilder = client.prepareBulk();

        try {
            //从消息中取商品id
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			TaskInfoVo taskInfo = GsonSingle.getGson().fromJson(text, TaskInfoVo.class);

                    //向文档对象中添加域
            bulkBuilder.add(client.prepareIndex("task_info", "task",taskInfo.getTaskId())
                    .setSource(
                            XContentFactory.jsonBuilder()
                                    .startObject()
                                    .field("cid", taskInfo.getCid())
                                    .field("content", taskInfo.getContent())
                                    .field("createDate", taskInfo.getCreateDate())
                                    .field("deadLineDate", taskInfo.getDeadLineDate())
                                    .field("masterId", taskInfo.getMasterId())
                                    .field("masterlevel", taskInfo.getMasterlevel())
                                    .field("money", taskInfo.getMoney())
                                    .field("name", taskInfo.getName())
                                    .field("sex", taskInfo.getSex())
                                    .field("sourcetype", taskInfo.getSourcetype())
                                    .field("state", taskInfo.getState())
                                    .field("taskId", taskInfo.getTaskId())
                                    .field("taskTitle",taskInfo.getTaskTitle())
                                    .field("txUrl",taskInfo.getTxUrl())
                                    .field("updatedate",taskInfo.getUpdatedate())
                                    .endObject()
                    )
            );
            //提交
            BulkResponse response = bulkBuilder.get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
