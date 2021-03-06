package com.xsl.search.service.common.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

//获取搜索节点
public class EsServer {
    private volatile static TransportClient client;
    //三个节点
    private static String[] hosts = new String[] {
            "47.93.19.164",
            "47.93.230.61",
            "47.93.200.190"
    };
    private EsServer(){}

    public static TransportClient getClient(){
        if(client == null){
            synchronized (EsServer.class){
                if(client == null){
                    // 设置集群名称
                    Settings settings = Settings.builder().put("cluster.name", "xsl").build();
                    // 创建client
                    try {
                        int count = 0;
                        for(count=0;count<hosts.length;count++){
                            client = new PreBuiltTransportClient(settings)
                                    .addTransportAddress(new TransportAddress(InetAddress.getByName(hosts[count]),9300));
                            if(client.connectedNodes().isEmpty() == false) break;
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
        return client;
    }

}
