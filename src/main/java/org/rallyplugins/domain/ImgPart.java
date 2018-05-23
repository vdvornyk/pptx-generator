package org.rallyplugins.domain;

import org.rallyplugins.commons.Resource;
import org.rallyplugins.commons.utils.ResourceUtils;
import org.apache.commons.io.IOUtils;
import org.docx4j.openpackaging.packages.PresentationMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;

import java.io.IOException;

public class ImgPart {
    private static byte[] iconFileBytes;
    private static byte[] logoFileBytes;
    private final BinaryPartAbstractImage imgPartIcon;
    private final BinaryPartAbstractImage imgPartLogo;

    static {
        beforeClass();
    }

    public ImgPart(PresentationMLPackage presentationMLPackage) {
        try {
            imgPartIcon = BinaryPartAbstractImage.createImagePart(presentationMLPackage, presentationMLPackage.getMainPresentationPart(), iconFileBytes);
            imgPartLogo = BinaryPartAbstractImage.createImagePart(presentationMLPackage, presentationMLPackage.getMainPresentationPart(), logoFileBytes);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void beforeClass() {
        try {
            iconFileBytes = IOUtils.toByteArray(ResourceUtils.getInputStreamFromResource(Resource.Const.ICON));
            logoFileBytes = IOUtils.toByteArray(ResourceUtils.getInputStreamFromResource(Resource.Const.LOGO));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public BinaryPartAbstractImage getImgPartIcon() {
        return imgPartIcon;
    }

    public BinaryPartAbstractImage getImgPartLogo() {
        return imgPartLogo;
    }
}
