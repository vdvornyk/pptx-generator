package org.rallyplugins.service;

import com.google.common.base.Joiner;
import org.rallyplugins.commons.PptxCreator;
import org.rallyplugins.domain.Pptx;
import org.rallyplugins.domain.Sprint;
import org.rallyplugins.domain.enums.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.Set;


@Service
public class PptxService {
    private Logger log = LoggerFactory.getLogger(PptxService.class.getName());
    //    @Value("#{webAppProperties['rally.base.domain']}")
    public static String RALLY_DOMAIN = "http://rally-host.net/";

    public void generateNewPptx(Sprint sprint, OutputStream os) throws Exception {
        log.info("PPTX_SERV={}", RALLY_DOMAIN);
        Pptx pptx = new Pptx(sprint);
        PptxCreator pptxCreator = new PptxCreator(pptx);
        pptxCreator.createPptx(os);
    }

    public static String getFileName(Sprint sprint) {
        String sprintName = sprint.getSprintName();
        Set<Team> teamSet = sprint.getTeams();

        String teams = Joiner.on("-").join(teamSet);
        return String.format("%s_%s_Review.pptx", teams, sprintName);
    }
}
