package com.aimprosoft.android.optima.centralizedApp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOfferDateInterval;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetDetailOffers;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TlcDetailsOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;
import com.aimprosoft.android.optima.centralizedApp.util.LocalSharedPreferencesManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "configuratore_unico_base.db";
    private static final int DATABASE_VERSION = 210; //11.27.2018

    //Versions less than this can't be updated with keeping user data
    //Also UPDATE_DATABASE_VERSION_LIMIT constant should be changed if structure in some offer table was changed
    private static final int UPDATE_DATABASE_VERSION_LIMIT = 190;

    private final String TAG = this.getClass().getSimpleName();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            if (database.getVersion() == 0){
                TableUtils.createTableIfNotExists(connectionSource, EnergyOffer.class);
                TableUtils.createTableIfNotExists(connectionSource, EnergyOfferDateInterval.class);
                TableUtils.createTableIfNotExists(connectionSource, InternetOffer.class);
                TableUtils.createTableIfNotExists(connectionSource, MobileOffer.class);
                TableUtils.createTableIfNotExists(connectionSource, MobileDetailsOffer.class);
                TableUtils.createTableIfNotExists(connectionSource, Offer.class);
                TableUtils.createTableIfNotExists(connectionSource, GasOffer.class);
                TableUtils.createTableIfNotExists(connectionSource, GasOfferDateInterval.class);
                TableUtils.createTableIfNotExists(connectionSource, TLCOffer.class);
                TableUtils.createTableIfNotExists(connectionSource, WlrOfferDetails.class);
                TableUtils.createTableIfNotExists(connectionSource, WlrOffers.class);
                TableUtils.createTableIfNotExists(connectionSource, TlcDetailsOffer.class);
                TableUtils.createTableIfNotExists(connectionSource, EnergyOfferDetails.class);
                TableUtils.createTableIfNotExists(connectionSource, GasDetailOffers.class);
                TableUtils.createTableIfNotExists(connectionSource, InternetDetailOffers.class);
            }
        } catch (Throwable e) {
            Log.e(TAG, "error creating DB - " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        LocalSharedPreferencesManager.getInstance().storeSharedPreferencesIntValue(MyApplication.getContext(), LocalSharedPreferencesManager.CURRENT_DB_VERSION, oldVersion);
    }

    public static int getUpdateDatabaseVersionLimit() {
        return UPDATE_DATABASE_VERSION_LIMIT;
    }

    public static void clearDbTable(Class databaseTable) {
        try {
            TableUtils.clearTable(HelperFactory.getConnectionSource(), databaseTable);
        } catch (SQLException e) {
            Log.e("MyApplication", "error while clearing table", e);
        }
    }
}