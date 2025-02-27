# Apollo - Android

This Android application is a simple music player. 
The long term goal is to create a self-hosted Spotify clone, which can play music from
both the device locally, as from your own self-hosted music library. 

The work on the backend part will begin after the client is finished.
At this point, it is open if the backend will use Subsonic or built from scratch.

## Features (_in progress_)

* **Local Music Playback:** Plays audio files stored on your Android device.
* **Basic Playback Controls:** Includes play, pause, skip, and rewind functionality.
* **Library Browsing:** Allows users to browse their local music library by artists, albums, or songs.
* **Playlist Creation:** Planned feature to allow users to create and manage playlists.
* **Basic UI:** A simple and intuitive user interface for easy navigation.

## Getting Started

### Prerequisites

* Android Studio
* Android SDK

### Installation

1.  Clone the repository to your local machine:

    ```bash
    git clone https://github.com/IvanCarapovic/apollo-android.git
    ```

2.  Open the project in Android Studio.
3.  Build and run the application on an Android emulator or device. Preferably on a local device with some songs in the local library.

### Usage

1.  Grant the application permission to access local storage.
2.  Browse your music library and select a song to play.
3.  Use the playback controls to manage the audio.

## Development

### Technologies Used

* Kotlin and Jetpack Compose
* Android SDK
* ExoPlayer

### Contributing

Contributions are _currently_ not accepted as the the project is in it's early stages.
In the future it is planned to accept contributions.

## Future Enhancements

* Playlist management.
* Search functionality.
* Equalizer (depending on Media3 support)
* Background playback.
* Streaming and caching from backend

## License

This project is licensed under the terms of the [LICENSE](LICENSE) file.