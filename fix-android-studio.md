# Fix Android Studio Gradle Sync

## The Issue
Android Studio sync is failing because of the mixed configuration between command-line builds and IDE builds.

## Solution

### 1. In Android Studio:
1. **File → Invalidate Caches and Restart** → **Invalidate and Restart**
2. **File → Settings → Build → Gradle**
   - Set "Use Gradle from" to **'gradle-wrapper.properties' file**
   - Set "Gradle JVM" to **Project SDK** or **Android Studio JVM**
3. **File → Sync Project with Gradle Files**

### 2. For Command Line Builds:
Always use: `./always-build.sh`

### 3. Gradle Configuration:
- `gradle.properties` is now compatible with Android Studio
- `always-build.sh` uses isolated cache for command-line builds
- Android Studio will use standard Gradle cache

## Why This Works:
- Android Studio uses default Gradle settings
- Command-line builds use isolated cache to prevent permission issues  
- Both can coexist without conflicts