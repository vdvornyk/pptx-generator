package org.rallyplugins.commons;

import org.rallyplugins.domain.Pptx;
import org.rallyplugins.domain.slide.AbstractSlide;

public class SlideBuildStrategy {
    public static SlideBuilder getSlideBuilder(Pptx pptx, int i) throws Exception {
        AbstractSlide slide = pptx.getSlides().get(i);
        SlideBuilder slideBuilder = new SlideBuilder(slide);
        switch (slide.getType()) {
            case MAIN:
                return slideBuilder
                        .withIcon(pptx.getImgPart().getImgPartIcon())
                        .withLogoLeft(pptx.getImgPart().getImgPartLogo())
                        .withRedShape1()
                        .withRedShape2()
                        .withMainTitle()
                        .withDate()
                        .withSecurity();
            case AGENDA:
                return slideBuilder
                        .withAgenda()
                        .withLogoRight(pptx.getImgPart().getImgPartLogo())
                        .withHeadline();
            case GOALS:
                return slideBuilder
                        .withGoals()
                        .withLogoRight(pptx.getImgPart().getImgPartLogo())
                        .withHeadline();
            case DELIVERABLES:
                return slideBuilder
                        .withLogoRight(pptx.getImgPart().getImgPartLogo())
                        .withHeadline()
                        .withDeliverables();
            case SPRINT_SUMMARY:
                return slideBuilder
                        .withFeaturePlaceholder()
                        .withLogoRight(pptx.getImgPart().getImgPartLogo())
                        .withHeadline();
            case ACTOR:
                return slideBuilder
                        .withIcon(pptx.getImgPart().getImgPartIcon())
                        .withLogoLeft(pptx.getImgPart().getImgPartLogo())
                        .withRedShape1()
                        .withRedShape2()
                        .withActor()
                        .withDate()
                        .withSecurity();
            case FEATURE_PLACEHOLDER:
                return slideBuilder.withFeaturePlaceholder().withFeatureHeadline().withFeatureTeamName();
            case FINAL:
                return slideBuilder
                        .withIcon(pptx.getImgPart().getImgPartIcon())
                        .withLogoLeft(pptx.getImgPart().getImgPartLogo())
                        .withRedShape1()
                        .withRedShape2()
                        .withLastPage();
        }
        return slideBuilder;
    }
}
