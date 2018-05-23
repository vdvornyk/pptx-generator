package org.rallyplugins.domain.enums;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public enum Team {
    Default,
    Nbrst,
    Triar,
    Centauri,
    Jazz,
    Team77,
    Geli,
    Venera;

    @JsonCreator
    public static Team fromString(@JsonProperty("team") String team) {
        for (Team type : Team.values()) {
            if (type.toString().equalsIgnoreCase(team)) {
                return type;
            }
        }
        return Default;
    }
}
