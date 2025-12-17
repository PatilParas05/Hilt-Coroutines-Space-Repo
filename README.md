![Alt Text](Hiltro.png)


# 🛰️ Hiltro: The Android Coroutines & Hilt Template
**Hiltro is a focused Android learning project demonstrating the integration of modern Jetpack tools such as Dagger Hilt, Kotlin Coroutines (Flow), and Jetpack Compose.**
**It serves as a practical template for building scalable, testable, and modern Android applications.**
**The app uses a space exploration theme to showcase asynchronous data loading, dependency injection, and clean architecture patterns.**

---

## 💡 Core Learning Focus

### 1. Dagger Hilt (Dependency Injection)
Hilt manages object creation and lifecycle across the app, ensuring clean separation of concerns.
Key concepts demonstrated:
- @HiltAndroidApp on SpaceExpoApp.kt to trigger Hilt code generation
- @AndroidEntryPoint on MainActivity.kt for dependency injection
- @HiltViewModel and @Inject constructors in ViewModels
- @Binds in RepositoryModule.kt to map interfaces (e.g., SpaceRepository) to implementations

### 2. Kotlin Coroutines & Flow (Asynchronous Data)
The project uses Coroutines and Flow for safe, reactive, and lifecycle‑aware data handling.
Highlights:
- Suspend functions (getAllSpaceObjects) for non‑blocking operations
- StateFlow (_uiState) to represent UI states: Loading, Success, Error
- viewModelScope.launch for lifecycle‑aware background tasks
- collectAsState() in Compose to automatically update UI on state changes
---
### 🌌 Application Overview
- Theme: Dark, immersive space‑explorer UI
- Navigation: Simple list → detail flow
- Architecture: Clean Architecture (Data, Domain, Presentation layers)

### 🛠️ Tech Stack
| Category              | Technology                 |
|-----------------------|----------------------------|
| Language              | Kotlin                     |
| UI Toolkit            | Jetpack Compose            |
| Dependency Injection  | Dagger Hilt                |
| Concurrency           | Kotlin Coroutines & Flow   |
| Image Loading         | Coil                       |



### 🚀 Getting Started
Prerequisites
- Android Studio (latest version)
- Android SDK 34+

### Installation
Clone the repository:
``` bash
git clone https://github.com/your-username/Hiltro.git
```
**Verify Hilt Configuration**
Ensure your AndroidManifest.xml references the Hilt Application class:
```
<application
    android:name=".SpaceExpoApp"
    ... >
</application>
```
**Build and Run**
- Open the project in Android Studio
- Run Build > Rebuild Project
- Launch on an emulator or physical device

## 🤝 Contribution
**Contributions are welcome!**
Focus on improving the educational value of the project, especially around Hilt and Coroutines.

### 📝 License
Distributed under the MIT License.
See the LICENSE file for details.


