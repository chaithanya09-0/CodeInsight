# CodeInsight — Coding Performance & Skill Analysis System

**CodeInsight** is an Android application developed in **100% Java** for Mobile Application Development (MAD) coursework. It helps programmers track their coding practice, analyze mistake patterns, identify weak programming topics through a rule-based diagnostic engine, monitor improvement over time with interactive analytics, and receive personalized practice recommendations.

---

## 🛠️ Technology Stack & Requirements

- **Language:** **Java 17** (100% Java codebase, no Kotlin)
- **UI Framework:** **Android XML Layouts** + ViewBinding + Google **Material 3** Design
- **Architecture:** Clean Architecture + **MVVM** (`ViewModel` + `LiveData`)
- **Navigation:** Standard Android `Activities`, `Fragments`, and `BottomNavigationView`
- **Local Persistence:** **Room Database** (Java `annotationProcessor`, relational foreign keys, cascade delete, index optimization)
- **Preferences:** SharedPreferences (`UserPreferencesManager` with LiveData observers)
- **Networking:** **Retrofit 2.9.0** + Gson Converter + OkHttp Logging Interceptor
- **Background Synchronization:** **WorkManager** (`LeetCodeSyncWorker`)
- **Data Visualization & Charts:** **MPAndroidChart** (`PieChart`, `BarChart`, `LineChart`, `HorizontalBarChart`)
- **Unit Testing:** **JUnit 4**

---

## 📂 Project Architecture

```
com.codeinsight.app/
├── CodeInsightApp.java                     # Application class (DB, Repositories, Theme setup)
│
├── data/
│   ├── local/
│   │   ├── dao/                            # Room DAOs (ProblemDao, AttemptDao, MistakeDao, TopicDao, etc.)
│   │   ├── database/                       # CodeInsightDatabase (Abstract Room database + ThreadPool)
│   │   └── entity/                         # Room Entities (CodingProblem, CodingAttempt, Mistake, Topic, etc.)
│   ├── preferences/                        # UserPreferencesManager (SharedPreferences + LiveData)
│   ├── remote/
│   │   └── leetcode/                       # GraphQLRequest, LeetCodeApiService, LeetCodeSyncRepository
│   ├── repository/                         # ProblemRepository (DB queries, LiveData streams, Aggregations)
│   └── worker/                             # LeetCodeSyncWorker (WorkManager periodic background sync)
│
├── domain/
│   ├── analysis/                           # WeakAreaAnalysisEngine, DashboardStats, AnalyticsData, etc.
│   └── model/                              # Domain Enums (Platform, Difficulty, ProblemStatus, MistakeCategory)
│
└── ui/
    ├── MainActivity.java                   # Main screen holding BottomNavigationView & Fragments
    ├── adapter/                            # RecyclerView Adapters:
    │   ├── ProblemAdapter.java
    │   ├── AttemptAdapter.java
    │   ├── MistakeAdapter.java
    │   ├── RecommendationAdapter.java
    │   ├── TopicDiagnosticAdapter.java
    │   └── SyncHistoryAdapter.java
    │
    ├── dashboard/
    │   ├── DashboardFragment.java          # Key metrics, PieChart, Weekly BarChart, Weak topics
    │   └── DashboardViewModel.java
    │
    ├── problems/
    │   ├── ProblemsFragment.java           # Filter chips, Search, Sort, Problem list, FAB
    │   ├── ProblemsViewModel.java
    │   ├── AddEditProblemActivity.java     # Add/Edit problem form with topic chips & initial attempt
    │   ├── AddEditProblemViewModel.java
    │   ├── ProblemDetailActivity.java      # Details, URL opening, attempts list, log attempt dialog
    │   └── ProblemDetailViewModel.java
    │
    ├── mistakes/
    │   ├── MistakeLoggerActivity.java      # 9 mistake category chips, what went wrong, prevention notes
    │   └── MistakeViewModel.java
    │
    ├── analytics/
    │   ├── AnalyticsFragment.java          # MPAndroidChart Bar, Line, Pie, & Horizontal Bar charts
    │   └── AnalyticsViewModel.java
    │
    ├── recommendations/
    │   ├── RecommendationsFragment.java    # Recommendations & Weak Areas Diagnostic tabs
    │   └── RecommendationsViewModel.java
    │
    ├── leetcode/
    │   ├── LeetCodeSyncActivity.java       # GraphQL sync, public profile inspection, deduplication
    │   └── LeetCodeViewModel.java
    │
    └── settings/
        ├── SettingsFragment.java           # User profile, theme toggle, JSON backup export, clear data
        └── SettingsViewModel.java
```

---

## 🌟 Application Modules & Features

### 1. Dashboard
- **Total problems solved** with difficulty breakdown (Easy, Medium, Hard).
- **Overall acceptance rate** percentage.
- **Average solving time** (minutes per solution).
- **Weekly coding activity:** MPAndroidChart `BarChart` displaying attempt volume over the last 7 days.
- **Difficulty distribution:** MPAndroidChart `PieChart` with color-coded difficulty slices.
- **Weakest programming topics:** Quick-warning list of topics requiring deliberate practice.
- **Recent problems:** Recycler list of recently updated problem entries with click navigation.

