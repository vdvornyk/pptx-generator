package org.rallyplugins.domain.enums;

import org.apache.commons.lang.ArrayUtils;

public enum DeliveryCol {
    Title(2776538),
    Acceptance_Criteria(4591050),
    Presenter(1003300),
    Details(806450);

    private static final String GRID_COL = "<a:gridCol w=\"%d\"/>";

    private int width;
    private String fullName;


    private DeliveryCol(final int width) {
        this.width = width;
        this.fullName = name().replace("_", " ");
    }

    public String getFullName() {
        return fullName;
    }

    public static String getGridCols(boolean isMultPresenter) {
        StringBuilder builder = new StringBuilder();

        DeliveryCol actualCols[] = getActualValues(isMultPresenter);
        for (int i = 0; i < actualCols.length; i++) {
            int actWidth;
            if (!isMultPresenter && i == 1) {
                actWidth = Acceptance_Criteria.width + Presenter.width;
            } else {
                actWidth = actualCols[i].width;
            }
            builder.append(String.format(GRID_COL, actWidth));

        }
        return builder.toString();
    }

    public static int colCount(boolean isMultPresenter) {
        return isMultPresenter ? values().length : values().length - 1;
    }

    public static DeliveryCol[] getActualValues(boolean isMultPresenter) {
        return isMultPresenter ? values() :
                (DeliveryCol[]) ArrayUtils.removeElement(values(), Presenter);
    }
}
