package com.studyjun.tucao.manager;


public interface StringCache {
    public String getString(String url);

    public void putString(String url, String json);
}