plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

//kotlin {
//    jvmTarget = "17"
//}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2") // Replace with the latest version
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")
    implementation("javax.inject:javax.inject:1")

}




