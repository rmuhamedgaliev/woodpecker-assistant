package io.github.rmuhamedgaliev.woodpecker.repository;

import io.github.woodpeckeryt.youtracksdk.entities.YouTrack;
import io.github.woodpeckeryt.youtracksdk.user.User;
import io.github.woodpeckeryt.youtracksdk.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class YoutrackUserRepository {

    private final UserService userService;

    public YoutrackUserRepository() {
        String token = "perm:cmluYXQubXVoYW1lZGdhbGlldg==.RA==.iBh1e8T0vHTq6Vb5UHzhAzy10jfXvg";
        String host = "https://youtrack.citronium.com";
        String projectId = "72d91e0c-9712-4063-8ad8-1fddbea33a6f";

        YouTrack youTrack = new YouTrack(token, host, projectId);
        this.userService = youTrack.getUserService();
    }

    public User getMe() {
        return this.userService.getMeInformation();
    }
}
