package com.gregbarasch.scholarshipmanagementsystem.database.dao;

import java.util.Collection;

/**
 * FIXME exception handling inside DAO.... Dao exception? not sql exception...
 * Convention is to wrap sqlexception with layer-specific exception and error code, like in hibernate...
 * No boolean return values
 */
public interface Dao<T> {

    T get(long id);

    Collection<T> getAll();

    boolean insert(T item);

    boolean insertOrUpdate(T item);

    boolean update(T item);

    boolean delete(T item);
}
