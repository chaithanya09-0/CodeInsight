package com.codeinsight.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.codeinsight.app.data.local.entity.CodingAttempt;
import java.util.List;

@Dao
public interface AttemptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CodingAttempt attempt);

    @Update
    void update(CodingAttempt attempt);

    @Delete
    void delete(CodingAttempt attempt);

    @Query("SELECT * FROM coding_attempts WHERE problemId = :problemId ORDER BY attemptedAt DESC")
    LiveData<List<CodingAttempt>> getAttemptsForProblem(long problemId);

    @Query("SELECT * FROM coding_attempts WHERE problemId = :problemId ORDER BY attemptedAt DESC")
    List<CodingAttempt> getAttemptsForProblemSync(long problemId);

    @Query("SELECT * FROM coding_attempts ORDER BY attemptedAt DESC")
    List<CodingAttempt> getAllAttemptsSync();

    @Query("SELECT COUNT(*) FROM coding_attempts WHERE accepted = 1")
    LiveData<Integer> getAcceptedAttemptsCount();

    @Query("SELECT COUNT(*) FROM coding_attempts")
    LiveData<Integer> getTotalAttemptsCount();

    @Query("SELECT AVG(durationMinutes) FROM coding_attempts WHERE durationMinutes IS NOT NULL AND accepted = 1")
    LiveData<Double> getAverageSolvingDuration();
}

