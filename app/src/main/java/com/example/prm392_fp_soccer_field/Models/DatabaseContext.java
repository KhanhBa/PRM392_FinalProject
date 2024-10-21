

package com.example.prm392_fp_soccer_field.Models;


import android.annotation.SuppressLint;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseContext {

    @SuppressLint("AuthLeak")
    private static final String DATABASE_URL = "jdbc:sqlserver://outfit4rent.online:1433;databaseName=PRM392_BookSocerYard;user=sa;password=Fall24@FengShuiKoi";
    private ConnectionSource connectionSource;

    public DatabaseContext() throws SQLException {
        this.connectionSource = new JdbcConnectionSource(DATABASE_URL);
    }

    public <T, ID> Dao<T, ID> getDao(Class<T> clazz) throws SQLException {
        return DaoManager.createDao(connectionSource, clazz);
    }

    public void close() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}