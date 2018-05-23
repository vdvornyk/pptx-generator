package org.rallyplugins.domain.slide;

import org.rallyplugins.domain.enums.Actor;
import org.rallyplugins.domain.enums.SlideType;
import org.docx4j.openpackaging.packages.PresentationMLPackage;

import java.util.Set;

public class AgendaSlide extends AbstractSlide {

    private final Set<Actor> actors;

    public AgendaSlide(PresentationMLPackage pkg, int pageNum, Set<Actor> actors) throws Exception {
        super(pkg, pageNum);
        this.actors = actors;
    }

    @Override
    public SlideType getType() {
        return SlideType.AGENDA;
    }

    @Override
    public String getHeadline() {
        return "Spint Review - Agenda";
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public String getActorsString(){
        StringBuilder actorsXml = new StringBuilder();
        for (final Actor actor : actors) {
            actorsXml.append(String.format(STKH_GROUP_TPL, actor.toString()));
        }
        return actorsXml.toString();
    }

    public final static String STKH_GROUP_TPL="<a:p>"+
            "            <a:pPr lvl=\"1\"/>"+
            "            <a:r>"+
            "                <a:rPr lang=\"en-US\" smtClean=\"0\">"+
            "                    <a:latin typeface=\"Headline\" pitchFamily=\"18\" charset=\"0\"/>"+
            "                </a:rPr>"+
            "                <a:t>%s</a:t>"+
            "            </a:r>"+
            "        </a:p>";
}
