package com.codeinsight.app.data.local.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "sync_history",
    foreignKeys = @ForeignKey(
        entity = PlatformConnection.class,
        parentColumns = "id",
        childColumns = "connectionId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index("connectionId")}
)
public class SyncHistory {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long connectionId;
    private String status; // SUCCESS, FAILED
    private int itemsSynced;
    private String errorMessage;
    private long syncedAt;

    public SyncHistory() {
        this.itemsSynced = 0;
        this.syncedAt = System.currentTimeMillis();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getConnectionId() { return connectionId; }
    public void setConnectionId(long connectionId) { this.connectionId = connectionId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getItemsSynced() { return itemsSynced; }
    public void setItemsSynced(int itemsSynced) { this.itemsSynced = itemsSynced; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public long getSyncedAt() { return syncedAt; }
    public void setSyncedAt(long syncedAt) { this.syncedAt = syncedAt; }
}

