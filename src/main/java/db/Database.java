package db;

import model.User;

import java.util.Collection;
import java.util.Objects;

public interface Database {
     <T> void addData(T objects);


    <T>  T findObjectById(Object Id);


    <T> Collection<T> findAll();

}
