# 📚 SchoolApp

> A personal hobby Android project for exploring modern Android development concepts and expanding my skills during free time.

## 🎯 Project Motive

This project serves as a **learning playground** to:
- Explore and practice modern Android development patterns and libraries
- Experiment with Firebase integration (Authentication, Firestore, Realtime Database)
- Build a practical app that could be useful for students managing their academic life
- Stay updated with the latest Android SDK and Kotlin features

## ✨ Features

### 🔐 Authentication
- **Google Sign-In** integration using the latest Credential Manager API
- Secure Firebase Authentication
- User profile management with profile picture display

### 📝 Todo Management
- Create, view, and manage personal todo items
- Data persistence with Firebase Firestore
- Clean list-based UI

### 📋 Assignment Tracking
- Add and track academic assignments with:
  - Title and description
  - Unit information
  - Total marks and marks obtained
- **Assignment Status Dashboard** with pie chart visualization (using MPAndroidChart)

### 🏠 Customizable Home Menu
- Grid-based primary menu with 7 customizable options:
  - Todo
  - Assignment
  - Web Link (Chrome Custom Tabs)
  - Mail (Gmail integration)
  - Drive
  - Timetable
  - Calendar
- Ability to enable/disable menu items per user preference
- Menu configuration synced with Firebase

### ⚙️ Settings
- User profile display with Google account info
- Manage primary menu visibility
- Logout functionality

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin |
| **Min SDK** | 26 (Android 8.0) |
| **Target SDK** | 36 |
| **UI** | Android XML Layouts, Material Design |
| **Architecture** | Activity-based |
| **Authentication** | Firebase Auth + Google Sign-In (Credential Manager) |
| **Database** | Firebase Firestore & Realtime Database |
| **Charts** | MPAndroidChart |
| **Web** | Chrome Custom Tabs |
| **Async** | Kotlin Coroutines |
| **Build** | Gradle with Kotlin DSL |

## 📁 Project Structure

```
app/src/main/java/edu/schoolapp/
├── SchoolApp.kt                    # Application class with SharedPreferences
├── LoginActivity.kt                # Google Sign-In entry point
├── HomeActivity.kt                 # Main dashboard with primary menu
├── TodoFragment.kt                 # Todo list screen
├── ActivityAddTodo.kt              # Add new todo
├── AssignmentActivity.kt           # Assignment list screen
├── AddAssignmentActivity.kt        # Add new assignment
├── AssignmentStatusActivity.kt     # Pie chart status visualization
├── SettingsActivity.kt             # User settings and logout
├── ManageListFragment.kt           # Enable/disable menu items
├── googleSingIn/                   # Google authentication service
│   ├── GoogleAuth.kt               # Auth data model
│   └── GoogleAuthenticationService.kt
└── model/                          # Data models
    ├── TodoModel.kt
    ├── AssignmentModel.kt
    └── PrimaryMenuDao.kt
```

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest stable version recommended)
- JDK 11 or higher
- A Firebase project with:
  - Firebase Authentication (Google Sign-In enabled)
  - Cloud Firestore
  - Realtime Database

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/SchoolApp.git
   cd SchoolApp
   ```

2. **Configure Firebase**
   - Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
   - Add an Android app with package name `edu.schoolapp`
   - Download `google-services.json` and place it in the `app/` directory
   - Enable Google Sign-In in Firebase Authentication
   - Create Firestore and Realtime Database instances

3. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```
   Or open the project in Android Studio and run directly.

## 📱 Screenshots

*Coming soon...*

## 🗺️ Roadmap

- [ ] Migrate to Jetpack Compose
- [ ] Implement MVVM architecture with ViewModels
- [ ] Add Timetable and Calendar functionality
- [ ] Add Drive integration
- [ ] Implement notifications for assignment deadlines
- [ ] Add dark mode support
- [ ] Write unit and UI tests

## 📄 License

This project is for personal learning purposes. Feel free to explore and learn from the code!

---

**Note:** This is a hobby project built during free time for learning and skill development. It's continuously evolving as I explore new Android concepts and libraries.
