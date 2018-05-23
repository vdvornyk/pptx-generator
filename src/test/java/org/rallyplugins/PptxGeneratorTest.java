package org.rallyplugins;

import org.rallyplugins.commons.PptxCreator;
import org.rallyplugins.domain.Pptx;
import org.rallyplugins.domain.Sprint;
import org.rallyplugins.domain.Story;
import org.rallyplugins.domain.enums.Actor;
import org.rallyplugins.domain.enums.Team;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class PptxGeneratorTest {
    private static final String outputfilepath = "C:\\dev\\tmp\\ppt\\pptx-out2.pptx";

    @Test
    public void shouldAddPlaceholerTextToSlide() throws Exception {


        Pptx pptx = new Pptx(getNewSprint());
        PptxCreator pptxCreator = new PptxCreator(pptx);
        ByteArrayOutputStream baos = pptxCreator.createPptx();

        FileUtils.writeByteArrayToFile(new java.io.File(outputfilepath), baos.toByteArray());
    }


    private Sprint getNewSprint() throws Exception {
        String projectName = "Huge Strategic Project";
        String tranche = "Tranche 3: Generate Electronic Confirmations";
        String title = "Sprints 27 - Review";

        Story story1 = new Story("Story 1",
                "S2860",
                Team.Centauri,
                Actor.OPS_USER,
                "Acceptance Criteria placeholder",
                true);
        Story story2 = new Story("Story name placeholder",
                "S6845",
                Team.Triar,
                Actor.PRODUCT_OWNER,
                "Acceptance Criteria placeholder",
                true);
        Story story3 = new Story("Release management in sprint 27",
                "S7772",
                Team.Triar,
                Actor.DELIVERY_MANAGER,
                "Acceptance Criteria placeholder",
                true);
        Story story4 = new Story("Release documentation",
                "S8125",
                Team.Nbrst,
                Actor.DELIVERY_MANAGER,
                "Acceptance Criteria placeholder",
                true);
        Story story5 = new Story(PRE-PROD release support",
                "S8126",
                Team.Nbrst,
                Actor.DELIVERY_MANAGER,
                "Acceptance Criteria placeholder",
                true);
        Story story6 = new Story("Story name 5",

                "S8119",
                Team.Triar,
                Actor.ARCHITECT,
                "Acceptance Criteria placeholder",
                true);
        Story story7 = new Story("Configuration for PRE-PROD SSZ BCP is not correct",

                "DE925",
                Team.Triar,
                Actor.PROD_SERVICES,
                "Acceptance Criteria placeholder",
                true);
        Story story8 = new Story("MT300 fields - complete action items after the review in sprint 26",

                "S5636",
                Team.Nbrst,
                Actor.PRODUCT_OWNER,
                "Acceptance Criteria placeholder",
                true);
        Story story9 = new Story("MT300 fields' specs and BDDs review (PO, BA & Dev) Sprint 27",

                "S8246",
                Team.Nbrst,
                Actor.PRODUCT_OWNER,
                "Acceptance Criteria placeholder",
                true);
        Story story10 = new Story("Knowledge transfer for new team members in sprint 27",

                "S8869",
                Team.Nbrst,
                Actor.DELIVERY_MANAGER,
                "Acceptance Criteria placeholder",
                true);
        Story story11 = new Story("Refinement in Sprint 27",

                "S8250",
                Team.Nbrst,
                Actor.DELIVERY_MANAGER,
                "Acceptance Criteria placeholder",
                false);


        Story stArr[] = new Story[]{story1, story2, story3, story4, story5, story6, story7, story8, story9, story10, story11};

        return new Sprint(projectName, tranche, title, Arrays.asList(stArr));
    }


}
