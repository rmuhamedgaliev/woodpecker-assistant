package io.github.woodpeckeryt.youtracksdk.transport;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ApacheTransport implements Transport {
    private final String token;
    private final String host;

    public ApacheTransport(String token, String host) {
        this.token = token;
        this.host = host;
    }

    @Override
    public String sendGetRequest(String path) {
        String result = null;

        HttpGet request = new HttpGet(this.host + path);

        request.addHeader("Authorization", "Bearer " + this.token);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String sendPostRequest(String path, Object sendObject) {
        String result = "";

        try(
            CloseableHttpClient client = HttpClients.createDefault();
            ) {
            HttpPost httpPost = new HttpPost(this.host + path);
            String requestData = new Gson().toJson(sendObject);
            StringEntity entity = new StringEntity(requestData, StandardCharsets.UTF_8);
            httpPost.setHeader("Authorization", "Bearer " + this.token);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");

            CloseableHttpResponse response = client.execute(httpPost);

            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
