plugins {
  kotlin("android") version "1.6.21"
  id("com.android.application") version "7.2.0"
}

repositories {
  mavenCentral()
  google()
}

android {
  compileSdk = 31
  namespace = "com.veyndan"

  defaultConfig {
    minSdk = 31
  }
}

dependencies {
  implementation("androidx.paging:paging-common:2.1.2")
  implementation("androidx.paging:paging-rxjava2:2.1.2")
  implementation("io.reactivex.rxjava2:rxjava:2.2.21")
  testImplementation("junit:junit:4.13.2")
}
