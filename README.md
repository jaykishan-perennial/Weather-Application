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

*User registration and login*  
<p align="center">
  <img src="https://github.com/user-attachments/assets/cdf83805-ec79-4580-b974-8efd877623ae" width="250">
  <img src="https://github.com/user-attachments/assets/d96643c0-8d07-4b22-89c5-c2062bf5bdcf" width="250">
</p>

*Home screen with weather and history tabs*  
<p align="center">
  <img src="https://github.com/user-attachments/assets/c2da37be-c92b-4760-83b4-2bc65a3b6a52" width="250">
  <img src="https://github.com/user-attachments/assets/4af651ef-9662-4a0e-9833-7001a8d49b09" width="250">
</p>


---

## Test coverage Screenshots 📸

*LocalAuthRepositoryImpl*  
<p >
  <img src="https://github.com/user-attachments/assets/01e53bfe-4663-421d-8c15-877c1990ccba">
</p>
*LoginViewModel*  
<p >
  <img src="https://github.com/user-attachments/assets/01e53bfe-4663-421d-8c15-877c1990ccba">
</p>
*SignUpViewModel*  
<p >
  <img src="https://github.com/user-attachments/assets/e51e6b6a-ceb0-4edb-8970-d244638aff3b">
</p>
*WeatherRepositoryImpl* 
<p>
  <img src="https://github.com/user-attachments/assets/4fdd0905-cd7c-418e-8854-f1d0363c1bed">
</p>
*WeatherHistoryUseCase* 
<p>
  <img src="https://github.com/user-attachments/assets/90b553cd-3bcf-4386-b05f-b0e02a3baf4e">
</p>

*CurrentWeatherUseCase* 
<p>
  <img src="https://github.com/user-attachments/assets/81339ab0-ea7a-49ad-9aec-c45b6bfd2128">
</p>

*WeatherHistoryViewModel* 
<p>
  <img src="https://github.com/user-attachments/assets/633adf35-1da0-4d36-81e5-0a69a255e815">
</p>

## Security tools used in app 🚀
**RoomDB:** Used 'sqlcipher'
**DataStore:** Used 'security.crypto'

## Installation 🚀

1. Clone this repository:
   ```bash
   git clone https://github.com/jaykishan-perennial/Weather-Application
   cd weather-app
   ```
2. Add your **OpenWeather API Key** in `local.properties`:
   ```properties
   OPEN_WEATHER_API_KEY=your_api_key_here
   SHARED_PREF_KEY_ALIAS=add_your_alias_here
   ```
3. Build and run the project in Android Studio.

## Note

Since I am the only contributor in this repository, I have created and maintaining only single branch named 'main'

---



## How It Works 🔧

1. **Registration & Sign-In:** Users can register and log in to personalize the experience.
2. **Current Weather Tab:** The app fetches the user's location and retrieves weather data using the OpenWeather API.
3. **Weather History Tab:** Previously fetched weather details are saved locally in Room DB and displayed in descending order.

---

## License 📜

This project is licensed under the MIT License. See the `LICENSE` file for more details.

---
