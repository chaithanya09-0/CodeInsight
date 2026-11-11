package com.codeinsight.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.MistakeWithProblem;
import com.codeinsight.app.domain.model.CategoryCount;
import java.util.List;

@Dao
public interface MistakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Mistake mistake);

    @Update
    void update(Mistake mistake);

    @Delete
    void delete(Mistake mistake);

    @Query("SELECT * FROM mistakes WHERE problemId = :problemId ORDER BY createdAt DESC")
    LiveData<List<Mistake>> getMistakesForProblem(long problemId);

    @Query("SELECT * FROM mistakes WHERE problemId = :problemId ORDER BY createdAt DESC")
    List<Mistake> getMistakesForProblemSync(long problemId);

    @Query("SELECT * FROM mistakes ORDER BY createdAt DESC")
    LiveData<List<Mistake>> getAllMistakes();

    @Transaction
    @Query("SELECT * FROM mistakes ORDER BY createdAt DESC")
    LiveData<List<MistakeWithProblem>> getAllMistakesWithProblem();

    @Transaction
    @Query("SELECT * FROM mistakes ORDER BY createdAt DESC")
    List<MistakeWithProblem> getAllMistakesWithProblemSync();

    @Query("SELECT category, COUNT(*) as count FROM mistakes GROUP BY category ORDER BY count DESC")
    LiveData<List<CategoryCount>> getMistakeCategoryCounts();

    @Query("SELECT category, COUNT(*) as count FROM mistakes GROUP BY category ORDER BY count DESC")
    List<CategoryCount> getMistakeCategoryCountsSync();

    @Query("DELETE FROM mistakes WHERE problemId = :problemId")
    void deleteMistakesForProblem(long problemId);
}