### 2. Problem Management
- **Full CRUD operations:** Add, edit, inspect, and delete problems.
- **Platform options:** LeetCode, CodeChef, HackerRank, Codeforces, Other.
- **Problem details:** Title, URL (clickable to open in browser), Difficulty (Easy, Medium, Hard), and Strategy notes.
- **Topics & Tags:** Pre-seeded DSA topics (Arrays, Strings, Hash Table, Dynamic Programming, Graphs, Trees, Recursion, etc.) + ability to dynamically add Custom Topics.
- **Attempt Tracking:** Log multiple attempts per problem (Accepted/Failed, Duration in minutes, Language, Notes).
- **Search & Filters:** Real-time search by title, topic, or notes; filter by platform, difficulty, and status chips.
- **Sorting:** Sort by Newest Updated, Oldest, Title (A-Z), Difficulty (Easy to Hard), Most Attempts.

### 3. Mistake Logger
- Direct recording of mistakes associated with specific problem attempts.
- **9 standard mistake categories:**
  1. *Logical error*
  2. *Syntax error*
  3. *Edge case missed*
  4. *Time Limit Exceeded*
  5. *Space complexity issue*
  6. *Incorrect algorithm*
  7. *Off-by-one error*
  8. *Recursion error*
  9. *Other*
- Records "What went wrong" and "Root Cause & Prevention Rule".
- Supports multiple mistakes per attempt with delete confirmation.

### 4. Analytics & Interactive Charts (MPAndroidChart)
- **Problems Solved Over Time:** Monthly trend `BarChart`.
- **Acceptance Rate Trends:** Smooth cubic `LineChart` showing performance trends.
- **Difficulty Distribution:** Donut `PieChart` showing Easy vs Medium vs Hard balance.
- **Topic-wise Performance:** `HorizontalBarChart` ranking topics by acceptance percentage.
- **Mistake Category Distribution:** `HorizontalBarChart` highlighting dominant failure causes.

### 5. Rule-Based Weak Area Detection Engine
Pure Java rule engine (`WeakAreaAnalysisEngine.java`) that evaluates:
- **Low Acceptance Rates:** Flags topics with acceptance rate < 35% as *Critical* and < 60% as *Needs Work*.
- **Data Sufficiency Gatekeeper:** Avoids jumping to premature conclusions (requires minimum attempts before diagnosing).
- **Dominant Mistake Detection:** Identifies which specific mistake category occurs most frequently per topic.

### 6. Personalized Practice Recommendations
- Generates data-backed, actionable study recommendations with explicit reasoning (e.g. *"Dynamic Programming is one of your weakest topics with an acceptance rate of 25%. Based on 4 attempts with 3 failures..."*).
- Provides actionable steps and checklists for each identified weakness.
- Prerequisite detection: Suggests mastering fundamentals (e.g. Recursion) before tackling complex topics (e.g. Backtracking).

### 7. LeetCode Integration Module
- **Zero-Password Public Integration:** Connects using LeetCode's public GraphQL endpoint without asking for passwords or session cookies.
- **Profile Inspection:** Fetches global rank, total solved, and easy/medium/hard metrics.
- **Deduplicated Problem Sync:** Imports solved problems while ensuring existing notes, attempts, and mistakes are preserved.
- **Sync History Log:** Records timestamp, items synced, and error statuses.
- **Background Sync:** WorkManager `LeetCodeSyncWorker` periodically syncs in the background when enabled.

### 8. Profile & Settings
- Developer name customization.
- **Dark Theme / Light Theme Toggle:** Instant theme switching using `AppCompatDelegate`.
- **Data Export (JSON):** Generates a complete JSON backup of problems, attempts, mistakes, and topics to share or backup.
- **Clear Local Data:** Destructive action protected by a confirmation dialog to safely wipe local database.

---

## 🗄️ Database Design (Room)

- **`coding_problems`:** `id`, `title`, `url`, `platform`, `difficulty`, `status`, `totalAttempts`, `notes`, `externalId`, `createdAt`, `updatedAt`.
- **`coding_attempts`:** `id`, `problemId` (FK -> `coding_problems.id`, cascade delete), `accepted`, `durationMinutes`, `language`, `notes`, `attemptedAt`.
- **`mistakes`:** `id`, `attemptId` (FK), `problemId` (FK), `category`, `description`, `notes`, `createdAt`.
- **`topics`:** `id`, `name` (unique index), `category`.
- **`problem_topic_cross_ref`:** Composite PK (`problemId`, `topicId`) with cascading foreign keys.
- **`recommendations`:** `id`, `topicId`, `type`, `title`, `description`, `reasoning`, `priority`, `dismissed`, `createdAt`.
- **`platform_connections`:** `id`, `platform`, `username`, `connected`, `lastSyncedAt`.
- **`sync_history`:** `id`, `connectionId` (FK), `status`, `itemsSynced`, `errorMessage`, `syncedAt`.

---

## 🚀 How to Run in Android Studio

1. Open **Android Studio** (Flamingo, Giraffe, Hedgehog, Iguana, Jellyfish, Koala, Ladybug or newer).
2. Click **Open** and select the folder:
   ```
   c:\Users\chait\OneDrive\Desktop\Mad Project
   ```
3. Android Studio will automatically recognize the Java project and synchronize Gradle using the local Android SDK.
4. Select an Android Virtual Device (AVD) or physical device running Android 8.0 (API 26) or higher.
5. Click **Run 'app'** (`Shift + F10`).
# CodeInsight
