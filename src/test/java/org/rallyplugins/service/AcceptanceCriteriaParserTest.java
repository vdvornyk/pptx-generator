package org.rallyplugins.service;

import com.google.common.collect.ImmutableList;
import com.topologi.diffx.xml.esc.XMLEscapeASCII;
import org.rallyplugins.commons.utils.ResourceUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class AcceptanceCriteriaParserTest {
    public static final String EXP_NAME_TPL_PATH = "/expected/%d.txt";
    public static final String ACTUAL_NAME_TPL_PATH = "/actual/%d.html";

    private AcceptanceCriteriaParser parser;
    private List<String> expectedList;
    private String actualHtml;
    private int testCount;

    public AcceptanceCriteriaParserTest(int fileCode) {
        try {
            testCount=fileCode;
            parser = new AcceptanceCriteriaParser();
            String expectedFilePath = String.format(EXP_NAME_TPL_PATH, fileCode);
            String actualFilePath = String.format(ACTUAL_NAME_TPL_PATH, fileCode);
            expectedList = ResourceUtils.getStringsFromFile(expectedFilePath);
            actualHtml = ResourceUtils.getTplFromFile(actualFilePath);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void shoulFindAcceptanceCriteriaWhenHtml() {
        //when

        ImmutableList<String> parsedAccCriterias = parser.parse(actualHtml);
        //then
        Assert.assertEquals( expectedList.size(), parsedAccCriterias.size());

        for (int i = 0; i < expectedList.size(); i++) {
            if(i==4){
                System.out.println("1");
            }
            String expectedConverted=expectedList.get(i).replace((char) 160, (char) 32).trim();
            String escapedString=XMLEscapeASCII.ASCII_ESCAPE.toAttributeValue(expectedConverted);
            Assert.assertEquals(escapedString, parsedAccCriterias.get(i));
        }
    }

    @Parameterized.Parameters
    public static java.util.Collection<Object[]> data() {
        final int N = 17;

        Object[][] data = new Object[N][1];
        for (int i = 0; i < N; i++) {
            data[i][0] = i;
        }

        return Arrays.asList(data);
    }
}
