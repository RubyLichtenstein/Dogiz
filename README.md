# Dog Breeds - Modern Android Development And Architecture Showcase

![Diagram](/art/diagrams.png)

---
# 🐶✨ Dogiz

Modern Android Development Showcase

Welcome to the "Dogiz", a premier demonstration of current Android best practices. While drawing inspiration from [Now in Android](https://github.com/android/nowinandroid), this project is intentionally streamlined, aiming to provide a more straightforward and digestible example.

Our focus encompasses:
- **Clean Architecture** for a structured development approach.
- Utilization of **Modern Libraries** such as **Kotlin Coroutines**, **Flow**, and **Jetpack Compose**.
- Rigorous **Testing** to ensure app reliability.
- Efficient **CI/CD** processes for smooth integrations and deployments.

Furthermore, this project serves as a practical complement to the [Guide to App Architecture](https://developer.android.com/topic/architecture) offered by Android Developers.

For those looking to grasp the essence of modern Android development without the intricacies of larger projects, the "Dogs App" stands as an invaluable resource.

---
### Overview

This project simplifies and presents some of the core ideas from [Now in Android](https://github.com/android/nowinandroid),

The app centers around dog breeds, offering information and images through a minimalistic yet captivating UI. At its core, the project exemplifies a layered approach:

- **UI Layer**: Provides the visual interactions with the user.
- **Domain Layer**: Holds our business logic and use-cases.
- **Data Layer**: Manages data sources, both local and remote.

---

### Clean Architecture in Android:

Clean Architecture, as implemented in Android, prioritizes the separation of concerns by defining distinct layers and abiding by the SOLID principles:

- **Separation of Concerns**: Each layer has a specific responsibility, ensuring clarity and modularity.
- **Layer Independence**: Central layers (like the domain layer) are platform-agnostic, using only pure Kotlin/Java, while outer layers (like UI or frameworks) integrate Android-specific code.
- **Testability**: Facilitates unit and integration testing as each layer can be isolated and tested independently.
- **Scalability and Maintainability**: By decoupling components, the architecture supports easier maintenance and scalability, allowing for adaptability to changing technologies or requirements.

In essence, Clean Architecture sets the foundation for a robust, sustainable, and evolvable Android app structure.

---

### Functional Programming with Kotlin Flow:

In the "Dogs App" showcase, we embrace the principles of functional programming (FP) to elevate our application design and logic:

- **Pure Functions**: Our app benefits from the predictability and testability of functions without side effects. This ensures consistent behavior as users navigate through the app, exploring various dog breeds.

- **Immutable Data**: By ensuring data remains unchanged, the "Dogs App" minimizes potential bugs, especially when fetching and displaying breed details.

- **Kotlin Flow Integration**: 
- **Composition Over Inheritance**:


By integrating FP principles, especially with Kotlin Flow, the "Dogs App" showcase stands as a testament to modern, functional, and efficient Android development.

---

Certainly! Here's a brief overview concerning UX, especially in the context of handling no connection, data persistence, and presenting user-friendly errors, formatted in markdown:

---

### UX Considerations: Handling Connectivity, Persistence, and Error Feedback

In a world driven by digital experiences, ensuring a seamless user experience (UX) is paramount. Within the "Dogs App" showcase, we've deeply considered this and incorporated the following UX-focused measures:

- **No Connection Handling**: Recognizing that users might not always have a stable internet connection, the app is designed to gracefully handle such scenarios. Instead of a blank screen or unexpected crashes, users are presented with friendly notices or cached data from previous sessions.

- **Data Persistence**: The importance of data persistence cannot be overstated. Whether it's saving a user's preferences or caching breed information for offline use, our app ensures that essential data remains accessible, even without an active connection.

- **Meaningful Error Feedback**: Errors are inevitable, but what sets an exceptional app apart is how these errors are communicated. We've prioritized providing users with clear, understandable, and actionable error messages. Instead of technical jargon, users receive guidance on what went wrong and potential steps to rectify it.

By integrating these UX strategies, the "Dogs App" ensures that users have a consistent, intuitive, and user-centric experience, even under less-than-ideal circumstances.

---

### Key Features

- **Offline Capability**: The app caches breed data and images for offline use.
- **Error Handling**: Provides error indications to users for a better experience.
- **Repository Pattern**: Applied to streamline data access and improve maintainability.
- **Material 3**: Utilized for an enhanced, modern user interface.

### Third-Party Libraries and Technologies

- **Hilt**: Dependency Injection
- **Ktor Client**: API calls
- **Jetpack Compose and Navigation-Compose**: UI and Navigation
- **Material 3**: Modern UI Components
- **Kotlin Serialization**: Data Parsing
- **Coil-Compose**: Image Loading
- **DataStore Core**: Local Data Storage
- **Kotlin Flow**: Asynchronous Data Handling

---

### Installation

To run the Dog Breeds app, ensure you have the latest version of Android Studio. Clone the repository, build the project, and run it on your emulator or actual device.

---

### Feedback and Contribution

Your feedback is invaluable! Please raise an issue if you find one, or even better, open a pull request with improvements.