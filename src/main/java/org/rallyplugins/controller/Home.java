package org.rallyplugins.controller;

import org.rallyplugins.domain.JsonData;
import org.rallyplugins.domain.Sprint;
import org.rallyplugins.domain.enums.Team;
import org.rallyplugins.service.PptxService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;


@Controller
public class Home {
    @Autowired
    private PptxService pptxService;

    private Logger log = Logger.getLogger(Home.class.getName());

    @RequestMapping("/")
    public ModelAndView home(ModelMap model) {
        model.addAttribute("jsonData", new JsonData());

        return new ModelAndView("index");
    }

    @RequestMapping(value = "/get-new-pptx", method = RequestMethod.POST)
    public void getFile(@ModelAttribute("jsonData") JsonData jsonData,
                        HttpServletResponse response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Sprint sprint = mapper.readValue(jsonData.getSprintJson(), Sprint.class);
            response.setContentType("application/pptx");
            response.setHeader("Content-Disposition", "attachment; filename=" + PptxService.getFileName(sprint));

            pptxService.generateNewPptx(sprint, response.getOutputStream());
            response.flushBuffer();


        } catch (Exception ex) {
            log.info("Error writing file to output stream....");
            throw new RuntimeException("Exception while generation file", ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public Team[] getTeams() {
        return Team.values();
    }
}
