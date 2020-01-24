package com.gojek.trending.assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BuiltBy implements Parcelable {

    private String href;
    private String avatar;
    private String username;

    public BuiltBy(String href, String avatar, String username) {
        this.href = href;
        this.avatar = avatar;
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeString(this.avatar);
        dest.writeString(this.username);
    }

    protected BuiltBy(Parcel in) {
        this.href = in.readString();
        this.avatar = in.readString();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<BuiltBy> CREATOR = new Parcelable.Creator<BuiltBy>() {
        @Override
        public BuiltBy createFromParcel(Parcel source) {
            return new BuiltBy(source);
        }

        @Override
        public BuiltBy[] newArray(int size) {
            return new BuiltBy[size];
        }
    };

    @Override
    public String toString() {
        return "BuiltBy{" +
                "href='" + href + '\'' +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

