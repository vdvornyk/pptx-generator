package org.rallyplugins.domain.enums;

import com.google.common.collect.ImmutableSet;
import org.rallyplugins.domain.slide.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public enum TplKey {
    part_id,
    story_code,
    story_link_id,
    team_name,
    page_num,
    rEmbedId,
    date,
    projectName,
    tranche,
    title,
    headline,
    actor,
    stkh_group,
    goals,
    gridColList,
    gridRowList;

    public static HashMap<String, String> getTplMapping(AbstractSlide slide, ImmutableSet<TplKey> tplKeys) throws JAXBException, IOException {
        HashMap<String, String> mapping = new HashMap<String, String>();
        for (TplKey key : tplKeys) {
            switch (key) {
                case part_id:
                    mapping.put(part_id.name(), slide.getNextId());
                    break;

                case story_code:
                    if (slide instanceof FeatureSlide) {
                        mapping.put(story_code.name(), ((FeatureSlide) slide).getStory().getCode());
                    }
                    break;
                case story_link_id:
                    if (slide instanceof FeatureSlide) {
                        mapping.put(story_link_id.name(), slide.createLinkId(((FeatureSlide) slide).getStory().getUrl()));
                    }
                    break;
                case team_name:
                    if (slide instanceof FeatureSlide) {
                        mapping.put(team_name.name(), ((FeatureSlide) slide).getStory().getTeam().toString());
                    }
                    break;
                case page_num:
                    mapping.put(page_num.name(), slide.getPageNum());
                    break;
                case rEmbedId:
                    mapping.put(rEmbedId.name(), slide.getrEmbedId());
                    break;
                case date:
                    mapping.put(date.name(), currentDate());
                    break;
                case projectName:
                    if (slide instanceof TitleSlide) {
                        mapping.put(projectName.name(), ((TitleSlide) slide).getPptxInfo().getProjectName());
                    }
                    break;
                case tranche:
                    if (slide instanceof TitleSlide) {
                        mapping.put(tranche.name(), ((TitleSlide) slide).getPptxInfo().getTranche());
                    }
                    break;
                case goals:
                    if (slide instanceof GoalsSlide) {
                        mapping.put(goals.name(), ((GoalsSlide) slide).getGoalsTpl());
                    }
                    break;
                case title:
                    if (slide instanceof TitleSlide) {
                        mapping.put(title.name(), ((TitleSlide) slide).getPptxInfo().getTitle());
                    }
                    break;
                case headline:
                    mapping.put(headline.name(), slide.getHeadline());
                    break;
                case actor:
                    if (slide instanceof ActorSlide) {
                        mapping.put(actor.name(), ((ActorSlide) slide).getActor().toString());
                    }
                    break;
                case stkh_group:
                    if (slide instanceof AgendaSlide) {
                        mapping.put(stkh_group.name(), ((AgendaSlide) slide).getActorsString());
                    }
                    break;
                case gridColList:
                    if (slide instanceof DeliverablesSlide) {
                        mapping.put(gridColList.name(), DeliveryCol.getGridCols(((DeliverablesSlide) slide).isMultPresenter()));
                    }
                    break;
                case gridRowList:
                    if (slide instanceof DeliverablesSlide) {
                        mapping.put(gridRowList.name(), ((DeliverablesSlide) slide).getRows());
                    }
                    break;
            }
        }
        return mapping;
    }

    private static String currentDate() {
        return new SimpleDateFormat("MMMM yyyy").format(new Date());
    }
}