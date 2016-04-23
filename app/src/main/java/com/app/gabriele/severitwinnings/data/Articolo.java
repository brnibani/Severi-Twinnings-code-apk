package com.app.gabriele.severitwinnings.data;

public class Articolo {

    private String title;  //titolo dell'articolo
    private String date; //data dell'articolo
    private String content;  //contenuto dell'articolo
    private String category;    //categoria dell'articolo (Generale, Studenti italiani in America,
                                //studenti americani in Italia, Focus USA, Focus Palestina)
    private String link;   //link relativo all'articolo
    private String link_comment;    //link relativo ai commenti
    private String num_comments;    //numero di commenti relativi all'articolo

    /**
     * Metodo che provvede a settare la data dell'articolo
     * @param d
     */
    public void setDate(String d){
        this.date = d;
    }

    /**
     * ritorna la data dell'articolo
     * @return
     */
    public String getDate(){
        return date;
    }


    /**
     * Restituisce il link relativo ai commenti dell'articolo
     * @return link
     */
    public String getLinkComment(){
        return link_comment;
    }

    /**
     * Permette di assegnare alla variabile link_comment il link relativo ai commenti dell'articolo
     * @param a (link dei commenti dell'articolo)
     */
    public void setLinkComment(String a ){
        this.link_comment = a;
    }

    /**
     * Restituisce il numero di commenti inseriti relativi a questo articolo
     * @return num_comments
     */
    public String getNumComments(){
        return num_comments;
    }

    /**
     * Permette di assegnare alla variabile num_comments il numero di commenti inseriti relativi
     * a questo articolo
     * @param a (numero di commenti)
     */
    public void setNumComments(String a ){
        this.num_comments = a;
    }

    /**
     * Permette di assegnare alla variabile link il link relativo a questo articolo
     * @param link
     */
    public void setLink(String link){
        this.link = link;
    }

    /**
     * Restituisce il link relativo a questo articolo
     * @return link
     */
    public String getLink(){
        return link;
    }

    /**
     * Restituisce la categoria dell'articolo
     * @return categoria
     */
    public String getCategory(){
        return category;
    }

    /**
     * Permette di assegnare, alla variabile category, la categoria dell'articolo
     * @param category
     */
    public void setCategory(String category){
        this.category = category;
    }

    /**
     * Restituisce il titolo dell'articolo
     * @return titolo
     */
    public String getTitle() {
        return title;
    }

    /**
     * Permette di assegnare, alla variabile title, il titolo dell'articolo
     * @param title (titolo dell'articolo)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Restituisce tutto il contenuto dell'articolo (testo, immagini, ...)
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Permette di assegnare, alla variabile content, tutto il contenuto dell'articolo (testo, immagini, ...)
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return title;
    }


    //   private String img_copertina = null;
/*
    public void setImgCopertina(String img_copertina){
        this.img_copertina = img_copertina;
    }
    public String getImgCopertina(){
        return img_copertina;
    }
*/


}
