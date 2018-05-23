package org.rallyplugins.domain.enums;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public enum Actor {

    OPS_USER("Ops user"),
    ARCHITECT("Architect"),
    PRODUCT_OWNER("Product Owner"),
    DELIVERY_MANAGER("Delivery Manager"),
    PROD_SERVICES("Prod Services"),
    SECURITY_OFFICER("Security Officer"),
    OPS_MANAGER("Ops Manager"),
    QA("QA"),

    Default("Default");

    private String name;

    private Actor(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @JsonCreator
    public static Actor fromString(@JsonProperty("actor") String actor) {
        for (Actor type : Actor.values()) {
            if (type.toString().equalsIgnoreCase(actor)) {
                return type;
            }
        }
        return Default;
    }
}
