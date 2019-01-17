package com.aimprosoft.android.optima.centralizedApp.db.dao.impl;

import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.db.entity.Services;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicesDAOImpl extends AbstractDAOImpl<Services> {
    public ServicesDAOImpl() {
        super(Services.class);
    }

    public List<Services> getServicesByLineTypeAndServiceType(String lineType, String serviceType) {
        try {
            QueryBuilder<Services, Integer> servicesStringQueryBuilder = getBaseDAO().queryBuilder();
            servicesStringQueryBuilder.where().eq(Services.LINE_TYPE, lineType).and().eq(Services.SERVICE_TYPE, serviceType);
            List<Services> serviceses = servicesStringQueryBuilder.orderBy(Services.FIELD_GROUP, true).query();
            if (serviceses.size() != 0) {
                return serviceses;
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new ArrayList<>();
    }

    public List<Services> getServicesByLineType_ServiceType_FieldName(String lineType, String serviceType, String fieldName) {
        try {
            QueryBuilder<Services, Integer> servicesStringQueryBuilder = getBaseDAO().queryBuilder();
            servicesStringQueryBuilder.where().eq(Services.LINE_TYPE, lineType).and().eq(Services.SERVICE_TYPE, serviceType).and().eq(Services.FIELD_NAME, fieldName);
            List<Services> serviceses = servicesStringQueryBuilder.orderBy(Services.FIELD_GROUP, true).query();
            if (serviceses.size() != 0) {
                return serviceses;
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new ArrayList<>();
    }


    public List<Services> getServicesByFieldNameAndServiceType(String fieldName, String serviceType, String configuratorType) {
        try {
            List<Services> serviceses = getBaseDAO().queryBuilder().where().eq(Services.FIELD_NAME, fieldName).
                    and().eq(Services.SERVICE_TYPE, serviceType).
                    and().eq(Services.SYSTEM, configuratorType).query();
            if (serviceses.size() != 0) {
                return serviceses;
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new ArrayList<>();
    }


    public Services getServicesByFieldName(String fieldName) {
        try {
            List<Services> serviceses = getBaseDAO().queryBuilder().where().eq(Services.FIELD_NAME, fieldName).query();
            if (serviceses.size() != 0) {
                return serviceses.get(0);
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new Services();
    }

    public List<Services> getServicesByServiceType(String serviceType) {
        try {
            List<Services> serviceses = getBaseDAO().queryBuilder().where().eq(Services.SERVICE_TYPE, serviceType).query();
            if (serviceses.size() != 0) {
                return serviceses;
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new ArrayList<>();
    }

    public List<Services> getServicesByIds(Object[] objects) {
        try {
            Where<Services, Integer> where = getBaseDAO().queryBuilder().where().in(Services.SERVICE_ID, objects);
            List<Services> serviceses = where.query();
            if (serviceses.size() != 0) {
                return serviceses;
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new ArrayList<>();
    }

    public Services getServiceById(int id) {
        try {
            List<Services> serviceses = getBaseDAO().queryBuilder().where().eq(Services.SERVICE_ID, id).query();
            if (serviceses.size() != 0) {
                return serviceses.get(0);
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new Services();
    }

    public Services getServiceByLineTypeAndServiceDesc(int lineTypeId, String lineDesc) {
        try {
            Services services = getBaseDAO().queryBuilder().where().eq(Services.LINE_TYPE, lineTypeId).and().like(Services.SERVICE_DESC, lineDesc).queryForFirst();
            if (services!=null) {
                return services;
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new Services();
    }

    public Services getServiceByDesc(String desc) {
        try {
            List<Services> serviceses = getBaseDAO().queryBuilder().where().eq(Services.SERVICE_DESC, desc).query();
            if (serviceses.size() != 0) {
                return serviceses.get(0);
            }
        } catch (SQLException e) {
            Log.e(TAG, "can't get row", e);
        }
        return new Services();
    }
}
