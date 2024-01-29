
plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/")
    }
}
repositories {
    mavenCentral()
}