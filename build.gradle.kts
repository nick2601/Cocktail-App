// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Top-level build file (build.gradle.kts)
plugins {
    alias(libs.plugins.android.application) version "8.3.2" apply false
    alias(libs.plugins.kotlin.android) version "1.9.22" apply false
    alias(libs.plugins.dagger.hilt) version "2.51" apply false
}
