package org.rallyplugins.domain.slide;

import org.rallyplugins.domain.Sprint;
import org.rallyplugins.domain.Story;
import org.rallyplugins.domain.enums.SlideType;
import org.docx4j.openpackaging.packages.PresentationMLPackage;

public class GoalsSlide extends AbstractSlide {
    private Sprint sprint;

    public GoalsSlide(PresentationMLPackage pkg, int pageNum, Sprint sprint) throws Exception {
        super(pkg, pageNum);
        this.sprint = sprint;
    }

    @Override
    public SlideType getType() {
        return SlideType.GOALS;
    }

    @Override
    public String getHeadline() {
        return "Goals of the " + sprint.getTitle();
    }

    public String getGoalsTpl() {
        StringBuilder builder = new StringBuilder();
        for (Story story : sprint.getStories()) {
            builder.append(String.format(GOAL_TPL, story.getName()));
        }
        return builder.toString();
    }

    private static final String GOAL_TPL = " <a:p>" +
            "            <a:r>" +
            "                <a:rPr lang=\"en-US\" smtClean=\"0\">" +
            "                    <a:latin typeface=\"Headline\" pitchFamily=\"16\" charset=\"0\"/>" +
            "                </a:rPr>" +
            "                <a:t>%s</a:t>" +
            "            </a:r>n" +
            "        </a:p>";
}
