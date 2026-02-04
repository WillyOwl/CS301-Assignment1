# EmotiLog 📱

**EmotiLog** is an Android application designed to help users track and analyze their emotional well-being through simple, intuitive emoji-based logging.

## 🚀 Features

### Core Functionality
- **Quick Emotion Logging**: Tap emoji buttons to instantly log your current emotional state
- **Custom Emoji Support**: Add, edit, and delete personalized emoji entries with custom text
- **Comprehensive History**: View all your emotion logs in chronological order with timestamps
- **Statistical Analysis**: Get insights into your emotional patterns with daily summaries and frequency analysis

### User Interface
- **Material Design**: Clean, modern interface following Material Design principles
- **Intuitive Interaction**: 
  - Single tap to log emotions
  - Long press to select and edit custom emojis
- **Grid Layout**: Organized emoji display for easy access
- **Multi-Activity Navigation**: Seamless navigation between main logging, history, and summary screens

## 📱 App Structure

### Activities

#### MainActivity
The primary interface where users interact with emotion logging:
- Grid of emoji buttons for quick logging
- Custom emoji management (add, edit, delete)
- Navigation to History and Summary views
- Material Design components for consistent UX

#### HistoryActivity  
Chronological view of all logged emotions:
- Reverse chronological order (most recent first)
- Timestamp display for each entry
- Simple, readable list format

#### SummaryActivity
Statistical analysis and insights:
- Daily emotion breakdowns
- Count and percentage calculations
- Grouped data presentation
- Total logs summary

### Core Components

#### LogManager
Singleton pattern data manager:
- Centralized emotion log storage
- Immutable LogEntry inner class
- Thread-safe data access
- Defensive copying for data integrity

## 🛠️ Technical Specifications

### Android Configuration
- **Target SDK**: 36 (Android 15)
- **Minimum SDK**: 24 (Android 7.0)
- **Compile SDK**: 36
- **Version**: 1.0 (Version Code: 1)
- **Java Version**: 11
- **Package**: `com.example.emotilog`

### Dependencies
```gradle
- androidx.appcompat
- com.google.android.material
- androidx.activity
- androidx.constraintlayout
- junit (testing)
- androidx.test.ext:junit (Android testing)
- androidx.test.espresso:espresso-core (UI testing)
```

### Architecture
- **Pattern**: Singleton (LogManager)
- **UI Framework**: Material Design Components
- **Data Storage**: In-memory (ArrayList)
- **Navigation**: Intent-based activity switching

## 📋 Predefined Emotions

The app comes with 9 predefined emotional states:
- 😄 **Joyful**
- 😭 **Miserable** 
- 😇 **Blessed**
- 🤬 **Enraged**
- 🥳 **Elated**
- 🥱 **Drained**
- 🧘 **Serene**
- 😍 **Cherished**
- 😲 **Astonished**

## 🚧 Known Limitations

### Critical Issues
- **No Data Persistence**: All logs are lost when the app is closed
- **Memory Limitations**: No data size limits could lead to memory issues over time

### Feature Limitations
- No data validation for custom emoji input
- No bulk operations support
- Limited filtering and sorting capabilities
- No export/backup functionality
- No real-time updates in summary view
- No visual charts or graphs
- No date range filtering

### UI/UX Limitations
- Some hardcoded colors and sizes
- No accessibility features implemented
- No confirmation dialogs for deletion
- Limited custom emoji count management
- Performance issues with large datasets in TextView display

## 🏗️ Project Structure

```
app/
├── src/main/
│   ├── AndroidManifest.xml
│   ├── java/com/example/emotilog/
│   │   ├── MainActivity.java          # Main emotion logging interface
│   │   ├── HistoryActivity.java       # Chronological emotion history
│   │   ├── SummaryActivity.java       # Statistical analysis view
│   │   └── LogManager.java            # Singleton data manager
│   └── res/
│       ├── layout/                    # UI layouts
│       ├── values/
│       │   ├── strings.xml           # App strings and labels
│       │   ├── colors.xml            # Color resources
│       │   └── themes.xml            # App themes
│       └── drawable/                 # Image resources
├── build.gradle.kts                  # Build configuration
└── proguard-rules.pro               # ProGuard rules
```

## 🔧 Build & Run

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API level 24+
- Java 11

### Building
1. Clone or download the project
2. Open in Android Studio
3. Sync Gradle files
4. Build and run on device/emulator

### Testing
The project includes unit and instrumentation test configurations:
```bash
./gradlew test              # Run unit tests
./gradlew connectedCheck    # Run instrumentation tests
```

## 🎯 Usage

1. **Log Emotions**: Open the app and tap any emoji to log your current emotional state
2. **Custom Emojis**: Long press any emoji to edit or delete, or use "Add Emoji" to create new ones
3. **View History**: Tap "View History" to see all your logged emotions with timestamps
4. **Analyze Patterns**: Tap "View Summary" to get statistical insights into your emotional patterns

## 🔮 Future Enhancements

### High Priority
- **Data Persistence**: Implement SQLite or Room database for permanent storage
- **Data Export**: Allow users to export their emotional data
- **Visual Analytics**: Add charts and graphs for better pattern visualization

### Medium Priority
- **Filtering & Search**: Date range filtering, emotion type filtering
- **Backup & Sync**: Cloud backup and multi-device synchronization
- **Accessibility**: Full accessibility support for users with disabilities

### Low Priority  
- **Themes**: Dark mode and custom theme support
- **Notifications**: Reminder notifications for regular logging
- **Social Features**: Share insights or compare with friends (privacy-focused)

## 📄 License

This project is part of a development learning exercise. Please check with the original author for licensing terms.

## 👨‍💻 Development Notes

This app demonstrates several Android development concepts:
- Activity lifecycle and navigation
- Singleton design pattern
- Material Design implementation
- Event handling (click/long-click)
- Data management and collection operations
- Intent-based navigation
- Resource management

The codebase includes comprehensive inline documentation explaining design decisions, outstanding issues, and technical implementation details.
