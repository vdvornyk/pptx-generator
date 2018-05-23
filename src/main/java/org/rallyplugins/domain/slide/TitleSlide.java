package org.rallyplugins.domain.slide;

import org.rallyplugins.domain.PptxInfo;
import org.rallyplugins.domain.enums.SlideType;
import org.docx4j.openpackaging.packages.PresentationMLPackage;

public class TitleSlide extends AbstractSlide{
    private final PptxInfo pptxInfo;

    public TitleSlide(PresentationMLPackage pkg, int pageNum, PptxInfo pptxInfo) throws Exception {
        super(pkg, pageNum);
        this.pptxInfo = pptxInfo;
    }

    @Override
    public SlideType getType() {
        return SlideType.MAIN;
    }

    @Override
    public String getHeadline() {
        return EMPTY_HEADLINE;
    }

    public PptxInfo getPptxInfo() {
        return pptxInfo;
    }
}
