---

# Weather App 🌦️

A modern Weather app built using the OpenWeather API, Android, and Kotlin. This app showcases clean architecture principles combined with the MVVM pattern for a scalable and maintainable codebase. It allows users to register, sign in, and view current weather conditions as well as weather history.

---

## Features ✨

- **User Authentication:**  
  Registration and Sign-In functionality for personalized user experiences.

- **Current Weather:**  
  Fetches the user's current location and displays up-to-date weather information.

- **Weather History:**  
  Displays the history of weather searches in descending order, saved locally.

- **Offline Support:**  
  Weather history is stored in a Room database for offline access.

---

## Tech Stack 🛠️

- **Programming Language:** [Kotlin](https://kotlinlang.org/)
- **Architecture:** Clean Architecture + MVVM
- **Dependency Injection:** [Hilt](https://dagger.dev/hilt/)
- **Networking:** [Retrofit](https://square.github.io/retrofit/)
- **Database:** [Room](https://developer.android.com/training/data-storage/room)
- **API:** [OpenWeather API](https://openweathermap.org/api)

---

## Project Structure 📂

The project adheres to Clean Architecture principles with distinct layers for data, domain, and presentation:

```
WeatherApp/
├── app/
│   ├── main/
│       ├── java/
│           ├── data/                 # Data layer
│           │   ├── di/               # Dependency injection modules
│           │   ├── repositoryimpl/   # Repository implementations
│           │   └── source/           # Data sources
│           │       ├── local/        # Local data sources (Room, SharedPrefs, etc.)
│           │       └── remote/       # Remote data sources (API, Retrofit, etc.)
│           ├── domain/               # Domain layer
│           │   ├── model/            # Business models
│           │   ├── repository/       # Repository interfaces
│           │   └── usecases/         # Use case definitions
│           ├── ui/                   # Presentation layer
│           │   ├── auth/             # Authentication screens
│           │   └── home/             # Home screen (current weather and history)
│           └── utils/                # Utility classes and extensions
└── build.gradle                      # Project configuration
```

---

## Screenshots 📸

*(Add screenshots of your app for better visualization)*  
![Current Weather Tab](#)  
![Weather History Tab](#)

---

## Installation 🚀

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/weather-app.git
   cd weather-app
   ```
2. Add your **OpenWeather API Key** in `local.properties`:
   ```properties
   OPEN_WEATHER_API_KEY=your_api_key_here
   ```
3. Build and run the project in Android Studio.

---

## How It Works 🔧

1. **Registration & Sign-In:** Users can register and log in to personalize the experience.
2. **Current Weather Tab:** The app fetches the user's location and retrieves weather data using the OpenWeather API.
3. **Weather History Tab:** Previously fetched weather details are saved locally in Room DB and displayed in descending order.

---

## License 📜

This project is licensed under the MIT License. See the `LICENSE` file for more details.

---