package org.rallyplugins.commons;


import com.google.common.collect.Lists;
import com.google.common.collect.TreeMultimap;
import org.rallyplugins.domain.Pptx;
import org.rallyplugins.domain.Sprint;
import org.rallyplugins.domain.Story;
import org.rallyplugins.domain.enums.Actor;
import org.rallyplugins.domain.enums.Team;
import org.rallyplugins.domain.slide.*;

import java.util.*;

public class PptxTemplate {
    private static final int PART_SIZE = 11;
    private Pptx pptx;
    private Sprint sprint;

    private TreeMultimap<Actor, Story> actorGroups;
    private Set<Team> teams;
    private boolean multTeams;

    public PptxTemplate(Pptx pptx, Sprint sprint) {
        this.pptx = pptx;
        actorGroups = TreeMultimap.create();
        teams = new LinkedHashSet<Team>();
        this.sprint = sprint;
        parseStories(sprint.getStories());

        multTeams = teams.size() > 1;
    }

    public List<AbstractSlide> getTemplate() throws Exception {
        int slideNum = 0;
        List<AbstractSlide> slides = new ArrayList<AbstractSlide>();

        slides.add(new TitleSlide(pptx.getPML(), ++slideNum, pptx.getPptxInfo()));
        slides.add(new AgendaSlide(pptx.getPML(), ++slideNum, pptx.getPptxTemplate().getActors()));
        slides.add(new GoalsSlide(pptx.getPML(), ++slideNum, sprint));

        for (TreeMultimap<Actor, Story> multimap : actorGroupsPartition()) {
            slides.add(new DeliverablesSlide(pptx.getPML(), ++slideNum, multimap, multTeams));
        }

        for (Actor actor : actorGroups.keySet()) {
            Collection<Story> storyList = actorGroups.get(actor);
            slides.add(new ActorSlide(pptx.getPML(), ++slideNum, actor));
            for (Story story : storyList) {
                if (story.isFeature()) {
                    slides.add(new FeatureSlide(pptx.getPML(), ++slideNum, story));
                }
            }
        }

        for (Team team : pptx.getPptxTemplate().getTeams()) {
            slides.add(new SummarySlide(pptx.getPML(), ++slideNum, team));
        }

        slides.add(new FinalSlide(pptx.getPML(), ++slideNum));

        return slides;
    }

    private List<TreeMultimap<Actor, Story>> actorGroupsPartition() {

        List<TreeMultimap<Actor, Story>> partitionList = Lists.newArrayList();
        TreeMultimap<Actor, Story> partMap = createPartAndUpdatePartList(partitionList);

        int counter = 1;

        for (Actor actor : actorGroups.asMap().keySet()) {
            if (counter++ > PART_SIZE - 1) {
                partMap = createPartAndUpdatePartList(partitionList);
                counter = 1;
            }
            for (Story story : actorGroups.get(actor)) {
                partMap.put(actor, story);
                if (counter++ > PART_SIZE) {
                    partMap = createPartAndUpdatePartList(partitionList);
                    counter = 1;
                }
            }
        }


        return partitionList;
    }

    private TreeMultimap<Actor, Story> createPartAndUpdatePartList(List<TreeMultimap<Actor, Story>> partitionList) {
        TreeMultimap<Actor, Story> partMap = TreeMultimap.create();
        partitionList.add(partMap);
        return partMap;
    }

    private void parseStories(List<Story> stories) {
        for (Story story : stories) {
            actorGroups.put(story.getActor(), story);

            if (!teams.contains(story.getTeam())) {
                teams.add(story.getTeam());
            }
        }
    }

    public Set<Actor> getActors() {
        return actorGroups.keySet();
    }

    public Set<Team> getTeams() {
        return teams;
    }
}
