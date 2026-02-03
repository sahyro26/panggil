# Panggil - Modern Android Dialer App

<div align="center">
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android"/>
  <img src="https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin"/>
  <img src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose"/>
</div>

## 📱 Project Overview

**Panggil** is a modern Android dialer application with iPhone-like favorites functionality. The app features a clean, intuitive interface built with Jetpack Compose and follows the MVVM architecture pattern. It provides three main screens accessible via bottom navigation: Call, Favorite, and Contacts.

### Key Features

- 🎯 **Modern UI**: Built with Jetpack Compose and Material3 design system
- ⭐ **Smart Favorites**: iPhone-like favorite contacts with customizable quick actions
- 📞 **Call Management**: Numpad dialer with recent call history
- 📇 **Contacts Integration**: Native contacts integration
- 🎨 **Theming**: Support for light/dark themes and dynamic colors (Android 12+)
- 🏗️ **Clean Architecture**: MVVM pattern with clear separation of concerns

### Screenshots

> 📸 Screenshots will be added here as features are implemented

## 🏗️ Architecture Overview

This app follows the **MVVM (Model-View-ViewModel)** architecture pattern, which provides:

- **Separation of Concerns**: UI, business logic, and data are separated
- **Testability**: Easy to unit test ViewModels and repositories
- **Lifecycle Awareness**: ViewModels survive configuration changes
- **Reactive UI**: StateFlow for reactive state management

### Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                           UI Layer                           │
│  ┌────────────┐  ┌──────────────┐  ┌─────────────────┐    │
│  │CallScreen  │  │FavoriteScreen│  │ContactsScreen   │    │
│  │(Compose)   │  │(Compose)     │  │(Compose)        │    │
│  └─────┬──────┘  └──────┬───────┘  └────────┬────────┘    │
│        │                 │                    │              │
│        ▼                 ▼                    ▼              │
│  ┌────────────┐  ┌──────────────┐  ┌─────────────────┐    │
│  │CallViewModel│ │FavoriteViewModel│ContactsViewModel│    │
│  │(StateFlow) │  │(StateFlow)    │  │(StateFlow)      │    │
│  └─────┬──────┘  └──────┬───────┘  └────────┬────────┘    │
└────────┼─────────────────┼──────────────────┼──────────────┘
         │                 │                  │
┌────────┼─────────────────┼──────────────────┼──────────────┐
│        │         Domain Layer                │              │
│        └────────────┬────┴──────────────────┘              │
│                     ▼                                        │
│              ┌─────────────┐                                │
│              │  Use Cases  │                                │
│              │(Business    │                                │
│              │ Logic)      │                                │
│              └──────┬──────┘                                │
└─────────────────────┼───────────────────────────────────────┘
                      │
┌─────────────────────┼───────────────────────────────────────┐
│                     ▼          Data Layer                    │
│              ┌─────────────┐                                │
│              │Repositories │                                │
│              └──────┬──────┘                                │
│                     │                                        │
│        ┌────────────┼────────────┐                          │
│        ▼            ▼            ▼                          │
│   ┌────────┐  ┌─────────┐  ┌─────────┐                    │
│   │  Room  │  │Contacts │  │Call Log │                    │
│   │Database│  │Provider │  │Provider │                    │
│   └────────┘  └─────────┘  └─────────┘                    │
└──────────────────────────────────────────────────────────────┘
```

### Package Structure

```
tech.izdigital.panggil/
├── ui/                                  # UI Layer (View)
│   ├── navigation/                      # Navigation setup
│   │   ├── BottomNavItem.kt            # Bottom nav items definition
│   │   ├── NavigationGraph.kt          # Navigation graph
│   │   └── Screen.kt                   # Screen routes
│   ├── screens/                         # Screen composables
│   │   ├── call/                        # Call screen
│   │   │   ├── CallScreen.kt           # UI
│   │   │   └── CallViewModel.kt        # State management
│   │   ├── favorite/                    # Favorite screen
│   │   │   ├── FavoriteScreen.kt       # UI
│   │   │   └── FavoriteViewModel.kt    # State management
│   │   └── contacts/                    # Contacts screen
│   │       ├── ContactsScreen.kt       # UI
│   │       └── ContactsViewModel.kt    # State management
│   ├── components/                      # Reusable UI components
│   └── theme/                           # Material3 theme
│       ├── Color.kt                     # Color definitions
│       ├── Theme.kt                     # Theme setup
│       └── Type.kt                      # Typography
├── data/                                # Data Layer
│   ├── model/                           # Data models
│   │   ├── Contact.kt                  # Contact model
│   │   ├── CallLog.kt                  # Call log model
│   │   └── FavoriteAction.kt           # Favorite action types
│   ├── local/                           # Local data sources
│   │   ├── dao/                        # Room DAOs
│   │   └── database/                   # Room database
│   └── repository/                      # Repository pattern
├── domain/                              # Domain Layer
│   └── usecase/                        # Business logic use cases
└── util/                                # Utility classes
```

## 🛠️ Tech Stack

### Core Technologies

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Kotlin** | 2.0.21 | Programming language |
| **Jetpack Compose** | 2024.09.00 (BOM) | UI framework |
| **Material3** | Latest | Design system |
| **Navigation Compose** | 2.8.5 | Screen navigation |

### Architecture & State Management

| Technology | Version | Purpose |
|-----------|---------|---------|
| **ViewModel Compose** | 2.9.2 | MVVM state management |
| **Lifecycle Runtime** | 2.9.2 | Lifecycle awareness |
| **Coroutines** | 1.9.0 | Async operations |
| **StateFlow** | Built-in | Reactive state |

### Data Persistence

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Room Database** | 2.7.0-alpha12 | Local database |
| **Room KTX** | 2.7.0-alpha12 | Kotlin extensions |
| **KSP** | 2.0.21-1.0.29 | Annotation processing |

### Additional Libraries

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Material Icons Extended** | 1.7.6 | Additional icons |
| **Accompanist Permissions** | 0.36.0 | Runtime permissions |

### Why These Libraries?

- **Jetpack Compose**: Modern declarative UI, less boilerplate, better performance
- **Navigation Compose**: Type-safe navigation, state preservation, deep linking support
- **Room**: Type-safe database queries, compile-time verification, LiveData/Flow support
- **Coroutines**: Simplified async programming, structured concurrency, cancellation support
- **StateFlow**: Reactive state management, lifecycle-aware, better than LiveData for Compose

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Ladybug | 2024.2.1 or later
- **Minimum SDK**: API 26 (Android 8.0)
- **Target SDK**: API 36
- **JDK**: Version 11 or later

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/sahyro26/panggil.git
   cd panggil
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle
   - Wait for dependencies to download

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button (▶️) or press `Shift + F10`

### Build Variants

- **Debug**: Development build with debugging enabled
- **Release**: Production build with code optimization

## 💻 Development Guide

### Adding New Features

1. **Create Model** (if needed)
   - Add data class in `data/model/`
   - Define Room entity if persistent

2. **Create Repository** (if needed)
   - Add repository interface and implementation
   - Handle data operations

3. **Create Use Case** (if needed)
   - Add business logic in `domain/usecase/`
   - Keep ViewModels thin

4. **Create ViewModel**
   - Add in appropriate screen package
   - Define UI state data class
   - Expose StateFlow
   - Handle user actions

5. **Create Screen Composable**
   - Add in appropriate screen package
   - Collect state from ViewModel
   - Build UI with Compose
   - Keep composables pure (no business logic)

### Naming Conventions

- **Files**: PascalCase (e.g., `CallScreen.kt`, `ContactRepository.kt`)
- **Classes**: PascalCase (e.g., `CallViewModel`, `Contact`)
- **Functions**: camelCase (e.g., `loadContacts()`, `onCallClick()`)
- **Variables**: camelCase (e.g., `phoneNumber`, `favoriteList`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_FAVORITES`, `DEFAULT_ACTION`)

