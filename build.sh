#!/bin/bash
set -e

docker run --rm \
  -v "$(pwd)":/project \
  -w /project \
  ghcr.io/graalvm/graalvm-community:25 \
  ./gradlew nativeCompile
