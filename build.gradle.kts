buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
    extra.apply {
        set("room_version", "2.6.0")
    }
}

plugins {
    id("com.android.application") version "8.5.1" apply false
    id("com.android.library") version "8.5.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
