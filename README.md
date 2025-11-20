# WeatherTracker

WeatherTracker is a sample Android application developed as a technical exercise for an interview. The app allows users to view the weather forecast for the next 7 days and see detailed information for a selected day.

The app is built with **Jetpack Compose**, follows **Clean Architecture**, and uses a **multi-module** structure.

---

## 🏗 Architecture and Module Structure

The app is structured in modules to improve maintainability and scalability:

### Core Module
Provides common functionality across the app:
- **core:domain** → Business entities, constants, utilities.
- **core:data** → Shared Room database, providers and some utilities.
- **core:presentation** → Reusable UI components, `SnackbarController`, and presentation utilities.
- **core:di** → Koin modules for dependency injection.

### Feature Module: ForecastDaily
Encapsulates all functionality related to the daily forecast:
- **feature:forecast_daily:domain** → Feature-specific use cases (`GetDayForecastListUseCase`, `GetDayForecastDetailUseCase`) and entities.
- **feature:forecast_daily:data** → Repositories, API sources, and data mapping.
- **feature:forecast_daily:presentation** → ViewModels, UI states, and Jetpack Compose screens.
- **feature:forecast_daily:di** → Koin module for dependency injection for this feature.

---

## 🖥 Screens

### 7-Day Forecast List
Shows a brief description of the forecast for the day

### Day Detail Screen
Shows more detailed information about a specific day

---

## ⚡ Technical Decisions

### MVI (Model-View-Intent)
- Each screen has a single source of truth represented with **StateFlow**.
- User intents are handled in a predictable way.
- Makes **unit testing easier** as the data flow is deterministic.
- MVI was chosen over MVVM to have **clear state and event tracking**, especially for multi-step interactions. It fits perfectly with Compose's philosophy.

### Koin
- Chosen for its simplicity and seamless integration with **coroutines** and **Compose**.
- Supports modularization by feature.
- Provides the option to migrate the project to Kotlin Multiplatform Project.

### Ktor
- Kotlin-first: Kotlin idiomatic API, uses coroutines natively, and supports suspend functions.
- Easy integration with serialisation: Compatible with kotlinx.serialisation and other serialisers
- Very flexible and full control over requests/responses
- Provides the option to migrate the project to Kotlin Multiplatform Project.

---

## 🧩 Testing

- **Unit tests** use **Turbine**, **Truth**, and **Mockito**.
- ViewModels are tested for state emissions and error handling.
- Modular structure allows **isolated testing per feature**.
- Instrumented tests can be added to test persistence with Room or UI behavior in Compose.

---

## 📈 Scalability

The app is designed to scale:

- **Independent modules:** new features can be added without affecting existing modules.
- **Clean Architecture:** allows swapping APIs, repositories, or caching strategies without breaking presentation logic.
- **Flow and MVI:** ensures predictable state handling, easing future additions of screens or complex flows.
- **Room persistence:** provides offline support and can be extended for caching large datasets.

---

## 📈 Future Improvements

**1.    Integration of Location Search Screen**

-	Add a new screen to search for locations and select them.
-	This will allow users to quickly switch between cities for weather forecasts.

**2.    Favourite Locations**

-	Create a Room entity to store favourite locations.
-	Users will be able to save, view, and quickly access their preferred cities.

**3.	GPS Integration**

-	Use device GPS to automatically detect and select the current location.
-	Enhances user experience by showing the local weather without manual input.

**3.	Preferences**

-	Give the user the option to set units and language preferences.

**4.	Additional Enhancements**

-	Hourly forecast display.
-	Push notifications for severe weather alerts.
-	Background refresh of weather data using WorkManager.


---

## 🏗 Installation Instructions

The project uses the Google Secrets library to hide the API key. To run the project, it is necessary to create a file in the project root called secrets.properties. This file will include the following line: **ONE_CALL_3_API_KEY=yourAPIkey**

---

## 📝 Conclusion

WeatherTracker is a modular and scalable application built with **Clean Architecture** and **Jetpack Compose**, using **MVI** for predictable state management. The combination of Room for local persistence and Ktor for network access ensures reliable data handling and offline capabilities. Its modularity and architecture make it easy to expand with new features while maintaining maintainability and testability.