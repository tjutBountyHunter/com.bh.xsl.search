package listener;

import com.xsl.search.service.common.util.EsServer;
import com.xsl.search.service.common.util.GsonSingle;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import vo.UpdateTaskVo;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class UpdateTaskListener implements MessageListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTaskListener.class);

	@Override
	public void onMessage(Message message) {
		try {
			TransportClient client = EsServer.getClient();
			//获取更新信息
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			UpdateTaskVo updateTaskVo = GsonSingle.getGson().fromJson(text, UpdateTaskVo.class);
			LOGGER.info("AddTaskListener TaskInfoVo:{}", GsonSingle.getGson().toJson(updateTaskVo));

			XContentBuilder builder = XContentFactory.jsonBuilder().startObject();

			if(!StringUtils.isEmpty(updateTaskVo.getMasterlevel())){
				builder.field("masterlevel", updateTaskVo.getMasterlevel());
			}

			if(!StringUtils.isEmpty(updateTaskVo.getName())){
				builder.field("name", updateTaskVo.getName());
			}

			if(!StringUtils.isEmpty(updateTaskVo.getState())){
				builder.field("state", updateTaskVo.getState());
			}

			if(!StringUtils.isEmpty(updateTaskVo.getTxUrl())){
				builder.field("txUrl",updateTaskVo.getTxUrl());
			}

			if(!StringUtils.isEmpty(updateTaskVo.getUpdatedate())){
				builder.field("updatedate",updateTaskVo.getUpdatedate());
			}

			builder.endObject();

			//提交
			UpdateResponse response = client.prepareUpdate("task_info", "task",updateTaskVo.getTaskId()).setDoc(builder).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
