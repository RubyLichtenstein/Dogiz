# 🐶✨ Dogiz

### Modern Android Development and Architecture Showcase

Welcome to "Dogiz", a prime representation of the pinnacle in Android best practices. Drawing inspiration from [Now in Android](https://github.com/android/nowinandroid), Dogiz offers a more straightforward and easily digestible example, intentionally crafted without the complexities of larger projects.

Here's what Dogiz emphasizes:
- **Clean Architecture**: Structured, clear, and robust app development.
- **Modern Libraries**: Integrating tools like **Kotlin Coroutines**, **Flow**, and **Jetpack Compose** for efficient app functionality.
- **Rigorous Testing**: Ensuring the app's reliability at every step.
- **Efficient CI/CD**: Seamless integration and deployment processes.

Additionally, Dogiz aligns and complements the principles found in the [Guide to App Architecture](https://developer.android.com/topic/architecture) presented by Android Developers.

For enthusiasts aiming to delve into the nuances of modern Android development, "Dogiz" is your invaluable guide.

--- 

![Diagram](/art/diagrams.png)

---

### Overview

The "Dogiz" app delves into the world of dog breeds, showcasing breed details and images with a sleek and engaging user interface. The structural essence of the project is built upon distinct layers:

- **UI Layer**: Crafted for interactive and visual user engagement.
- **Domain Layer**: The heart of our app, containing business logic and primary use-cases.
- **Data Layer**: Orchestrates both local and remote data sources.

---

### Embracing Clean Architecture in Android:

In the realm of Android, Clean Architecture champions the concept of distinct responsibilities, aligning closely with the SOLID principles:

- **Distinct Roles**: Each layer is designed with a unique purpose, promoting clarity and organization.
- **Layer Autonomy**: While the core layers (e.g., domain) rely purely on Kotlin/Java, external layers (like UI) seamlessly integrate Android-specific elements.
- **Enhanced Testability**: The clear demarcation of layers ensures they can be tested in isolation, fostering reliability.
- **Adaptive Design**: The modular nature ensures the app remains flexible to evolving technologies or shifting requirements.

At its core, Clean Architecture paves the path for creating Android apps that are resilient, maintainable, and primed for evolution.

---

### Functional Programming with Kotlin Flow:

In the "Dogs App" showcase, we embrace the principles of functional programming (FP) to elevate our application design and logic:

- **Pure Functions**: Our app benefits from the predictability and testability of functions without side effects. This ensures consistent behavior as users navigate through the app, exploring various dog breeds.

- **Immutable Data**: By ensuring data remains unchanged, the "Dogs App" minimizes potential bugs, especially when fetching and displaying breed details.

- **Kotlin Flow Integration**: 
- **Composition Over Inheritance**:


By integrating FP principles, especially with Kotlin Flow, the "Dogs App" showcase stands as a testament to modern, functional, and efficient Android development.

---

### The UI Layer: An Exploration

The UI layer of the "Dogiz" app is where the magic of user interaction comes to life, and it's powered by a suite of modern Android tools and libraries:

- **Jetpack Compose**: A modern, fully declarative UI toolkit for Android. In "Dogiz", Jetpack Compose transforms app design into intuitive, dynamic, and cohesive user interfaces. It simplifies and accelerates UI development on Android by using Kotlin programming paradigms.

- **ViewModels as State Holders**: "Dogiz" employs ViewModels to manage UI-related data. By acting as state holders, ViewModels ensure that UI data survives configuration changes, such as screen rotations, ensuring a consistent user experience. Moreover, they play a pivotal role in decoupling data from UI components, promoting a more testable and maintainable structure.

- **Kotlin Flow**: As an integral part of our UI layer, Kotlin Flow assists in handling asynchronous streams of data. It's adept at representing a series of values over time, making it invaluable for operations like fetching breed details or updating the UI based on user interactions.

- **StateFlow**: An evolution of Flow, StateFlow is a state holder observable flow. In "Dogiz", it ensures that UI components always represent the latest state of the app, making UI updates more predictable and efficient. StateFlow holds a value, emits updates to that value, and can be easily integrated with Jetpack Compose for reactive UI updates.

With these tools and libraries in place, the UI layer of "Dogiz" stands as a testament to how modern, efficient, and user-centric Android development can be achieved.

---

### The Domain Layer: Core of the App

In the architectural journey of "Dogiz", the domain layer acts as the central hub, holding the primary business logic and rules of the application. Here's what defines this layer:

- **Pure Kotlin**: At the domain level, the code is written in pure Kotlin, devoid of any Android-specific dependencies. This purity ensures that the core business logic is platform-agnostic, making it more reusable, testable, and independent of external influences.

- **Use Cases (or Interactors)**: Within the domain layer, use cases represent distinct operations or actions the application can perform. For "Dogiz", a use case might involve fetching a specific breed's details or updating a user's favorites list. These use cases encapsulate individual business rules and serve as a bridge between the data sources and the presentation layer.

- **Unit Testing**: Given its pure nature, the domain layer is an ideal candidate for unit testing. Without the complications of UI or data source intricacies, testing the domain layer ensures that the business logic and use cases work as intended. In "Dogiz", unit tests within the domain layer validate that each use case behaves correctly, providing a safety net against potential regressions or bugs.

- **Decoupling**: The domain layer, by design, is kept separate from other layers like UI or data. This decoupling ensures that changes in one layer don't ripple uncontrollably through the entire app. For instance, if a UI library in "Dogiz" undergoes changes, the domain layer remains unaffected, ensuring stability and consistency.

In essence, the domain layer is where the foundational logic of "Dogiz" resides. By emphasizing purity, focused use cases, rigorous testing, and clear separation, it ensures that the app's core remains robust, adaptable, and true to its intended purpose.

---

### The Data Layer: Managing and Channeling Data

In the architectural blueprint of "Dogiz", the data layer stands as the gatekeeper, ensuring seamless data management, storage, and retrieval. Let's unpack its components:

- **Repository Pattern**: At the heart of the data layer is the repository pattern. This design pattern abstracts the origin of the data, be it from a network source, cache, or local database. The repository serves as a clean API for data access to the rest of the application. In "Dogiz", repositories ensure that the correct data source is queried, handle data transformations if needed, and provide a consistent data view to the domain and UI layers.

- **Ktor for Network Operations**: Ktor is a modern asynchronous framework used for crafting connected applications. Within the "Dogiz" data layer, Ktor handles network operations, facilitating API calls to fetch dog breed information or any other remote data. Its asynchronous nature, combined with Kotlin coroutines, ensures non-blocking operations, keeping the app responsive.

- **Room for Local Persistence**: Room is a persistence library that provides an abstraction layer over SQLite, making database operations more intuitive and efficient. In "Dogiz", Room manages local data storage, offering capabilities like caching breed information for offline access. With its annotations and compile-time checks, Room reduces the boilerplate and potential for errors, ensuring a smooth database experience.

- **Synchronizing Data Sources**: The synergy between remote (Ktor) and local (Room) data sources is vital. The repository pattern in "Dogiz" takes the lead here, deciding when to fetch fresh data from the network or when to serve cached data from Room. This orchestration ensures data consistency, optimizes performance, and enhances user experience, especially in scenarios with limited or no connectivity.

In summary, the data layer in "Dogiz" operates as the nerve center for data operations. By leveraging patterns like the repository and tools like Ktor and Room, it ensures that data is stored, retrieved, and channeled effectively and efficiently throughout the app.

---

### Hilt: Powering Dependency Injection in "Dogiz"

In the "Dogiz" app, which embodies the principles of Clean Architecture, managing dependencies effectively and cleanly is paramount. This is where **Hilt** shines.

- **Simplifying Dependency Injection**: While Clean Architecture emphasizes a clear separation of concerns, this often leads to components that require various dependencies to function. Hilt streamlines this process by automating dependency injection, ensuring that each component gets what it needs without excessive boilerplate.

- **Modular and Scalable**: Consistent with Clean Architecture's modular approach, Hilt promotes a modular codebase. As "Dogiz" grows and evolves, adding or modifying dependencies becomes straightforward, allowing the app to scale without compromising on code quality.

- **Scoped Components**: Hilt provides scoped components that align perfectly with Android's architecture components, like ViewModels, ensuring that dependencies have lifetimes that make sense within the Android lifecycle. This leads to more efficient memory usage and minimizes potential leaks.

- **Inter-layer Communication**: In the layered structure of "Dogiz", components from different layers, like the UI, domain, and data layers, often need to communicate. Hilt ensures that these layers can seamlessly obtain and share dependencies without tight coupling, preserving the integrity of Clean Architecture.

In essence, Hilt not only complements but enhances the Clean Architecture principles employed in "Dogiz", ensuring a cohesive, maintainable, and efficient dependency management system.


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