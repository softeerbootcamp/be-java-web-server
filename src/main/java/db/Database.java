package db;

import model.User;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public interface Database<T> {
    Optional<T> findObjectById(String Id);
     void addData(T objects);




    Collection<T> findAll();

}
