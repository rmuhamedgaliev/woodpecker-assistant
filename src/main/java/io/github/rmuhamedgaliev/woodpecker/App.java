/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.github.rmuhamedgaliev.woodpecker;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log
@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);


//        String token = "perm:cm11aGFtZWRnYWxpZXY=.NDctMA==.rj70ZJ7eklBifZPuEJXeO1z9Ul5My0";
//        String host = "https://woodpeckeryt.myjetbrains.com/youtrack";
//        String projectId = "72d91e0c-9712-4063-8ad8-1fddbea33a6f";
//
//        YouTrack youTrack = new YouTrack(token, host, projectId);
//        String issues = youTrack.getIssueService().getIssuesInProject();
//
//        Gson gson = new Gson();
//
//        Type itemsListType = new TypeToken<List<Issue>>() {
//        }.getType();
//
//        List<Issue> issue = gson.fromJson(issues, itemsListType);
//
//        System.out.println(issues);

    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < args.length; ++i) {
            log.info(args[i]);
        }
    }
}
