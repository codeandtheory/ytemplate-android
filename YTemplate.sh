#!/bin/bash
#
# Copyright (C) 2022 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Verify bash version. macOS comes with bash 3 preinstalled.
if [[ ${BASH_VERSINFO[0]} -lt 4 ]]
then
  echo "You need at least bash 4 to run this script."
  exit 1
fi

# exit when any command fails
set -e

if [[ $# -lt 2 ]]; then
   echo "Usage: bash customizer.sh my.new.package MyNewDataModel [ApplicationName]" >&2
   exit 2
fi

PACKAGE=$1
DATAMODEL=$2
APPNAME=$3
SUBDIR=${PACKAGE//.//} # Replaces . with /

for n in $(find . -type d \( -path '*/app/src/androidTest' -or -path '*/app/src/main' -or -path '*/app/src/test' \) )
do
  echo "Creating $n/java/$SUBDIR"
  mkdir -p $n/java/$SUBDIR
  echo "Moving files to $n/java/$SUBDIR"
  mv $n/java/ytemplate/android/* $n/java/$SUBDIR
  echo "Removing old $n/java/ytemplate/android/"
  rm -rf mv $n/java/ytemplate
done

# Jacoco Dependancies
echo "Creating buildSrc/src/main/java/$SUBDIR/build"
mkdir -p buildSrc/src/main/java/$SUBDIR/build
echo "Moving files to buildSrc/src/main/java/$SUBDIR/build"
mv buildSrc/src/main/java/ytemplate/android/build/* buildSrc/src/main/java/$SUBDIR/build
echo "Removing old buildSrc/src/main/java/ytemplate/android"
rm -rf mv buildSrc/src/main/java/ytemplate

# Rename package and imports
echo "Renaming packages to $PACKAGE"
find ./ -type f -name "*.kt" -exec sed -i.bak "s/package ytemplate.android/package $PACKAGE/g" {} \;
find ./ -type f -name "*.kt" -exec sed -i.bak "s/import ytemplate.android/import $PACKAGE/g" {} \;

# Gradle files
find ./ -type f -name "*.kts" -exec sed -i.bak "s/ytemplate.android/$PACKAGE/g" {} \;

# Manifest files
find ./ -type f -name "*.xml" -exec sed -i.bak "s/ytemplate.android/$PACKAGE/g" {} \;

# Rename model
echo "Renaming model to $DATAMODEL"
find ./ -type f -name "*.kt" -exec sed -i.bak "s/MyModel/${DATAMODEL^}/g" {} \; # First upper case
find ./ -type f -name "*.kt" -exec sed -i.bak "s/myModel/${DATAMODEL,}/g" {} \; # First lower case
find ./ -type f -name "*.kt*" -exec sed -i.bak "s/mymodel/${DATAMODEL,,}/g" {} \; # All lowercase

echo "Cleaning up"
find . -name "*.bak" -type f -delete

# Rename files
echo "Renaming files to $DATAMODEL"
find ./ -name "*MyModel*.kt" | sed "p;s/MyModel/${DATAMODEL^}/" | tr '\n' '\0' | xargs -0 -n 2 mv
# module names
if [[ -n $(find ./ -name "*-mymodel") ]]
then
  echo "Renaming modules to $DATAMODEL"
  find ./ -name "*-mymodel" -type d  | sed "p;s/mymodel/${DATAMODEL,,}/" |  tr '\n' '\0' | xargs -0 -n 2 mv
fi
# directories
echo "Renaming directories to $DATAMODEL"
find ./ -name "mymodel" -type d  | sed "p;s/mymodel/${DATAMODEL,,}/" |  tr '\n' '\0' | xargs -0 -n 2 mv

# Renaming YTemplateTheme
echo "Renaming YTemplateTheme to $APPNAME"
find ./ -type f -name "*.kt" -exec sed -i.bak "s/YTemplateTheme/${APPNAME}Theme/g" {} \;

# Rename app
if [[ $APPNAME ]]
then
    echo "Renaming app to $APPNAME"
    find ./ -type f \( -name "YTemplate.kt" -or -name "settings.gradle.kts" -or -name "*.xml" \) -exec sed -i.bak "s/YTemplate/$APPNAME/g" {} \;
    find ./ -name "YTemplate.kt" | sed "p;s/YTemplate/$APPNAME/" | tr '\n' '\0' | xargs -0 -n 2 mv
    find . -name "*.bak" -type f -delete
fi

# Remove additional files
echo "Removing additional files"
rm -rf .google/
# rm -rf .github/
# rm -rf CONTRIBUTING.md LICENSE README.md customizer.sh
# rm -rf .git/
echo "Done!"
