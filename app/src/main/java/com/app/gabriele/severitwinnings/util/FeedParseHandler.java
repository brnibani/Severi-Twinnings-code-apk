package com.app.gabriele.severitwinnings.util;

/**
 * Created by Gabriele on 07/05/2015.
 */
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.app.gabriele.severitwinnings.data.Articolo;

public class FeedParseHandler extends DefaultHandler {

    private List<Articolo> rssItems;

    // Used to reference item while parsing
    private Articolo currentItem;

    private boolean parsingTitle;
    private boolean parsingContents;
    private boolean parsingCategory;
    private boolean parsingLink;
    private boolean parsingLinkComment;
    private boolean parsingNumComments;
    private boolean parsingDate;
    private StringBuffer currentDateSb;
    private StringBuffer currentCategorySb;
    private StringBuffer currentTitleSb;
    private StringBuffer currentLinkSb;
    private StringBuffer currentContentSb;
    private StringBuffer currentLinkCommentSb;
    private StringBuffer currentNumCommentsSb;

    public FeedParseHandler() {
        rssItems = new ArrayList<Articolo>();
    }

    public List<Articolo> getItems() {
        return rssItems;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if ("item".equals(qName)) {
            currentItem = new Articolo();
        } else if ("title".equals(qName)) {
            parsingTitle = true;
            currentTitleSb = new StringBuffer();
        } else if ("pubDate".equals(qName)) {
            parsingDate = true;
            currentDateSb = new StringBuffer();
        } else if ("content:encoded".equals(qName)) {
            parsingContents = true;
            currentContentSb = new StringBuffer();
        } else if ("category".equals(qName)){
            parsingCategory = true;
            currentCategorySb = new StringBuffer();
        } else if ("link".equals(qName)){
            parsingLink = true;
            currentLinkSb = new StringBuffer();
        } else if ("wfw:commentRss".equals(qName)){
            parsingLinkComment = true;
            currentLinkCommentSb = new StringBuffer();
        } else if ("slash:comments".equals(qName)){
            parsingNumComments = true;
            currentNumCommentsSb = new StringBuffer();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if ("item".equals(qName)) {
            rssItems.add(currentItem);
            currentItem = null;
        } else if ("title".equals(qName)) {
            parsingTitle = false;

            if (currentItem != null) // There is a title tag for a whole channel present. It is being parsed before the entry tag is present, so we need to check if item is not null
                currentItem.setTitle(currentTitleSb.toString());

        } else if ("pubDate".equals(qName)) {

            parsingDate = false;

            if (currentItem != null)
                currentItem.setDate(currentDateSb.toString());

        } else if ("content:encoded".equals(qName)) {
            parsingContents = false;

            if (currentItem != null)
                currentItem.setContent(currentContentSb.toString());

        } else if ("category".equals(qName)){
            parsingCategory = false;

            if(currentItem != null)
                currentItem.setCategory(currentCategorySb.toString());
        } else if ("link".equals(qName)){
            parsingLink = false;

            if(currentItem != null)
                currentItem.setLink(currentLinkSb.toString());
        } else if ("wfw:commentRss".equals(qName)){
            parsingLinkComment = false;

            if(currentItem != null)
                currentItem.setLinkComment(currentLinkCommentSb.toString());
        }else if ("slash:comments".equals(qName)){
            parsingNumComments = false;

            if(currentItem != null)
                currentItem.setNumComments(currentNumCommentsSb.toString());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (parsingTitle) {
            if (currentItem != null)
                currentTitleSb.append(new String(ch, start, length));
        } else if (parsingDate) {
            if (currentItem != null)
                currentDateSb.append(new String(ch, start, length));
        } else if (parsingContents) {
            if (currentItem != null)
                currentContentSb.append(new String(ch, start, length));
        } else if (parsingCategory){
            if (currentCategorySb != null){
                currentCategorySb.append(new String(ch, start, length));
            }
        } else if (parsingLink){
            if(currentLinkSb != null){
                currentLinkSb.append(new String(ch,start,length));
            }
        } else if (parsingLinkComment){
            if(currentLinkCommentSb != null){
                currentLinkCommentSb.append(new String(ch,start,length));
            }
        }else if (parsingNumComments){
            if(currentNumCommentsSb != null){
                currentNumCommentsSb.append(new String(ch,start,length));
            }
        }
    }

}
