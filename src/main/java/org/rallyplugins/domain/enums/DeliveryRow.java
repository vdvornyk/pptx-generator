package org.rallyplugins.domain.enums;

public enum DeliveryRow {

    Header(180975),
    Actor(180975),
    Story(515938);

    private static final String ROW_TPL = "<a:tr h=\"%d\">%s</a:tr>";
    private long height;

    private DeliveryRow(long height) {
        this.height = height;
    }

    public String getRow(String cells) {
        return String.format(ROW_TPL, height, cells);
    }
}
