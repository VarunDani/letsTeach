package edu.gatech.mas.letsteach.utility;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ListBean {

    private int listId;
    private String listName;
    private String listDescription;

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    private Drawable image;

    public ListBean(){}
    public ListBean(String listName, String listDescription)
    {
        this.listName = listName;
        this.listDescription = listDescription;
    }

    public ListBean(int listId, String listName, String listDescription) {
        this.listId = listId;
        this.listName = listName;
        this.listDescription = listDescription;
    }

    public ListBean(int listId, String listName, String listDescription,Drawable image) {
        this.listId = listId;
        this.listName = listName;
        this.listDescription = listDescription;
        this.image = image;
    }


    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }



}
