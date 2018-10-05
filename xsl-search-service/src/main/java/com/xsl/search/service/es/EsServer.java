package com.xsl.search.service.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;


//搜索节点
public class EsServer {
    private TransportClient client;
    private static String[] hosts = new String[] {
            "47.93.19.164",
            "47.93.230.61",
            "47.93.200.190"
    };
    public  EsServer(){
        // 设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "xsl").build();
        // 创建client
        try {
            int count = 0;
            for(count=0;count<hosts.length;count++){
                client = new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(InetAddress.getByName(hosts[count]),9300));
                if(client.connectedNodes().isEmpty() == false) break;
                System.out.println("node: "+ hosts[count] +"  connected fail");
            }
        } catch (Exception e1) {
            System.out.print("node1 connected fail");
            e1.printStackTrace();
        }
    }

    public  TransportClient getClient(){
        return client;
    }


}
