package org.rallyplugins.domain;

public class PptxInfo {
    private String projectName;
    private String tranche;
    private String title;

    public PptxInfo(String projectName, String tranche, String title) {
        this.projectName = projectName;
        this.tranche = tranche;
        this.title = title;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTranche() {
        return tranche;
    }

    public String getTitle() {
        return title;
    }
}
