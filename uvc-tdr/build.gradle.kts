plugins {
    alias(libs.plugins.android.library)
}

val libuvcRoot = rootProject.file("third-party/AndroidUSBCamera/libuvc")

android {
    namespace = "com.jiangdg.uvccamera"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a")
        }
        externalNativeBuild {
            ndkBuild {
                arguments += listOf("-j${Runtime.getRuntime().availableProcessors()}")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("$libuvcRoot/src/main/AndroidManifest.xml")
            java.setSrcDirs(listOf("$libuvcRoot/src/main/java"))
            jni.setSrcDirs(listOf("$libuvcRoot/src/main/jni"))
        }
    }

    externalNativeBuild {
        ndkBuild {
            path = file("$libuvcRoot/src/main/jni/Android.mk")
        }
    }

    packaging {
        jniLibs {
            pickFirsts +=
                listOf(
                    "**/libUVCCamera.so",
                    "**/libuvc.so",
                    "**/libusb100.so",
                    "**/libjpeg-turbo1500.so",
                )
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation("com.elvishew:xlog:1.11.0")
}
