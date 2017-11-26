package com.culturecam.culturecam.entities;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageDTO implements Parcelable{
    private Bitmap image;
    private String title;
    private String rightsURL;
    private String sourceURL;

    public ImageDTO() {
    }

    public ImageDTO(Parcel in) {
        title = in.readString();
        rightsURL = in.readString();
        sourceURL = in.readString();
        image = in.readParcelable(null);
    }

    public static final Parcelable.Creator<ImageDTO> CREATOR
            = new Parcelable.Creator<ImageDTO>() {
        public ImageDTO createFromParcel(Parcel in) {
            return new ImageDTO(in);
        }

        public ImageDTO[] newArray(int size) {
            return new ImageDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(rightsURL);
        dest.writeString(sourceURL);
        dest.writeParcelable(image,0);
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRightsURL() {
        return rightsURL;
    }

    public void setRightsURL(String rightsURL) {
        this.rightsURL = rightsURL;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }
}
