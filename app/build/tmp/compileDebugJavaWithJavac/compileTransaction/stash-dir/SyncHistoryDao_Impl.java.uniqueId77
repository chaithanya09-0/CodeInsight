package com.codeinsight.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.codeinsight.app.data.local.entity.SyncHistory;
import java.lang.Class;
import java.lang.Exception;
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
public final class SyncHistoryDao_Impl implements SyncHistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SyncHistory> __insertionAdapterOfSyncHistory;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSyncHistoryForConnection;

  public SyncHistoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSyncHistory = new EntityInsertionAdapter<SyncHistory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `sync_history` (`id`,`connectionId`,`status`,`itemsSynced`,`errorMessage`,`syncedAt`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SyncHistory entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getConnectionId());
        if (entity.getStatus() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getStatus());
        }
        statement.bindLong(4, entity.getItemsSynced());
        if (entity.getErrorMessage() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getErrorMessage());
        }
        statement.bindLong(6, entity.getSyncedAt());
      }
    };
    this.__preparedStmtOfDeleteSyncHistoryForConnection = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM sync_history WHERE connectionId = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final SyncHistory syncHistory) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfSyncHistory.insertAndReturnId(syncHistory);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteSyncHistoryForConnection(final long connectionId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSyncHistoryForConnection.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, connectionId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteSyncHistoryForConnection.release(_stmt);
    }
  }

  @Override
  public LiveData<List<SyncHistory>> getSyncHistoryForConnection(final long connectionId) {
    final String _sql = "SELECT * FROM sync_history WHERE connectionId = ? ORDER BY syncedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, connectionId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"sync_history"}, false, new Callable<List<SyncHistory>>() {
      @Override
      @Nullable
      public List<SyncHistory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfConnectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "connectionId");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfItemsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "itemsSynced");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "syncedAt");
          final List<SyncHistory> _result = new ArrayList<SyncHistory>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SyncHistory _item;
            _item = new SyncHistory();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpConnectionId;
            _tmpConnectionId = _cursor.getLong(_cursorIndexOfConnectionId);
            _item.setConnectionId(_tmpConnectionId);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final int _tmpItemsSynced;
            _tmpItemsSynced = _cursor.getInt(_cursorIndexOfItemsSynced);
            _item.setItemsSynced(_tmpItemsSynced);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _item.setErrorMessage(_tmpErrorMessage);
            final long _tmpSyncedAt;
            _tmpSyncedAt = _cursor.getLong(_cursorIndexOfSyncedAt);
            _item.setSyncedAt(_tmpSyncedAt);
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
  public LiveData<List<SyncHistory>> getRecentSyncHistory(final int limit) {
    final String _sql = "SELECT * FROM sync_history ORDER BY syncedAt DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return __db.getInvalidationTracker().createLiveData(new String[] {"sync_history"}, false, new Callable<List<SyncHistory>>() {
      @Override
      @Nullable
      public List<SyncHistory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfConnectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "connectionId");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfItemsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "itemsSynced");
          final int _cursorIndexOfErrorMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "errorMessage");
          final int _cursorIndexOfSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "syncedAt");
          final List<SyncHistory> _result = new ArrayList<SyncHistory>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SyncHistory _item;
            _item = new SyncHistory();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpConnectionId;
            _tmpConnectionId = _cursor.getLong(_cursorIndexOfConnectionId);
            _item.setConnectionId(_tmpConnectionId);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final int _tmpItemsSynced;
            _tmpItemsSynced = _cursor.getInt(_cursorIndexOfItemsSynced);
            _item.setItemsSynced(_tmpItemsSynced);
            final String _tmpErrorMessage;
            if (_cursor.isNull(_cursorIndexOfErrorMessage)) {
              _tmpErrorMessage = null;
            } else {
              _tmpErrorMessage = _cursor.getString(_cursorIndexOfErrorMessage);
            }
            _item.setErrorMessage(_tmpErrorMessage);
            final long _tmpSyncedAt;
            _tmpSyncedAt = _cursor.getLong(_cursorIndexOfSyncedAt);
            _item.setSyncedAt(_tmpSyncedAt);
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
