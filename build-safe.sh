#!/bin/bash

# Safe build script that prevents permission issues
echo "🔧 Safe build script starting..."

# Kill any stuck Java processes
killall -9 java 2>/dev/null || true

# Set environment variables to use local directory
export GRADLE_USER_HOME="$(pwd)/gradle-cache"
export GRADLE_OPTS="-Dorg.gradle.daemon=false -Dfile.encoding=UTF-8"
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"

# Create local gradle home if it doesn't exist
mkdir -p gradle-cache

# Remove any problematic lock files and directories
rm -rf gradle-cache/daemon 2>/dev/null || true
find gradle-cache -name "*.lock" -delete 2>/dev/null || true
find . -name "*.lock" -delete 2>/dev/null || true

# Stop any running daemons
echo "Stopping Gradle daemons..."
./gradlew --stop 2>/dev/null || true

# Build the project
echo "Building project..."
./gradlew --no-daemon build

echo "✅ Build complete!"