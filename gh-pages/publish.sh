#!/usr/bin/env bash
# Copyright (c) 2025 KotlinCrypto
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
set -e

readonly DIR_SCRIPT="$( cd "$( dirname "$0" )" >/dev/null && pwd )"
readonly REPO_NAME="hash"

trap 'rm -rf "$DIR_SCRIPT/$REPO_NAME"' EXIT

cd "$DIR_SCRIPT"
git clone -b gh-pages --single-branch https://github.com/KotlinCrypto/$REPO_NAME.git
rm -rf "$DIR_SCRIPT/$REPO_NAME/"*
echo "$REPO_NAME.kotlincrypto.org" > "$DIR_SCRIPT/$REPO_NAME/CNAME"

cd ..
./gradlew clean -DKMP_TARGETS_ALL
./gradlew dokkaGenerate --no-build-cache -DKMP_TARGETS_ALL
cp -aR build/dokka/html/* gh-pages/$REPO_NAME

cd "$DIR_SCRIPT/$REPO_NAME"
PACKAGE_LIST="$(sed "s|module:|module:library/|g" "package-list")"
echo "$PACKAGE_LIST" > package-list

git add --all
git commit -S --message "Update dokka docs"
git push
