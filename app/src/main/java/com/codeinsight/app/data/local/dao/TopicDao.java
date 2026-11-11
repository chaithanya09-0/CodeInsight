package com.codeinsight.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.codeinsight.app.data.local.entity.ProblemTopicCrossRef;
import com.codeinsight.app.data.local.entity.Topic;
import java.util.List;

@Dao
public interface TopicDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Topic topic);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Topic> topics);

    @Delete
    void delete(Topic topic);

    @Query("SELECT * FROM topics ORDER BY name ASC")
    LiveData<List<Topic>> getAllTopics();

    @Query("SELECT * FROM topics ORDER BY name ASC")
    List<Topic> getAllTopicsSync();

    @Query("SELECT * FROM topics WHERE LOWER(TRIM(name)) = LOWER(TRIM(:name)) LIMIT 1")
    Topic getTopicByName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProblemTopicCrossRef(ProblemTopicCrossRef crossRef);

    @Query("DELETE FROM problem_topic_cross_ref WHERE problemId = :problemId")
    void deleteTopicsForProblem(long problemId);

    @Query("SELECT t.* FROM topics t INNER JOIN problem_topic_cross_ref ptcr ON t.id = ptcr.topicId WHERE ptcr.problemId = :problemId")
    LiveData<List<Topic>> getTopicsForProblem(long problemId);

    @Query("SELECT t.* FROM topics t INNER JOIN problem_topic_cross_ref ptcr ON t.id = ptcr.topicId WHERE ptcr.problemId = :problemId")
    List<Topic> getTopicsForProblemSync(long problemId);

    @Query("SELECT problemId FROM problem_topic_cross_ref WHERE topicId = :topicId")
    List<Long> getProblemIdsForTopic(long topicId);

    @Query("DELETE FROM problem_topic_cross_ref WHERE topicId = :topicId")
    void deleteCrossRefsForTopic(long topicId);

    @Query("DELETE FROM topics WHERE id = :topicId")
    void deleteTopicById(long topicId);
}
