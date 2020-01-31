package mobi.rayson.highlevelrest;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HighLevelRestClientTest {

    private RestHighLevelClient client;

    @Before
    public void setUp() throws Exception {
        client = buildClient();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void testCreateIndex() throws IOException {
        IndexResponse response = index(client);
        assertEquals(RestStatus.OK, response.status());
    }

    @Test
    public void testGetIndex() throws IOException {
        GetResponse response = get(client);
        String index = response.getIndex();
        assertEquals("people", index);

        Map<String, Object> sources = response.getSource();
        assertEquals("lee", sources.get("name"));
    }

    @Test
    public void testExists() throws IOException {
        GetRequest request = new GetRequest("people", "student", "1");
        assertTrue(client.exists(request, RequestOptions.DEFAULT));
    }

    private static RestHighLevelClient buildClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    private IndexResponse index(RestHighLevelClient client) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>(4);
        jsonMap.put("name", "lee");
        jsonMap.put("age", 18);
        jsonMap.put("message", "Hello es. I am 18.");
        IndexRequest request = new IndexRequest("people", "student", "1").source(jsonMap);
        return client.index(request, RequestOptions.DEFAULT);
    }

    private GetResponse get(RestHighLevelClient client) throws IOException {
        GetRequest request = new GetRequest("people", "student", "1");
        return client.get(request, RequestOptions.DEFAULT);
    }

    private DeleteResponse delete(RestHighLevelClient client) throws IOException {
        DeleteRequest request = new DeleteRequest("people", "student", "1");
        return client.delete(request, RequestOptions.DEFAULT);
    }
}
