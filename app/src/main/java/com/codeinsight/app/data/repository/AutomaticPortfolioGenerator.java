package com.codeinsight.app.data.repository;

import android.util.Log;
import com.codeinsight.app.data.local.dao.AttemptDao;
import com.codeinsight.app.data.local.dao.MistakeDao;
import com.codeinsight.app.data.local.dao.ProblemDao;
import com.codeinsight.app.data.local.dao.TopicDao;

/**
 * Disabled generator: ensures zero synthetic/fake problems are ever inserted.
 * Real LeetCode accounts and user manual entries only.
 */
public class AutomaticPortfolioGenerator {

    private static final String TAG = "AutoPortfolioGen";

    private final ProblemDao problemDao;
    private final AttemptDao attemptDao;
    private final MistakeDao mistakeDao;
    private final TopicDao topicDao;

    public AutomaticPortfolioGenerator(
            ProblemDao problemDao,
            AttemptDao attemptDao,
            MistakeDao mistakeDao,
            TopicDao topicDao
    ) {
        this.problemDao = problemDao;
        this.attemptDao = attemptDao;
        this.mistakeDao = mistakeDao;
        this.topicDao = topicDao;
    }

    public synchronized void generateFullPortfolio() {
        try {
            if (problemDao != null) {
                problemDao.purgeSyntheticDemoProblems();
            }
            Log.d(TAG, "Synthetic portfolio generation disabled. Demo data purged.");
        } catch (Exception e) {
            Log.e(TAG, "Error purging synthetic data", e);
        }
    }
}
