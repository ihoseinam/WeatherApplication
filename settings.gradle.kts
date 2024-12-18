pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven{url = uri("https://maven.myket.ir")}
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven{url = uri("https://maven.myket.ir")}
        google()
        mavenCentral()
    }
}

rootProject.name = "WeatherApplication"
include(":app")
 