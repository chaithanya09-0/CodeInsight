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
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import com.codeinsight.app.data.local.entity.Topic;
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
public final class ProblemDao_Impl implements ProblemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CodingProblem> __insertionAdapterOfCodingProblem;

  private final EntityDeletionOrUpdateAdapter<CodingProblem> __deletionAdapterOfCodingProblem;

  private final EntityDeletionOrUpdateAdapter<CodingProblem> __updateAdapterOfCodingProblem;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAutoCuratedLeetCodeProblems;

  private final SharedSQLiteStatement __preparedStmtOfPurgeSyntheticDemoProblems;

  private final SharedSQLiteStatement __preparedStmtOfPurgeOrphanedMistakes;

  private final SharedSQLiteStatement __preparedStmtOfPurgeOrphanedAttempts;

  private final SharedSQLiteStatement __preparedStmtOfPurgeOrphanedCrossRefs;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllProblems;

  public ProblemDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCodingProblem = new EntityInsertionAdapter<CodingProblem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `coding_problems` (`id`,`title`,`url`,`platform`,`difficulty`,`status`,`totalAttempts`,`notes`,`externalId`,`accountUsername`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CodingProblem entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getUrl() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getUrl());
        }
        if (entity.getPlatform() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPlatform());
        }
        if (entity.getDifficulty() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDifficulty());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getStatus());
        }
        statement.bindLong(7, entity.getTotalAttempts());
        if (entity.getNotes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotes());
        }
        if (entity.getExternalId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getExternalId());
        }
        if (entity.getAccountUsername() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAccountUsername());
        }
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfCodingProblem = new EntityDeletionOrUpdateAdapter<CodingProblem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `coding_problems` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CodingProblem entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfCodingProblem = new EntityDeletionOrUpdateAdapter<CodingProblem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `coding_problems` SET `id` = ?,`title` = ?,`url` = ?,`platform` = ?,`difficulty` = ?,`status` = ?,`totalAttempts` = ?,`notes` = ?,`externalId` = ?,`accountUsername` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CodingProblem entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getUrl() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getUrl());
        }
        if (entity.getPlatform() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPlatform());
        }
        if (entity.getDifficulty() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDifficulty());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getStatus());
        }
        statement.bindLong(7, entity.getTotalAttempts());
        if (entity.getNotes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotes());
        }
        if (entity.getExternalId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getExternalId());
        }
        if (entity.getAccountUsername() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAccountUsername());
        }
        statement.bindLong(11, entity.getCreatedAt());
        statement.bindLong(12, entity.getUpdatedAt());
        statement.bindLong(13, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAutoCuratedLeetCodeProblems = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM coding_problems WHERE notes = 'Imported from LeetCode sync'";
        return _query;
      }
    };
    this.__preparedStmtOfPurgeSyntheticDemoProblems = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM coding_problems WHERE (platform = 'LeetCode' AND (accountUsername = '' OR accountUsername IS NULL OR accountUsername = 'undefined')) OR externalId LIKE 'demo-%' OR (externalId LIKE 'leetcode-%' AND (accountUsername = '' OR accountUsername IS NULL)) OR notes LIKE '%complement lookup%' OR notes LIKE '%Unbounded knapsack%' OR notes LIKE '%DP boolean array%' OR notes LIKE '%Automatic portfolio%'";
        return _query;
      }
    };
    this.__preparedStmtOfPurgeOrphanedMistakes = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM mistakes WHERE problemId NOT IN (SELECT id FROM coding_problems)";
        return _query;
      }
    };
    this.__preparedStmtOfPurgeOrphanedAttempts = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM coding_attempts WHERE problemId NOT IN (SELECT id FROM coding_problems)";
        return _query;
      }
    };
    this.__preparedStmtOfPurgeOrphanedCrossRefs = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM problem_topic_cross_ref WHERE problemId NOT IN (SELECT id FROM coding_problems)";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllProblems = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM coding_problems";
        return _query;
      }
    };
  }

  @Override
  public long insert(final CodingProblem problem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfCodingProblem.insertAndReturnId(problem);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final CodingProblem problem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCodingProblem.handle(problem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final CodingProblem problem) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCodingProblem.handle(problem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAutoCuratedLeetCodeProblems() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAutoCuratedLeetCodeProblems.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAutoCuratedLeetCodeProblems.release(_stmt);
    }
  }

  @Override
  public void purgeSyntheticDemoProblems() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfPurgeSyntheticDemoProblems.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfPurgeSyntheticDemoProblems.release(_stmt);
    }
  }

  @Override
  public void purgeOrphanedMistakes() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfPurgeOrphanedMistakes.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfPurgeOrphanedMistakes.release(_stmt);
    }
  }

  @Override
  public void purgeOrphanedAttempts() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfPurgeOrphanedAttempts.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfPurgeOrphanedAttempts.release(_stmt);
    }
  }

  @Override
  public void purgeOrphanedCrossRefs() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfPurgeOrphanedCrossRefs.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfPurgeOrphanedCrossRefs.release(_stmt);
    }
  }

  @Override
  public void deleteAllProblems() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllProblems.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAllProblems.release(_stmt);
    }
  }

  @Override
  public LiveData<List<CodingProblem>> getAllProblems() {
    final String _sql = "SELECT * FROM coding_problems ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_problems"}, false, new Callable<List<CodingProblem>>() {
      @Override
      @Nullable
      public List<CodingProblem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfTotalAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAttempts");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfExternalId = CursorUtil.getColumnIndexOrThrow(_cursor, "externalId");
          final int _cursorIndexOfAccountUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "accountUsername");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<CodingProblem> _result = new ArrayList<CodingProblem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CodingProblem _item;
            _item = new CodingProblem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _item.setTitle(_tmpTitle);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            _item.setUrl(_tmpUrl);
            final String _tmpPlatform;
            if (_cursor.isNull(_cursorIndexOfPlatform)) {
              _tmpPlatform = null;
            } else {
              _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            }
            _item.setPlatform(_tmpPlatform);
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            _item.setDifficulty(_tmpDifficulty);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final int _tmpTotalAttempts;
            _tmpTotalAttempts = _cursor.getInt(_cursorIndexOfTotalAttempts);
            _item.setTotalAttempts(_tmpTotalAttempts);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item.setNotes(_tmpNotes);
            final String _tmpExternalId;
            if (_cursor.isNull(_cursorIndexOfExternalId)) {
              _tmpExternalId = null;
            } else {
              _tmpExternalId = _cursor.getString(_cursorIndexOfExternalId);
            }
            _item.setExternalId(_tmpExternalId);
            final String _tmpAccountUsername;
            if (_cursor.isNull(_cursorIndexOfAccountUsername)) {
              _tmpAccountUsername = null;
            } else {
              _tmpAccountUsername = _cursor.getString(_cursorIndexOfAccountUsername);
            }
            _item.setAccountUsername(_tmpAccountUsername);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item.setUpdatedAt(_tmpUpdatedAt);
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
  public LiveData<List<ProblemWithDetails>> getAllProblemsWithDetails() {
    final String _sql = "SELECT * FROM coding_problems ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_attempts", "mistakes",
        "problem_topic_cross_ref", "topics",
        "coding_problems"}, true, new Callable<List<ProblemWithDetails>>() {
      @Override
      @Nullable
      public List<ProblemWithDetails> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
            final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
            final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
            final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
            final int _cursorIndexOfTotalAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAttempts");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfExternalId = CursorUtil.getColumnIndexOrThrow(_cursor, "externalId");
            final int _cursorIndexOfAccountUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "accountUsername");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final LongSparseArray<ArrayList<CodingAttempt>> _collectionAttempts = new LongSparseArray<ArrayList<CodingAttempt>>();
            final LongSparseArray<ArrayList<Mistake>> _collectionMistakes = new LongSparseArray<ArrayList<Mistake>>();
            final LongSparseArray<ArrayList<Topic>> _collectionTopics = new LongSparseArray<ArrayList<Topic>>();
            while (_cursor.moveToNext()) {
              final Long _tmpKey;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey != null) {
                if (!_collectionAttempts.containsKey(_tmpKey)) {
                  _collectionAttempts.put(_tmpKey, new ArrayList<CodingAttempt>());
                }
              }
              final Long _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_1 != null) {
                if (!_collectionMistakes.containsKey(_tmpKey_1)) {
                  _collectionMistakes.put(_tmpKey_1, new ArrayList<Mistake>());
                }
              }
              final Long _tmpKey_2;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_2 = null;
              } else {
                _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_2 != null) {
                if (!_collectionTopics.containsKey(_tmpKey_2)) {
                  _collectionTopics.put(_tmpKey_2, new ArrayList<Topic>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(_collectionAttempts);
            __fetchRelationshipmistakesAscomCodeinsightAppDataLocalEntityMistake(_collectionMistakes);
            __fetchRelationshiptopicsAscomCodeinsightAppDataLocalEntityTopic(_collectionTopics);
            final List<ProblemWithDetails> _result = new ArrayList<ProblemWithDetails>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final ProblemWithDetails _item;
              final CodingProblem _tmpProblem;
              if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfTitle) && _cursor.isNull(_cursorIndexOfUrl) && _cursor.isNull(_cursorIndexOfPlatform) && _cursor.isNull(_cursorIndexOfDifficulty) && _cursor.isNull(_cursorIndexOfStatus) && _cursor.isNull(_cursorIndexOfTotalAttempts) && _cursor.isNull(_cursorIndexOfNotes) && _cursor.isNull(_cursorIndexOfExternalId) && _cursor.isNull(_cursorIndexOfAccountUsername) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt))) {
                _tmpProblem = new CodingProblem();
                final long _tmpId;
                _tmpId = _cursor.getLong(_cursorIndexOfId);
                _tmpProblem.setId(_tmpId);
                final String _tmpTitle;
                if (_cursor.isNull(_cursorIndexOfTitle)) {
                  _tmpTitle = null;
                } else {
                  _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
                }
                _tmpProblem.setTitle(_tmpTitle);
                final String _tmpUrl;
                if (_cursor.isNull(_cursorIndexOfUrl)) {
                  _tmpUrl = null;
                } else {
                  _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
                }
                _tmpProblem.setUrl(_tmpUrl);
                final String _tmpPlatform;
                if (_cursor.isNull(_cursorIndexOfPlatform)) {
                  _tmpPlatform = null;
                } else {
                  _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
                }
                _tmpProblem.setPlatform(_tmpPlatform);
                final String _tmpDifficulty;
                if (_cursor.isNull(_cursorIndexOfDifficulty)) {
                  _tmpDifficulty = null;
                } else {
                  _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
                }
                _tmpProblem.setDifficulty(_tmpDifficulty);
                final String _tmpStatus;
                if (_cursor.isNull(_cursorIndexOfStatus)) {
                  _tmpStatus = null;
                } else {
                  _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
                }
                _tmpProblem.setStatus(_tmpStatus);
                final int _tmpTotalAttempts;
                _tmpTotalAttempts = _cursor.getInt(_cursorIndexOfTotalAttempts);
                _tmpProblem.setTotalAttempts(_tmpTotalAttempts);
                final String _tmpNotes;
                if (_cursor.isNull(_cursorIndexOfNotes)) {
                  _tmpNotes = null;
                } else {
                  _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
                }
                _tmpProblem.setNotes(_tmpNotes);
                final String _tmpExternalId;
                if (_cursor.isNull(_cursorIndexOfExternalId)) {
                  _tmpExternalId = null;
                } else {
                  _tmpExternalId = _cursor.getString(_cursorIndexOfExternalId);
                }
                _tmpProblem.setExternalId(_tmpExternalId);
                final String _tmpAccountUsername;
                if (_cursor.isNull(_cursorIndexOfAccountUsername)) {
                  _tmpAccountUsername = null;
                } else {
                  _tmpAccountUsername = _cursor.getString(_cursorIndexOfAccountUsername);
                }
                _tmpProblem.setAccountUsername(_tmpAccountUsername);
                final long _tmpCreatedAt;
                _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                _tmpProblem.setCreatedAt(_tmpCreatedAt);
                final long _tmpUpdatedAt;
                _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
                _tmpProblem.setUpdatedAt(_tmpUpdatedAt);
              } else {
                _tmpProblem = null;
              }
              final ArrayList<CodingAttempt> _tmpAttemptsCollection;
              final Long _tmpKey_3;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_3 = null;
              } else {
                _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_3 != null) {
                _tmpAttemptsCollection = _collectionAttempts.get(_tmpKey_3);
              } else {
                _tmpAttemptsCollection = new ArrayList<CodingAttempt>();
              }
              final ArrayList<Mistake> _tmpMistakesCollection;
              final Long _tmpKey_4;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_4 = null;
              } else {
                _tmpKey_4 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_4 != null) {
                _tmpMistakesCollection = _collectionMistakes.get(_tmpKey_4);
              } else {
                _tmpMistakesCollection = new ArrayList<Mistake>();
              }
              final ArrayList<Topic> _tmpTopicsCollection;
              final Long _tmpKey_5;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_5 = null;
              } else {
                _tmpKey_5 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_5 != null) {
                _tmpTopicsCollection = _collectionTopics.get(_tmpKey_5);
              } else {
                _tmpTopicsCollection = new ArrayList<Topic>();
              }
              _item = new ProblemWithDetails();
              _item.problem = _tmpProblem;
              _item.attempts = _tmpAttemptsCollection;
              _item.mistakes = _tmpMistakesCollection;
              _item.topics = _tmpTopicsCollection;
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
  public List<ProblemWithDetails> getAllProblemsWithDetailsSync() {
    final String _sql = "SELECT * FROM coding_problems ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
        final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
        final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
        final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
        final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
        final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
        final int _cursorIndexOfTotalAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAttempts");
        final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
        final int _cursorIndexOfExternalId = CursorUtil.getColumnIndexOrThrow(_cursor, "externalId");
        final int _cursorIndexOfAccountUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "accountUsername");
        final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
        final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
        final LongSparseArray<ArrayList<CodingAttempt>> _collectionAttempts = new LongSparseArray<ArrayList<CodingAttempt>>();
        final LongSparseArray<ArrayList<Mistake>> _collectionMistakes = new LongSparseArray<ArrayList<Mistake>>();
        final LongSparseArray<ArrayList<Topic>> _collectionTopics = new LongSparseArray<ArrayList<Topic>>();
        while (_cursor.moveToNext()) {
          final Long _tmpKey;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey = null;
          } else {
            _tmpKey = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey != null) {
            if (!_collectionAttempts.containsKey(_tmpKey)) {
              _collectionAttempts.put(_tmpKey, new ArrayList<CodingAttempt>());
            }
          }
          final Long _tmpKey_1;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_1 = null;
          } else {
            _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_1 != null) {
            if (!_collectionMistakes.containsKey(_tmpKey_1)) {
              _collectionMistakes.put(_tmpKey_1, new ArrayList<Mistake>());
            }
          }
          final Long _tmpKey_2;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_2 = null;
          } else {
            _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_2 != null) {
            if (!_collectionTopics.containsKey(_tmpKey_2)) {
              _collectionTopics.put(_tmpKey_2, new ArrayList<Topic>());
            }
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(_collectionAttempts);
        __fetchRelationshipmistakesAscomCodeinsightAppDataLocalEntityMistake(_collectionMistakes);
        __fetchRelationshiptopicsAscomCodeinsightAppDataLocalEntityTopic(_collectionTopics);
        final List<ProblemWithDetails> _result = new ArrayList<ProblemWithDetails>(_cursor.getCount());
        while (_cursor.moveToNext()) {
          final ProblemWithDetails _item;
          final CodingProblem _tmpProblem;
          if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfTitle) && _cursor.isNull(_cursorIndexOfUrl) && _cursor.isNull(_cursorIndexOfPlatform) && _cursor.isNull(_cursorIndexOfDifficulty) && _cursor.isNull(_cursorIndexOfStatus) && _cursor.isNull(_cursorIndexOfTotalAttempts) && _cursor.isNull(_cursorIndexOfNotes) && _cursor.isNull(_cursorIndexOfExternalId) && _cursor.isNull(_cursorIndexOfAccountUsername) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt))) {
            _tmpProblem = new CodingProblem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _tmpProblem.setId(_tmpId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _tmpProblem.setTitle(_tmpTitle);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            _tmpProblem.setUrl(_tmpUrl);
            final String _tmpPlatform;
            if (_cursor.isNull(_cursorIndexOfPlatform)) {
              _tmpPlatform = null;
            } else {
              _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            }
            _tmpProblem.setPlatform(_tmpPlatform);
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            _tmpProblem.setDifficulty(_tmpDifficulty);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpProblem.setStatus(_tmpStatus);
            final int _tmpTotalAttempts;
            _tmpTotalAttempts = _cursor.getInt(_cursorIndexOfTotalAttempts);
            _tmpProblem.setTotalAttempts(_tmpTotalAttempts);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _tmpProblem.setNotes(_tmpNotes);
            final String _tmpExternalId;
            if (_cursor.isNull(_cursorIndexOfExternalId)) {
              _tmpExternalId = null;
            } else {
              _tmpExternalId = _cursor.getString(_cursorIndexOfExternalId);
            }
            _tmpProblem.setExternalId(_tmpExternalId);
            final String _tmpAccountUsername;
            if (_cursor.isNull(_cursorIndexOfAccountUsername)) {
              _tmpAccountUsername = null;
            } else {
              _tmpAccountUsername = _cursor.getString(_cursorIndexOfAccountUsername);
            }
            _tmpProblem.setAccountUsername(_tmpAccountUsername);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _tmpProblem.setCreatedAt(_tmpCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _tmpProblem.setUpdatedAt(_tmpUpdatedAt);
          } else {
            _tmpProblem = null;
          }
          final ArrayList<CodingAttempt> _tmpAttemptsCollection;
          final Long _tmpKey_3;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_3 = null;
          } else {
            _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_3 != null) {
            _tmpAttemptsCollection = _collectionAttempts.get(_tmpKey_3);
          } else {
            _tmpAttemptsCollection = new ArrayList<CodingAttempt>();
          }
          final ArrayList<Mistake> _tmpMistakesCollection;
          final Long _tmpKey_4;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_4 = null;
          } else {
            _tmpKey_4 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_4 != null) {
            _tmpMistakesCollection = _collectionMistakes.get(_tmpKey_4);
          } else {
            _tmpMistakesCollection = new ArrayList<Mistake>();
          }
          final ArrayList<Topic> _tmpTopicsCollection;
          final Long _tmpKey_5;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_5 = null;
          } else {
            _tmpKey_5 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_5 != null) {
            _tmpTopicsCollection = _collectionTopics.get(_tmpKey_5);
          } else {
            _tmpTopicsCollection = new ArrayList<Topic>();
          }
          _item = new ProblemWithDetails();
          _item.problem = _tmpProblem;
          _item.attempts = _tmpAttemptsCollection;
          _item.mistakes = _tmpMistakesCollection;
          _item.topics = _tmpTopicsCollection;
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
  public LiveData<List<ProblemWithDetails>> getProblemsForAccountWithDetails(final String account) {
    final String _sql = "SELECT * FROM coding_problems WHERE accountUsername = ? OR accountUsername = '' OR accountUsername IS NULL ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (account == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, account);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_attempts", "mistakes",
        "problem_topic_cross_ref", "topics",
        "coding_problems"}, true, new Callable<List<ProblemWithDetails>>() {
      @Override
      @Nullable
      public List<ProblemWithDetails> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
            final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
            final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
            final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
            final int _cursorIndexOfTotalAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAttempts");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfExternalId = CursorUtil.getColumnIndexOrThrow(_cursor, "externalId");
            final int _cursorIndexOfAccountUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "accountUsername");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final LongSparseArray<ArrayList<CodingAttempt>> _collectionAttempts = new LongSparseArray<ArrayList<CodingAttempt>>();
            final LongSparseArray<ArrayList<Mistake>> _collectionMistakes = new LongSparseArray<ArrayList<Mistake>>();
            final LongSparseArray<ArrayList<Topic>> _collectionTopics = new LongSparseArray<ArrayList<Topic>>();
            while (_cursor.moveToNext()) {
              final Long _tmpKey;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey != null) {
                if (!_collectionAttempts.containsKey(_tmpKey)) {
                  _collectionAttempts.put(_tmpKey, new ArrayList<CodingAttempt>());
                }
              }
              final Long _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_1 != null) {
                if (!_collectionMistakes.containsKey(_tmpKey_1)) {
                  _collectionMistakes.put(_tmpKey_1, new ArrayList<Mistake>());
                }
              }
              final Long _tmpKey_2;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_2 = null;
              } else {
                _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_2 != null) {
                if (!_collectionTopics.containsKey(_tmpKey_2)) {
                  _collectionTopics.put(_tmpKey_2, new ArrayList<Topic>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(_collectionAttempts);
            __fetchRelationshipmistakesAscomCodeinsightAppDataLocalEntityMistake(_collectionMistakes);
            __fetchRelationshiptopicsAscomCodeinsightAppDataLocalEntityTopic(_collectionTopics);
            final List<ProblemWithDetails> _result = new ArrayList<ProblemWithDetails>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final ProblemWithDetails _item;
              final CodingProblem _tmpProblem;
              if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfTitle) && _cursor.isNull(_cursorIndexOfUrl) && _cursor.isNull(_cursorIndexOfPlatform) && _cursor.isNull(_cursorIndexOfDifficulty) && _cursor.isNull(_cursorIndexOfStatus) && _cursor.isNull(_cursorIndexOfTotalAttempts) && _cursor.isNull(_cursorIndexOfNotes) && _cursor.isNull(_cursorIndexOfExternalId) && _cursor.isNull(_cursorIndexOfAccountUsername) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt))) {
                _tmpProblem = new CodingProblem();
                final long _tmpId;
                _tmpId = _cursor.getLong(_cursorIndexOfId);
                _tmpProblem.setId(_tmpId);
                final String _tmpTitle;
                if (_cursor.isNull(_cursorIndexOfTitle)) {
                  _tmpTitle = null;
                } else {
                  _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
                }
                _tmpProblem.setTitle(_tmpTitle);
                final String _tmpUrl;
                if (_cursor.isNull(_cursorIndexOfUrl)) {
                  _tmpUrl = null;
                } else {
                  _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
                }
                _tmpProblem.setUrl(_tmpUrl);
                final String _tmpPlatform;
                if (_cursor.isNull(_cursorIndexOfPlatform)) {
                  _tmpPlatform = null;
                } else {
                  _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
                }
                _tmpProblem.setPlatform(_tmpPlatform);
                final String _tmpDifficulty;
                if (_cursor.isNull(_cursorIndexOfDifficulty)) {
                  _tmpDifficulty = null;
                } else {
                  _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
                }
                _tmpProblem.setDifficulty(_tmpDifficulty);
                final String _tmpStatus;
                if (_cursor.isNull(_cursorIndexOfStatus)) {
                  _tmpStatus = null;
                } else {
                  _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
                }
                _tmpProblem.setStatus(_tmpStatus);
                final int _tmpTotalAttempts;
                _tmpTotalAttempts = _cursor.getInt(_cursorIndexOfTotalAttempts);
                _tmpProblem.setTotalAttempts(_tmpTotalAttempts);
                final String _tmpNotes;
                if (_cursor.isNull(_cursorIndexOfNotes)) {
                  _tmpNotes = null;
                } else {
                  _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
                }
                _tmpProblem.setNotes(_tmpNotes);
                final String _tmpExternalId;
                if (_cursor.isNull(_cursorIndexOfExternalId)) {
                  _tmpExternalId = null;
                } else {
                  _tmpExternalId = _cursor.getString(_cursorIndexOfExternalId);
                }
                _tmpProblem.setExternalId(_tmpExternalId);
                final String _tmpAccountUsername;
                if (_cursor.isNull(_cursorIndexOfAccountUsername)) {
                  _tmpAccountUsername = null;
                } else {
                  _tmpAccountUsername = _cursor.getString(_cursorIndexOfAccountUsername);
                }
                _tmpProblem.setAccountUsername(_tmpAccountUsername);
                final long _tmpCreatedAt;
                _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                _tmpProblem.setCreatedAt(_tmpCreatedAt);
                final long _tmpUpdatedAt;
                _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
                _tmpProblem.setUpdatedAt(_tmpUpdatedAt);
              } else {
                _tmpProblem = null;
              }
              final ArrayList<CodingAttempt> _tmpAttemptsCollection;
              final Long _tmpKey_3;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_3 = null;
              } else {
                _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_3 != null) {
                _tmpAttemptsCollection = _collectionAttempts.get(_tmpKey_3);
              } else {
                _tmpAttemptsCollection = new ArrayList<CodingAttempt>();
              }
              final ArrayList<Mistake> _tmpMistakesCollection;
              final Long _tmpKey_4;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_4 = null;
              } else {
                _tmpKey_4 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_4 != null) {
                _tmpMistakesCollection = _collectionMistakes.get(_tmpKey_4);
              } else {
                _tmpMistakesCollection = new ArrayList<Mistake>();
              }
              final ArrayList<Topic> _tmpTopicsCollection;
              final Long _tmpKey_5;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_5 = null;
              } else {
                _tmpKey_5 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_5 != null) {
                _tmpTopicsCollection = _collectionTopics.get(_tmpKey_5);
              } else {
                _tmpTopicsCollection = new ArrayList<Topic>();
              }
              _item = new ProblemWithDetails();
              _item.problem = _tmpProblem;
              _item.attempts = _tmpAttemptsCollection;
              _item.mistakes = _tmpMistakesCollection;
              _item.topics = _tmpTopicsCollection;
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
  public List<ProblemWithDetails> getProblemsForAccountWithDetailsSync(final String account) {
    final String _sql = "SELECT * FROM coding_problems WHERE accountUsername = ? OR accountUsername = '' OR accountUsername IS NULL ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (account == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, account);
    }
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
        final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
        final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
        final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
        final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
        final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
        final int _cursorIndexOfTotalAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAttempts");
        final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
        final int _cursorIndexOfExternalId = CursorUtil.getColumnIndexOrThrow(_cursor, "externalId");
        final int _cursorIndexOfAccountUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "accountUsername");
        final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
        final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
        final LongSparseArray<ArrayList<CodingAttempt>> _collectionAttempts = new LongSparseArray<ArrayList<CodingAttempt>>();
        final LongSparseArray<ArrayList<Mistake>> _collectionMistakes = new LongSparseArray<ArrayList<Mistake>>();
        final LongSparseArray<ArrayList<Topic>> _collectionTopics = new LongSparseArray<ArrayList<Topic>>();
        while (_cursor.moveToNext()) {
          final Long _tmpKey;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey = null;
          } else {
            _tmpKey = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey != null) {
            if (!_collectionAttempts.containsKey(_tmpKey)) {
              _collectionAttempts.put(_tmpKey, new ArrayList<CodingAttempt>());
            }
          }
          final Long _tmpKey_1;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_1 = null;
          } else {
            _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_1 != null) {
            if (!_collectionMistakes.containsKey(_tmpKey_1)) {
              _collectionMistakes.put(_tmpKey_1, new ArrayList<Mistake>());
            }
          }
          final Long _tmpKey_2;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_2 = null;
          } else {
            _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_2 != null) {
            if (!_collectionTopics.containsKey(_tmpKey_2)) {
              _collectionTopics.put(_tmpKey_2, new ArrayList<Topic>());
            }
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(_collectionAttempts);
        __fetchRelationshipmistakesAscomCodeinsightAppDataLocalEntityMistake(_collectionMistakes);
        __fetchRelationshiptopicsAscomCodeinsightAppDataLocalEntityTopic(_collectionTopics);
        final List<ProblemWithDetails> _result = new ArrayList<ProblemWithDetails>(_cursor.getCount());
        while (_cursor.moveToNext()) {
          final ProblemWithDetails _item;
          final CodingProblem _tmpProblem;
          if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfTitle) && _cursor.isNull(_cursorIndexOfUrl) && _cursor.isNull(_cursorIndexOfPlatform) && _cursor.isNull(_cursorIndexOfDifficulty) && _cursor.isNull(_cursorIndexOfStatus) && _cursor.isNull(_cursorIndexOfTotalAttempts) && _cursor.isNull(_cursorIndexOfNotes) && _cursor.isNull(_cursorIndexOfExternalId) && _cursor.isNull(_cursorIndexOfAccountUsername) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt))) {
            _tmpProblem = new CodingProblem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _tmpProblem.setId(_tmpId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _tmpProblem.setTitle(_tmpTitle);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            _tmpProblem.setUrl(_tmpUrl);
            final String _tmpPlatform;
            if (_cursor.isNull(_cursorIndexOfPlatform)) {
              _tmpPlatform = null;
            } else {
              _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            }
            _tmpProblem.setPlatform(_tmpPlatform);
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            _tmpProblem.setDifficulty(_tmpDifficulty);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpProblem.setStatus(_tmpStatus);
            final int _tmpTotalAttempts;
            _tmpTotalAttempts = _cursor.getInt(_cursorIndexOfTotalAttempts);
            _tmpProblem.setTotalAttempts(_tmpTotalAttempts);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _tmpProblem.setNotes(_tmpNotes);
            final String _tmpExternalId;
            if (_cursor.isNull(_cursorIndexOfExternalId)) {
              _tmpExternalId = null;
            } else {
              _tmpExternalId = _cursor.getString(_cursorIndexOfExternalId);
            }
            _tmpProblem.setExternalId(_tmpExternalId);
            final String _tmpAccountUsername;
            if (_cursor.isNull(_cursorIndexOfAccountUsername)) {
              _tmpAccountUsername = null;
            } else {
              _tmpAccountUsername = _cursor.getString(_cursorIndexOfAccountUsername);
            }
            _tmpProblem.setAccountUsername(_tmpAccountUsername);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _tmpProblem.setCreatedAt(_tmpCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _tmpProblem.setUpdatedAt(_tmpUpdatedAt);
          } else {
            _tmpProblem = null;
          }
          final ArrayList<CodingAttempt> _tmpAttemptsCollection;
          final Long _tmpKey_3;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_3 = null;
          } else {
            _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_3 != null) {
            _tmpAttemptsCollection = _collectionAttempts.get(_tmpKey_3);
          } else {
            _tmpAttemptsCollection = new ArrayList<CodingAttempt>();
          }
          final ArrayList<Mistake> _tmpMistakesCollection;
          final Long _tmpKey_4;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_4 = null;
          } else {
            _tmpKey_4 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_4 != null) {
            _tmpMistakesCollection = _collectionMistakes.get(_tmpKey_4);
          } else {
            _tmpMistakesCollection = new ArrayList<Mistake>();
          }
          final ArrayList<Topic> _tmpTopicsCollection;
          final Long _tmpKey_5;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_5 = null;
          } else {
            _tmpKey_5 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_5 != null) {
            _tmpTopicsCollection = _collectionTopics.get(_tmpKey_5);
          } else {
            _tmpTopicsCollection = new ArrayList<Topic>();
          }
          _item = new ProblemWithDetails();
          _item.problem = _tmpProblem;
          _item.attempts = _tmpAttemptsCollection;
          _item.mistakes = _tmpMistakesCollection;
          _item.topics = _tmpTopicsCollection;
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
  public CodingProblem getProblemById(final long id) {
    final String _sql = "SELECT * FROM coding_problems WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfTotalAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAttempts");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfExternalId = CursorUtil.getColumnIndexOrThrow(_cursor, "externalId");
      final int _cursorIndexOfAccountUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "accountUsername");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
      final CodingProblem _result;
      if (_cursor.moveToFirst()) {
        _result = new CodingProblem();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _result.setTitle(_tmpTitle);
        final String _tmpUrl;
        if (_cursor.isNull(_cursorIndexOfUrl)) {
          _tmpUrl = null;
        } else {
          _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        }
        _result.setUrl(_tmpUrl);
        final String _tmpPlatform;
        if (_cursor.isNull(_cursorIndexOfPlatform)) {
          _tmpPlatform = null;
        } else {
          _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
        }
        _result.setPlatform(_tmpPlatform);
        final String _tmpDifficulty;
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _tmpDifficulty = null;
        } else {
          _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
        }
        _result.setDifficulty(_tmpDifficulty);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _result.setStatus(_tmpStatus);
        final int _tmpTotalAttempts;
        _tmpTotalAttempts = _cursor.getInt(_cursorIndexOfTotalAttempts);
        _result.setTotalAttempts(_tmpTotalAttempts);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _result.setNotes(_tmpNotes);
        final String _tmpExternalId;
        if (_cursor.isNull(_cursorIndexOfExternalId)) {
          _tmpExternalId = null;
        } else {
          _tmpExternalId = _cursor.getString(_cursorIndexOfExternalId);
        }
        _result.setExternalId(_tmpExternalId);
        final String _tmpAccountUsername;
        if (_cursor.isNull(_cursorIndexOfAccountUsername)) {
          _tmpAccountUsername = null;
        } else {
          _tmpAccountUsername = _cursor.getString(_cursorIndexOfAccountUsername);
        }
        _result.setAccountUsername(_tmpAccountUsername);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.setCreatedAt(_tmpCreatedAt);
        final long _tmpUpdatedAt;
        _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _result.setUpdatedAt(_tmpUpdatedAt);
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
  public LiveData<ProblemWithDetails> getProblemWithDetails(final long id) {
    final String _sql = "SELECT * FROM coding_problems WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_attempts", "mistakes",
        "problem_topic_cross_ref", "topics",
        "coding_problems"}, true, new Callable<ProblemWithDetails>() {
      @Override
      @Nullable
      public ProblemWithDetails call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
            final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
            final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
            final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
            final int _cursorIndexOfTotalAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAttempts");
            final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
            final int _cursorIndexOfExternalId = CursorUtil.getColumnIndexOrThrow(_cursor, "externalId");
            final int _cursorIndexOfAccountUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "accountUsername");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final LongSparseArray<ArrayList<CodingAttempt>> _collectionAttempts = new LongSparseArray<ArrayList<CodingAttempt>>();
            final LongSparseArray<ArrayList<Mistake>> _collectionMistakes = new LongSparseArray<ArrayList<Mistake>>();
            final LongSparseArray<ArrayList<Topic>> _collectionTopics = new LongSparseArray<ArrayList<Topic>>();
            while (_cursor.moveToNext()) {
              final Long _tmpKey;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey != null) {
                if (!_collectionAttempts.containsKey(_tmpKey)) {
                  _collectionAttempts.put(_tmpKey, new ArrayList<CodingAttempt>());
                }
              }
              final Long _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_1 != null) {
                if (!_collectionMistakes.containsKey(_tmpKey_1)) {
                  _collectionMistakes.put(_tmpKey_1, new ArrayList<Mistake>());
                }
              }
              final Long _tmpKey_2;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_2 = null;
              } else {
                _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_2 != null) {
                if (!_collectionTopics.containsKey(_tmpKey_2)) {
                  _collectionTopics.put(_tmpKey_2, new ArrayList<Topic>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(_collectionAttempts);
            __fetchRelationshipmistakesAscomCodeinsightAppDataLocalEntityMistake(_collectionMistakes);
            __fetchRelationshiptopicsAscomCodeinsightAppDataLocalEntityTopic(_collectionTopics);
            final ProblemWithDetails _result;
            if (_cursor.moveToFirst()) {
              final CodingProblem _tmpProblem;
              if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfTitle) && _cursor.isNull(_cursorIndexOfUrl) && _cursor.isNull(_cursorIndexOfPlatform) && _cursor.isNull(_cursorIndexOfDifficulty) && _cursor.isNull(_cursorIndexOfStatus) && _cursor.isNull(_cursorIndexOfTotalAttempts) && _cursor.isNull(_cursorIndexOfNotes) && _cursor.isNull(_cursorIndexOfExternalId) && _cursor.isNull(_cursorIndexOfAccountUsername) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt))) {
                _tmpProblem = new CodingProblem();
                final long _tmpId;
                _tmpId = _cursor.getLong(_cursorIndexOfId);
                _tmpProblem.setId(_tmpId);
                final String _tmpTitle;
                if (_cursor.isNull(_cursorIndexOfTitle)) {
                  _tmpTitle = null;
                } else {
                  _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
                }
                _tmpProblem.setTitle(_tmpTitle);
                final String _tmpUrl;
                if (_cursor.isNull(_cursorIndexOfUrl)) {
                  _tmpUrl = null;
                } else {
                  _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
                }
                _tmpProblem.setUrl(_tmpUrl);
                final String _tmpPlatform;
                if (_cursor.isNull(_cursorIndexOfPlatform)) {
                  _tmpPlatform = null;
                } else {
                  _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
                }
                _tmpProblem.setPlatform(_tmpPlatform);
                final String _tmpDifficulty;
                if (_cursor.isNull(_cursorIndexOfDifficulty)) {
                  _tmpDifficulty = null;
                } else {
                  _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
                }
                _tmpProblem.setDifficulty(_tmpDifficulty);
                final String _tmpStatus;
                if (_cursor.isNull(_cursorIndexOfStatus)) {
                  _tmpStatus = null;
                } else {
                  _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
                }
                _tmpProblem.setStatus(_tmpStatus);
                final int _tmpTotalAttempts;
                _tmpTotalAttempts = _cursor.getInt(_cursorIndexOfTotalAttempts);
                _tmpProblem.setTotalAttempts(_tmpTotalAttempts);
                final String _tmpNotes;
                if (_cursor.isNull(_cursorIndexOfNotes)) {
                  _tmpNotes = null;
                } else {
                  _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
                }
                _tmpProblem.setNotes(_tmpNotes);
                final String _tmpExternalId;
                if (_cursor.isNull(_cursorIndexOfExternalId)) {
                  _tmpExternalId = null;
                } else {
                  _tmpExternalId = _cursor.getString(_cursorIndexOfExternalId);
                }
                _tmpProblem.setExternalId(_tmpExternalId);
                final String _tmpAccountUsername;
                if (_cursor.isNull(_cursorIndexOfAccountUsername)) {
                  _tmpAccountUsername = null;
                } else {
                  _tmpAccountUsername = _cursor.getString(_cursorIndexOfAccountUsername);
                }
                _tmpProblem.setAccountUsername(_tmpAccountUsername);
                final long _tmpCreatedAt;
                _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
                _tmpProblem.setCreatedAt(_tmpCreatedAt);
                final long _tmpUpdatedAt;
                _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
                _tmpProblem.setUpdatedAt(_tmpUpdatedAt);
              } else {
                _tmpProblem = null;
              }
              final ArrayList<CodingAttempt> _tmpAttemptsCollection;
              final Long _tmpKey_3;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_3 = null;
              } else {
                _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_3 != null) {
                _tmpAttemptsCollection = _collectionAttempts.get(_tmpKey_3);
              } else {
                _tmpAttemptsCollection = new ArrayList<CodingAttempt>();
              }
              final ArrayList<Mistake> _tmpMistakesCollection;
              final Long _tmpKey_4;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_4 = null;
              } else {
                _tmpKey_4 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_4 != null) {
                _tmpMistakesCollection = _collectionMistakes.get(_tmpKey_4);
              } else {
                _tmpMistakesCollection = new ArrayList<Mistake>();
              }
              final ArrayList<Topic> _tmpTopicsCollection;
              final Long _tmpKey_5;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_5 = null;
              } else {
                _tmpKey_5 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_5 != null) {
                _tmpTopicsCollection = _collectionTopics.get(_tmpKey_5);
              } else {
                _tmpTopicsCollection = new ArrayList<Topic>();
              }
              _result = new ProblemWithDetails();
              _result.problem = _tmpProblem;
              _result.attempts = _tmpAttemptsCollection;
              _result.mistakes = _tmpMistakesCollection;
              _result.topics = _tmpTopicsCollection;
            } else {
              _result = null;
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
  public ProblemWithDetails getProblemWithDetailsSync(final long id) {
    final String _sql = "SELECT * FROM coding_problems WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
      try {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
        final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
        final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
        final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
        final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
        final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
        final int _cursorIndexOfTotalAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAttempts");
        final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
        final int _cursorIndexOfExternalId = CursorUtil.getColumnIndexOrThrow(_cursor, "externalId");
        final int _cursorIndexOfAccountUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "accountUsername");
        final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
        final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
        final LongSparseArray<ArrayList<CodingAttempt>> _collectionAttempts = new LongSparseArray<ArrayList<CodingAttempt>>();
        final LongSparseArray<ArrayList<Mistake>> _collectionMistakes = new LongSparseArray<ArrayList<Mistake>>();
        final LongSparseArray<ArrayList<Topic>> _collectionTopics = new LongSparseArray<ArrayList<Topic>>();
        while (_cursor.moveToNext()) {
          final Long _tmpKey;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey = null;
          } else {
            _tmpKey = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey != null) {
            if (!_collectionAttempts.containsKey(_tmpKey)) {
              _collectionAttempts.put(_tmpKey, new ArrayList<CodingAttempt>());
            }
          }
          final Long _tmpKey_1;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_1 = null;
          } else {
            _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_1 != null) {
            if (!_collectionMistakes.containsKey(_tmpKey_1)) {
              _collectionMistakes.put(_tmpKey_1, new ArrayList<Mistake>());
            }
          }
          final Long _tmpKey_2;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_2 = null;
          } else {
            _tmpKey_2 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_2 != null) {
            if (!_collectionTopics.containsKey(_tmpKey_2)) {
              _collectionTopics.put(_tmpKey_2, new ArrayList<Topic>());
            }
          }
        }
        _cursor.moveToPosition(-1);
        __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(_collectionAttempts);
        __fetchRelationshipmistakesAscomCodeinsightAppDataLocalEntityMistake(_collectionMistakes);
        __fetchRelationshiptopicsAscomCodeinsightAppDataLocalEntityTopic(_collectionTopics);
        final ProblemWithDetails _result;
        if (_cursor.moveToFirst()) {
          final CodingProblem _tmpProblem;
          if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfTitle) && _cursor.isNull(_cursorIndexOfUrl) && _cursor.isNull(_cursorIndexOfPlatform) && _cursor.isNull(_cursorIndexOfDifficulty) && _cursor.isNull(_cursorIndexOfStatus) && _cursor.isNull(_cursorIndexOfTotalAttempts) && _cursor.isNull(_cursorIndexOfNotes) && _cursor.isNull(_cursorIndexOfExternalId) && _cursor.isNull(_cursorIndexOfAccountUsername) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfUpdatedAt))) {
            _tmpProblem = new CodingProblem();
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _tmpProblem.setId(_tmpId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            _tmpProblem.setTitle(_tmpTitle);
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            _tmpProblem.setUrl(_tmpUrl);
            final String _tmpPlatform;
            if (_cursor.isNull(_cursorIndexOfPlatform)) {
              _tmpPlatform = null;
            } else {
              _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
            }
            _tmpProblem.setPlatform(_tmpPlatform);
            final String _tmpDifficulty;
            if (_cursor.isNull(_cursorIndexOfDifficulty)) {
              _tmpDifficulty = null;
            } else {
              _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            }
            _tmpProblem.setDifficulty(_tmpDifficulty);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpProblem.setStatus(_tmpStatus);
            final int _tmpTotalAttempts;
            _tmpTotalAttempts = _cursor.getInt(_cursorIndexOfTotalAttempts);
            _tmpProblem.setTotalAttempts(_tmpTotalAttempts);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _tmpProblem.setNotes(_tmpNotes);
            final String _tmpExternalId;
            if (_cursor.isNull(_cursorIndexOfExternalId)) {
              _tmpExternalId = null;
            } else {
              _tmpExternalId = _cursor.getString(_cursorIndexOfExternalId);
            }
            _tmpProblem.setExternalId(_tmpExternalId);
            final String _tmpAccountUsername;
            if (_cursor.isNull(_cursorIndexOfAccountUsername)) {
              _tmpAccountUsername = null;
            } else {
              _tmpAccountUsername = _cursor.getString(_cursorIndexOfAccountUsername);
            }
            _tmpProblem.setAccountUsername(_tmpAccountUsername);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _tmpProblem.setCreatedAt(_tmpCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _tmpProblem.setUpdatedAt(_tmpUpdatedAt);
          } else {
            _tmpProblem = null;
          }
          final ArrayList<CodingAttempt> _tmpAttemptsCollection;
          final Long _tmpKey_3;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_3 = null;
          } else {
            _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_3 != null) {
            _tmpAttemptsCollection = _collectionAttempts.get(_tmpKey_3);
          } else {
            _tmpAttemptsCollection = new ArrayList<CodingAttempt>();
          }
          final ArrayList<Mistake> _tmpMistakesCollection;
          final Long _tmpKey_4;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_4 = null;
          } else {
            _tmpKey_4 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_4 != null) {
            _tmpMistakesCollection = _collectionMistakes.get(_tmpKey_4);
          } else {
            _tmpMistakesCollection = new ArrayList<Mistake>();
          }
          final ArrayList<Topic> _tmpTopicsCollection;
          final Long _tmpKey_5;
          if (_cursor.isNull(_cursorIndexOfId)) {
            _tmpKey_5 = null;
          } else {
            _tmpKey_5 = _cursor.getLong(_cursorIndexOfId);
          }
          if (_tmpKey_5 != null) {
            _tmpTopicsCollection = _collectionTopics.get(_tmpKey_5);
          } else {
            _tmpTopicsCollection = new ArrayList<Topic>();
          }
          _result = new ProblemWithDetails();
          _result.problem = _tmpProblem;
          _result.attempts = _tmpAttemptsCollection;
          _result.mistakes = _tmpMistakesCollection;
          _result.topics = _tmpTopicsCollection;
        } else {
          _result = null;
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
  public CodingProblem getProblemByExternalId(final String externalId) {
    final String _sql = "SELECT * FROM coding_problems WHERE externalId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (externalId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, externalId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
      final int _cursorIndexOfPlatform = CursorUtil.getColumnIndexOrThrow(_cursor, "platform");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfTotalAttempts = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAttempts");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfExternalId = CursorUtil.getColumnIndexOrThrow(_cursor, "externalId");
      final int _cursorIndexOfAccountUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "accountUsername");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
      final CodingProblem _result;
      if (_cursor.moveToFirst()) {
        _result = new CodingProblem();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        _result.setTitle(_tmpTitle);
        final String _tmpUrl;
        if (_cursor.isNull(_cursorIndexOfUrl)) {
          _tmpUrl = null;
        } else {
          _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
        }
        _result.setUrl(_tmpUrl);
        final String _tmpPlatform;
        if (_cursor.isNull(_cursorIndexOfPlatform)) {
          _tmpPlatform = null;
        } else {
          _tmpPlatform = _cursor.getString(_cursorIndexOfPlatform);
        }
        _result.setPlatform(_tmpPlatform);
        final String _tmpDifficulty;
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _tmpDifficulty = null;
        } else {
          _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
        }
        _result.setDifficulty(_tmpDifficulty);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _result.setStatus(_tmpStatus);
        final int _tmpTotalAttempts;
        _tmpTotalAttempts = _cursor.getInt(_cursorIndexOfTotalAttempts);
        _result.setTotalAttempts(_tmpTotalAttempts);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _result.setNotes(_tmpNotes);
        final String _tmpExternalId;
        if (_cursor.isNull(_cursorIndexOfExternalId)) {
          _tmpExternalId = null;
        } else {
          _tmpExternalId = _cursor.getString(_cursorIndexOfExternalId);
        }
        _result.setExternalId(_tmpExternalId);
        final String _tmpAccountUsername;
        if (_cursor.isNull(_cursorIndexOfAccountUsername)) {
          _tmpAccountUsername = null;
        } else {
          _tmpAccountUsername = _cursor.getString(_cursorIndexOfAccountUsername);
        }
        _result.setAccountUsername(_tmpAccountUsername);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.setCreatedAt(_tmpCreatedAt);
        final long _tmpUpdatedAt;
        _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
        _result.setUpdatedAt(_tmpUpdatedAt);
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
  public LiveData<Integer> getTotalProblemsCount() {
    final String _sql = "SELECT COUNT(*) FROM coding_problems";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_problems"}, false, new Callable<Integer>() {
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
  public LiveData<Integer> getSolvedProblemsCount() {
    final String _sql = "SELECT COUNT(*) FROM coding_problems WHERE status = 'Solved'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"coding_problems"}, false, new Callable<Integer>() {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(
      @NonNull final LongSparseArray<ArrayList<CodingAttempt>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipcodingAttemptsAscomCodeinsightAppDataLocalEntityCodingAttempt(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`problemId`,`accepted`,`durationMinutes`,`language`,`notes`,`attemptedAt` FROM `coding_attempts` WHERE `problemId` IN (");
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
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "problemId");
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
        final ArrayList<CodingAttempt> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
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
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipmistakesAscomCodeinsightAppDataLocalEntityMistake(
      @NonNull final LongSparseArray<ArrayList<Mistake>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipmistakesAscomCodeinsightAppDataLocalEntityMistake(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`attemptId`,`problemId`,`category`,`description`,`notes`,`createdAt` FROM `mistakes` WHERE `problemId` IN (");
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
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "problemId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfAttemptId = 1;
      final int _cursorIndexOfProblemId = 2;
      final int _cursorIndexOfCategory = 3;
      final int _cursorIndexOfDescription = 4;
      final int _cursorIndexOfNotes = 5;
      final int _cursorIndexOfCreatedAt = 6;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<Mistake> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Mistake _item_1;
          _item_1 = new Mistake();
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          _item_1.setId(_tmpId);
          final long _tmpAttemptId;
          _tmpAttemptId = _cursor.getLong(_cursorIndexOfAttemptId);
          _item_1.setAttemptId(_tmpAttemptId);
          final long _tmpProblemId;
          _tmpProblemId = _cursor.getLong(_cursorIndexOfProblemId);
          _item_1.setProblemId(_tmpProblemId);
          final String _tmpCategory;
          if (_cursor.isNull(_cursorIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
          }
          _item_1.setCategory(_tmpCategory);
          final String _tmpDescription;
          if (_cursor.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
          }
          _item_1.setDescription(_tmpDescription);
          final String _tmpNotes;
          if (_cursor.isNull(_cursorIndexOfNotes)) {
            _tmpNotes = null;
          } else {
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
          }
          _item_1.setNotes(_tmpNotes);
          final long _tmpCreatedAt;
          _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
          _item_1.setCreatedAt(_tmpCreatedAt);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptopicsAscomCodeinsightAppDataLocalEntityTopic(
      @NonNull final LongSparseArray<ArrayList<Topic>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshiptopicsAscomCodeinsightAppDataLocalEntityTopic(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `topics`.`id` AS `id`,`topics`.`name` AS `name`,`topics`.`category` AS `category`,_junction.`problemId` FROM `problem_topic_cross_ref` AS _junction INNER JOIN `topics` ON (_junction.`topicId` = `topics`.`id`) WHERE _junction.`problemId` IN (");
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
      // _junction.problemId;
      final int _itemKeyIndex = 3;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfName = 1;
      final int _cursorIndexOfCategory = 2;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<Topic> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Topic _item_1;
          final String _tmpName;
          if (_cursor.isNull(_cursorIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _cursor.getString(_cursorIndexOfName);
          }
          final String _tmpCategory;
          if (_cursor.isNull(_cursorIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
          }
          _item_1 = new Topic(_tmpName,_tmpCategory);
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          _item_1.setId(_tmpId);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
