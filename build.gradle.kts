plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.navigation.safe.args) apply false
// Use alias here
}


buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // เพิ่มเวอร์ชัน AGP ที่รองรับ
        classpath("com.android.tools.build:gradle:8.2.2") // Or another version compatible with your setup.
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7") // Or your desired version
    }
}