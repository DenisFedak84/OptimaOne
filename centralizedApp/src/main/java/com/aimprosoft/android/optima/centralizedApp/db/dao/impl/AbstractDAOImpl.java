package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.content.Context;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.HelperFactory;
import com.aimprosoft.android.optima.centralizedApp.db.dao.AbstractDAO;
import com.aimprosoft.android.optima.centralizedApp.db.entity.BaseEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAOImpl<Entity extends BaseEntity> implements AbstractDAO<Entity> {

    protected final String TAG = this.getClass().getSimpleName();
    Dao<Entity, Integer> dao;
    @SuppressWarnings("FieldCanBeLocal")
    private ConnectionSource connectionSource;
    private DatabaseConnection readWriteConnection;

    @SuppressWarnings("UnusedDeclaration")
    public Context getContext() {
        return MyApplication.getContext();
    }

    public AbstractDAOImpl(Class<Entity> entity) {
        try {
            connectionSource = HelperFactory.getConnectionSource();
            readWriteConnection = connectionSource.getReadWriteConnection();
            this.dao = DaoManager.createDao(connectionSource, entity);
        } catch (SQLException | NullPointerException e) {
            Log.e(TAG, "connection failed or connection don't created", e);
        }
    }

    @Override
    public List<Entity> getAllRows() throws SQLException {
        Savepoint savepoint = null;
        List<Entity> entities = new ArrayList<>();
        try {
            savepoint = readWriteConnection.setSavePoint(String.valueOf(this.hashCode()));
            entities = dao.queryForAll();

            readWriteConnection.commit(savepoint);
        } catch (Throwable e) {
            try {
                readWriteConnection.rollback(savepoint);
            } catch (Throwable ignored) {
            }
            Log.w(TAG, "can't get info from base", e);
        }
        return entities;
    }

    @Override
    public boolean delete(Entity o) {
        Savepoint savepoint = null;
        try {
            savepoint = readWriteConnection.setSavePoint(String.valueOf(o.hashCode()));
            dao.delete(o);
            readWriteConnection.commit(savepoint);
            return true;
        } catch (Throwable e) {
            try {
                readWriteConnection.rollback(savepoint);
            } catch (Throwable ignored) {
            }
            Log.w(TAG, "can't delete row", e);
            return false;
        }
    }

    @Override
    public boolean insert(Entity o) {
        Savepoint savepoint = null;
        try {
            savepoint = readWriteConnection.setSavePoint(String.valueOf(o.hashCode()));
            dao.create(o);
            readWriteConnection.commit(savepoint);
            return true;
        } catch (Throwable e) {
            try {
                readWriteConnection.rollback(savepoint);
            } catch (Throwable ignored) {
            }
            Log.w(TAG, "can't insert new row", e);
            return false;
        }
    }

    @Override
    public boolean update(Entity o) {
        Savepoint savepoint = null;
        try {
            savepoint = readWriteConnection.setSavePoint(String.valueOf(o.hashCode()));
            dao.update(o);
            readWriteConnection.commit(savepoint);
            return true;
        } catch (Throwable e) {
            try {
                readWriteConnection.rollback(savepoint);
            } catch (Throwable ignored) {
            }
            Log.w(TAG, "problem with update row", e);
            return false;
        }
    }

    @Override
    public Dao<Entity, Integer> getBaseDAO() {
        return dao;
    }

}
