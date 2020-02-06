package io.github.woodpeckeryt.youtracksdk.issue;

public class Issue {
    private Long numberInProject;
    private String summary;
    private String description;
    private String idReadable;
    private Boolean usesMarkdown;
    private String wikifiedDescription;
    private Boolean isDraft;

    private Integer votes;
    private Long commentsCount;

    private Long created;
    private Long updated;
    private Long resolved;

    //new fields
    private Object links;
    private Object visibility;
    private Object project;
    private Object watchers;
    private Object voters;
    private Object reporter;
    private String draftOwner;
    private Object attachments;
    private Object comments;
    private Object tags;
    private Object subtasks;
    private Object customFields;
    private Object updater;
    private String C;
    private Object parent;

    public Long getNumberInProject() {
        return numberInProject;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "numberInProject=" + numberInProject +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", idReadable='" + idReadable + '\'' +
                ", usesMarkdown=" + usesMarkdown +
                ", wikifiedDescription='" + wikifiedDescription + '\'' +
                ", isDraft=" + isDraft +
                ", votes=" + votes +
                ", commentsCount=" + commentsCount +
                ", created=" + created +
                ", updated=" + updated +
                ", resolved=" + resolved +
                ", links=" + links +
                ", visibility=" + visibility +
                ", project=" + project +
                ", watchers=" + watchers +
                ", voters=" + voters +
                ", reporter=" + reporter +
                ", draftOwner='" + draftOwner + '\'' +
                ", attachments=" + attachments +
                ", comments=" + comments +
                ", tags=" + tags +
                ", subtasks=" + subtasks +
                ", customFields=" + customFields +
                ", updater=" + updater +
                ", C='" + C + '\'' +
                ", parent=" + parent +
                '}';
    }
}
