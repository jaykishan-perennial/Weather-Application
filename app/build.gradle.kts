import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt.android.main)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.openweatherapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.openweatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val localFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(localFile.inputStream())

        val apiKey = properties.getProperty("OPEN_WEATHER_API_KEY") ?: ""
        buildConfigField(
            type = "String",
            name = "OPEN_WEATHER_API_KEY",
            value = apiKey
        )

        val aliasKey = properties.getProperty("SHARED_PREF_KEY_ALIAS") ?: ""
        buildConfigField(
            type = "String",
            name = "SHARED_PREF_KEY_ALIAS",
            value = aliasKey
        )


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //dagger hilt setup
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //room database setup
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //okhttp and interceptor setup
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //retrofit setup
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //joda date-time
    implementation(libs.joda.time)

    //fragment navigation setup
    implementation(libs.androidx.navigation.fragment.ktx)

    implementation(libs.glide)

    //data Store
    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.mockito.core)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.truth)
    testImplementation(libs.androidx.core.testing)


    // Security
    implementation (libs.security.crypto)

    // Sqlcipher
    implementation (libs.sqlcipher)
    implementation (libs.sqlite)
}