package org.rallyplugins.domain.slide;

import org.rallyplugins.domain.enums.SlideType;
import org.rallyplugins.domain.Story;
import org.docx4j.openpackaging.packages.PresentationMLPackage;

public class FeatureSlide extends AbstractSlide {

    private final Story story;

    public FeatureSlide(PresentationMLPackage pkg, int slideNum, Story story) throws Exception {
        super(pkg, slideNum);
        this.story = story;
    }

    public Story getStory() {
        return story;
    }

    @Override
    public SlideType getType() {
        return SlideType.FEATURE_PLACEHOLDER;
    }

    @Override
    public String getHeadline() {
        return story.getName();
    }
}


