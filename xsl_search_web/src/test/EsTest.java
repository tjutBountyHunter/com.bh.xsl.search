//import service.EsServer;
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.elasticsearch.action.ActionFuture;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchRequestBuilder;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchType;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.lucene.search.function.CombineFunction;
//import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
//import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
//import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
//import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
//import org.elasticsearch.script.Script;
//import org.elasticsearch.script.ScriptType;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.ScriptSortBuilder;
//import org.elasticsearch.search.sort.SortBuilder;
//import org.elasticsearch.search.sort.SortBuilders;
//import org.elasticsearch.search.sort.SortOrder;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.junit.Test;
//
//import javax.jms.*;
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.concurrent.ExecutionException;
//
//import static org.elasticsearch.index.query.QueryBuilders.functionScoreQuery;
//
///**
// * 官方参考文档
// * https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/transport-client.html
// */
//
//public class EsTest {
//    @Test
//    public void test1() throws UnknownHostException, ExecutionException, InterruptedException {
//
//        //建立script
//
//        // 设置集群名称
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        // 创建client
//        TransportClient client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(new TransportAddress(InetAddress.getByName("47.93.19.164"),9300));
//        // 搜索数据
//
//
//
////       QueryBuilder build = QueryBuilders.multiMatchQuery("手机","item_title","item_sell_point");
//
//
//        String index = "test5";
//        String type = "item";
//        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index).setTypes(type);
//        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
////分页
//        searchRequestBuilder.setFrom(0).setSize(10);
////explain为true表示根据数据相关度排序，和关键字匹配最高的排在前面
//        searchRequestBuilder.setExplain(true);
//
//
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.matchQuery("item_title", "手机"));
//
//
//        //score
//
//
//        ScoreFunctionBuilder<?> scoreFunctionBuilder = ScoreFunctionBuilders.fieldValueFactorFunction("click").modifier(FieldValueFactorFunction.Modifier.LN1P).factor(0.1f);
//        FunctionScoreQueryBuilder query = functionScoreQuery(queryBuilder,scoreFunctionBuilder).boostMode(CombineFunction.SUM);
//
//        searchRequestBuilder.setQuery(query);
//
//        SearchResponse searchResponse = searchRequestBuilder.execute().get();
//
//
//
//
//
//        SearchHits hits = searchResponse.getHits();
//        System.out.print("hit:"+hits.totalHits);
//        System.out.print("\n");
////        // 输出结果
//       for(SearchHit hit:hits){
//           System.out.println(hit.getId()+":"+hit.getScore());
//
//       }
//        // 关闭client
//        client.close();
//    }
//
//    @Test
//    public void test2() throws IOException, JMSException {
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://47.93.230.61:61616");
//        Connection connection = connectionFactory.createConnection();
//        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
//        Topic topic = session.createTopic("alterTaskMQ");
//        MessageProducer producer = session.createProducer(topic);
//        TextMessage textMessage = session.createTextMessage("1985");
//        producer.send(textMessage);
//        producer.close();
//        session.close();
//    }
//
//    @Test
//    public void test3() throws UnknownHostException{
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        // 创建client
//        TransportClient client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(new TransportAddress(InetAddress.getByName("47.93.19.164"),9300));
//
//        GetResponse response = client.prepareGet("test4","item","847278").get();
//
//        Integer click = (Integer) response.getSourceAsMap().get("item_price");
//
//        System.out.print(click);
//    }
//
//    public EsTest() {
//    }
//
//    @Test
//    public void test4(){
//
////        nowTime.add(Calendar.DAY_OF_MONTH, -1);
////        nowTime.add(Calendar.HOUR_OF_DAY, -1);
////        nowTime.add(Calendar.DAY_OF_MONTH, -1);
////        int year = nowTime.get(Calendar.YEAR);
////        int month = nowTime.get(Calendar.MONTH) + 1;
////        int day =nowTime.get(Calendar.DAY_OF_MONTH);
////        int hour = nowTime.get(Calendar.HOUR);
////        int min = nowTime.get(Calendar.MINUTE);
//        String scriptCode = "long data_millis = doc['date'].date.millis;" +
//                "long now_millis  = params.now_millis;" +
//                "long difference = now_millis - data_millis + 28800000;" +
//                "long difference_of_hour = difference/3600000;" +
//                "long click=doc['click'].value;" +
//                "double click_score;" +
//                "if(click<=20) click_score=click*0.25;" +
//                "else if(click>20) click_score=20*0.25+(click-20)*0.1;" +
//                "double score = 10*(_score+click_score)/(difference_of_hour+5);" +
//                "return score;";
//        // 创建client
//        EsServer esServer;
//        esServer = new EsServer();
//        TransportClient client = esServer.getClient();
//        Map<String, Object> params = new HashMap<>();
//        Calendar nowTime = Calendar.getInstance();
//        params.put("now_millis", nowTime.getTimeInMillis());
//        Script script = new Script(ScriptType.INLINE,"painless",scriptCode,params);
//        SortBuilder sortBuilder = SortBuilders.scriptSort(script, ScriptSortBuilder.ScriptSortType.NUMBER);
//        sortBuilder.order(SortOrder.DESC);
//        SearchSourceBuilder sb = new SearchSourceBuilder();
//        sb.sort(sortBuilder);
//        System.out.println(sb.toString());
//
//        ActionFuture<SearchResponse> search = client.search(new SearchRequest(new String[] { "test" }, sb));
//        SearchResponse haha =  search.actionGet();
//        for (SearchHit hit : haha.getHits()) {
//            System.out.println(hit.getId()+":"+hit.getScore()+" "+hit.getSourceAsMap().get("date")+"\n");
//        }
//    }
//    @Test
//    public void test5() throws ParseException {
//
//        Map<String, Object> params = new HashMap<>();
//        Calendar nowTime = Calendar.getInstance();
//        params.put("now_millis", nowTime.getTimeInMillis());
//        String inlineScript = "long data_millis = doc['date'].date.millis;" +
//                "long now_millis  = params.now_millis;" +
//                "long difference = now_millis - data_millis + 28800000;" +
//                "long difference_of_hour = difference/3600000;" +
//                "long click=doc['click'].value;" +
//                "double click_score;" +
//                "if(click<=20) click_score=click*0.25;" +
//                "else if(click>20) click_score=20*0.25+(click-20)*0.1;" +
//                "double score = 10*(_score+click_score)/(difference_of_hour+5);" +
//                "return score;";
//        Script script = new Script( ScriptType.INLINE, "painless",inlineScript, params);
//        ScriptScoreFunctionBuilder scriptBuilder = ScoreFunctionBuilders.scriptFunction(script);
//
//        EsServer esServer;
//        esServer = new EsServer();
//        TransportClient client = esServer.getClient();
//
//
//
//
//
//        SearchRequestBuilder requestBuilder = client.prepareSearch("test")
//                .setTypes("item")
//                .setQuery(functionScoreQuery(QueryBuilders.matchPhraseQuery("item_title", "手机"),scriptBuilder));
//        SearchResponse response = requestBuilder.setFrom(0).setSize(5).execute().actionGet();
//
//
//        SearchHit[] hits = response.getHits().getHits();
//        for(int j =0;j<hits.length;j++){
//            System.out.println(hits[j].getId()+":");
//            System.out.println(hits[j].getScore()+"  "+hits[j].getSourceAsMap().get("date"));
//        }
//
//    }
//    @Test
//    public void test6() throws IOException, JMSException, ExecutionException, InterruptedException {
//        EsServer esServer;
//        esServer = new EsServer();
//        TransportClient client = esServer.getClient();
//
//        String inlineScript = "ctx._source.click=ctx._source.click+1;";
//
//        Map<String, Object> params = new HashMap<>();
//        Script script = new Script(ScriptType.INLINE, "painless",inlineScript, params);
//
////        UpdateRequestBuilder rs = client.prepareUpdate("test","item","830972").setScript(script);
////        rs.get();
//
//
//        UpdateRequest request = new UpdateRequest();
//        request.index("test")
//                .type("item")
//                .id("830972")
//                .script(script);
//        client.update(request).get();
//    }
//
//    @Test
//    public void test7() throws IOException, JMSException, ExecutionException, InterruptedException {
//        EsServer esServer;
//        esServer = new EsServer();
//        TransportClient client = esServer.getClient();
//
//        //获取搜索日志
//        SearchRequestBuilder requestBuilder = client.prepareSearch("test").setTypes("log").setQuery(QueryBuilders.matchAllQuery());
//        //聚合分析查询出现次数最多的10个词汇
//        SearchResponse actionGet = requestBuilder.addAggregation(AggregationBuilders.terms("hotWord").field("keywords").size(10)).execute().actionGet();
//        //获取分析后的数据
//        Aggregations aggregations = actionGet.getAggregations();
//        Terms trem = aggregations.get("hotWord");
//        List<Terms.Bucket> buckets = (List<Terms.Bucket>) trem.getBuckets();
//        List<String> hotWords = new ArrayList<>();
//        for (Terms.Bucket bucket : buckets) {
//            String key = (String) bucket.getKey();
//            hotWords.add(key);
//        }
//    }
//    @Test
//    public void test8() throws IOException, JMSException, ExecutionException, InterruptedException, ParseException {
//
//        String date = "2018-05-20T16:00:00.000Z";
//        date = date.replace("Z", " UTC");//注意是空格+UTC
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
//        Date d = format.parse(date);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(d);
//        System.out.print(calendar.get(Calendar.HOUR_OF_DAY));
//    }
//
//    @Test
//    public void test9() throws IOException {
////        PythonInterpreter interpreter = new PythonInterpreter();
////        PySystemState sys = Py.getSystemState();
////        sys.path.add("/Users/jiangzeyu/eclipse-workspace/key-word-check/src/test/sources/jieba-master-1/build/lib");
////        sys.path.add("/Users/jiangzeyu/eclipse-workspace/key-word-check/src/test/sources/jieba-master-1/build/lib/jieba");
////        //sys.path.add("/Users/jiangzeyu/eclipse-workspace/key-word-check/Jython/Lib");
//////        interpreter.exec("path = \"/Users/jiangzeyu/eclipse-workspace/key-word-check/Jython/Lib\"");
////        //InputStream filepy = new FileInputStream("classpath: jieba-master-1/test/try.py");
////        InputStream filepy = new FileInputStream("/Users/jiangzeyu/eclipse-workspace/key-word-check/src/test/sources/jieba-master-1/demo/demo2.py");
////        ByteArrayOutputStream baos=new   ByteArrayOutputStream();
////        interpreter.setOut(baos);
////
////
////
////
////        String str = new String("明天电&&&工**车&&队cheduidaikao北区拿快递".getBytes(),"UTF-8");
////        str = str.replace("&","");
////        str = str.replace("*","");
////        str = str.replace("$","");
////        str = str.replace("%","");
////        byte[] bytes=str.getBytes();
////
////        interpreter.exec("text="+ new PyByteArray(bytes));
////        interpreter.execfile(filepy);
////
////        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
////        BufferedReader in = new BufferedReader(new InputStreamReader(swapStream));
////        String line;
////        System.out.println("*******************");
////
////        List<String> list = new ArrayList<String>(10);
////        while ((line = in.readLine()) != null) {
////            String[] strArray = line.split(" ");
////            list.add(strArray[0]);
////            if(strArray[1].equals("nz")){
////                System.out.println("unlegal: "+strArray[0]);
////            }
////        }
////
////        System.out.println("------------------");
////
////        for(String s : list){
////            System.out.println(s);
////        }
//
//
////        PyFunction func = (PyFunction)interpreter.get("fenxi",PyFunction.class);
////        PyObject pyobj = func.__call__(new PyString(str));
////        System.out.println("anwser = " + pyobj.toString());
//    }
//}
