package com.codeinsight.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.codeinsight.app.data.local.entity.CodingAttempt;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
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
public final class AttemptDao_Impl implements AttemptDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CodingAttempt> __insertionAdapterOfCodingAttempt;

  private final EntityDeletionOrUpdateAdapter<CodingAttempt> __deletionAdapterOfCodingAttempt;

  private final EntityDeletionOrUpdateAdapter<CodingAttempt> __updateAdapterOfCodingAttempt;

  public AttemptDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCodingAttempt = new EntityInsertionAdapter<CodingAttempt>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `coding_attempts` (`id`,`problemId`,`accepted`,`durationMinutes`,`language`,`notes`,`attemptedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CodingAttempt entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProblemId());
        final int _tmp = entity.isAccepted() ? 1 : 0;
        statement.bindLong(3, _tmp);
        if (entity.getDurationMinutes() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getDurationMinutes());
        }
        if (entity.getLanguage() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLanguage());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        statement.bindLong(7, entity.getAttemptedAt());
      }
    };
    this.__deletionAdapterOfCodingAttempt = new EntityDeletionOrUpdateAdapter<CodingAttempt>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `coding_attempts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CodingAttempt entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCodingAttempt = new EntityDeletionOrUpdateAdapter<CodingAttempt>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `coding_attempts` SET `id` = ?,`problemId` = ?,`accepted` = ?,`durationMinutes` = ?,`language` = ?,`notes` = ?,`attemptedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CodingAttempt entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProblemId());
        final int _tmp = entity.isAccepted() ? 1 : 0;
        statement.bindLong(3, _tmp);
        if (entity.getDurationMinutes() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getDurationMinutes());
        }
        if (entity.getLanguage() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLanguage());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        statement.bindLong(7, entity.getAttemptedAt());
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public long insert(final CodingAttempt attempt) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfCodingAttempt.insertAndReturnId(attempt);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final CodingAttempt attempt) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCodingAttempt.handle(attempt);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final CodingAttempt attempt) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCodingAttempt.handle(attempt);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<CodingAttempt>> getAttemptsForProblem(final long problemId) {
    final String _sql = "SELECT * FROM coding_attempts WHERE problemId = ? ORDER BY attemptedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, problemId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_attempts"}, false, new Callable<List<CodingAttempt>>() {
      @Override
      @Nullable
      public List<CodingAttempt> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProblemId = CursorUtil.getColumnIndexOrThrow(_cursor, "problemId");
          final int _cursorIndexOfAccepted = CursorUtil.getColumnIndexOrThrow(_cursor, "accepted");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfAttemptedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptedAt");
          final List<CodingAttempt> _result = new ArrayList<CodingAttempt>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CodingAttempt _item;
            _item = new CodingAttempt();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpProblemId;
            _tmpProblemId = _cursor.getLong(_cursorIndexOfProblemId);
            _item.setProblemId(_tmpProblemId);
            final boolean _tmpAccepted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAccepted);
            _tmpAccepted = _tmp != 0;
            _item.setAccepted(_tmpAccepted);
            final Integer _tmpDurationMinutes;
            if (_cursor.isNull(_cursorIndexOfDurationMinutes)) {
              _tmpDurationMinutes = null;
            } else {
              _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            }
            _item.setDurationMinutes(_tmpDurationMinutes);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            _item.setLanguage(_tmpLanguage);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item.setNotes(_tmpNotes);
            final long _tmpAttemptedAt;
            _tmpAttemptedAt = _cursor.getLong(_cursorIndexOfAttemptedAt);
            _item.setAttemptedAt(_tmpAttemptedAt);
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
  public List<CodingAttempt> getAttemptsForProblemSync(final long problemId) {
    final String _sql = "SELECT * FROM coding_attempts WHERE problemId = ? ORDER BY attemptedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, problemId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProblemId = CursorUtil.getColumnIndexOrThrow(_cursor, "problemId");
      final int _cursorIndexOfAccepted = CursorUtil.getColumnIndexOrThrow(_cursor, "accepted");
      final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
      final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfAttemptedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptedAt");
      final List<CodingAttempt> _result = new ArrayList<CodingAttempt>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CodingAttempt _item;
        _item = new CodingAttempt();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpProblemId;
        _tmpProblemId = _cursor.getLong(_cursorIndexOfProblemId);
        _item.setProblemId(_tmpProblemId);
        final boolean _tmpAccepted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAccepted);
        _tmpAccepted = _tmp != 0;
        _item.setAccepted(_tmpAccepted);
        final Integer _tmpDurationMinutes;
        if (_cursor.isNull(_cursorIndexOfDurationMinutes)) {
          _tmpDurationMinutes = null;
        } else {
          _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
        }
        _item.setDurationMinutes(_tmpDurationMinutes);
        final String _tmpLanguage;
        if (_cursor.isNull(_cursorIndexOfLanguage)) {
          _tmpLanguage = null;
        } else {
          _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
        }
        _item.setLanguage(_tmpLanguage);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
        final long _tmpAttemptedAt;
        _tmpAttemptedAt = _cursor.getLong(_cursorIndexOfAttemptedAt);
        _item.setAttemptedAt(_tmpAttemptedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<CodingAttempt> getAllAttemptsSync() {
    final String _sql = "SELECT * FROM coding_attempts ORDER BY attemptedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfProblemId = CursorUtil.getColumnIndexOrThrow(_cursor, "problemId");
      final int _cursorIndexOfAccepted = CursorUtil.getColumnIndexOrThrow(_cursor, "accepted");
      final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "durationMinutes");
      final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfAttemptedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptedAt");
      final List<CodingAttempt> _result = new ArrayList<CodingAttempt>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CodingAttempt _item;
        _item = new CodingAttempt();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpProblemId;
        _tmpProblemId = _cursor.getLong(_cursorIndexOfProblemId);
        _item.setProblemId(_tmpProblemId);
        final boolean _tmpAccepted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAccepted);
        _tmpAccepted = _tmp != 0;
        _item.setAccepted(_tmpAccepted);
        final Integer _tmpDurationMinutes;
        if (_cursor.isNull(_cursorIndexOfDurationMinutes)) {
          _tmpDurationMinutes = null;
        } else {
          _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
        }
        _item.setDurationMinutes(_tmpDurationMinutes);
        final String _tmpLanguage;
        if (_cursor.isNull(_cursorIndexOfLanguage)) {
          _tmpLanguage = null;
        } else {
          _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
        }
        _item.setLanguage(_tmpLanguage);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
        final long _tmpAttemptedAt;
        _tmpAttemptedAt = _cursor.getLong(_cursorIndexOfAttemptedAt);
        _item.setAttemptedAt(_tmpAttemptedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Integer> getAcceptedAttemptsCount() {
    final String _sql = "SELECT COUNT(*) FROM coding_attempts WHERE accepted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_attempts"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public LiveData<Integer> getTotalAttemptsCount() {
    final String _sql = "SELECT COUNT(*) FROM coding_attempts";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_attempts"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
  public LiveData<Double> getAverageSolvingDuration() {
    final String _sql = "SELECT AVG(durationMinutes) FROM coding_attempts WHERE durationMinutes IS NOT NULL AND accepted = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_attempts"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
