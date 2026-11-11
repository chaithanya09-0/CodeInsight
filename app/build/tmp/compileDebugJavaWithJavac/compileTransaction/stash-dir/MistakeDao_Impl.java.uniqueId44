package com.codeinsight.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.codeinsight.app.data.local.entity.CodingAttempt;
import com.codeinsight.app.data.local.entity.CodingProblem;
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.MistakeWithProblem;
import com.codeinsight.app.domain.model.CategoryCount;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MistakeDao_Impl implements MistakeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Mistake> __insertionAdapterOfMistake;

  private final EntityDeletionOrUpdateAdapter<Mistake> __deletionAdapterOfMistake;

  private final EntityDeletionOrUpdateAdapter<Mistake> __updateAdapterOfMistake;

  private final SharedSQLiteStatement __preparedStmtOfDeleteMistakesForProblem;

  public MistakeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMistake = new EntityInsertionAdapter<Mistake>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `mistakes` (`id`,`attemptId`,`problemId`,`category`,`description`,`notes`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Mistake entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAttemptId());
        statement.bindLong(3, entity.getProblemId());
        if (entity.getCategory() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCategory());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDescription());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        statement.bindLong(7, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfMistake = new EntityDeletionOrUpdateAdapter<Mistake>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `mistakes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Mistake entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfMistake = new EntityDeletionOrUpdateAdapter<Mistake>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `mistakes` SET `id` = ?,`attemptId` = ?,`problemId` = ?,`category` = ?,`description` = ?,`notes` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Mistake entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAttemptId());
        statement.bindLong(3, entity.getProblemId());
        if (entity.getCategory() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCategory());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDescription());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteMistakesForProblem = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM mistakes WHERE problemId = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Mistake mistake) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfMistake.insertAndReturnId(mistake);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Mistake mistake) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMistake.handle(mistake);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Mistake mistake) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMistake.handle(mistake);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteMistakesForProblem(final long problemId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteMistakesForProblem.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, problemId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteMistakesForProblem.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Mistake>> getMistakesForProblem(final long problemId) {
    final String _sql = "SELECT * FROM mistakes WHERE problemId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, problemId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"mistakes"}, false, new Callable<List<Mistake>>() {
      @Override
      @Nullable
      public List<Mistake> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAttemptId = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptId");
          final int _cursorIndexOfProblemId = CursorUtil.getColumnIndexOrThrow(_cursor, "problemId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Mistake> _result = new ArrayList<Mistake>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Mistake _item;
            _item = new Mistake();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpAttemptId;
            _tmpAttemptId = _cursor.getLong(_cursorIndexOfAttemptId);
            _item.setAttemptId(_tmpAttemptId);
            final long _tmpProblemId;
            _tmpProblemId = _cursor.getLong(_cursorIndexOfProblemId);
            _item.setProblemId(_tmpProblemId);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item.setCategory(_tmpCategory);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item.setNotes(_tmpNotes);
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
  public List<Mistake> getMistakesForProblemSync(final long problemId) {
    final String _sql = "SELECT * FROM mistakes WHERE problemId = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, problemId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAttemptId = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptId");
      final int _cursorIndexOfProblemId = CursorUtil.getColumnIndexOrThrow(_cursor, "problemId");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final List<Mistake> _result = new ArrayList<Mistake>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Mistake _item;
        _item = new Mistake();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final long _tmpAttemptId;
        _tmpAttemptId = _cursor.getLong(_cursorIndexOfAttemptId);
        _item.setAttemptId(_tmpAttemptId);
        final long _tmpProblemId;
        _tmpProblemId = _cursor.getLong(_cursorIndexOfProblemId);
        _item.setProblemId(_tmpProblemId);
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        _item.setCategory(_tmpCategory);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _item.setDescription(_tmpDescription);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
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

  @Override
  public LiveData<List<Mistake>> getAllMistakes() {
    final String _sql = "SELECT * FROM mistakes ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"mistakes"}, false, new Callable<List<Mistake>>() {
      @Override
      @Nullable
      public List<Mistake> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAttemptId = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptId");
          final int _cursorIndexOfProblemId = CursorUtil.getColumnIndexOrThrow(_cursor, "problemId");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Mistake> _result = new ArrayList<Mistake>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Mistake _item;
            _item = new Mistake();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final long _tmpAttemptId;
            _tmpAttemptId = _cursor.getLong(_cursorIndexOfAttemptId);
            _item.setAttemptId(_tmpAttemptId);
            final long _tmpProblemId;
            _tmpProblemId = _cursor.getLong(_cursorIndexOfProblemId);
            _item.setProblemId(_tmpProblemId);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item.setCategory(_tmpCategory);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _item.setDescription(_tmpDescription);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item.setNotes(_tmpNotes);
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
  public LiveData<List<MistakeWithProblem>> getAllMistakesWithProblem() {
    final String _sql = "SELECT * FROM mistakes ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_problems",
        "coding_attempts", "mistakes"}, true, new Callable<List<MistakeWithProblem>>() {
      @Override
      @Nullable
      public List<MistakeWithProblem> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfAttemptId = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptId");
            final int _cursorIndexOfProblemId = CursorUtil.getColumnIndexOrThrow(_cursor, "problemId");
            final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final LongSparseArray<CodingProblem> _collectionProblem = new LongSparseArray<CodingProblem>();
            final LongSparseArray<CodingAttempt> _collectionAttempt = new LongSparseArray<CodingAttempt>();
            while (_cursor.moveToNext()) {
              final Long _tmpKey;
              if (_cursor.isNull(_cursorIndexOfProblemId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getLong(_cursorIndexOfProblemId);
              }
              if (_tmpKey != null) {
                _collectionProblem.put(_tmpKey, null);
              }
              final Long _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfAttemptId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getLong(_cursorIndexOfAttemptId);
              }
              if (_tmpKey_1 != null) {
                _collectionAttempt.put(_tmpKey_1, null);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcodingProblemsAscomCodeinsightAppDataLocalEntityCodingProblem(_collectionProblem);
            __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(_collectionAttempt);
            final List<MistakeWithProblem> _result = new ArrayList<MistakeWithProblem>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final MistakeWithProblem _item;
              final Mistake _tmpMistake;
              if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfAttemptId) && _cursor.isNull(_cursorIndexOfProblemId) && _cursor.isNull(_cursorIndexOfCategory) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfNotes) && _cursor.isNull(_cursorIndexOfCreatedAt))) {
                _tmpMistake = new Mistake();
                final long _tmpId;
                _tmpId = _cursor.getLong(_cursorIndexOfId);
                _tmpMistake.setId(_tmpId);
                final long _tmpAttemptId;
                _tmpAttemptId = _cursor.getLong(_cursorIndexOfAttemptId);
                _tmpMistake.setAttemptId(_tmpAttemptId);
                final long _tmpProblemId;
                _tmpProblemId = _cursor.getLong(_cursorIndexOfProblemId);
                _tmpMistake.setProblemId(_tmpProblemId);
                final String _tmpCategory;
                if (_cursor.isNull(_cursorIndexOfCategory)) {
                  _tmpCategory = null;
                } else {
                  _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
                }
                _tmpMistake.setCategory(_tmpCategory);
                final String _tmpDescription;
                if (_cursor.isNull(_cursorIndexOfDescription)) {
                  _tmpDescription = null;
                } else {
                  _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
                }
                _tmpMistake.setDescription(_tmpDescription);
                final String _tmpNotes;
                if (_cursor.isNull(_cursorIndexOfNotes)) {
                  _tmpNotes = null;
                } else {
                  _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
                }
                _tmpMistake.setNotes(_tmpNotes);
                final long _tmpCreatedAt;
                _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                _tmpMistake.setCreatedAt(_tmpCreatedAt);
              } else {
                _tmpMistake = null;
              }
              final CodingProblem _tmpProblem;
              final Long _tmpKey_2;
              if (_cursor.isNull(_cursorIndexOfProblemId)) {
                _tmpKey_2 = null;
              } else {
                _tmpKey_2 = _cursor.getLong(_cursorIndexOfProblemId);
              }
              if (_tmpKey_2 != null) {
                _tmpProblem = _collectionProblem.get(_tmpKey_2);
              } else {
                _tmpProblem = null;
              }
              final CodingAttempt _tmpAttempt;
              final Long _tmpKey_3;
              if (_cursor.isNull(_cursorIndexOfAttemptId)) {
                _tmpKey_3 = null;
              } else {
                _tmpKey_3 = _cursor.getLong(_cursorIndexOfAttemptId);
              }
              if (_tmpKey_3 != null) {
                _tmpAttempt = _collectionAttempt.get(_tmpKey_3);
              } else {
                _tmpAttempt = null;
              }
              _item = new MistakeWithProblem();
              _item.mistake = _tmpMistake;
              _item.problem = _tmpProblem;
              _item.attempt = _tmpAttempt;
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<MistakeWithProblem> getAllMistakesWithProblemSync() {
    final String _sql = "SELECT * FROM mistakes ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
        final int _cursorIndexOfAttemptId = CursorUtil.getColumnIndexOrThrow(_cursor, "attemptId");
        final int _cursorIndexOfProblemId = CursorUtil.getColumnIndexOrThrow(_cursor, "problemId");
        final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
        final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
        final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
        final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
        final LongSparseArray<CodingProblem> _collectionProblem = new LongSparseArray<CodingProblem>();
        final LongSparseArray<CodingAttempt> _collectionAttempt = new LongSparseArray<CodingAttempt>();
        while (_cursor.moveToNext()) {
          final Long _tmpKey;
          if (_cursor.isNull(_cursorIndexOfProblemId)) {
            _tmpKey = null;
          } else {
            _tmpKey = _cursor.getLong(_cursorIndexOfProblemId);
          }
          if (_tmpKey != null) {
            _collectionProblem.put(_tmpKey, null);
          }
          final Long _tmpKey_1;
          if (_cursor.isNull(_cursorIndexOfAttemptId)) {
            _tmpKey_1 = null;
          } else {
            _tmpKey_1 = _cursor.getLong(_cursorIndexOfAttemptId);
          }
          if (_tmpKey_1 != null) {
            _collectionAttempt.put(_tmpKey_1, null);
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshipcodingProblemsAscomCodeinsightAppDataLocalEntityCodingProblem(_collectionProblem);
        __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(_collectionAttempt);
        final List<MistakeWithProblem> _result = new ArrayList<MistakeWithProblem>(_cursor.getCount());
        while (_cursor.moveToNext()) {
          final MistakeWithProblem _item;
          final Mistake _tmpMistake;
          if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfAttemptId) && _cursor.isNull(_cursorIndexOfProblemId) && _cursor.isNull(_cursorIndexOfCategory) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfNotes) && _cursor.isNull(_cursorIndexOfCreatedAt))) {
            _tmpMistake = new Mistake();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _tmpMistake.setId(_tmpId);
            final long _tmpAttemptId;
            _tmpAttemptId = _cursor.getLong(_cursorIndexOfAttemptId);
            _tmpMistake.setAttemptId(_tmpAttemptId);
            final long _tmpProblemId;
            _tmpProblemId = _cursor.getLong(_cursorIndexOfProblemId);
            _tmpMistake.setProblemId(_tmpProblemId);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _tmpMistake.setCategory(_tmpCategory);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            _tmpMistake.setDescription(_tmpDescription);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _tmpMistake.setNotes(_tmpNotes);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _tmpMistake.setCreatedAt(_tmpCreatedAt);
          } else {
            _tmpMistake = null;
          }
          final CodingProblem _tmpProblem;
          final Long _tmpKey_2;
          if (_cursor.isNull(_cursorIndexOfProblemId)) {
            _tmpKey_2 = null;
          } else {
            _tmpKey_2 = _cursor.getLong(_cursorIndexOfProblemId);
          }
          if (_tmpKey_2 != null) {
            _tmpProblem = _collectionProblem.get(_tmpKey_2);
          } else {
            _tmpProblem = null;
          }
          final CodingAttempt _tmpAttempt;
          final Long _tmpKey_3;
          if (_cursor.isNull(_cursorIndexOfAttemptId)) {
            _tmpKey_3 = null;
          } else {
            _tmpKey_3 = _cursor.getLong(_cursorIndexOfAttemptId);
          }
          if (_tmpKey_3 != null) {
            _tmpAttempt = _collectionAttempt.get(_tmpKey_3);
          } else {
            _tmpAttempt = null;
          }
          _item = new MistakeWithProblem();
          _item.mistake = _tmpMistake;
          _item.problem = _tmpProblem;
          _item.attempt = _tmpAttempt;
          _result.add(_item);
        }
        __db.setTransactionSuccessful();
        return _result;
      } finally {
        _cursor.close();
        _statement.release();
      }
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<CategoryCount>> getMistakeCategoryCounts() {
    final String _sql = "SELECT category, COUNT(*) as count FROM mistakes GROUP BY category ORDER BY count DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"mistakes"}, false, new Callable<List<CategoryCount>>() {
      @Override
      @Nullable
      public List<CategoryCount> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCategory = 0;
          final int _cursorIndexOfCount = 1;
          final List<CategoryCount> _result = new ArrayList<CategoryCount>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CategoryCount _item;
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final int _tmpCount;
            _tmpCount = _cursor.getInt(_cursorIndexOfCount);
            _item = new CategoryCount(_tmpCategory,_tmpCount);
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
  public List<CategoryCount> getMistakeCategoryCountsSync() {
    final String _sql = "SELECT category, COUNT(*) as count FROM mistakes GROUP BY category ORDER BY count DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCategory = 0;
      final int _cursorIndexOfCount = 1;
      final List<CategoryCount> _result = new ArrayList<CategoryCount>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CategoryCount _item;
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final int _tmpCount;
        _tmpCount = _cursor.getInt(_cursorIndexOfCount);
        _item = new CategoryCount(_tmpCategory,_tmpCount);
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

  private void __fetchRelationshipcodingProblemsAscomCodeinsightAppDataLocalEntityCodingProblem(
      @NonNull final LongSparseArray<CodingProblem> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, false, (map) -> {
        __fetchRelationshipcodingProblemsAscomCodeinsightAppDataLocalEntityCodingProblem(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`title`,`url`,`platform`,`difficulty`,`status`,`totalAttempts`,`notes`,`externalId`,`accountUsername`,`createdAt`,`updatedAt` FROM `coding_problems` WHERE `id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfTitle = 1;
      final int _cursorIndexOfUrl = 2;
      final int _cursorIndexOfPlatform = 3;
      final int _cursorIndexOfDifficulty = 4;
      final int _cursorIndexOfStatus = 5;
      final int _cursorIndexOfTotalAttempts = 6;
      final int _cursorIndexOfNotes = 7;
      final int _cursorIndexOfExternalId = 8;
      final int _cursorIndexOfAccountUsername = 9;
      final int _cursorIndexOfCreatedAt = 10;
      final int _cursorIndexOfUpdatedAt = 11;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final CodingProblem _item_1;
          _item_1 = new CodingProblem();
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          _item_1.setId(_tmpId);
          final String _tmpTitle;
          if (_cursor.isNull(_cursorIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
          }
          _item_1.setTitle(_tmpTitle);
          final String _tmpUrl;
          if (_cursor.isNull(_cursorIndexOfUrl)) {
            _tmpUrl = null;
          } else {
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
          }
          _item_1.setUrl(_tmpUrl);
          final String _tmpPlatform;
          if (_cursor.isNull(_cursorIndexOfPlatform)) {
            _tmpPlatform = null;
          } else {
            _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
          }
          _item_1.setPlatform(_tmpPlatform);
          final String _tmpDifficulty;
          if (_cursor.isNull(_cursorIndexOfDifficulty)) {
            _tmpDifficulty = null;
          } else {
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
          }
          _item_1.setDifficulty(_tmpDifficulty);
          final String _tmpStatus;
          if (_cursor.isNull(_cursorIndexOfStatus)) {
            _tmpStatus = null;
          } else {
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
          }
          _item_1.setStatus(_tmpStatus);
          final int _tmpTotalAttempts;
          _tmpTotalAttempts = _cursor.getInt(_cursorIndexOfTotalAttempts);
          _item_1.setTotalAttempts(_tmpTotalAttempts);
          final String _tmpNotes;
          if (_cursor.isNull(_cursorIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
          }
          _item_1.setNotes(_tmpNotes);
          final String _tmpExternalId;
          if (_cursor.isNull(_cursorIndexOfExternalId)) {
            _tmpExternalId = null;
          } else {
            _tmpExternalId = _cursor.getString(_cursorIndexOfExternalId);
          }
          _item_1.setExternalId(_tmpExternalId);
          final String _tmpAccountUsername;
          if (_cursor.isNull(_cursorIndexOfAccountUsername)) {
            _tmpAccountUsername = null;
          } else {
            _tmpAccountUsername = _cursor.getString(_cursorIndexOfAccountUsername);
          }
          _item_1.setAccountUsername(_tmpAccountUsername);
          final long _tmpCreatedAt;
          _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
          _item_1.setCreatedAt(_tmpCreatedAt);
          final long _tmpUpdatedAt;
          _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
          _item_1.setUpdatedAt(_tmpUpdatedAt);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(
      @NonNull final LongSparseArray<CodingAttempt> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, false, (map) -> {
        __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`problemId`,`accepted`,`durationMinutes`,`language`,`notes`,`attemptedAt` FROM `coding_attempts` WHERE `id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfProblemId = 1;
      final int _cursorIndexOfAccepted = 2;
      final int _cursorIndexOfDurationMinutes = 3;
      final int _cursorIndexOfLanguage = 4;
      final int _cursorIndexOfNotes = 5;
      final int _cursorIndexOfAttemptedAt = 6;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final CodingAttempt _item_1;
          _item_1 = new CodingAttempt();
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          _item_1.setId(_tmpId);
          final long _tmpProblemId;
          _tmpProblemId = _cursor.getLong(_cursorIndexOfProblemId);
          _item_1.setProblemId(_tmpProblemId);
          final boolean _tmpAccepted;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfAccepted);
          _tmpAccepted = _tmp != 0;
          _item_1.setAccepted(_tmpAccepted);
          final Integer _tmpDurationMinutes;
          if (_cursor.isNull(_cursorIndexOfDurationMinutes)) {
            _tmpDurationMinutes = null;
          } else {
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
          }
          _item_1.setDurationMinutes(_tmpDurationMinutes);
          final String _tmpLanguage;
          if (_cursor.isNull(_cursorIndexOfLanguage)) {
            _tmpLanguage = null;
          } else {
            _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
          }
          _item_1.setLanguage(_tmpLanguage);
          final String _tmpNotes;
          if (_cursor.isNull(_cursorIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
          }
          _item_1.setNotes(_tmpNotes);
          final long _tmpAttemptedAt;
          _tmpAttemptedAt = _cursor.getLong(_cursorIndexOfAttemptedAt);
          _item_1.setAttemptedAt(_tmpAttemptedAt);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
