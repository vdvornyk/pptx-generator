package org.rallyplugins.commons.deliverable;

import com.google.common.collect.ImmutableList;
import org.rallyplugins.domain.Story;
import org.rallyplugins.domain.enums.DeliveryRow;
import org.rallyplugins.domain.slide.DeliverablesSlide;
import org.rallyplugins.service.AcceptanceCriteriaParser;

import java.io.IOException;

public class StoryRowBuilder extends AbstractRowBuilder {
    private static final String TPL_PATH = "/templates/part/deliverable/cell.tpl";
    private static final String TPL_LINK_PATH = "/templates/part/deliverable/cell_link.tpl";
    private DeliverablesSlide slide;
    private AcceptanceCriteriaParser acceptanceParser;
    private Story story;

    public StoryRowBuilder(DeliverablesSlide slide, boolean multPresenter, Story story) {
        super(multPresenter);
        acceptanceParser = new AcceptanceCriteriaParser();
        this.story = story;
        this.slide = slide;
    }

    @Override
    public String buildRow() throws IOException {
        StringBuilder cellBuilder = new StringBuilder();
        cellBuilder.append(formatTemplate(prepareName()));
        cellBuilder.append(formatTemplate(prepareAcceptanceCriteria()));
        if (multPresenter) {
            cellBuilder.append(formatTemplate(prepareTeamName()));
        }

        String rId = slide.createLinkId(story.getUrl());
        cellBuilder.append(String.format(getLnkTpl(), rId, story.getCode()));

        return DeliveryRow.Story.getRow(cellBuilder.toString());

    }

    private String getLnkTpl() throws IOException {
        return getTplFromFile(TPL_LINK_PATH);
    }

    @Override
    public String getTplPath() {
        return TPL_PATH;
    }

    private String prepareName() {
        return prepareSimpleText(story.getName());
    }

    private String prepareTeamName() {
        return prepareSimpleText(story.getTeam().toString());
    }

    private String prepareSimpleText(String text) {
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder
                .append(BU_NONE)
                .append(String.format(SIMPLE_TEXT_ROW, text))
                .append(END_PARAM);
        return addToParagraf(nameBuilder.toString());
    }

    private String addToParagraf(String content) {
        return String.format(PARAGRAF, content);
    }

    private String prepareAcceptanceCriteria() {
        StringBuilder acceptanceCriteriaBuilder = new StringBuilder();
        ImmutableList<String> acceptanceList = acceptanceParser.parse(story.getDescription());

        if (acceptanceList == null || acceptanceList.isEmpty()) {
            acceptanceCriteriaBuilder.append(
                    addToParagraf(RED_DOT +
                            String.format(SIMPLE_TEXT_ROW, Story.ACCEPTANCE_PLACEHOLDER) +
                            END_PARAM));

        } else {
            int i = 1;
            for (String acceptanceCriteria : acceptanceList) {
                String paragrafContent = RED_DOT + String.format(SIMPLE_TEXT_ROW, acceptanceCriteria);
                if (i++ == acceptanceList.size()) {
                    paragrafContent += END_PARAM;
                }
                acceptanceCriteriaBuilder
                        .append(String.format(PARAGRAF, paragrafContent));

            }
        }

        return acceptanceCriteriaBuilder.toString();
    }

    public static final String BU_NONE = "<a:pPr marL=\"0\" marR=\"0\" lvl=\"0\" indent=\"0\" algn=\"l\" defTabSz=\"914400\"" +
            "                   rtl=\"0\" eaLnBrk=\"0\" fontAlgn=\"base\" latinLnBrk=\"0\" hangingPunct=\"0\">" +
            "                <a:lnSpc>" +
            "                    <a:spcPct val=\"100000\"/>" +
            "                </a:lnSpc>" +
            "                <a:spcBef>" +
            "                    <a:spcPct val=\"65000\"/>" +
            "                </a:spcBef>" +
            "                <a:spcAft>" +
            "                    <a:spcPct val=\"0\"/>" +
            "                </a:spcAft>" +
            "                <a:buClr>" +
            "                    <a:srgbClr val=\"E60000\"/>" +
            "                </a:buClr>" +
            "                <a:buSzTx/>" +
            "                <a:buFont typeface=\"Symbol\" pitchFamily=\"18\" charset=\"2\"/>" +
            "                <a:buNone/>" +
            "                <a:tabLst/>" +
            "            </a:pPr>";

