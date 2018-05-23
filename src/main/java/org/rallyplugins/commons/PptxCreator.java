package org.rallyplugins.commons;

import org.rallyplugins.domain.Pptx;
import org.rallyplugins.domain.slide.AbstractSlide;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io.SaveToZipFile;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

public class PptxCreator {
    private Pptx pptx;

    public PptxCreator(Pptx pptx) throws Exception {
        this.pptx = pptx;
    }

    public ByteArrayOutputStream createPptx() throws Exception {
        generateSlides();
        return writeToByteArrayOS();
    }

    public void createPptx(OutputStream os) throws Exception {
        generateSlides();
        writeToExistingOS(os);
    }

    private void generateSlides() throws Exception {
        List<AbstractSlide> slides = pptx.getSlides();
        for (int i = 0; i < slides.size(); i++) {
            SlideBuildStrategy.getSlideBuilder(pptx, i).build();
        }
    }

    private ByteArrayOutputStream writeToByteArrayOS() throws Docx4JException {
        SaveToZipFile saveToZipFile = new SaveToZipFile(pptx.getPML());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        saveToZipFile.save(os);
        return os;
    }

    private void writeToExistingOS(OutputStream os) throws Docx4JException {
        SaveToZipFile saveToZipFile = new SaveToZipFile(pptx.getPML());
        saveToZipFile.save(os);
    }
}
