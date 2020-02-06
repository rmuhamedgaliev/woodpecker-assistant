package io.github.woodpeckeryt.youtracksdk.issue.dto;

import java.util.List;

public class IssueCreateDTO {
    private final String summary;
    private final String description;
    private final IssueCreateProjectDTO project;
    private final List<IssueCreateCustomFieldDTO> customFields;

    public IssueCreateDTO(String summary, String description, IssueCreateProjectDTO project, List<IssueCreateCustomFieldDTO> customFields) {
        this.summary = summary;
        this.description = description;
        this.project = project;
        this.customFields = customFields;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public IssueCreateProjectDTO getProject() {
        return project;
    }

    public List<IssueCreateCustomFieldDTO> getCustomFields() {
        return customFields;
    }
}
