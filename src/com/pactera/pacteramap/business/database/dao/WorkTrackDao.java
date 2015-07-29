package com.pactera.pacteramap.business.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.pactera.pacteramap.business.database.bean.WorkTrack;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table WORK_TRACK.
*/
public class WorkTrackDao extends AbstractDao<WorkTrack, Long> {

    public static final String TABLENAME = "WORK_TRACK";

    /**
     * Properties of entity WorkTrack.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Date = new Property(1, String.class, "date", false, "DATE");
        public final static Property UserName = new Property(2, String.class, "userName", false, "USER_NAME");
        public final static Property UserImei = new Property(3, String.class, "userImei", false, "USER_IMEI");
        public final static Property Longitude = new Property(4, String.class, "longitude", false, "LONGITUDE");
        public final static Property Latitude = new Property(5, String.class, "latitude", false, "LATITUDE");
        public final static Property IsMark = new Property(6, String.class, "isMark", false, "IS_MARK");
        public final static Property MarkIndex = new Property(7, String.class, "markIndex", false, "MARK_INDEX");
        public final static Property Desc = new Property(8, String.class, "desc", false, "DESC");
    };


    public WorkTrackDao(DaoConfig config) {
        super(config);
    }
    
    public WorkTrackDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'WORK_TRACK' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'DATE' TEXT," + // 1: date
                "'USER_NAME' TEXT," + // 2: userName
                "'USER_IMEI' TEXT," + // 3: userImei
                "'LONGITUDE' TEXT," + // 4: longitude
                "'LATITUDE' TEXT," + // 5: latitude
                "'IS_MARK' TEXT," + // 6: isMark
                "'MARK_INDEX' TEXT," + // 7: markIndex
                "'DESC' TEXT);"); // 8: desc
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'WORK_TRACK'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, WorkTrack entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(2, date);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(3, userName);
        }
 
        String userImei = entity.getUserImei();
        if (userImei != null) {
            stmt.bindString(4, userImei);
        }
 
        String longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindString(5, longitude);
        }
 
        String latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindString(6, latitude);
        }
 
        String isMark = entity.getIsMark();
        if (isMark != null) {
            stmt.bindString(7, isMark);
        }
 
        String markIndex = entity.getMarkIndex();
        if (markIndex != null) {
            stmt.bindString(8, markIndex);
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(9, desc);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public WorkTrack readEntity(Cursor cursor, int offset) {
        WorkTrack entity = new WorkTrack( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // date
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // userImei
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // longitude
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // latitude
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // isMark
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // markIndex
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // desc
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, WorkTrack entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDate(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUserImei(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLongitude(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLatitude(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIsMark(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMarkIndex(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDesc(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(WorkTrack entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(WorkTrack entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
