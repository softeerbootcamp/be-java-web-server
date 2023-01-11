package service;

import java.util.HashMap;

public interface Service {

    <T> void saveData(HashMap<T, T> data);
}
