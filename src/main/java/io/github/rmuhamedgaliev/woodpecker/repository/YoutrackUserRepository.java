package io.github.rmuhamedgaliev.woodpecker.repository;

import io.github.woodpeckeryt.youtracksdk.entities.YouTrack;
import io.github.woodpeckeryt.youtracksdk.user.User;
import io.github.woodpeckeryt.youtracksdk.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YoutrackUserRepository {

    private final UserService userService;
    private final YouTrack youTrack;

    @Autowired
    public YoutrackUserRepository(YouTrack youTrack) {
        this.youTrack = youTrack;
        this.userService = this.youTrack.getUserService();
    }

    public User getMe() {
        return this.userService.getMeInformation();
    }
}
