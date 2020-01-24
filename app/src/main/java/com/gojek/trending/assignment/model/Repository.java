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
    private String stars;
    private String forks;
    private String currentPeriodStars;
    private List<BuiltBy> builtBy;

    public Repository(String author, String name, String avatar, String url, String description, String language, String languageColor, String stars, String forks, String currentPeriodStars, List<BuiltBy> builtBy) {
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
        dest.writeString(this.stars);
        dest.writeString(this.forks);
        dest.writeString(this.currentPeriodStars);
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
        this.stars = in.readString();
        this.currentPeriodStars = in.readString();
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
