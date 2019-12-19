package com.xiperlabs.ventas.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.xiperlabs.ventas.db.entities.TokenApp;


@Dao
public interface TokenAppDao {
    @Query("DELETE FROM tokenapp")
    void deleteAll();

    @Query("SELECT * FROM tokenapp LIMIT 1")
    TokenApp findToken();

    @Insert
    void insertTokenApp(TokenApp tokenApp);
}
