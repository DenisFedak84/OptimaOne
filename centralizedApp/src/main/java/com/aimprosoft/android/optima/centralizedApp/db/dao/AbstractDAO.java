package com.aimprosoft.android.optima.centralizedApp.db.dao;

import android.database.sqlite.SQLiteException;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public interface AbstractDAO<T> {

    List<T> getAllRows() throws SQLiteException, SQLException;

    boolean delete(T t);

    boolean insert(T t);

    boolean update(T t);

    Dao<T, Integer> getBaseDAO();
}
