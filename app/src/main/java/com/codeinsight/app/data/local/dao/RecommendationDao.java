package com.codeinsight.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.codeinsight.app.data.local.entity.Recommendation;
import java.util.List;

@Dao
public interface RecommendationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Recommendation recommendation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Recommendation> recommendations);

    @Update
    void update(Recommendation recommendation);

    @Delete
    void delete(Recommendation recommendation);

    @Query("SELECT * FROM recommendations WHERE dismissed = 0 ORDER BY priority DESC, createdAt DESC")
    LiveData<List<Recommendation>> getActiveRecommendations();

    @Query("SELECT * FROM recommendations WHERE dismissed = 0 ORDER BY priority DESC, createdAt DESC")
    List<Recommendation> getActiveRecommendationsSync();

    @Query("UPDATE recommendations SET dismissed = 1 WHERE id = :id")
    void dismissRecommendation(long id);

    @Query("DELETE FROM recommendations")
    void deleteAllRecommendations();
}
