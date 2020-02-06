package io.github.woodpeckeryt.youtracksdk.user;

public class User {

    private String avatarUrl;
    private String login;
    private String fullName;
    private String email;
    private String jabberAccountName;
    private String ringId;
    private Boolean banned;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getLogin() {
        return login;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getJabberAccountName() {
        return jabberAccountName;
    }

    public String getRingId() {
        return ringId;
    }

    public Boolean getBanned() {
        return banned;
    }
}
