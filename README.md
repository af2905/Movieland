[![API](https://img.shields.io/badge/API-23%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=23)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

# Movieland

### An application to search, watch and save movies, TV shows and popular people.

<img src="https://github.com/af2905/Movieland/assets/54403828/be6c16f1-2e64-4183-b4be-9917e5d825f9" width="200" />

![Image of Yaktocat](https://github.com/af2905/Movieland/blob/dev/app/images/movieland_github_pic1.png)



![Image of Yaktocat](https://github.com/af2905/Movieland/blob/dev/app/images/movieland_github_pic2.png)

## Description
Multi-module application with one activity and many fragments.

The main screen contains lists of popular movies, TV shows and people. Also a list of now watching movies on the main screen.

After clicking the show more button, screens will open where films (top, popular, etc.), TV shows (top, popular, etc.) and popular people are presented.

It is possible to view detailed information about a movie, TV show or person and add it to favorites.

The search screen allows you to search by movies, TV shows, and people.

The Favorites screen contains your favorite movies, TV shows, and people.

## Configurations
The application is built using command build.gradle.

The app contains an activity that manages fragments.

Used MVI pattern. 

A navigation component is used to navigate between fragments.
Dagger2 is used for dependency injection.

***
#### Used libraries:
* **dagger2** - for dependency injection.
* **coroutines** - to perform async tasks.
* **room** - to save data in the database.
* **retrofit2** - to download data from the network.
* **glide** - to upload images.
***



## Usage
An application to search, watch and save movies, TV shows and popular people.

If you want to run the application, then you need to get an api key on the site https://www.themoviedb.org and place "your_key" in keystore.properties in the field THE_MOVIE_DATABASE_API_KEY="your_key" or [link to app on Google Play](https://play.google.com/store/apps/details?id=com.github.af2905.movieland)

## TODO list
* add module - Settings
* 
* 

## Contacts
If you have any questions, you can contact me by e-mail af2905g@gmail.com
