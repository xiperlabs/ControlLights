package com.xiperlabs.ventas.app;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import com.xiperlabs.ventas.R;
import com.xiperlabs.ventas.callbacks.AlertCallback;
import com.xiperlabs.ventas.db.AppDatabase;
import com.xiperlabs.ventas.db.entities.TokenApp;

public class VentasApp extends Application {

    private AppExecutors mAppExecutors;
    public ProgressDialog progress;
    private static TokenApp token;
    private static double lat;
    private static double lng;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
        createToken();
    }

    public TokenApp getToken() {
        if (token == null) {
            createToken();
        }
        return token;
    }

    private void createToken() {
        mAppExecutors.diskIO().execute(() -> {
            getDatabase().runInTransaction(() -> {
                token = getDatabase().tokenAppDao().findToken();
            });
        });
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, getmAppExecutors());
    }

    public AppExecutors getmAppExecutors() {
        return mAppExecutors;
    }

    public void showAlert(String msg, String title, Context context, AlertCallback alertCallback) {
        mAppExecutors.mainThread().execute(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(msg)
                    .setTitle(title)
                    .setCancelable(false)
                    .setNeutralButton("OK", (dialog, id) -> {
                        dialog.cancel();
                        if (alertCallback != null) {
                            alertCallback.okSelected(0);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    public void showAlertYesNot(String msg, String title, Context context, AlertCallback alertCallback) {
        mAppExecutors.mainThread().execute(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(msg)
                    .setTitle(title)
                    .setCancelable(false)
                    .setNeutralButton("SI", (dialog, id) -> {
                        dialog.cancel();
                        if (alertCallback != null) {
                            alertCallback.okSelected(0);
                        }
                    })
                    .setNegativeButton("NO", (dialog, id) -> {
                        dialog.cancel();
                        if (alertCallback != null) {
                            alertCallback.okSelected(1);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    public void showProgress(String msg, Context context) {
        try {
            mAppExecutors.mainThread().execute(() -> {
                if (progress == null) {
                    progress = new ProgressDialog(context, R.style.MyProgress);
                    progress.setMessage("\n" + msg);
                    progress.setIndeterminate(true);
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
