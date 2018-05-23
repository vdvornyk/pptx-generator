package org.rallyplugins.domain;

import org.rallyplugins.commons.PptxTemplate;
import org.rallyplugins.commons.Resource;
import org.rallyplugins.commons.utils.ResourceUtils;
import org.rallyplugins.domain.slide.AbstractSlide;
import org.apache.commons.io.IOUtils;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.PresentationML.MainPresentationPart;
import org.pptx4j.pml.Presentation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;

public class Pptx {

    private static Logger log = Logger.getLogger(Pptx.class.getName());

    private static byte[] tplFileBytes;
    private final ImgPart imgPart;

    private final PptxTemplate pptxTemplate;
    private Sprint sprint;
    private List<AbstractSlide> slides;
    private PresentationMLPackage presentationMLPackage;

    static {
        beforeClass();
    }

    public Pptx(Sprint sprint) throws Exception {

        initPptxPackage();
        this.sprint = sprint;
        this.imgPart = new ImgPart(presentationMLPackage);
        this.pptxTemplate = new PptxTemplate(this, sprint);
        slides = this.pptxTemplate.getTemplate();
    }

    public void initPptxPackage() throws Exception {

        presentationMLPackage = (PresentationMLPackage) PresentationMLPackage.load(new ByteArrayInputStream(tplFileBytes));
        MainPresentationPart mpp = (MainPresentationPart) presentationMLPackage.getParts().getParts().get(new PartName("/ppt/presentation.xml"));
        mpp.getJaxbElement().setSldIdLst(new Presentation.SldIdLst());
    }

    public List<AbstractSlide> getSlides() {
        return slides;
    }


    public PresentationMLPackage getPML() {
        return presentationMLPackage;
    }

    public ImgPart getImgPart() {
        return imgPart;
    }

    public PptxInfo getPptxInfo() {
        return sprint.getPptxInfo();
    }

    public PptxTemplate getPptxTemplate() {
        return pptxTemplate;
    }

    private static void beforeClass() {
        try {
            tplFileBytes = IOUtils.toByteArray(ResourceUtils.getInputStreamFromResource(Resource.Const.TEMPLATE_PPTX));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
