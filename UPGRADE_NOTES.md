# Android SDK 36 Upgrade Notes

## Important Behavior Changes

Updating to targetSdkVersion 36 (Android 15) introduces several behavior changes that may affect your app:

### Key Changes in Android 16 (API 36)

1. **Predictive Back System Animations**
   - Back-to-home, cross-task, and cross-activity animations are enabled by default
   - May require testing to ensure smooth transition experiences

2. **Large Screen Behavior Changes**
   - Changes to how the system manages orientation, resizability, and aspect ratio restrictions
   - App orientation, resizability, and aspect ratio constraints are ignored on large screens by default
   - Apps not fully ready can temporarily opt out of this behavior

3. **Security Enhancements**
   - Improved security against malicious intents
   - May require reviewing your app's inter-component communication

### Testing Recommendations

1. Test your app on devices running Android 15/16 to ensure compatibility
2. Use the Android SDK Upgrade Assistant in Android Studio for guidance
3. Pay special attention to:
   - Foreground service behavior
   - UI layout on large screens
   - Back navigation animations
   - Camera and media access

### Resources

- [Android 16 Behavior Changes (Targeted)](https://developer.android.com/about/versions/16/behavior-changes-16)
- [Android 16 Behavior Changes (All Apps)](https://developer.android.com/about/versions/16/behavior-changes-all)
- [Android 15 Behavior Changes](https://developer.android.com/about/versions/15/behavior-changes-15)

Review these resources thoroughly to ensure your app remains compatible and provides the best user experience.
