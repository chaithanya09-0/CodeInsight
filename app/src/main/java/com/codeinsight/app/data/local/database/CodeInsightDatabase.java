package com.codeinsight.app.data.local.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.codeinsight.app.data.local.dao.AttemptDao;
import com.codeinsight.app.data.local.dao.MistakeDao;
import com.codeinsight.app.data.local.dao.PlatformConnectionDao;
import com.codeinsight.app.data.local.dao.ProblemDao;
import com.codeinsight.app.data.local.dao.RecommendationDao;
import com.codeinsight.app.data.local.dao.SyncHistoryDao;
import com.codeinsight.app.data.local.dao.TopicDao;
import com.codeinsight.app.data.local.entity.CodingAttempt;
import com.codeinsight.app.data.local.entity.CodingProblem;
import com.codeinsight.app.data.local.entity.Mistake;
import com.codeinsight.app.data.local.entity.PlatformConnection;
import com.codeinsight.app.data.local.entity.ProblemTopicCrossRef;
import com.codeinsight.app.data.local.entity.Recommendation;
import com.codeinsight.app.data.local.entity.StudySession;
import com.codeinsight.app.data.local.entity.SyncHistory;
import com.codeinsight.app.data.local.entity.Topic;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
    entities = {
        CodingProblem.class,
        CodingAttempt.class,
        Mistake.class,
        Topic.class,
        ProblemTopicCrossRef.class,
        Recommendation.class,
        PlatformConnection.class,
        SyncHistory.class,
        StudySession.class
    },
    version = 5,
    exportSchema = false
)
public abstract class CodeInsightDatabase extends RoomDatabase {

    public abstract ProblemDao problemDao();
    public abstract AttemptDao attemptDao();
    public abstract MistakeDao mistakeDao();
    public abstract TopicDao topicDao();
    public abstract RecommendationDao recommendationDao();
    public abstract PlatformConnectionDao platformConnectionDao();
    public abstract SyncHistoryDao syncHistoryDao();

