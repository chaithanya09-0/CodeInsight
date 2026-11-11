package com.codeinsight.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.codeinsight.app.data.local.entity.SyncHistory;
import java.util.List;

@Dao
public interface SyncHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SyncHistory syncHistory);

    @Query("SELECT * FROM sync_history WHERE connectionId = :connectionId ORDER BY syncedAt DESC")
    LiveData<List<SyncHistory>> getSyncHistoryForConnection(long connectionId);

    @Query("SELECT * FROM sync_history ORDER BY syncedAt DESC LIMIT :limit")
    LiveData<List<SyncHistory>> getRecentSyncHistory(int limit);

    @Query("DELETE FROM sync_history WHERE connectionId = :connectionId")
    void deleteSyncHistoryForConnection(long connectionId);
}
