package org.rallyplugins.commons;

import com.google.common.collect.ImmutableSet;
import org.rallyplugins.commons.utils.ResourceUtils;
import org.rallyplugins.domain.enums.TplKey;
import org.rallyplugins.domain.slide.AbstractSlide;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.pptx4j.jaxb.Context;
import org.pptx4j.pml.CTGraphicalObjectFrame;
import org.pptx4j.pml.Pic;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.rallyplugins.domain.enums.TplKey.*;

public class SlideBuilder {

    private List<Object> slideObjectList;
    private AbstractSlide slide;

    public SlideBuilder(AbstractSlide slide) {
        this.slide = checkNotNull(slide);
        this.slideObjectList = new LinkedList<Object>();
    }

    public void build() throws InvalidFormatException, JAXBException, IOException {
        if (slideObjectList.size() != 0) {
            withPageNum();
            slide.getSlidePart().getJaxbElement().getCSld().getSpTree().getSpOrGrpSpOrGraphicFrame().addAll(slideObjectList);
        }
    }

    public SlideBuilder withLogoLeft(BinaryPartAbstractImage imagePart) throws Exception {
        slide.setRelIdForImg(imagePart);
        return withParams(Resource.Const.TPL_LOGO_LEFT, getTplMapping(part_id, rEmbedId), Pic.class);

    }

    public SlideBuilder withLogoRight(BinaryPartAbstractImage imagePart) throws Exception {
        slide.setRelIdForImg(imagePart);
        return withParams(Resource.Const.TPL_LOGO_RIGHT, getTplMapping(part_id, rEmbedId), Pic.class);

    }

    public SlideBuilder withHeadline() throws Exception {
        return withParams(Resource.Const.TPL_HEADLINE_BIG, getTplMapping(part_id, headline));
    }

    public SlideBuilder withIcon(BinaryPartAbstractImage imagePart) throws Exception {
        slide.setRelIdForImg(imagePart);
        return withParams(Resource.Const.TPL_ICON, getTplMapping(part_id, rEmbedId), Pic.class);
    }

    public SlideBuilder withRedShape1() throws Exception {
        return withParams(Resource.Const.TPL_RED_SHAPE1, getTplMapping(part_id));
    }

    public SlideBuilder withRedShape2() throws Exception {
        return withParams(Resource.Const.TPL_RED_SHAPE2, getTplMapping(part_id));
    }

    public SlideBuilder withFeatureHeadline() throws IOException, JAXBException, InvalidFormatException {
        return withParams(Resource.Const.TPL_FEATURE_HEADLINE,
                getTplMapping(part_id, headline, story_code, story_link_id));
    }

    public SlideBuilder withFeaturePlaceholder() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_FEATURE_PLH, getTplMapping(part_id));
    }

    public SlideBuilder withFeatureTeamName() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_TEAM_NAME, getTplMapping(part_id, team_name));
    }

    public SlideBuilder withLastPage() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_LAST_PAGE, getTplMapping(part_id));
    }

    public SlideBuilder withMainTitle() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_MAIN_TITLE, getTplMapping(part_id, projectName, tranche, title));
    }

    public SlideBuilder withDate() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_DATE, getTplMapping(part_id, date));
    }

    public SlideBuilder withSecurity() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_SECURITY, getTplMapping(part_id));
    }

    public SlideBuilder withActor() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_ACTOR, getTplMapping(part_id, actor));
    }

    public SlideBuilder withAgenda() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_AGENDA, getTplMapping(part_id, stkh_group));
    }

    public SlideBuilder withGoals() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_GOALS, getTplMapping(part_id, goals));
    }

    public SlideBuilder withDeliverables() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_DELIVERABLE, getTplMapping(part_id, gridColList, gridRowList), CTGraphicalObjectFrame.class);
    }

    private SlideBuilder withPageNum() throws JAXBException, IOException {
        return withParams(Resource.Const.TPL_SLIDE_PAGE, getTplMapping(part_id, page_num));
    }


    private SlideBuilder withParams(String tplPath, HashMap<String, String> mapping) throws JAXBException, IOException {
        return withParams(tplPath, mapping, null);
    }

    private SlideBuilder withParams(String tplPath, HashMap<String, String> mapping, Class<?> declaredType) throws JAXBException, IOException {

        String pageNumTpl = ResourceUtils.getTplFromFile(tplPath);
        Object part;
        if (declaredType == null) {
            part = XmlUtils.unmarshallFromTemplate(pageNumTpl, mapping, Context.jcPML);
        } else {
            part = XmlUtils.unmarshallFromTemplate(pageNumTpl, mapping, Context.jcPML, declaredType);
        }

        slideObjectList.add(part);
        return this;
    }

    private HashMap<String, String> getTplMapping(TplKey... tplKeys) throws JAXBException, IOException {
        return TplKey.getTplMapping(slide, ImmutableSet.copyOf(tplKeys));
    }
}
