package org.rallyplugins.domain.slide;

import org.rallyplugins.domain.enums.SlideType;
import org.rallyplugins.domain.enums.Team;
import org.docx4j.openpackaging.packages.PresentationMLPackage;

public class SummarySlide extends AbstractSlide {
    private Team team;


    public SummarySlide(PresentationMLPackage pkg, int pageNum, Team team) throws Exception {
        super(pkg, pageNum);
        this.team = team;
    }

    @Override
    public SlideType getType() {
        return SlideType.SPRINT_SUMMARY;
    }

    @Override
    public String getHeadline() {
        return "Sprint Summary for " + team.name();
    }
}
