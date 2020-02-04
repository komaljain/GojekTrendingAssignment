package com.gojek.trending.assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Repository implements Parcelable {

    private String author;
    private String name;
    private String avatar;
    private String url;
    private String description;
    private String language;
    private String languageColor;
    private int stars;
    private int forks;
    private int currentPeriodStars;
    private List<BuiltBy> builtBy;

    public Repository(String author, String name, String avatar, String url, String description, String language, String languageColor, int stars, int forks, int currentPeriodStars, List<BuiltBy> builtBy) {
        this.author = author;
        this.name = name;
        this.avatar = avatar;
        this.url = url;
        this.description = description;
        this.language = language;
        this.languageColor = languageColor;
        this.stars = stars;
        this.forks = forks;
        this.currentPeriodStars = currentPeriodStars;
        this.builtBy = builtBy;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(int currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }

    public List<BuiltBy> getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(List<BuiltBy> builtBy) {
        this.builtBy = builtBy;
    }

    public static Creator<Repository> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.url);
        dest.writeString(this.description);
        dest.writeString(this.language);
        dest.writeString(this.languageColor);
        dest.writeInt(this.stars);
        dest.writeInt(this.forks);
        dest.writeInt(this.currentPeriodStars);
        dest.writeList(this.builtBy);
    }

    protected Repository(Parcel in) {
        this.author = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.url = in.readString();
        this.description = in.readString();
        this.language = in.readString();
        this.languageColor = in.readString();
        this.stars = in.readInt();
        this.forks = in.readInt();
        this.currentPeriodStars = in.readInt();
        this.builtBy = new ArrayList<BuiltBy>();
        in.readList(this.builtBy, BuiltBy.class.getClassLoader());
    }

    public static final Parcelable.Creator<Repository> CREATOR = new Parcelable.Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel source) {
            return new Repository(source);
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };

    @Override
    public String toString() {
        return "Repository{" +
                "author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", languageColor='" + languageColor + '\'' +
                ", stars='" + stars + '\'' +
                ", forks='" + forks + '\'' +
                ", currentPeriodStars='" + currentPeriodStars + '\'' +
                ", builtBy=" + builtBy +
                '}';
    }
}
