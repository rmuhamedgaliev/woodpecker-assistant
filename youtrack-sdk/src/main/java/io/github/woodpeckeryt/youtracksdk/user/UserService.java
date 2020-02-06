package io.github.woodpeckeryt.youtracksdk.user;

import com.google.gson.Gson;
import io.github.woodpeckeryt.youtracksdk.transport.Transport;

public class UserService {
    private final Transport gsonTransport;
    private final Gson gson;


    public UserService(Transport gsonTransport) {
        this.gsonTransport = gsonTransport;
        gson = new Gson();
    }

    public User getMeInformation() {
        String issuePath = "/api/admin/users/me?fields=login,fullname,email,jabberAccountName,ringId,guestonline,banned,avatarUrl";
        String response = this.gsonTransport.sendGetRequest(issuePath);
        return gson.fromJson(response, User.class);
    }
}
