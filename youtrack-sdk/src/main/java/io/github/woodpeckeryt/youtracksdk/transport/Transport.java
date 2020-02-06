package io.github.woodpeckeryt.youtracksdk.transport;

public interface Transport {

    String sendGetRequest(String path);

    String sendPostRequest(String path, Object sendObject);
}
