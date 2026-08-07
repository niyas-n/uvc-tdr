# uvc-tdr

Android library (AAR) for Magellan TDR UVC capture on Datalogic 9900i kiosks.

Based on [chenyeju295/AndroidUSBCamera](https://github.com/chenyeju295/AndroidUSBCamera) `libuvc` 3.3.6 with native patches for **NV12** (and H264 descriptor) negotiation on the Magellan top-down reader webcam gadget.

The AAR bundles everything the consumer app needs:

- `com.jiangdg.usb.USBMonitor`
- `com.jiangdg.uvc.UVCCamera`
- JNI: `libUVCCamera.so`, `libuvc.so`, `libusb100.so`, `libjpeg-turbo1500.so` (arm64-v8a + armeabi-v7a)

No JitPack or Maven publish required — copy the built AAR into your app.

## Requirements

- JDK 17+
- Android SDK (compileSdk 36)
- Android NDK (via Android Studio SDK Manager)

## Build

```bash
./gradlew :uvc-tdr:assembleRelease
```

Output:

```
uvc-tdr/build/outputs/aar/uvc-tdr-release.aar
```

Or use the helper script:

```bash
./scripts/build-aar.sh
```

This copies the release AAR to `releases/uvc-tdr-<version>.aar`.

## Use in MishiPay app

Copy the AAR into the main project:

```bash
cp releases/uvc-tdr-1.0.0.aar \
  ../mishipay-android-multiplatform-poc/feature-scanner/libs/uvc-tdr.aar
```

Gradle dependency (already wired in `feature-scanner`):

```kotlin
implementation(files("libs/uvc-tdr.aar"))
```

**Do not** also depend on `libausbc` or Maven `libuvc` — the AAR is self-contained.

## Patched files

Native NV12/H264 format negotiation (vs stock libuvc):

- `third-party/AndroidUSBCamera/libuvc/src/main/jni/libuvc/src/stream.c`
- `third-party/AndroidUSBCamera/libuvc/src/main/jni/libuvc/include/libuvc/libuvc.h`
- `third-party/AndroidUSBCamera/libuvc/src/main/jni/UVCCamera/UVCPreview.cpp`
- `third-party/AndroidUSBCamera/libuvc/src/main/java/com/jiangdg/uvc/UVCCamera.java`

## Versioning

Tag releases in git (`v1.0.0`, `v1.0.1`) and note the AAR version in the main app commit message when updating.

## License

See [LICENSE](LICENSE) (upstream AndroidUSBCamera / libuvc terms apply).
