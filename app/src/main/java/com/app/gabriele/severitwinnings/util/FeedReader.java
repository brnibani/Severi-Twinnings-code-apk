package com.app.gabriele.severitwinnings.util;

/**
 * Created by Gabriele on 07/05/2015.
 */
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import com.app.gabriele.severitwinnings.data.Articolo;


public class FeedReader {

    private String FeedUrl;


    public FeedReader(String feedUrl) {
        this.FeedUrl = feedUrl;
    }


    public List<Articolo> getItems() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        FeedParseHandler handler = new FeedParseHandler();
        saxParser.parse(FeedUrl, handler);

        return handler.getItems();

    }

}
