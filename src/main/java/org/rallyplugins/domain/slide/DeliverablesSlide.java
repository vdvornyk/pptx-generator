package org.rallyplugins.domain.slide;

import com.google.common.collect.TreeMultimap;
import org.rallyplugins.commons.deliverable.ActorRowBuilder;
import org.rallyplugins.commons.deliverable.HeaderRowBuilder;
import org.rallyplugins.commons.deliverable.StoryRowBuilder;
import org.rallyplugins.domain.Story;
import org.rallyplugins.domain.enums.Actor;
import org.rallyplugins.domain.enums.SlideType;
import org.docx4j.openpackaging.packages.PresentationMLPackage;

import java.io.IOException;

public class DeliverablesSlide extends AbstractSlide {
    private TreeMultimap<Actor, Story> actorGroups;

    private boolean multPresenter;

    public DeliverablesSlide(PresentationMLPackage pkg, int pageNum, TreeMultimap<Actor, Story> actorGroups, boolean multPresenter) throws Exception {
        super(pkg, pageNum);
        this.actorGroups = actorGroups;
        this.multPresenter = multPresenter;
    }

    @Override
    public SlideType getType() {
        return SlideType.DELIVERABLES;
    }

    @Override
    public String getHeadline() {
        return "Deliverables – All Teams";
    }

    public String getRows() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(new HeaderRowBuilder(multPresenter).buildRow());
        for (Actor actor : actorGroups.asMap().keySet()) {
            stringBuilder.append(new ActorRowBuilder(multPresenter, actor).buildRow());
            for (Story story : actorGroups.get(actor)) {
                stringBuilder.append(new StoryRowBuilder(this, multPresenter, story).buildRow());
            }
        }
        return stringBuilder.toString();
    }

    public boolean isMultPresenter() {
        return multPresenter;
    }
}
