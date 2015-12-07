package com.app.gabriele.severitwinnings.data;

public class RssAtomItem {

    private String title;
    private String content;
    private String category;
    private String link;
    private String img_copertina = null;
    private String link_comment;
    private String num_comments;


    public void setImgCopertina(String img_copertina){
        this.img_copertina = img_copertina;
    }
    public String getImgCopertina(){
        return img_copertina;
    }

    public String getLinkComment(){return link_comment;}
    public void setLinkComment(String a ){this.link_comment = a;}

    public String getNumComments(){return num_comments;}
    public void setNumComments(String a ){this.num_comments = a;}

    public void setLink(String link){
        this.link = link;
    }

    public String getLink(){
        return link;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return title;
    }

}
