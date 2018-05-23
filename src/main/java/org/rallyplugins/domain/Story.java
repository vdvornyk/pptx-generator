package org.rallyplugins.domain;

import com.topologi.diffx.xml.esc.XMLEscapeASCII;
import org.rallyplugins.domain.enums.Actor;
import org.rallyplugins.domain.enums.Team;
import org.rallyplugins.service.PptxService;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.apache.log4j.Logger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Story implements Comparable {
    public static final String ACCEPTANCE_PLACEHOLDER = "Acceptance Criteria placeholder";
    private Logger log = Logger.getLogger(Story.class.getName());
    private String objectId;
    private String name;

    private String description;
    private String code;
    private Team team;
    private Actor actor;
    private String projectId;
    private boolean feature;

    public Story() {
    }

    public Story(String title, String code, Team team, Actor actor, String description, boolean feature) {
        this.name = title;
        this.code = code;
        this.team = team;
        this.actor = actor;
        this.feature = feature;
        this.description=description;
    }

    public String getName() {
        return XMLEscapeASCII.ASCII_ESCAPE.toAttributeValue(name);
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return String.format("%s/#/%s/detail/userstory/%s", PptxService.RALLY_DOMAIN, projectId, objectId);
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Team getTeam() {
        return team != null ? team : Team.Default;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Actor getActor() {
        return actor != null ? actor : Actor.Default;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public boolean isFeature() {
        return feature;
    }

    public void setFeature(boolean feature) {
        this.feature = feature;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Story) o).name);
    }

    @Override
    public String toString() {
        return "Story{" +
                "objectId='" + objectId + '\'' +
                ", name='" + name + '\'' +
                ", url='" + getUrl() + '\'' +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", team=" + getTeam() +
                ", actor=" + getActor() +
                ", feature=" + feature +
                '}';
    }
}
