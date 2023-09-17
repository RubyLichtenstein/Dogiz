pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Dog Breeds"
include(":app")
include(":feature:breeds")
include(":feature:images")
include(":feature:favorites")
include(":core:data")
include(":domain")
