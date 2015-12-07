package com.app.gabriele.severitwinnings.util;

/**
 * Created by Gabriele on 07/05/2015.
 */
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import com.app.gabriele.severitwinnings.data.RssAtomItem;


public class RssAtomReader {

    private String rssUrl;

    /**
     * Constructor
     *
     * @param rssUrl
     */
    public RssAtomReader(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    /**
     * Get RSS items.
     *
     * @return
     */
    public List<RssAtomItem> getItems() throws Exception {
        // SAX parse RSS data
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser saxParser = factory.newSAXParser();

        RssAtomParseHandler handler = new RssAtomParseHandler();

        saxParser.parse(rssUrl, handler);

        return handler.getItems();

    }

}