    private static volatile CodeInsightDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    private static final androidx.room.migration.Migration MIGRATION_1_2 = new androidx.room.migration.Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("DROP INDEX IF EXISTS `index_platform_connections_platform`");
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `isActive` INTEGER NOT NULL DEFAULT 1"); } catch (Exception ignored) {}
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `totalSolved` INTEGER NOT NULL DEFAULT 0"); } catch (Exception ignored) {}
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `easySolved` INTEGER NOT NULL DEFAULT 0"); } catch (Exception ignored) {}
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `mediumSolved` INTEGER NOT NULL DEFAULT 0"); } catch (Exception ignored) {}
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `hardSolved` INTEGER NOT NULL DEFAULT 0"); } catch (Exception ignored) {}
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `ranking` INTEGER NOT NULL DEFAULT 0"); } catch (Exception ignored) {}
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `tagCountsJson` TEXT"); } catch (Exception ignored) {}
            db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_platform_connections_platform_username` ON `platform_connections` (`platform`, `username`)");
        }
    };

    private static final androidx.room.migration.Migration MIGRATION_2_3 = new androidx.room.migration.Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            try { db.execSQL("ALTER TABLE `coding_problems` ADD COLUMN `accountUsername` TEXT NOT NULL DEFAULT ''"); } catch (Exception ignored) {}
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_coding_problems_accountUsername` ON `coding_problems` (`accountUsername`)");
            db.execSQL("DELETE FROM `coding_problems` WHERE `externalId` LIKE 'demo-%' OR `notes` LIKE '%complement lookup%' OR `notes` LIKE '%Unbounded knapsack%' OR `notes` LIKE '%DP boolean array%' OR `notes` LIKE '%Automatic portfolio%'");
            db.execSQL("DELETE FROM `mistakes` WHERE `problemId` NOT IN (SELECT `id` FROM `coding_problems`)");
            db.execSQL("DELETE FROM `coding_attempts` WHERE `problemId` NOT IN (SELECT `id` FROM `coding_problems`)");
            db.execSQL("DELETE FROM `problem_topic_cross_ref` WHERE `problemId` NOT IN (SELECT `id` FROM `coding_problems`)");
        }
    };

    private static final androidx.room.migration.Migration MIGRATION_3_4 = new androidx.room.migration.Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `totalSubmissions` INTEGER NOT NULL DEFAULT 0"); } catch (Exception ignored) {}
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `submissionCalendarJson` TEXT NOT NULL DEFAULT '{}'"); } catch (Exception ignored) {}
            db.execSQL("DELETE FROM `coding_problems` WHERE (`platform` = 'LeetCode' AND (`accountUsername` = '' OR `accountUsername` IS NULL OR `accountUsername` = 'undefined')) OR `externalId` LIKE 'demo-%' OR (`externalId` LIKE 'leetcode-%' AND (`accountUsername` = '' OR `accountUsername` IS NULL))");
            db.execSQL("DELETE FROM `mistakes` WHERE `problemId` NOT IN (SELECT `id` FROM `coding_problems`)");
            db.execSQL("DELETE FROM `coding_attempts` WHERE `problemId` NOT IN (SELECT `id` FROM `coding_problems`)");
            db.execSQL("DELETE FROM `problem_topic_cross_ref` WHERE `problemId` NOT IN (SELECT `id` FROM `coding_problems`)");
        }
    };

    private static final androidx.room.migration.Migration MIGRATION_4_5 = new androidx.room.migration.Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `totalSubmissions` INTEGER NOT NULL DEFAULT 0"); } catch (Exception ignored) {}
            try { db.execSQL("ALTER TABLE `platform_connections` ADD COLUMN `submissionCalendarJson` TEXT NOT NULL DEFAULT '{}'"); } catch (Exception ignored) {}
            db.execSQL("DELETE FROM `coding_problems` WHERE (`platform` = 'LeetCode' AND (`accountUsername` = '' OR `accountUsername` IS NULL OR `accountUsername` = 'undefined')) OR `externalId` LIKE 'demo-%' OR (`externalId` LIKE 'leetcode-%' AND (`accountUsername` = '' OR `accountUsername` IS NULL))");
            db.execSQL("DELETE FROM `mistakes` WHERE `problemId` NOT IN (SELECT `id` FROM `coding_problems`)");
            db.execSQL("DELETE FROM `coding_attempts` WHERE `problemId` NOT IN (SELECT `id` FROM `coding_problems`)");
            db.execSQL("DELETE FROM `problem_topic_cross_ref` WHERE `problemId` NOT IN (SELECT `id` FROM `coding_problems`)");
        }
    };

    public static CodeInsightDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (CodeInsightDatabase.class) {
                if (INSTANCE == null) {
                    CodeInsightDatabase dbInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        CodeInsightDatabase.class,
                        "codeinsight_java_database"
                    )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                    .fallbackToDestructiveMigration()
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            databaseWriteExecutor.execute(() -> {
                                try {
                                    if (INSTANCE != null) {
                                        seedInitialTopics(INSTANCE.topicDao());
                                    }
                                } catch (Exception ignored) {}
                            });
                        }

                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                            try {
                                db.execSQL("PRAGMA foreign_keys = ON;");
                            } catch (Exception ignored) {}
                            databaseWriteExecutor.execute(() -> {
                                try {
                                    if (INSTANCE != null) {
                                        ProblemDao pDao = INSTANCE.problemDao();
                                        pDao.purgeSyntheticDemoProblems();
                                        pDao.purgeOrphanedMistakes();
                                        pDao.purgeOrphanedAttempts();
                                        pDao.purgeOrphanedCrossRefs();
                                    }
                                } catch (Exception ignored) {}
                            });
                        }
                    })
                    .build();
                    INSTANCE = dbInstance;
                }
            }
        }
        return INSTANCE;
    }

    public static void seedInitialTopics(TopicDao dao) {
        List<Topic> topics = new ArrayList<>();
        for (String topicName : com.codeinsight.app.domain.model.TopicNormalizer.PREDEFINED_TOPICS) {
            topics.add(new Topic(topicName, "DSA"));
        }
        dao.insertAll(topics);
    }

    public static void normalizeAndMergeExistingTopics(TopicDao dao) {
        if (dao == null) return;
        List<Topic> existingTopics = dao.getAllTopicsSync();
        if (existingTopics == null || existingTopics.isEmpty()) return;

        // Ensure baseline canonical topics exist
        seedInitialTopics(dao);

        for (Topic t : existingTopics) {
            String rawName = t.getName();
            String canonical = com.codeinsight.app.domain.model.TopicNormalizer.normalize(rawName);

            // If platform name or invalid topic, clean it up
            if (canonical == null) {
                dao.deleteCrossRefsForTopic(t.getId());
                dao.deleteTopicById(t.getId());
                continue;
            }

            // If name needs normalization or merging
            if (!canonical.equals(rawName)) {
                Topic targetTopic = dao.getTopicByName(canonical);
                long targetId;
                if (targetTopic != null) {
                    targetId = targetTopic.getId();
                } else {
                    targetId = dao.insert(new Topic(canonical, "DSA"));
                    if (targetId <= 0) {
                        Topic retry = dao.getTopicByName(canonical);
                        targetId = retry != null ? retry.getId() : -1;
                    }
                }

                if (targetId > 0 && targetId != t.getId()) {
                    List<Long> problemIds = dao.getProblemIdsForTopic(t.getId());
                    if (problemIds != null) {
                        for (Long pid : problemIds) {
                            dao.insertProblemTopicCrossRef(new ProblemTopicCrossRef(pid, targetId));
                        }
                    }
                    dao.deleteCrossRefsForTopic(t.getId());
                    dao.deleteTopicById(t.getId());
                }
            }
        }
    }
}
