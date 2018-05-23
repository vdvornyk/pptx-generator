package org.rallyplugins.domain;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.rallyplugins.domain.enums.Team;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprint {
    private Logger log = Logger.getLogger(Sprint.class.getName());
    private String projectName;
    private String projectId;
    private String tranche;
    private String title;

    private List<Story> stories;
    private SprintData sprintData;

    public Sprint() {

    }

    public Sprint(String projectName, String tranche, String title, List<Story> stories) {
        this.projectName = projectName;
        this.tranche = tranche;
        this.title = title;
        this.stories = stories;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
        for (Story story : this.stories) {
            story.setProjectId(projectId);
        }
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTranche() {
        return tranche;
    }

    public void setTranche(String tranche) {
        this.tranche = tranche;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PptxInfo getPptxInfo() {
        return new PptxInfo(projectName, tranche, title);
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {

        this.projectId = projectId;
    }

    public SprintData getSprintData() {
        return sprintData;
    }

    public void setSprintData(SprintData sprintData) {
        this.sprintData = sprintData;
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "projectName='" + projectName + '\'' +
                ", tranche='" + tranche + '\'' +
                ", title='" + title + '\'' +
                ", stories=" + StringUtils.join(stories, ";\n ") +
                '}';
    }

    public Set<Team> getTeams() {
        return Sets.newLinkedHashSet(Lists.transform(stories, new Function<Story, Team>() {
            public Team apply(Story story) {
                return story.getTeam();
            }
        }));
    }

    public String getSprintName() {
        return sprintData.getSprintName();
    }

}
