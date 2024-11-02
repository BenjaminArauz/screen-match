package com.example.screenmatchspring.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> clase);
}