### Code Style

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable names
- Add KDoc comments for public APIs
- Keep functions small and focused
- Prefer immutability

### Testing Guidelines

#### Unit Tests (ViewModels)
```kotlin
class CallViewModelTest {
    // Test ViewModel state changes
    // Test business logic
    // Mock repositories
}
```

#### UI Tests (Compose)
```kotlin
class CallScreenTest {
    // Test UI rendering
    // Test user interactions
    // Use ComposeTestRule
}
```

#### Repository Tests
```kotlin
class ContactRepositoryTest {
    // Test data operations
    // Mock Room DAO
    // Test error handling
}
```

## 🗺️ Roadmap

### Phase 1: Call Screen Implementation ✅ (In Progress)
- [ ] Numpad layout (0-9, *, #)
- [ ] Phone number display
- [ ] Call button functionality
- [ ] Delete/Clear buttons
- [ ] Recent calls list
- [ ] Call type indicators (incoming/outgoing/missed)
- [ ] Call log permissions

### Phase 2: Favorite System
- [ ] Room database setup
- [ ] Favorite contacts CRUD
- [ ] Custom action assignment
- [ ] Quick action buttons
- [ ] Drag & drop reordering
- [ ] Empty state UI

### Phase 3: Contacts Integration
- [ ] Contacts permission handling
- [ ] Load device contacts
- [ ] Search/filter contacts
- [ ] Contact selection
- [ ] Native contacts app integration
- [ ] Contact details view

### Phase 4: Data Persistence
- [ ] Room database entities
- [ ] DAOs implementation
- [ ] Repository pattern
- [ ] Data migration
- [ ] Backup & restore

### Phase 5: WhatsApp Integration
- [ ] WhatsApp call action
- [ ] WhatsApp message action
- [ ] WhatsApp video call action
- [ ] Deep linking
- [ ] Availability checks

### Phase 6: Additional Features
- [ ] Set as default dialer
- [ ] Call blocking
- [ ] Call recording
- [ ] Speed dial
- [ ] Themes customization
- [ ] Export/Import favorites

## 📋 Permissions

The app requires the following permissions:

| Permission | Purpose | Required |
|-----------|---------|----------|
| `READ_PHONE_STATE` | Access phone state info | Yes |
| `CALL_PHONE` | Make phone calls | Yes |
| `READ_CALL_LOG` | Read call history | Yes |
| `WRITE_CALL_LOG` | Modify call history | No |
| `READ_CONTACTS` | Access device contacts | Yes |
| `WRITE_CONTACTS` | Modify contacts | No |
| `INTERNET` | WhatsApp integration | Yes |

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Contribution Guidelines

- Follow the existing code style
- Add tests for new features
- Update documentation
- Write clear commit messages

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Panggil Development Team**

- GitHub: [@sahyro26](https://github.com/sahyro26)

## 🙏 Acknowledgments

- Android Jetpack team for amazing libraries
- Material Design team for design guidelines
- The open-source community

## 📞 Support

If you encounter any issues or have questions:

1. Check existing [Issues](https://github.com/sahyro26/panggil/issues)
2. Create a new issue with detailed information
3. Provide error logs and screenshots if applicable

---

<div align="center">
  Made with ❤️ using Jetpack Compose
</div>
