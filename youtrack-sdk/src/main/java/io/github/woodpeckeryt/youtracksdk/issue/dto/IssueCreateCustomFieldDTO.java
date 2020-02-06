package io.github.woodpeckeryt.youtracksdk.issue.dto;

import com.google.gson.annotations.SerializedName;

public class IssueCreateCustomFieldDTO {
    @SerializedName("$type")
    private final String type;
    private final String name;
    private final Object value;

    public IssueCreateCustomFieldDTO(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
