package com.codeinsight.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import java.util.List;

@Dao
public interface PlatformConnectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PlatformConnection connection);

    @Update
    void update(PlatformConnection connection);

    @Query("SELECT * FROM platform_connections WHERE platform = :platform ORDER BY isActive DESC, lastSyncAt DESC LIMIT 1")
    PlatformConnection getConnectionByPlatform(String platform);

    @Query("SELECT * FROM platform_connections WHERE platform = :platform ORDER BY isActive DESC, lastSyncAt DESC LIMIT 1")
    LiveData<PlatformConnection> getConnectionByPlatformLive(String platform);

    @Query("SELECT * FROM platform_connections WHERE platform = :platform AND isActive = 1 LIMIT 1")
    PlatformConnection getActiveConnectionSync(String platform);

    @Query("SELECT * FROM platform_connections WHERE platform = :platform AND isActive = 1 LIMIT 1")
    LiveData<PlatformConnection> getActiveConnectionLive(String platform);

    @Query("SELECT * FROM platform_connections WHERE platform = :platform AND username = :username LIMIT 1")
    PlatformConnection getConnection(String platform, String username);

    @Query("SELECT * FROM platform_connections WHERE platform = :platform ORDER BY isActive DESC, lastSyncAt DESC")
    List<PlatformConnection> getConnectionsByPlatformSync(String platform);

    @Query("SELECT * FROM platform_connections WHERE platform = :platform ORDER BY isActive DESC, lastSyncAt DESC")
    LiveData<List<PlatformConnection>> getConnectionsByPlatformLive(String platform);

    @Query("SELECT * FROM platform_connections ORDER BY platform ASC, username ASC")
    LiveData<List<PlatformConnection>> getAllConnections();

    @Query("UPDATE platform_connections SET isActive = 0 WHERE platform = :platform")
    void deactivateAll(String platform);

    @Query("UPDATE platform_connections SET isActive = 1 WHERE platform = :platform AND username = :username")
    void setActiveProfile(String platform, String username);

    @Query("DELETE FROM platform_connections WHERE platform = :platform AND username = :username")
    void deleteConnection(String platform, String username);

    @Query("DELETE FROM platform_connections WHERE platform = :platform")
    void deleteConnection(String platform);
}

