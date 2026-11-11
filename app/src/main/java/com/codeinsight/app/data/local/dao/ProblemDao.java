package com.codeinsight.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.codeinsight.app.data.local.entity.CodingProblem;
import com.codeinsight.app.data.local.entity.ProblemWithDetails;
import java.util.List;

@Dao
public interface ProblemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CodingProblem problem);

    @Update
    void update(CodingProblem problem);

    @Delete
    void delete(CodingProblem problem);

    @Query("SELECT * FROM coding_problems ORDER BY updatedAt DESC")
    LiveData<List<CodingProblem>> getAllProblems();

    @Transaction
    @Query("SELECT * FROM coding_problems ORDER BY updatedAt DESC")
    LiveData<List<ProblemWithDetails>> getAllProblemsWithDetails();

    @Transaction
    @Query("SELECT * FROM coding_problems ORDER BY updatedAt DESC")
    List<ProblemWithDetails> getAllProblemsWithDetailsSync();

    @Transaction
    @Query("SELECT * FROM coding_problems WHERE accountUsername = :account OR accountUsername = '' OR accountUsername IS NULL ORDER BY updatedAt DESC")
    LiveData<List<ProblemWithDetails>> getProblemsForAccountWithDetails(String account);

    @Transaction
    @Query("SELECT * FROM coding_problems WHERE accountUsername = :account OR accountUsername = '' OR accountUsername IS NULL ORDER BY updatedAt DESC")
    List<ProblemWithDetails> getProblemsForAccountWithDetailsSync(String account);

    @Query("SELECT * FROM coding_problems WHERE id = :id")
    CodingProblem getProblemById(long id);

    @Transaction
    @Query("SELECT * FROM coding_problems WHERE id = :id")
    LiveData<ProblemWithDetails> getProblemWithDetails(long id);

    @Transaction
    @Query("SELECT * FROM coding_problems WHERE id = :id")
    ProblemWithDetails getProblemWithDetailsSync(long id);

    @Query("SELECT * FROM coding_problems WHERE externalId = :externalId LIMIT 1")
    CodingProblem getProblemByExternalId(String externalId);

    @Query("SELECT COUNT(*) FROM coding_problems")
    LiveData<Integer> getTotalProblemsCount();

    @Query("SELECT COUNT(*) FROM coding_problems WHERE status = 'Solved'")
    LiveData<Integer> getSolvedProblemsCount();

    @Query("DELETE FROM coding_problems WHERE notes = 'Imported from LeetCode sync'")
    void deleteAutoCuratedLeetCodeProblems();

    @Query("DELETE FROM coding_problems WHERE (platform = 'LeetCode' AND (accountUsername = '' OR accountUsername IS NULL OR accountUsername = 'undefined')) OR externalId LIKE 'demo-%' OR (externalId LIKE 'leetcode-%' AND (accountUsername = '' OR accountUsername IS NULL)) OR notes LIKE '%complement lookup%' OR notes LIKE '%Unbounded knapsack%' OR notes LIKE '%DP boolean array%' OR notes LIKE '%Automatic portfolio%'")
    void purgeSyntheticDemoProblems();

    @Query("DELETE FROM mistakes WHERE problemId NOT IN (SELECT id FROM coding_problems)")
    void purgeOrphanedMistakes();

    @Query("DELETE FROM coding_attempts WHERE problemId NOT IN (SELECT id FROM coding_problems)")
    void purgeOrphanedAttempts();

    @Query("DELETE FROM problem_topic_cross_ref WHERE problemId NOT IN (SELECT id FROM coding_problems)")
    void purgeOrphanedCrossRefs();

    @Query("DELETE FROM coding_problems")
    void deleteAllProblems();
}

