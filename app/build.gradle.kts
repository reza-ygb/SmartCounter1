plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "ir.reza_ygb.smartcounter" // <-- شناسه جدید
    compileSdk = 36

    // بلوک جدید برای تنظیمات امضای دیجیتال
    signingConfigs {
        create("release") {
            // این قسمت‌ها باید توسط شما با اطلاعات فایل کلیدتان پر شود
            // storeFile = file("path/to/your/keystore.jks")
            // storePassword = "YOUR_STORE_PASSWORD"
            // keyAlias = "YOUR_KEY_ALIAS"
            // keyPassword = "YOUR_KEY_PASSWORD"
        }
    }

    defaultConfig {
        applicationId = "ir.reza_ygb.smartcounter" // <-- شناسه جدید و منحصر به فرد
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0" // نسخه رسمی اول

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true // فعال‌سازی بهینه‌سازی و مبهم‌سازی
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // اتصال تنظیمات امضا به نسخه نهایی
            signingConfig = signingConfigs.getByName("release")
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
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended) // <-- این خط اضافه شد
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}