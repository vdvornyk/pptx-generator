package org.rallyplugins.commons.deliverable;

import org.rallyplugins.domain.enums.DeliveryCol;
import org.rallyplugins.domain.enums.DeliveryRow;

import java.io.IOException;

public class HeaderRowBuilder extends AbstractRowBuilder {
    private static final String TPL_PATH = "/templates/part/deliverable/cell_header.tpl";

    public HeaderRowBuilder(boolean multPresenter) {
        super(multPresenter);
    }

    @Override
    public String buildRow() throws IOException {
        DeliveryCol diliveryCols[] = DeliveryCol.getActualValues(multPresenter);
        StringBuilder cellBuilder = new StringBuilder();
        for (DeliveryCol deliveryCol : diliveryCols) {
            cellBuilder.append(formatTemplate(deliveryCol.getFullName()));
        }

        return DeliveryRow.Header.getRow(cellBuilder.toString());
    }

    @Override
    public String getTplPath() {
        return TPL_PATH;
    }
}
