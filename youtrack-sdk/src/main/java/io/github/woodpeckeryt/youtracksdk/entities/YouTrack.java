package io.github.woodpeckeryt.youtracksdk.entities;

import io.github.woodpeckeryt.youtracksdk.issue.IssueService;
import io.github.woodpeckeryt.youtracksdk.transport.ApacheTransport;
import io.github.woodpeckeryt.youtracksdk.transport.Transport;
import io.github.woodpeckeryt.youtracksdk.user.UserService;

public class YouTrack {
    private final String token;
    private final String host;

    private final Transport gsonTransport;

    private final IssueService issueService;
    private final UserService userService;

    public YouTrack(String token, String host) {
        this.token = token;
        this.host = host;

        this.gsonTransport = new ApacheTransport(this.token, this.host);
        this.issueService = new IssueService(this.gsonTransport);
        this.userService = new UserService(this.gsonTransport);
    }

    public IssueService getIssueService() {
        return issueService;
    }

    public UserService getUserService() { return userService; }
}
