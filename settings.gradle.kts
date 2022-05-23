rootProject.name = "androidx-paging-2-to-3-regressions"

pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
  }
  resolutionStrategy {
    eachPlugin {
      if(requested.id.namespace == "com.android") {
        useModule("com.android.tools.build:gradle:${requested.version}")
      }
    }
  }
}
