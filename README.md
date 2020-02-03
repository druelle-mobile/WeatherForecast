# Weather Forecast

This Android application is my personal view of an exercise realized for **[Tabesto]**(https://www.tabesto.com/).

See the following statements.

## Statements

### Objectives

Develop a native application containing at least the following elements :

* A list view displaying 5 days, every 8hours weather forecast for Paris
* A detail view displaying a maximum of informations for a specific day's weather
* On the detail view, display "Hot" if temperature is > 15°C, "Cold" if temperature < 10°C

### What you need to focus on :
* Choice of architecture
* Unit Tests implementation
* Justify any dependencies that you might use
* Code quality and code conventions

### Nice to have :
* Once weather datas have been downloaded, user can access it offline
* Any addition that you can think of (feature/design/etc.)

*The use of external dependencies is authorized, but you will have to justify the use for each of them.*


## OpenWeatherMap API

**[OpenWeather]**(https://openweather.co.uk/) is a small IT company that provides APIs to work with weather datas, satellite imagery and other environmental data.

In this project, I am using this API : **[5 day weather forecast]**(https://openweathermap.org/forecast5).

The API key used is not included in the GIT repository.


## Conception

### Android requirements

**Minimum version :** Android 23 - Marshmallow (6.0)

**Target version :** Android 29 - Android 10

**Compile version :** Android 29 - Android 10

### Architecture and Dependencies

* Kotlin
* MVVM Pattern
* DataBinding
* Android Arch Components
    * Navigation
    * Lifecycle - LiveData & ViewModel
    * Room

* [GSON](https://github.com/google/gson) : to serialize and deserialize POJO/POKO to JSON and reverse
* [Retrofit](https://github.com/square/retrofit) : to make REST API calls
* [Koin](https://github.com/InsertKoinIO/koin) : for dependency injection
* [RxJava2](https://github.com/ReactiveX/RxJava) : to do reactive programming
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) : to add specific extensions to do reactive programming with specific Android components
* [Material](https://github.com/material-components/material-components-android) : to use Material Design UI components
* [Constraint Layouts](https://developer.android.com/jetpack/androidx/releases/constraintlayout) : to simplify layouts' conception
* [Picasso](https://github.com/square/picasso) : to easily download images
* [SDP](https://github.com/intuit/sdp) : to provide a new unit size, that easily scale with different screen's sizes (SDP for Scalable DP)
* [SSP](https://github.com/intuit/ssp) : to provide a new unit size, that easily scale with different screen's sizes (SSP for Scalable SP)
* [Timber](https://github.com/JakeWharton/timber) : to easily get logs in application
* [Stetho](https://github.com/facebook/stetho) : to debug application
* [EasyPermissions](ex : https://github.com/googlesamples/easypermissions) : to easily manage permissions


## Author

**Geoffrey Druelle** - [geoffrey-druelle.ovh](https://geoffrey-druelle.ovh)