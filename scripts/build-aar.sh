#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
VERSION="${1:-1.0.0}"
cd "$ROOT"

./gradlew :uvc-tdr:assembleRelease

AAR_SRC="$ROOT/uvc-tdr/build/outputs/aar/uvc-tdr-release.aar"
AAR_DST="$ROOT/releases/uvc-tdr-${VERSION}.aar"

mkdir -p "$ROOT/releases"
cp "$AAR_SRC" "$AAR_DST"

echo "Built: $AAR_DST"
echo ""
echo "Copy to main app:"
echo "  cp \"$AAR_DST\" ../mishipay-android-multiplatform-poc/feature-scanner/libs/uvc-tdr.aar"
