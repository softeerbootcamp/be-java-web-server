package service;

import model.User;

import java.util.HashMap;

public interface Service<T> {

     T createModel(HashMap<String,String> e);

}
