package org.rallyplugins.domain.slide;

import org.rallyplugins.domain.enums.Actor;
import org.rallyplugins.domain.enums.SlideType;
import org.docx4j.openpackaging.packages.PresentationMLPackage;

public class ActorSlide extends AbstractSlide {
    private Actor actor;

    public ActorSlide(PresentationMLPackage pkg, int pageNum, Actor actor) throws Exception {
        super(pkg, pageNum);
        this.actor = actor;
    }

    @Override
    public SlideType getType() {
        return SlideType.ACTOR;
    }

    @Override
    public String getHeadline() {
        return EMPTY_HEADLINE;
    }

    public Actor getActor() {
        return actor;
    }
}
