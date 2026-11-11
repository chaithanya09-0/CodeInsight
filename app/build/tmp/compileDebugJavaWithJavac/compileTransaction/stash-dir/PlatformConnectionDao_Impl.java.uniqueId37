package com.codeinsight.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PlatformConnectionDao_Impl implements PlatformConnectionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PlatformConnection> __insertionAdapterOfPlatformConnection;

  private final EntityDeletionOrUpdateAdapter<PlatformConnection> __updateAdapterOfPlatformConnection;

  private final SharedSQLiteStatement __preparedStmtOfDeactivateAll;

  private final SharedSQLiteStatement __preparedStmtOfSetActiveProfile;

  private final SharedSQLiteStatement __preparedStmtOfDeleteConnection;

  private final SharedSQLiteStatement __preparedStmtOfDeleteConnection_1;

  public PlatformConnectionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlatformConnection = new EntityInsertionAdapter<PlatformConnection>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `platform_connections` (`id`,`platform`,`username`,`isConnected`,`isActive`,`lastSyncAt`,`totalSolved`,`easySolved`,`mediumSolved`,`hardSolved`,`ranking`,`tagCountsJson`,`totalSubmissions`,`submissionCalendarJson`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final PlatformConnection entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getPlatform() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPlatform());
        }
        if (entity.getUsername() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getUsername());
        }
        final int _tmp = entity.isConnected() ? 1 : 0;
        statement.bindLong(4, _tmp);
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        if (entity.getLastSyncAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getLastSyncAt());
        }
        statement.bindLong(7, entity.getTotalSolved());
        statement.bindLong(8, entity.getEasySolved());
        statement.bindLong(9, entity.getMediumSolved());
        statement.bindLong(10, entity.getHardSolved());
        statement.bindLong(11, entity.getRanking());
        if (entity.getTagCountsJson() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTagCountsJson());
        }
        statement.bindLong(13, entity.getTotalSubmissions());
        if (entity.getSubmissionCalendarJson() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getSubmissionCalendarJson());
        }
      }
    };
    this.__updateAdapterOfPlatformConnection = new EntityDeletionOrUpdateAdapter<PlatformConnection>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `platform_connections` SET `id` = ?,`platform` = ?,`username` = ?,`isConnected` = ?,`isActive` = ?,`lastSyncAt` = ?,`totalSolved` = ?,`easySolved` = ?,`mediumSolved` = ?,`hardSolved` = ?,`ranking` = ?,`tagCountsJson` = ?,`totalSubmissions` = ?,`submissionCalendarJson` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final PlatformConnection entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getPlatform() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPlatform());
        }
        if (entity.getUsername() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getUsername());
        }
        final int _tmp = entity.isConnected() ? 1 : 0;
        statement.bindLong(4, _tmp);
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        if (entity.getLastSyncAt() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getLastSyncAt());
        }
        statement.bindLong(7, entity.getTotalSolved());
        statement.bindLong(8, entity.getEasySolved());
        statement.bindLong(9, entity.getMediumSolved());
        statement.bindLong(10, entity.getHardSolved());
        statement.bindLong(11, entity.getRanking());
        if (entity.getTagCountsJson() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTagCountsJson());
        }
        statement.bindLong(13, entity.getTotalSubmissions());
        if (entity.getSubmissionCalendarJson() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getSubmissionCalendarJson());
        }
        statement.bindLong(15, entity.getId());
      }
    };
    this.__preparedStmtOfDeactivateAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE platform_connections SET isActive = 0 WHERE platform = ?";
        return _query;
      }
    };
    this.__preparedStmtOfSetActiveProfile = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE platform_connections SET isActive = 1 WHERE platform = ? AND username = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteConnection = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM platform_connections WHERE platform = ? AND username = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteConnection_1 = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM platform_connections WHERE platform = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final PlatformConnection connection) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfPlatformConnection.insertAndReturnId(connection);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final PlatformConnection connection) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPlatformConnection.handle(connection);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deactivateAll(final String platform) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeactivateAll.acquire();
    int _argIndex = 1;
    if (platform == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, platform);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeactivateAll.release(_stmt);
    }
  }

  @Override
  public void setActiveProfile(final String platform, final String username) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSetActiveProfile.acquire();
    int _argIndex = 1;
    if (platform == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, platform);
    }
    _argIndex = 2;
    if (username == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, username);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfSetActiveProfile.release(_stmt);
    }
  }

  @Override
  public void deleteConnection(final String platform, final String username) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteConnection.acquire();
    int _argIndex = 1;
    if (platform == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, platform);
    }
    _argIndex = 2;
    if (username == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, username);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteConnection.release(_stmt);
    }
  }

  @Override
  public void deleteConnection(final String platform) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteConnection_1.acquire();
    int _argIndex = 1;
    if (platform == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, platform);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteConnection_1.release(_stmt);
    }
  }

  @Override
  public PlatformConnection getConnectionByPlatform(final String platform) {
    final String _sql = "SELECT * FROM platform_connections WHERE platform = ? ORDER BY isActive DESC, lastSyncAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (platform == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, platform);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfIsConnected = CursorUtil.getColumnIndexOrThrow(_cursor, "isConnected");
      final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
      final int _cursorIndexOfLastSyncAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAt");
      final int _cursorIndexOfTotalSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSolved");
      final int _cursorIndexOfEasySolved = CursorUtil.getColumnIndexOrThrow(_cursor, "easySolved");
      final int _cursorIndexOfMediumSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "mediumSolved");
      final int _cursorIndexOfHardSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "hardSolved");
      final int _cursorIndexOfRanking = CursorUtil.getColumnIndexOrThrow(_cursor, "ranking");
      final int _cursorIndexOfTagCountsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tagCountsJson");
      final int _cursorIndexOfTotalSubmissions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSubmissions");
      final int _cursorIndexOfSubmissionCalendarJson = CursorUtil.getColumnIndexOrThrow(_cursor, "submissionCalendarJson");
      final PlatformConnection _result;
      if (_cursor.moveToFirst()) {
        _result = new PlatformConnection();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpPlatform;
        if (_cursor.isNull(_cursorIndexOfPlatform)) {
          _tmpPlatform = null;
        } else {
          _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
        }
        _result.setPlatform(_tmpPlatform);
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        _result.setUsername(_tmpUsername);
        final boolean _tmpIsConnected;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsConnected);
        _tmpIsConnected = _tmp != 0;
        _result.setConnected(_tmpIsConnected);
        final boolean _tmpIsActive;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp_1 != 0;
        _result.setActive(_tmpIsActive);
        final Long _tmpLastSyncAt;
        if (_cursor.isNull(_cursorIndexOfLastSyncAt)) {
          _tmpLastSyncAt = null;
        } else {
          _tmpLastSyncAt = _cursor.getLong(_cursorIndexOfLastSyncAt);
        }
        _result.setLastSyncAt(_tmpLastSyncAt);
        final int _tmpTotalSolved;
        _tmpTotalSolved = _cursor.getInt(_cursorIndexOfTotalSolved);
        _result.setTotalSolved(_tmpTotalSolved);
        final int _tmpEasySolved;
        _tmpEasySolved = _cursor.getInt(_cursorIndexOfEasySolved);
        _result.setEasySolved(_tmpEasySolved);
        final int _tmpMediumSolved;
        _tmpMediumSolved = _cursor.getInt(_cursorIndexOfMediumSolved);
        _result.setMediumSolved(_tmpMediumSolved);
        final int _tmpHardSolved;
        _tmpHardSolved = _cursor.getInt(_cursorIndexOfHardSolved);
        _result.setHardSolved(_tmpHardSolved);
        final int _tmpRanking;
        _tmpRanking = _cursor.getInt(_cursorIndexOfRanking);
        _result.setRanking(_tmpRanking);
        final String _tmpTagCountsJson;
        if (_cursor.isNull(_cursorIndexOfTagCountsJson)) {
          _tmpTagCountsJson = null;
        } else {
          _tmpTagCountsJson = _cursor.getString(_cursorIndexOfTagCountsJson);
        }
        _result.setTagCountsJson(_tmpTagCountsJson);
        final int _tmpTotalSubmissions;
        _tmpTotalSubmissions = _cursor.getInt(_cursorIndexOfTotalSubmissions);
        _result.setTotalSubmissions(_tmpTotalSubmissions);
        final String _tmpSubmissionCalendarJson;
        if (_cursor.isNull(_cursorIndexOfSubmissionCalendarJson)) {
          _tmpSubmissionCalendarJson = null;
        } else {
          _tmpSubmissionCalendarJson = _cursor.getString(_cursorIndexOfSubmissionCalendarJson);
        }
        _result.setSubmissionCalendarJson(_tmpSubmissionCalendarJson);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<PlatformConnection> getConnectionByPlatformLive(final String platform) {
    final String _sql = "SELECT * FROM platform_connections WHERE platform = ? ORDER BY isActive DESC, lastSyncAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (platform == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, platform);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"platform_connections"}, false, new Callable<PlatformConnection>() {
      @Override
      @Nullable
      public PlatformConnection call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfIsConnected = CursorUtil.getColumnIndexOrThrow(_cursor, "isConnected");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfLastSyncAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAt");
          final int _cursorIndexOfTotalSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSolved");
          final int _cursorIndexOfEasySolved = CursorUtil.getColumnIndexOrThrow(_cursor, "easySolved");
          final int _cursorIndexOfMediumSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "mediumSolved");
          final int _cursorIndexOfHardSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "hardSolved");
          final int _cursorIndexOfRanking = CursorUtil.getColumnIndexOrThrow(_cursor, "ranking");
          final int _cursorIndexOfTagCountsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tagCountsJson");
          final int _cursorIndexOfTotalSubmissions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSubmissions");
          final int _cursorIndexOfSubmissionCalendarJson = CursorUtil.getColumnIndexOrThrow(_cursor, "submissionCalendarJson");
          final PlatformConnection _result;
          if (_cursor.moveToFirst()) {
            _result = new PlatformConnection();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpPlatform;
            if (_cursor.isNull(_cursorIndexOfPlatform)) {
              _tmpPlatform = null;
            } else {
              _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            }
            _result.setPlatform(_tmpPlatform);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            _result.setUsername(_tmpUsername);
            final boolean _tmpIsConnected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsConnected);
            _tmpIsConnected = _tmp != 0;
            _result.setConnected(_tmpIsConnected);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _result.setActive(_tmpIsActive);
            final Long _tmpLastSyncAt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAt)) {
              _tmpLastSyncAt = null;
            } else {
              _tmpLastSyncAt = _cursor.getLong(_cursorIndexOfLastSyncAt);
            }
            _result.setLastSyncAt(_tmpLastSyncAt);
            final int _tmpTotalSolved;
            _tmpTotalSolved = _cursor.getInt(_cursorIndexOfTotalSolved);
            _result.setTotalSolved(_tmpTotalSolved);
            final int _tmpEasySolved;
            _tmpEasySolved = _cursor.getInt(_cursorIndexOfEasySolved);
            _result.setEasySolved(_tmpEasySolved);
            final int _tmpMediumSolved;
            _tmpMediumSolved = _cursor.getInt(_cursorIndexOfMediumSolved);
            _result.setMediumSolved(_tmpMediumSolved);
            final int _tmpHardSolved;
            _tmpHardSolved = _cursor.getInt(_cursorIndexOfHardSolved);
            _result.setHardSolved(_tmpHardSolved);
            final int _tmpRanking;
            _tmpRanking = _cursor.getInt(_cursorIndexOfRanking);
            _result.setRanking(_tmpRanking);
            final String _tmpTagCountsJson;
            if (_cursor.isNull(_cursorIndexOfTagCountsJson)) {
              _tmpTagCountsJson = null;
            } else {
              _tmpTagCountsJson = _cursor.getString(_cursorIndexOfTagCountsJson);
            }
            _result.setTagCountsJson(_tmpTagCountsJson);
            final int _tmpTotalSubmissions;
            _tmpTotalSubmissions = _cursor.getInt(_cursorIndexOfTotalSubmissions);
            _result.setTotalSubmissions(_tmpTotalSubmissions);
            final String _tmpSubmissionCalendarJson;
            if (_cursor.isNull(_cursorIndexOfSubmissionCalendarJson)) {
              _tmpSubmissionCalendarJson = null;
            } else {
              _tmpSubmissionCalendarJson = _cursor.getString(_cursorIndexOfSubmissionCalendarJson);
            }
            _result.setSubmissionCalendarJson(_tmpSubmissionCalendarJson);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public PlatformConnection getActiveConnectionSync(final String platform) {
    final String _sql = "SELECT * FROM platform_connections WHERE platform = ? AND isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (platform == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, platform);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfIsConnected = CursorUtil.getColumnIndexOrThrow(_cursor, "isConnected");
      final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
      final int _cursorIndexOfLastSyncAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAt");
      final int _cursorIndexOfTotalSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSolved");
      final int _cursorIndexOfEasySolved = CursorUtil.getColumnIndexOrThrow(_cursor, "easySolved");
      final int _cursorIndexOfMediumSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "mediumSolved");
      final int _cursorIndexOfHardSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "hardSolved");
      final int _cursorIndexOfRanking = CursorUtil.getColumnIndexOrThrow(_cursor, "ranking");
      final int _cursorIndexOfTagCountsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tagCountsJson");
      final int _cursorIndexOfTotalSubmissions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSubmissions");
      final int _cursorIndexOfSubmissionCalendarJson = CursorUtil.getColumnIndexOrThrow(_cursor, "submissionCalendarJson");
      final PlatformConnection _result;
      if (_cursor.moveToFirst()) {
        _result = new PlatformConnection();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpPlatform;
        if (_cursor.isNull(_cursorIndexOfPlatform)) {
          _tmpPlatform = null;
        } else {
          _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
        }
        _result.setPlatform(_tmpPlatform);
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        _result.setUsername(_tmpUsername);
        final boolean _tmpIsConnected;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsConnected);
        _tmpIsConnected = _tmp != 0;
        _result.setConnected(_tmpIsConnected);
        final boolean _tmpIsActive;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp_1 != 0;
        _result.setActive(_tmpIsActive);
        final Long _tmpLastSyncAt;
        if (_cursor.isNull(_cursorIndexOfLastSyncAt)) {
          _tmpLastSyncAt = null;
        } else {
          _tmpLastSyncAt = _cursor.getLong(_cursorIndexOfLastSyncAt);
        }
        _result.setLastSyncAt(_tmpLastSyncAt);
        final int _tmpTotalSolved;
        _tmpTotalSolved = _cursor.getInt(_cursorIndexOfTotalSolved);
        _result.setTotalSolved(_tmpTotalSolved);
        final int _tmpEasySolved;
        _tmpEasySolved = _cursor.getInt(_cursorIndexOfEasySolved);
        _result.setEasySolved(_tmpEasySolved);
        final int _tmpMediumSolved;
        _tmpMediumSolved = _cursor.getInt(_cursorIndexOfMediumSolved);
        _result.setMediumSolved(_tmpMediumSolved);
        final int _tmpHardSolved;
        _tmpHardSolved = _cursor.getInt(_cursorIndexOfHardSolved);
        _result.setHardSolved(_tmpHardSolved);
        final int _tmpRanking;
        _tmpRanking = _cursor.getInt(_cursorIndexOfRanking);
        _result.setRanking(_tmpRanking);
        final String _tmpTagCountsJson;
        if (_cursor.isNull(_cursorIndexOfTagCountsJson)) {
          _tmpTagCountsJson = null;
        } else {
          _tmpTagCountsJson = _cursor.getString(_cursorIndexOfTagCountsJson);
        }
        _result.setTagCountsJson(_tmpTagCountsJson);
        final int _tmpTotalSubmissions;
        _tmpTotalSubmissions = _cursor.getInt(_cursorIndexOfTotalSubmissions);
        _result.setTotalSubmissions(_tmpTotalSubmissions);
        final String _tmpSubmissionCalendarJson;
        if (_cursor.isNull(_cursorIndexOfSubmissionCalendarJson)) {
          _tmpSubmissionCalendarJson = null;
        } else {
          _tmpSubmissionCalendarJson = _cursor.getString(_cursorIndexOfSubmissionCalendarJson);
        }
        _result.setSubmissionCalendarJson(_tmpSubmissionCalendarJson);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<PlatformConnection> getActiveConnectionLive(final String platform) {
    final String _sql = "SELECT * FROM platform_connections WHERE platform = ? AND isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (platform == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, platform);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"platform_connections"}, false, new Callable<PlatformConnection>() {
      @Override
      @Nullable
      public PlatformConnection call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfIsConnected = CursorUtil.getColumnIndexOrThrow(_cursor, "isConnected");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfLastSyncAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAt");
          final int _cursorIndexOfTotalSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSolved");
          final int _cursorIndexOfEasySolved = CursorUtil.getColumnIndexOrThrow(_cursor, "easySolved");
          final int _cursorIndexOfMediumSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "mediumSolved");
          final int _cursorIndexOfHardSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "hardSolved");
          final int _cursorIndexOfRanking = CursorUtil.getColumnIndexOrThrow(_cursor, "ranking");
          final int _cursorIndexOfTagCountsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tagCountsJson");
          final int _cursorIndexOfTotalSubmissions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSubmissions");
          final int _cursorIndexOfSubmissionCalendarJson = CursorUtil.getColumnIndexOrThrow(_cursor, "submissionCalendarJson");
          final PlatformConnection _result;
          if (_cursor.moveToFirst()) {
            _result = new PlatformConnection();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpPlatform;
            if (_cursor.isNull(_cursorIndexOfPlatform)) {
              _tmpPlatform = null;
            } else {
              _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            }
            _result.setPlatform(_tmpPlatform);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            _result.setUsername(_tmpUsername);
            final boolean _tmpIsConnected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsConnected);
            _tmpIsConnected = _tmp != 0;
            _result.setConnected(_tmpIsConnected);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _result.setActive(_tmpIsActive);
            final Long _tmpLastSyncAt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAt)) {
              _tmpLastSyncAt = null;
            } else {
              _tmpLastSyncAt = _cursor.getLong(_cursorIndexOfLastSyncAt);
            }
            _result.setLastSyncAt(_tmpLastSyncAt);
            final int _tmpTotalSolved;
            _tmpTotalSolved = _cursor.getInt(_cursorIndexOfTotalSolved);
            _result.setTotalSolved(_tmpTotalSolved);
            final int _tmpEasySolved;
            _tmpEasySolved = _cursor.getInt(_cursorIndexOfEasySolved);
            _result.setEasySolved(_tmpEasySolved);
            final int _tmpMediumSolved;
            _tmpMediumSolved = _cursor.getInt(_cursorIndexOfMediumSolved);
            _result.setMediumSolved(_tmpMediumSolved);
            final int _tmpHardSolved;
            _tmpHardSolved = _cursor.getInt(_cursorIndexOfHardSolved);
            _result.setHardSolved(_tmpHardSolved);
            final int _tmpRanking;
            _tmpRanking = _cursor.getInt(_cursorIndexOfRanking);
            _result.setRanking(_tmpRanking);
            final String _tmpTagCountsJson;
            if (_cursor.isNull(_cursorIndexOfTagCountsJson)) {
              _tmpTagCountsJson = null;
            } else {
              _tmpTagCountsJson = _cursor.getString(_cursorIndexOfTagCountsJson);
            }
            _result.setTagCountsJson(_tmpTagCountsJson);
            final int _tmpTotalSubmissions;
            _tmpTotalSubmissions = _cursor.getInt(_cursorIndexOfTotalSubmissions);
            _result.setTotalSubmissions(_tmpTotalSubmissions);
            final String _tmpSubmissionCalendarJson;
            if (_cursor.isNull(_cursorIndexOfSubmissionCalendarJson)) {
              _tmpSubmissionCalendarJson = null;
            } else {
              _tmpSubmissionCalendarJson = _cursor.getString(_cursorIndexOfSubmissionCalendarJson);
            }
            _result.setSubmissionCalendarJson(_tmpSubmissionCalendarJson);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public PlatformConnection getConnection(final String platform, final String username) {
    final String _sql = "SELECT * FROM platform_connections WHERE platform = ? AND username = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (platform == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, platform);
    }
    _argIndex = 2;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfIsConnected = CursorUtil.getColumnIndexOrThrow(_cursor, "isConnected");
      final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
      final int _cursorIndexOfLastSyncAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAt");
      final int _cursorIndexOfTotalSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSolved");
      final int _cursorIndexOfEasySolved = CursorUtil.getColumnIndexOrThrow(_cursor, "easySolved");
      final int _cursorIndexOfMediumSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "mediumSolved");
      final int _cursorIndexOfHardSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "hardSolved");
      final int _cursorIndexOfRanking = CursorUtil.getColumnIndexOrThrow(_cursor, "ranking");
      final int _cursorIndexOfTagCountsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tagCountsJson");
      final int _cursorIndexOfTotalSubmissions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSubmissions");
      final int _cursorIndexOfSubmissionCalendarJson = CursorUtil.getColumnIndexOrThrow(_cursor, "submissionCalendarJson");
      final PlatformConnection _result;
      if (_cursor.moveToFirst()) {
        _result = new PlatformConnection();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpPlatform;
        if (_cursor.isNull(_cursorIndexOfPlatform)) {
          _tmpPlatform = null;
        } else {
          _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
        }
        _result.setPlatform(_tmpPlatform);
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        _result.setUsername(_tmpUsername);
        final boolean _tmpIsConnected;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsConnected);
        _tmpIsConnected = _tmp != 0;
        _result.setConnected(_tmpIsConnected);
        final boolean _tmpIsActive;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp_1 != 0;
        _result.setActive(_tmpIsActive);
        final Long _tmpLastSyncAt;
        if (_cursor.isNull(_cursorIndexOfLastSyncAt)) {
          _tmpLastSyncAt = null;
        } else {
          _tmpLastSyncAt = _cursor.getLong(_cursorIndexOfLastSyncAt);
        }
        _result.setLastSyncAt(_tmpLastSyncAt);
        final int _tmpTotalSolved;
        _tmpTotalSolved = _cursor.getInt(_cursorIndexOfTotalSolved);
        _result.setTotalSolved(_tmpTotalSolved);
        final int _tmpEasySolved;
        _tmpEasySolved = _cursor.getInt(_cursorIndexOfEasySolved);
        _result.setEasySolved(_tmpEasySolved);
        final int _tmpMediumSolved;
        _tmpMediumSolved = _cursor.getInt(_cursorIndexOfMediumSolved);
        _result.setMediumSolved(_tmpMediumSolved);
        final int _tmpHardSolved;
        _tmpHardSolved = _cursor.getInt(_cursorIndexOfHardSolved);
        _result.setHardSolved(_tmpHardSolved);
        final int _tmpRanking;
        _tmpRanking = _cursor.getInt(_cursorIndexOfRanking);
        _result.setRanking(_tmpRanking);
        final String _tmpTagCountsJson;
        if (_cursor.isNull(_cursorIndexOfTagCountsJson)) {
          _tmpTagCountsJson = null;
        } else {
          _tmpTagCountsJson = _cursor.getString(_cursorIndexOfTagCountsJson);
        }
        _result.setTagCountsJson(_tmpTagCountsJson);
        final int _tmpTotalSubmissions;
        _tmpTotalSubmissions = _cursor.getInt(_cursorIndexOfTotalSubmissions);
        _result.setTotalSubmissions(_tmpTotalSubmissions);
        final String _tmpSubmissionCalendarJson;
        if (_cursor.isNull(_cursorIndexOfSubmissionCalendarJson)) {
          _tmpSubmissionCalendarJson = null;
        } else {
          _tmpSubmissionCalendarJson = _cursor.getString(_cursorIndexOfSubmissionCalendarJson);
        }
        _result.setSubmissionCalendarJson(_tmpSubmissionCalendarJson);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PlatformConnection> getConnectionsByPlatformSync(final String platform) {
    final String _sql = "SELECT * FROM platform_connections WHERE platform = ? ORDER BY isActive DESC, lastSyncAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (platform == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, platform);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
      final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
      final int _cursorIndexOfIsConnected = CursorUtil.getColumnIndexOrThrow(_cursor, "isConnected");
      final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
      final int _cursorIndexOfLastSyncAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAt");
      final int _cursorIndexOfTotalSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSolved");
      final int _cursorIndexOfEasySolved = CursorUtil.getColumnIndexOrThrow(_cursor, "easySolved");
      final int _cursorIndexOfMediumSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "mediumSolved");
      final int _cursorIndexOfHardSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "hardSolved");
      final int _cursorIndexOfRanking = CursorUtil.getColumnIndexOrThrow(_cursor, "ranking");
      final int _cursorIndexOfTagCountsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tagCountsJson");
      final int _cursorIndexOfTotalSubmissions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSubmissions");
      final int _cursorIndexOfSubmissionCalendarJson = CursorUtil.getColumnIndexOrThrow(_cursor, "submissionCalendarJson");
      final List<PlatformConnection> _result = new ArrayList<PlatformConnection>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final PlatformConnection _item;
        _item = new PlatformConnection();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpPlatform;
        if (_cursor.isNull(_cursorIndexOfPlatform)) {
          _tmpPlatform = null;
        } else {
          _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
        }
        _item.setPlatform(_tmpPlatform);
        final String _tmpUsername;
        if (_cursor.isNull(_cursorIndexOfUsername)) {
          _tmpUsername = null;
        } else {
          _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        }
        _item.setUsername(_tmpUsername);
        final boolean _tmpIsConnected;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsConnected);
        _tmpIsConnected = _tmp != 0;
        _item.setConnected(_tmpIsConnected);
        final boolean _tmpIsActive;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
        _tmpIsActive = _tmp_1 != 0;
        _item.setActive(_tmpIsActive);
        final Long _tmpLastSyncAt;
        if (_cursor.isNull(_cursorIndexOfLastSyncAt)) {
          _tmpLastSyncAt = null;
        } else {
          _tmpLastSyncAt = _cursor.getLong(_cursorIndexOfLastSyncAt);
        }
        _item.setLastSyncAt(_tmpLastSyncAt);
        final int _tmpTotalSolved;
        _tmpTotalSolved = _cursor.getInt(_cursorIndexOfTotalSolved);
        _item.setTotalSolved(_tmpTotalSolved);
        final int _tmpEasySolved;
        _tmpEasySolved = _cursor.getInt(_cursorIndexOfEasySolved);
        _item.setEasySolved(_tmpEasySolved);
        final int _tmpMediumSolved;
        _tmpMediumSolved = _cursor.getInt(_cursorIndexOfMediumSolved);
        _item.setMediumSolved(_tmpMediumSolved);
        final int _tmpHardSolved;
        _tmpHardSolved = _cursor.getInt(_cursorIndexOfHardSolved);
        _item.setHardSolved(_tmpHardSolved);
        final int _tmpRanking;
        _tmpRanking = _cursor.getInt(_cursorIndexOfRanking);
        _item.setRanking(_tmpRanking);
        final String _tmpTagCountsJson;
        if (_cursor.isNull(_cursorIndexOfTagCountsJson)) {
          _tmpTagCountsJson = null;
        } else {
          _tmpTagCountsJson = _cursor.getString(_cursorIndexOfTagCountsJson);
        }
        _item.setTagCountsJson(_tmpTagCountsJson);
        final int _tmpTotalSubmissions;
        _tmpTotalSubmissions = _cursor.getInt(_cursorIndexOfTotalSubmissions);
        _item.setTotalSubmissions(_tmpTotalSubmissions);
        final String _tmpSubmissionCalendarJson;
        if (_cursor.isNull(_cursorIndexOfSubmissionCalendarJson)) {
          _tmpSubmissionCalendarJson = null;
        } else {
          _tmpSubmissionCalendarJson = _cursor.getString(_cursorIndexOfSubmissionCalendarJson);
        }
        _item.setSubmissionCalendarJson(_tmpSubmissionCalendarJson);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<PlatformConnection>> getConnectionsByPlatformLive(final String platform) {
    final String _sql = "SELECT * FROM platform_connections WHERE platform = ? ORDER BY isActive DESC, lastSyncAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (platform == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, platform);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"platform_connections"}, false, new Callable<List<PlatformConnection>>() {
      @Override
      @Nullable
      public List<PlatformConnection> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfIsConnected = CursorUtil.getColumnIndexOrThrow(_cursor, "isConnected");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfLastSyncAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAt");
          final int _cursorIndexOfTotalSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSolved");
          final int _cursorIndexOfEasySolved = CursorUtil.getColumnIndexOrThrow(_cursor, "easySolved");
          final int _cursorIndexOfMediumSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "mediumSolved");
          final int _cursorIndexOfHardSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "hardSolved");
          final int _cursorIndexOfRanking = CursorUtil.getColumnIndexOrThrow(_cursor, "ranking");
          final int _cursorIndexOfTagCountsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tagCountsJson");
          final int _cursorIndexOfTotalSubmissions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSubmissions");
          final int _cursorIndexOfSubmissionCalendarJson = CursorUtil.getColumnIndexOrThrow(_cursor, "submissionCalendarJson");
          final List<PlatformConnection> _result = new ArrayList<PlatformConnection>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlatformConnection _item;
            _item = new PlatformConnection();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpPlatform;
            if (_cursor.isNull(_cursorIndexOfPlatform)) {
              _tmpPlatform = null;
            } else {
              _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            }
            _item.setPlatform(_tmpPlatform);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            _item.setUsername(_tmpUsername);
            final boolean _tmpIsConnected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsConnected);
            _tmpIsConnected = _tmp != 0;
            _item.setConnected(_tmpIsConnected);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _item.setActive(_tmpIsActive);
            final Long _tmpLastSyncAt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAt)) {
              _tmpLastSyncAt = null;
            } else {
              _tmpLastSyncAt = _cursor.getLong(_cursorIndexOfLastSyncAt);
            }
            _item.setLastSyncAt(_tmpLastSyncAt);
            final int _tmpTotalSolved;
            _tmpTotalSolved = _cursor.getInt(_cursorIndexOfTotalSolved);
            _item.setTotalSolved(_tmpTotalSolved);
            final int _tmpEasySolved;
            _tmpEasySolved = _cursor.getInt(_cursorIndexOfEasySolved);
            _item.setEasySolved(_tmpEasySolved);
            final int _tmpMediumSolved;
            _tmpMediumSolved = _cursor.getInt(_cursorIndexOfMediumSolved);
            _item.setMediumSolved(_tmpMediumSolved);
            final int _tmpHardSolved;
            _tmpHardSolved = _cursor.getInt(_cursorIndexOfHardSolved);
            _item.setHardSolved(_tmpHardSolved);
            final int _tmpRanking;
            _tmpRanking = _cursor.getInt(_cursorIndexOfRanking);
            _item.setRanking(_tmpRanking);
            final String _tmpTagCountsJson;
            if (_cursor.isNull(_cursorIndexOfTagCountsJson)) {
              _tmpTagCountsJson = null;
            } else {
              _tmpTagCountsJson = _cursor.getString(_cursorIndexOfTagCountsJson);
            }
            _item.setTagCountsJson(_tmpTagCountsJson);
            final int _tmpTotalSubmissions;
            _tmpTotalSubmissions = _cursor.getInt(_cursorIndexOfTotalSubmissions);
            _item.setTotalSubmissions(_tmpTotalSubmissions);
            final String _tmpSubmissionCalendarJson;
            if (_cursor.isNull(_cursorIndexOfSubmissionCalendarJson)) {
              _tmpSubmissionCalendarJson = null;
            } else {
              _tmpSubmissionCalendarJson = _cursor.getString(_cursorIndexOfSubmissionCalendarJson);
            }
            _item.setSubmissionCalendarJson(_tmpSubmissionCalendarJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<PlatformConnection>> getAllConnections() {
    final String _sql = "SELECT * FROM platform_connections ORDER BY platform ASC, username ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"platform_connections"}, false, new Callable<List<PlatformConnection>>() {
      @Override
      @Nullable
      public List<PlatformConnection> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfIsConnected = CursorUtil.getColumnIndexOrThrow(_cursor, "isConnected");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfLastSyncAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAt");
          final int _cursorIndexOfTotalSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSolved");
          final int _cursorIndexOfEasySolved = CursorUtil.getColumnIndexOrThrow(_cursor, "easySolved");
          final int _cursorIndexOfMediumSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "mediumSolved");
          final int _cursorIndexOfHardSolved = CursorUtil.getColumnIndexOrThrow(_cursor, "hardSolved");
          final int _cursorIndexOfRanking = CursorUtil.getColumnIndexOrThrow(_cursor, "ranking");
          final int _cursorIndexOfTagCountsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "tagCountsJson");
          final int _cursorIndexOfTotalSubmissions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSubmissions");
          final int _cursorIndexOfSubmissionCalendarJson = CursorUtil.getColumnIndexOrThrow(_cursor, "submissionCalendarJson");
          final List<PlatformConnection> _result = new ArrayList<PlatformConnection>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlatformConnection _item;
            _item = new PlatformConnection();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpPlatform;
            if (_cursor.isNull(_cursorIndexOfPlatform)) {
              _tmpPlatform = null;
            } else {
              _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            }
            _item.setPlatform(_tmpPlatform);
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            _item.setUsername(_tmpUsername);
            final boolean _tmpIsConnected;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsConnected);
            _tmpIsConnected = _tmp != 0;
            _item.setConnected(_tmpIsConnected);
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            _item.setActive(_tmpIsActive);
            final Long _tmpLastSyncAt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAt)) {
              _tmpLastSyncAt = null;
            } else {
              _tmpLastSyncAt = _cursor.getLong(_cursorIndexOfLastSyncAt);
            }
            _item.setLastSyncAt(_tmpLastSyncAt);
            final int _tmpTotalSolved;
            _tmpTotalSolved = _cursor.getInt(_cursorIndexOfTotalSolved);
            _item.setTotalSolved(_tmpTotalSolved);
            final int _tmpEasySolved;
            _tmpEasySolved = _cursor.getInt(_cursorIndexOfEasySolved);
            _item.setEasySolved(_tmpEasySolved);
            final int _tmpMediumSolved;
            _tmpMediumSolved = _cursor.getInt(_cursorIndexOfMediumSolved);
            _item.setMediumSolved(_tmpMediumSolved);
            final int _tmpHardSolved;
            _tmpHardSolved = _cursor.getInt(_cursorIndexOfHardSolved);
            _item.setHardSolved(_tmpHardSolved);
            final int _tmpRanking;
            _tmpRanking = _cursor.getInt(_cursorIndexOfRanking);
            _item.setRanking(_tmpRanking);
            final String _tmpTagCountsJson;
            if (_cursor.isNull(_cursorIndexOfTagCountsJson)) {
              _tmpTagCountsJson = null;
            } else {
              _tmpTagCountsJson = _cursor.getString(_cursorIndexOfTagCountsJson);
            }
            _item.setTagCountsJson(_tmpTagCountsJson);
            final int _tmpTotalSubmissions;
            _tmpTotalSubmissions = _cursor.getInt(_cursorIndexOfTotalSubmissions);
            _item.setTotalSubmissions(_tmpTotalSubmissions);
            final String _tmpSubmissionCalendarJson;
            if (_cursor.isNull(_cursorIndexOfSubmissionCalendarJson)) {
              _tmpSubmissionCalendarJson = null;
            } else {
              _tmpSubmissionCalendarJson = _cursor.getString(_cursorIndexOfSubmissionCalendarJson);
            }
            _item.setSubmissionCalendarJson(_tmpSubmissionCalendarJson);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
