package service;

import model.User;

import java.util.HashMap;
import java.util.Map;

public interface Service<T> {

    T createModel(Map<String, String> e);

}
