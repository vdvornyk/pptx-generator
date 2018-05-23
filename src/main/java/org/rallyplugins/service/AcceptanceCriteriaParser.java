package org.rallyplugins.service;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.topologi.diffx.xml.esc.XMLEscapeASCII;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class AcceptanceCriteriaParser {

    public ImmutableList<String> parse(String htmlString) {
        String cleanedHtml = Jsoup.clean(htmlString, Whitelist.basic().addTags("span"));
        String cleanNbspString = cleanedHtml.replaceAll("&nbsp;", String.valueOf((char) 32)).trim();
        Document document = Jsoup.parse(cleanNbspString);
        for (String selector : selectors) {
            Elements elements = document.select(selector);
            if (elements != null && elements.size() > 0) {
                ImmutableList<String> accCriterias = FluentIterable
                        .from(elements)
                        .filter(new FilterElements())
                        .transform(new TransformElements())
                        .toImmutableList();
                if (accCriterias.size() > 0) {
                    return accCriterias;
                }
            }
        }
        return null;
    }

    private static final ImmutableList<String> selectors = ImmutableList.copyOf(new String[]{
            "b:contains(Acceptance Criteria)+ol>li",
            "b:contains(Acceptance Criteria)+ul>li",
            "b:contains(Acceptance Criteria)+b+ol>li",
            "b:contains(Acceptance Criteria)+b+ul>li",
            "b:contains(Acceptance Criteria)+br+ol>li",
            "b:contains(Acceptance Criteria)+br+ul>li",
            "b:contains(Acceptance Criteria)+span+ol>li",
            "b:contains(Acceptance Criteria)+span+ul>li",
            "b:contains(Acceptance Criteria)+b+br+ul>li",
            "b:contains(Acceptance Criteria)+b+br+ol>li",
            "b:contains(Acceptance Criteria)+span+b+ul>li",
            "b:contains(Acceptance Criteria)+span+b+ol>li",
            "b:contains(Acceptance Criteria)+br ~ span",
            "u:contains(Acceptance Criteria) ~ i"
    });

    private static class TransformElements implements Function<Element, String> {

        @Override
        public String apply(Element element) {
            return XMLEscapeASCII.ASCII_ESCAPE.toAttributeValue(element.text());
        }
    }

    private static class FilterElements implements Predicate<Element> {
        @Override
        public boolean apply(final Element element) {
            String filter = element.text().replace((char) 160, (char) 32).trim();
            return !Strings.isNullOrEmpty(filter) && !filter.isEmpty();
        }
    }
}
