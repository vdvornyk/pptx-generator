package org.rallyplugins.domain.slide;

import org.rallyplugins.domain.enums.SlideType;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.PresentationML.SlideLayoutPart;
import org.docx4j.openpackaging.parts.PresentationML.SlidePart;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;

import javax.xml.bind.JAXBException;
import java.util.Random;

public abstract class AbstractSlide {
    protected static final String EMPTY_HEADLINE = "NO_HEALDINE";
    protected final SlidePart slidePart;

    protected final int pageNum;
    protected int partId;
    private String rEmbedId;

    public AbstractSlide(PresentationMLPackage pkg, int pageNum) throws Exception {
        this.slidePart = newSlidePart(pkg, pageNum);

        this.pageNum = pageNum;
        this.partId = getInitialIdForShape();
    }

    public String getNextId() {
        return String.valueOf(partId++);
    }

    private static int getInitialIdForShape() {
        final int min = 1000;
        final int max = 10000;
        return new Random().nextInt(max - min + 1) + min;
    }

    private SlidePart newSlidePart(PresentationMLPackage pkg, int slideNum) throws InvalidFormatException, JAXBException {
        String slidePath = String.format("/ppt/slides/slide%d.xml", slideNum);
        SlideLayoutPart slP = (SlideLayoutPart) pkg.getParts().getParts().get(new PartName("/ppt/slideLayouts/slideLayout6.xml"));
        return PresentationMLPackage.createSlidePart(pkg.getMainPresentationPart(), slP, new PartName(slidePath));
    }

    public SlidePart getSlidePart() {
        return slidePart;
    }

    public String getPageNum() {
        return String.valueOf(pageNum);
    }

    public void setRelIdForImg(BinaryPartAbstractImage imagePart) throws InvalidFormatException {
        Relationship rel = slidePart.addTargetPart(imagePart);
        this.rEmbedId = rel.getId();
    }

    public String getrEmbedId() {
        return rEmbedId;
    }

    public String createLinkId(String url) {
        org.docx4j.relationships.ObjectFactory factory = new org.docx4j.relationships.ObjectFactory();
        org.docx4j.relationships.Relationship rell = factory.createRelationship();
        rell.setType(Namespaces.HYPERLINK);
        rell.setTarget(url);
        rell.setTargetMode("External");
        slidePart.getRelationshipsPart().addRelationship(rell);
        return rell.getId();
    }

    public abstract SlideType getType();

    public abstract String getHeadline();
}
