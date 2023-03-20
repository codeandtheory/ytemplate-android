YTemplate
==================
YTemplate aims to build your initial setup for your Android project quickly. The project provides you organised structures following MVVM architecture and also provides dependencies to support major functionalities which an Android project needs

The project includes a shell script file `YTemplate.sh` which renames and restructures the Android project based on the package name, model name and application name the Android project needs.

### How to run the YTemplate script
- The script file `YTemplate.sh` takes two inputs
  - `<package_name>` is your app ID and should be in lower case.
  - `<application_name>` Optional input
  - `./YTemplate.sh <package_name> <application_name>`

Note: To run the shell script on Windows, one can run bash on ubuntu in Windows(starting from Windows 10).
Type ` bash YTemplate.sh <package_name> <data_base_entity> <application_name>`. It will execute the script


## Features
* Compose UI
* HILT dependency for dependency injection
* Jetpack navigation
* Version catalog support for handling dependencies
* MVVM architecture
* Room database support for Local database
* Ktor for remote database connection
* Jacoco support for test report generation

### Architecture
YTemplate follows MVVM architecture. Different modules support different layers of MVVM architecture.

##### Multi-Module
* `app` : Entry point for the app. Provides basic structures such as Application classes, Navigation components.
* `feature` : Module where different features can be added with the respective fragment/activity classes and view-models.
* `core/domain`(optional) : Module to add the domain/use case layers for different features which can be shared across multiple features.
* `core/data` : Module to handle the data layer of the app. This layer supports both local database and remote database.
* `build-logic` : Module where the dependencies are added for the Android project and contains build configuration logics for gradle tasks.

![](multiModuleArchitecture.png)

##### Single-Module
* `app` : Module to add various features to the app and providing basic structures such as Application classes, Navigation components. This is a combined version of app and feature module of Multi-module architecture.
* `domain`(optional) : Package structure which can be added under app module which would contain domain/use case files. This is similar to core/domain in multi-module
* `data` : Package structure to handle the data layer placed under the app module. This is similar to core/data in multi-module
* `buildSrc` : Module containing complex build logic encapsulated as custom task.

![](singleModuleArchitecture.png)

### How to generate test report
- Generating jacoco test report
  - Gradle command `clean build createMergedJacocoReport`
    - From android studio
      - Open gradle menu bar from android studio right side panel
      - Click on the gradle icon and
      - In command popup window type `clean build createMergedJacocoReport` and press enter
      - Wait for the execution completion,
      - After successful execution each module level execution report will be stored in 'module\build\reports\jacoco\html\index.html'.

### How to generate dokka report
- Gradle command single module `clean build dokkaHtml` for multi module `clean build dokkaHtmlMultiModule`
  - From android studio
  - Open gradle menu bar from android studio right side panel
  - Click on the gradle icon and
  - In command popup window type `dokkaHtml` for multi module `dokkaHtmlMultiModule`

### How to check KTLint
- Gradle command for checking lint error `ktlintCheck`
- Gradle command for formatting code `ktlintFormat`
