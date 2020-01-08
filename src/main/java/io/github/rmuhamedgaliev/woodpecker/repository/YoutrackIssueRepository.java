package io.github.rmuhamedgaliev.woodpecker.repository;

import io.github.woodpeckeryt.youtracksdk.entities.YouTrack;
import io.github.woodpeckeryt.youtracksdk.issue.Issue;
import io.github.woodpeckeryt.youtracksdk.issue.IssueService;
import io.github.woodpeckeryt.youtracksdk.issue.dto.IssueCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class YoutrackIssueRepository {

    private final YouTrack youTrack;
    private final IssueService issueService;

    @Autowired
    public YoutrackIssueRepository(YouTrack youTrack) {
        this.youTrack = youTrack;
        this.issueService = youTrack.getIssueService();
    }

    public Issue createIssue(IssueCreateDTO issueCreateDTO) {
        return this.issueService.createIssue(issueCreateDTO);
    }
}
