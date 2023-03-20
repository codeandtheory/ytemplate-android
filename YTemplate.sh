#!/bin/bash

# Verify bash version. macOS comes with bash 3 preinstalled.
if [[ ${BASH_VERSINFO[0]} -lt 4 ]]
then
  echo "You need at least bash 4 to run this script."
  exit 1
fi

# exit when any command fails
set -e

if [[ $# -lt 1 ]]; then
   echo "Usage: bash YTemplate.sh my.new.package [ApplicationName]" >&1
   exit 2
fi

PACKAGE=$1
APPNAME=$2
SUBDIR=${PACKAGE//.//} # Replaces . with /

# Creating package name directory under java  folder
for n in $(find . -type d \( -path '*/src/androidTest' -or -path '*/src/main' -or -path '*/src/test' \) )
do
  echo "Creating $n/java/$SUBDIR"
  mkdir -p $n/java/$SUBDIR
  echo "Moving files to $n/java/$SUBDIR"
  mv $n/java/ytemplate/android/* $n/java/$SUBDIR || echo "No such file or directory found"
  echo "Removing old $n/java/ytemplate/android/" || echo "File not found"
  rm -rf mv $n/java/ytemplate
done

# Rename package and imports
echo "Renaming packages to $PACKAGE"
find ./ -type f -name "*.kt" -exec sed -i.bak "s/package ytemplate.android/package $PACKAGE/g" {} \;
find ./ -type f -name "*.kt" -exec sed -i.bak "s/import ytemplate.android/import $PACKAGE/g" {} \;

# Gradle files
find ./ -type f -name "*.kts" -exec sed -i.bak "s/ytemplate.android/$PACKAGE/g" {} \;

# Manifest files
find ./ -type f -name "*.xml" -exec sed -i.bak "s/ytemplate.android/$PACKAGE/g" {} \;

# Rename ytemplate.android
echo "Renaming ytemplate.android to $PACKAGE"
find ./ -type f -name "*.kt" -exec sed -i.bak "s/ytemplate.android/$PACKAGE/g" {} \;

#Cleaning Up .bak files
echo "Cleaning up"
find . -name "*.bak" -type f -delete

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
 rm -rf .git/
echo "Done!"
