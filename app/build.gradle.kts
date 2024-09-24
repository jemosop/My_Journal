plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.myjournal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myjournal"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.support.annotations)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

        val room_version = "2.6.1"

        implementation ("androidx.room:room-runtime:2.6.1")
        annotationProcessor ("androidx.room:room-compiler:$room_version")

        // To use Kotlin annotation processing tool (kapt)
       // kapt ("androidx.room:room-compiler:$room_version")
        // To use Kotlin Symbol Processing (KSP)
       // ksp ("androidx.room:room-compiler:$room_version")

        // optional - RxJava2 support for Room
        implementation ("androidx.room:room-rxjava2:$room_version")

        // optional - RxJava3 support for Room
        implementation ("androidx.room:room-rxjava3:$room_version")

        // optional - Guava support for Room, including Optional and ListenableFuture
        implementation ("androidx.room:room-guava:$room_version")

        // optional - Test helpers
        testImplementation ("androidx.room:room-testing:$room_version")

        // optional - Paging 3 Integration
        implementation ("androidx.room:room-paging:$room_version")

}