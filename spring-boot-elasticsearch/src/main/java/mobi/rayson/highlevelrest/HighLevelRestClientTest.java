package mobi.rayson.highlevelrest;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HighLevelRestClientTest {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = buildClient();
        ActionResponse response = delete(client);
        System.out.println(response);
        client.close();
    }

    private static RestHighLevelClient buildClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    private static IndexResponse index(RestHighLevelClient client) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "lee");
        jsonMap.put("age", 18);
        jsonMap.put("message", "Hello es. I am 18.");
        IndexRequest request = new IndexRequest("people", "student", "1").source(jsonMap);
        return client.index(request, RequestOptions.DEFAULT);
    }

    private static GetResponse get(RestHighLevelClient client) throws IOException {
        GetRequest request = new GetRequest("people", "student", "1");
        return client.get(request, RequestOptions.DEFAULT);
    }

    private static DeleteResponse delete(RestHighLevelClient client) throws IOException {
        DeleteRequest request = new DeleteRequest("people", "student", "1");
        return client.delete(request, RequestOptions.DEFAULT);
    }
}
