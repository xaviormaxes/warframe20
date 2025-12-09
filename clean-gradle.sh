#!/bin/bash

# Clean Gradle script for cross-platform development
# Run this script when switching between Windows and macOS

echo "🧹 Cleaning Gradle caches for cross-platform compatibility..."

# Stop all Gradle daemons
echo "Stopping Gradle daemons..."
./gradlew --stop

# Remove local project .gradle directory
if [ -d ".gradle" ]; then
    echo "Removing local .gradle directory..."
    rm -rf .gradle
fi

# Remove global Gradle caches (optional - uncomment if needed)
# echo "Removing global Gradle caches..."
# rm -rf ~/.gradle/caches
# rm -rf ~/.gradle/daemon

# Remove build directories
echo "Cleaning build directories..."
./gradlew clean

# Invalidate caches and restart (if using IntelliJ/Android Studio)
echo "✅ Gradle cleanup complete!"
echo "💡 In Android Studio: File > Invalidate Caches and Restart"
echo "💡 Run './gradlew build' to verify everything works"