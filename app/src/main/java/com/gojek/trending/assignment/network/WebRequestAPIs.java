package com.gojek.trending.assignment.network;

public enum WebRequestAPIs {


    GET_REPOSITORIES("repositories");

    private String url;

    WebRequestAPIs(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}