    public static final String SIMPLE_TEXT_ROW = "<a:r>" +
            "                <a:rPr kumimoji=\"0\" lang=\"en-GB\" sz=\"1000\" b=\"0\" i=\"0\" u=\"none\"" +
            "                       strike=\"noStrike\" cap=\"none\" normalizeH=\"0\" baseline=\"0\"" +
            "                       smtClean=\"0\">" +
            "                    <a:ln>" +
            "                        <a:noFill/>" +
            "                    </a:ln>" +
            "                    <a:solidFill>" +
            "                        <a:schemeClr val=\"tx1\"/>" +
            "                    </a:solidFill>" +
            "                    <a:effectLst/>" +
            "                    <a:latin typeface=\"Arial\" charset=\"0\"/>" +
            "                    <a:ea typeface=\"Arial Unicode MS\" pitchFamily=\"34\" charset=\"-128\"/>" +
            "                    <a:cs typeface=\"Arial Unicode MS\" pitchFamily=\"34\" charset=\"-128\"/>" +
            "                </a:rPr>" +
            "                <a:t>%s</a:t>" +
            "            </a:r>";

    public static final String RED_DOT = "<a:pPr marL=\"0\" marR=\"0\" lvl=\"0\" indent=\"0\" algn=\"l\" defTabSz=\"914400\"" +
            "                                               rtl=\"0\" eaLnBrk=\"0\" fontAlgn=\"base\" latinLnBrk=\"0\" hangingPunct=\"0\">" +
            "                                            <a:lnSpc>" +
            "                                                <a:spcPct val=\"100000\"/>" +
            "                                            </a:lnSpc>" +
            "                                            <a:spcBef>" +
            "                                                <a:spcPct val=\"65000\"/>" +
            "                                            </a:spcBef>" +
            "                                            <a:spcAft>" +
            "                                                <a:spcPct val=\"0\"/>" +
            "                                            </a:spcAft>" +
            "                                            <a:buClr>" +
            "                                                <a:srgbClr val=\"E60000\"/>" +
            "                                            </a:buClr>" +
            "                                            <a:buSzTx/>" +
            "                                            <a:buFont typeface=\"Symbol\" pitchFamily=\"18\" charset=\"2\"/>" +
            "                                            <a:buChar char=\"·\"/>" +
            "                                            <a:tabLst/>" +
            "                                        </a:pPr>";
    public static final String END_PARAM = "<a:endParaRPr kumimoji=\"0\" lang=\"en-US\" sz=\"1000\" b=\"0\" i=\"0\" u=\"none\"" +
            "                          strike=\"noStrike\" cap=\"none\" normalizeH=\"0\" baseline=\"0\"" +
            "                          smtClean=\"0\">" +
            "                <a:ln>" +
            "                    <a:noFill/>" +
            "                </a:ln>" +
            "                <a:solidFill>" +
            "                    <a:schemeClr val=\"tx1\"/>" +
            "                </a:solidFill>" +
            "                <a:effectLst/>" +
            "                <a:latin typeface=\"Arial\" charset=\"0\"/>" +
            "                <a:ea typeface=\"Arial Unicode MS\" pitchFamily=\"34\" charset=\"-128\"/>" +
            "                <a:cs typeface=\"Arial Unicode MS\" pitchFamily=\"34\" charset=\"-128\"/>" +
            "            </a:endParaRPr>";
    public static final String PARAGRAF = "<a:p>%s</a:p>";
}
