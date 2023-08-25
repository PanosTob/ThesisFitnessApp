@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("kotlin-android")
    kotlin("kapt")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
}

android {
    val versionMajor = 0
    val versionMinor = 0
    val versionPatch = 0
    val versionClassifier = 1
    val versionClassifierName = "beta"

    fun generateVersionCode(): Int {
        return versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionClassifier
    }

    fun generateVersionName(): String {
        var versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
        if (versionClassifier > 0 && versionClassifierName.isNotEmpty()) {
            versionName += "-${versionClassifierName}${versionClassifier}"
        }
        return versionName
    }

    namespace = "gr.dipae.thesisfitnessapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "gr.dipae.thesisfitnessapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("debug")
    }

    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }

    lint {
        abortOnError = false
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("keystore/debug.keystore")
            keyAlias = "androiddebugkey"
            storePassword = "android"
            keyPassword = "android"
        }
        /*create("release") {
            storeFile = file("keystore/thesis_fitness.jks")
            keyAlias = "ThesisFitnessApp"
            keyPassword = "f5FW2HhkPGcgkt7"
            storePassword = "f5FW2HhkPGcgkt7"
        }*/
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            signingConfig = null
        }
        release {
            isMinifyEnabled = false
            isDebuggable = false
//            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions.add("env")

    productFlavors {
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "Thesis Fitness DEV")
            buildConfigField("String", "GOOGLE_STORAGE_FIREBASE", "\"https://firebasestorage.googleapis.com/v0/b/thesis-fitness-app-8c2a1.appspot.com/o/\"")
            buildConfigField("String", "GOOGLE_STORAGE_FIREBASE_QUERY_PARAMS", "\"?alt=media&token=ccee9573-4e8e-4b78-9d0d-19bdb870556c\"")
            buildConfigField("String", "FOOD_API_URL", "\"https://api.nal.usda.gov/fdc/\"")
            signingConfig = signingConfigs.getByName("debug")
        }

        create("prod") {
            dimension = "env"
            resValue("string", "app_name", "Thesis Fitness")
            buildConfigField("String", "GOOGLE_STORAGE_FIREBASE", "\"https://firebasestorage.googleapis.com/v0/b/thesis-fitness-app-8c2a1.appspot.com/o/\"")
            buildConfigField("String", "GOOGLE_STORAGE_FIREBASE_QUERY_PARAMS", "\"?alt=media&token=ccee9573-4e8e-4b78-9d0d-19bdb870556c\"")
            buildConfigField("String", "FOOD_API_URL", "\"https://api.nal.usda.gov/fdc/\"")
//            signingConfig = signingConfigs.getByName("release")
        }
    }

    ndkVersion = "21.0.6113669"

    applicationVariants.all {
        outputs.all {
            val output = (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl)
            output.outputFileName = "${flavorName}_${versionName}_Thesis Fitness_${buildType.name}.apk"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    kapt {
        correctErrorTypes = true
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    //CORE LIBS
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("org.jetbrains.kotlin:kotlin-bom:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.fragment:fragment-ktx:1.6.0")

    val lifecycleVersion by extra { "2.6.1" }
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    implementation("androidx.navigation:navigation-fragment-ktx:${rootProject.extra.get("navVersion")}")
    implementation("androidx.navigation:navigation-ui-ktx:${rootProject.extra.get("navVersion")}")
    implementation("androidx.navigation:navigation-compose:${rootProject.extra.get("navVersion")}")

    //COMPOSE LIBS
    implementation("androidx.compose:compose-bom:2023.06.01")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    val accompanistVersion by extra { "0.30.1" }
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-flowlayout:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-permissions:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-insets:$accompanistVersion")

    //Video Player
//    implementation("com.google.android.exoplayer:exoplayer:2.18.7")

    //Google Libs
    implementation(platform("com.google.firebase:firebase-bom:32.1.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.android.gms:play-services-auth:20.6.0")

    //Room Libs
    val roomVersion by extra { "2.5.2" }
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.databinding:adapters:3.2.0-alpha11")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")

    //Remote calls Libs
    val okHttp by extra { "4.11.0" }
    implementation("com.squareup.okhttp3:okhttp:$okHttp")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttp")

    val retrofit by extra { "2.9.0" }
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit")

    val moshi by extra { "1.15.0" }
    implementation("com.squareup.moshi:moshi-kotlin:$moshi")
    implementation("com.squareup.moshi:moshi-adapters:$moshi")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi")

    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")


    //Hilt Libs | Dependency injection
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:${rootProject.extra.get("hiltVersion")}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra.get("hiltVersion")}")

    //Glide libs | async rendering
    val glide by extra { "4.15.1" }
    implementation("com.github.bumptech.glide:glide:$glide")
    kapt("com.github.bumptech.glide:compiler:$glide")

    //Coil async image loader
    val coil by extra { "2.1.0" }
    implementation("io.coil-kt:coil-compose:$coil")

    // Google Maps in Compose
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.maps.android:maps-compose:2.12.0")
    implementation("com.google.maps.android:maps-compose-utils:2.12.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.maps.android:android-maps-utils:2.2.3")
    implementation("com.google.maps.android:maps-utils-ktx:3.3.0")

    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //Lottie Animation
    implementation("com.airbnb.android:lottie-compose:6.1.0")

    //Line Charts
    val chartsVersion by extra { "1.9.2" }
    implementation("com.patrykandpatrick.vico:core:$chartsVersion")
    implementation("com.patrykandpatrick.vico:compose:$chartsVersion")
    implementation("com.patrykandpatrick.vico:compose-m3:$chartsVersion")

    //Pie Chart
    implementation("co.yml:ycharts:2.1.0")

    //Tests libs
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose:compose-bom:2023.06.01")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
}