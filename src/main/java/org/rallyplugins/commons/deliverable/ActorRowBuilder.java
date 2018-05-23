package org.rallyplugins.commons.deliverable;

import org.rallyplugins.domain.enums.Actor;
import org.rallyplugins.domain.enums.DeliveryCol;
import org.rallyplugins.domain.enums.DeliveryRow;

import java.io.IOException;

public class ActorRowBuilder extends AbstractRowBuilder {
    private static final String TPL_PATH = "/templates/part/deliverable/cell_group.tpl";
    private Actor actor;

    public ActorRowBuilder(boolean multPresenter, Actor actor) {
        super(multPresenter);
        this.actor = actor;
    }

    @Override
    public String buildRow() throws IOException {
        StringBuilder cellBuilder = new StringBuilder();
        int cellCount = DeliveryCol.colCount(multPresenter);
        cellBuilder.append(formatTemplate(cellCount, actor.toString()));
        for (int i = 1; i < cellCount; i++) {
            cellBuilder.append(NULL_CELL_TPL);
        }
        return DeliveryRow.Actor.getRow(cellBuilder.toString());
    }

    @Override
    public String getTplPath() {
        return TPL_PATH;
    }

    private static final String NULL_CELL_TPL =
            "<a:tc hMerge=\"1\">" +
                    "    <a:txBody>" +
                    "        <a:bodyPr/>" +
                    "        <a:lstStyle/>" +
                    "        <a:p>" +
                    "            <a:endParaRPr lang=\"en-US\"/>" +
                    "        </a:p>" +
                    "    </a:txBody>" +
                    "    <a:tcPr/>" +
                    "</a:tc>";
}
