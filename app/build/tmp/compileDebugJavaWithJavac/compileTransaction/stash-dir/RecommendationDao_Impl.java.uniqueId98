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
import com.codeinsight.app.data.local.entity.Recommendation;
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
public final class RecommendationDao_Impl implements RecommendationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Recommendation> __insertionAdapterOfRecommendation;

  private final EntityDeletionOrUpdateAdapter<Recommendation> __deletionAdapterOfRecommendation;

  private final EntityDeletionOrUpdateAdapter<Recommendation> __updateAdapterOfRecommendation;

  private final SharedSQLiteStatement __preparedStmtOfDismissRecommendation;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllRecommendations;

  public RecommendationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecommendation = new EntityInsertionAdapter<Recommendation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `recommendations` (`id`,`topicName`,`type`,`title`,`description`,`reasoning`,`priority`,`dismissed`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Recommendation entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTopicName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTopicName());
        }
        if (entity.getType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getType());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDescription());
        }
        if (entity.getReasoning() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getReasoning());
        }
        statement.bindLong(7, entity.getPriority());
        final int _tmp = entity.isDismissed() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfRecommendation = new EntityDeletionOrUpdateAdapter<Recommendation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recommendations` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Recommendation entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfRecommendation = new EntityDeletionOrUpdateAdapter<Recommendation>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `recommendations` SET `id` = ?,`topicName` = ?,`type` = ?,`title` = ?,`description` = ?,`reasoning` = ?,`priority` = ?,`dismissed` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final Recommendation entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTopicName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTopicName());
        }
        if (entity.getType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getType());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDescription());
        }
        if (entity.getReasoning() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getReasoning());
        }
        statement.bindLong(7, entity.getPriority());
        final int _tmp = entity.isDismissed() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfDismissRecommendation = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE recommendations SET dismissed = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllRecommendations = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM recommendations";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Recommendation recommendation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfRecommendation.insertAndReturnId(recommendation);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAll(final List<Recommendation> recommendations) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRecommendation.insert(recommendations);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Recommendation recommendation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfRecommendation.handle(recommendation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Recommendation recommendation) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfRecommendation.handle(recommendation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void dismissRecommendation(final long id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDismissRecommendation.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDismissRecommendation.release(_stmt);
    }
  }

  @Override
  public void deleteAllRecommendations() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllRecommendations.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAllRecommendations.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Recommendation>> getActiveRecommendations() {
    final String _sql = "SELECT * FROM recommendations WHERE dismissed = 0 ORDER BY priority DESC, createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"recommendations"}, false, new Callable<List<Recommendation>>() {
      @Override
      @Nullable
      public List<Recommendation> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTopicName = CursorUtil.getColumnIndexOrThrow(_cursor, "topicName");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfReasoning = CursorUtil.getColumnIndexOrThrow(_cursor, "reasoning");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfDismissed = CursorUtil.getColumnIndexOrThrow(_cursor, "dismissed");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Recommendation> _result = new ArrayList<Recommendation>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Recommendation _item;
            _item = new Recommendation();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTopicName;
            if (_cursor.isNull(_cursorIndexOfTopicName)) {
              _tmpTopicName = null;
            } else {
              _tmpTopicName = _cursor.getString(_cursorIndexOfTopicName);
            }
            _item.setTopicName(_tmpTopicName);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            _item.setType(_tmpType);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final String _tmpReasoning;
            if (_cursor.isNull(_cursorIndexOfReasoning)) {
              _tmpReasoning = null;
            } else {
              _tmpReasoning = _cursor.getString(_cursorIndexOfReasoning);
            }
            _item.setReasoning(_tmpReasoning);
            final int _tmpPriority;
            _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
            _item.setPriority(_tmpPriority);
            final boolean _tmpDismissed;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDismissed);
            _tmpDismissed = _tmp != 0;
            _item.setDismissed(_tmpDismissed);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
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
  public List<Recommendation> getActiveRecommendationsSync() {
    final String _sql = "SELECT * FROM recommendations WHERE dismissed = 0 ORDER BY priority DESC, createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTopicName = CursorUtil.getColumnIndexOrThrow(_cursor, "topicName");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfReasoning = CursorUtil.getColumnIndexOrThrow(_cursor, "reasoning");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfDismissed = CursorUtil.getColumnIndexOrThrow(_cursor, "dismissed");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final List<Recommendation> _result = new ArrayList<Recommendation>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Recommendation _item;
        _item = new Recommendation();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTopicName;
        if (_cursor.isNull(_cursorIndexOfTopicName)) {
          _tmpTopicName = null;
        } else {
          _tmpTopicName = _cursor.getString(_cursorIndexOfTopicName);
        }
        _item.setTopicName(_tmpTopicName);
        final String _tmpType;
        if (_cursor.isNull(_cursorIndexOfType)) {
          _tmpType = null;
        } else {
          _tmpType = _cursor.getString(_cursorIndexOfType);
        }
        _item.setType(_tmpType);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _item.setTitle(_tmpTitle);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _item.setDescription(_tmpDescription);
        final String _tmpReasoning;
        if (_cursor.isNull(_cursorIndexOfReasoning)) {
          _tmpReasoning = null;
        } else {
          _tmpReasoning = _cursor.getString(_cursorIndexOfReasoning);
        }
        _item.setReasoning(_tmpReasoning);
        final int _tmpPriority;
        _tmpPriority = _cursor.getInt(_cursorIndexOfPriority);
        _item.setPriority(_tmpPriority);
        final boolean _tmpDismissed;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDismissed);
        _tmpDismissed = _tmp != 0;
        _item.setDismissed(_tmpDismissed);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.setCreatedAt(_tmpCreatedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
