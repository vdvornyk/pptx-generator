package org.rallyplugins.domain.enums;

public enum DeliveryCell {
    Group(""),
    Data(""),
    Link("");

    private String tplPath;

    private DeliveryCell(String tplPath) {
        this.tplPath = tplPath;
    }

    public String getTpl() {
        return tplPath;
    }
}
