package org.rallyplugins.domain.slide;

import org.rallyplugins.domain.enums.SlideType;
import org.docx4j.openpackaging.packages.PresentationMLPackage;

public class FinalSlide extends AbstractSlide {

    public FinalSlide(PresentationMLPackage pkg, int pageNum) throws Exception {
        super(pkg, pageNum);
    }

    @Override
    public SlideType getType() {
        return SlideType.FINAL;
    }

    @Override
    public String getHeadline() {
        return EMPTY_HEADLINE;
    }
}
