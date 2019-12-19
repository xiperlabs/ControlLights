package com.xiperlabs.ventas.db;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.xiperlabs.ventas.app.AppExecutors;
import com.xiperlabs.ventas.db.dao.TokenAppDao;
import com.xiperlabs.ventas.db.entities.TokenApp;

@Database(entities = { TokenApp.class }, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "ventas-db";
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    private static AppDatabase sInstance;
    public abstract TokenAppDao tokenAppDao();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext(), executors);
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext, final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            database.setDatabaseCreated(executors);
                        });
                    }
                })
                .fallbackToDestructiveMigration()
                .build();
    }

    private void updateDatabaseCreated(final Context context, AppExecutors executors) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated(executors);
        }
    }

    private void setDatabaseCreated(AppExecutors executors) {
        mIsDatabaseCreated.postValue(true);
    }
}
