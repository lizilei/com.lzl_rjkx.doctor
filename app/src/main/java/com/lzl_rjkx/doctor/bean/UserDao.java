package com.lzl_rjkx.doctor.bean;

import android.database.Cursor;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public class UserDao {
    private DbManager db;

    public UserDao(DbManager db) {
        this.db = db;
    }

    public void add(Doctor doctor) {
        try {
            db.save(doctor);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void deleteByid(Object object) {
        try {
            db.deleteById(Doctor.class, object);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public Doctor findById(Object id) {
        try {
            return db.findById(Doctor.class, id);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Doctor> findAll() {
        try {
            return db.findAll(Doctor.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateZanState(Doctor doctor) {
        try {
            db.update(doctor, new String[]{"zanState"});
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void updateCollectState(Doctor doctor) {
        try {
            db.update(doctor, new String[]{"collectState"});
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public int getcolumnValue(int msgId, String columnName) {
        try {
            Cursor cursor = db.execQuery("select * from doctor where msgId=" + msgId);
            return cursor.getInt(cursor.getColumnIndex(columnName));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
