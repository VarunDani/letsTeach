package edu.gatech.mas.letsteach.feed;

import android.graphics.drawable.Drawable;

/**
 * Created by Mark Cua on 1/17/2017.
 */

public class Feed {

    private String name, location, imageUrl, description, timeStamp;

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    private Drawable image;

    public Feed(String name, String location, String imageUrl, String description, String timeStamp) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.description = description;
        this.timeStamp = timeStamp;
    }

    public Feed(String name, String location, String imageUrl, String description, String timeStamp,Drawable image) {
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.description = description;
        this.timeStamp = timeStamp;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
