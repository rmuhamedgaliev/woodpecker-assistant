package io.github.rmuhamedgaliev.woodpecker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.woodpeckeryt.youtracksdk.entities.YouTrack;
import io.github.woodpeckeryt.youtracksdk.issue.Issue;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class YoutrackService {

    public List<Issue> getYouTrackIssues(String token) {
        String host = "https://woodpeckeryt.myjetbrains.com/youtrack";
        String projectId = "72d91e0c-9712-4063-8ad8-1fddbea33a6f";

        YouTrack youTrack = new YouTrack(token, host, projectId);
        String issues = youTrack.getIssueService().getIssuesInProject();

        Gson gson = new Gson();

        Type itemsListType = new TypeToken<List<Issue>>() {
        }.getType();

        List<Issue> issue = gson.fromJson(issues, itemsListType);

        return issue;
    }
}
