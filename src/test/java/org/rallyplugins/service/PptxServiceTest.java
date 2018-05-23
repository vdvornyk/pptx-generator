package org.rallyplugins.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.rallyplugins.domain.Sprint;
import org.rallyplugins.domain.enums.Team;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PptxServiceTest {
    @Mock
    Sprint sprint;

    @Before
    public void setUp() throws Exception {
        when(sprint.getSprintName()).thenReturn("Sprint-31");
        when(sprint.getTeams()).thenReturn(Sets.newLinkedHashSet(Lists.newArrayList(Team.Nbrst, Team.Triar)));
    }

    @Test
    public void testGetFileName() throws Exception {
        Assert.assertEquals("Nbrst-Tria_Sprint-31_Review.pptx", PptxService.getFileName(sprint));
    }
}
