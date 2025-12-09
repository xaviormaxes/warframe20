@echo off
REM Clean Gradle script for cross-platform development
REM Run this script when switching between Windows and macOS

echo 🧹 Cleaning Gradle caches for cross-platform compatibility...

REM Stop all Gradle daemons
echo Stopping Gradle daemons...
gradlew.bat --stop

REM Remove local project .gradle directory
if exist ".gradle" (
    echo Removing local .gradle directory...
    rmdir /s /q .gradle
)

REM Remove global Gradle caches (optional - uncomment if needed)
REM echo Removing global Gradle caches...
REM rmdir /s /q %USERPROFILE%\.gradle\caches
REM rmdir /s /q %USERPROFILE%\.gradle\daemon

REM Remove build directories
echo Cleaning build directories...
gradlew.bat clean

REM Final message
echo ✅ Gradle cleanup complete!
echo 💡 In Android Studio: File ^> Invalidate Caches and Restart
echo 💡 Run 'gradlew.bat build' to verify everything works
pause