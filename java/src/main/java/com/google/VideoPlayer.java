package com.google;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video videoPlaying;
  private Video videoPaused;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    List<Video> videos = videoLibrary.getVideos();
    videos.sort(new VideoSorter()); // sort on alphabetical order by title

    System.out.println("Here's a list of all available videos:");
    videos.forEach(v -> System.out.println("\t" + v)); // print each video in the list
  }

  public void playVideo(String videoId) {
    Video videoRequested = videoLibrary.getVideo(videoId);
    if (videoRequested == null) { // check if requested video exists
      System.out.println("Cannot play video: Video does not exist");
    } else {
      if (videoPlaying != null) { // if a video is already playing
        stopVideo();
      }
      videoPlaying = videoRequested;
      System.out.println("Playing video: " + videoPlaying.getTitle());
    }
  }

  public void stopVideo() {
    if (videoPlaying == null) {
      System.out.println("Cannot stop video: No video is currently playing");
    } else {
      System.out.println("Stopping video: " + videoPlaying.getTitle());
      videoPlaying = null;
      videoPaused = null;
    }
  }

  public void playRandomVideo() {
    // check if videos are available
    if (videoLibrary.getVideos().size() <= 0) {
      System.out.println("No videos available");
    } else {
      if (videoPlaying != null) {
        stopVideo();
      }

      // generate random number
      Random r = new Random();
      int low = 0;
      int high = videoLibrary.getVideos().size();
      int result = r.nextInt(high-low) + low;

      // get video id of the video with the index of the random number
      String videoId = videoLibrary.getVideos().get(result).getVideoId();

      playVideo(videoId);
    }
  }

  public void pauseVideo() {
    if (videoPaused != null) {
      System.out.println("Video already paused: " + videoPaused.getTitle());
    } else if (videoPlaying == null) {
      System.out.println("Cannot pause video: No video is currently playing");
    } else {
      System.out.println("Pausing video: " + videoPlaying.getTitle());
      videoPaused = videoPlaying;
    }
  }

  public void continueVideo() {
    if (videoPlaying == null) {
      System.out.println("Cannot continue video: No video is currently playing");
    } else if (videoPaused == null) {
      System.out.println("Cannot continue video: Video is not paused");
    } else {
      System.out.println("Continuing video: " + videoPlaying.getTitle());
      videoPaused = null;
    }
  }

  public void showPlaying() {
    if (videoPlaying == null) {
      System.out.println("No video is currently playing");
    } else {
      String pausedStatus = (videoPaused == null) ? "" : " - PAUSED";
      System.out.println("Currently playing: " + videoPlaying + pausedStatus);
    }
  }

  public void createPlaylist(String playlistName) {
    VideoPlaylist playlistRequested = videoLibrary.getPlaylist(playlistName.toLowerCase());

    if (playlistName.contains(" ")) {
      System.out.println("Cannot create playlist: The playlist name cannot contain whitespace");
    } else if (playlistRequested != null) {
      System.out.println("Cannot create playlist: A playlist with the same name already exists");
    } else {
      videoLibrary.addPlaylist(playlistName);
      System.out.println("Successfully created new playlist: " + playlistName);
    }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    VideoPlaylist playlistRequested = videoLibrary.getPlaylist(playlistName.toLowerCase());
    Video videoRequested = videoLibrary.getVideo(videoId);

    if (playlistRequested == null) { // if playlist doesn't exist
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    } else if (videoRequested == null) { // if video doesn't exist
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
    } else if (playlistRequested.getVideos().contains(videoRequested)) {
      System.out.println("Cannot add video to " + playlistName + ": Video already added");
    } else {
      playlistRequested.addVideo(videoRequested); // add video to playlist
      System.out.println("Added video to " + playlistName + ": " + videoRequested.getTitle());
    }
  }

  public void showAllPlaylists() {
    List<VideoPlaylist> playlists = videoLibrary.getPlaylists();
    playlists.sort(new PlaylistSorter()); // sort on alphabetical order by name

    if (playlists.size() <= 0) {
      System.out.println("No playlists exist yet");
    } else {
      System.out.println("Showing all playlists:");
      playlists.forEach(p -> System.out.println("\t" + p.getName())); // print each playlist in the list
    }
  }

  public void showPlaylist(String playlistName) {
    VideoPlaylist playlistRequested = videoLibrary.getPlaylist(playlistName.toLowerCase());

    if (playlistRequested == null) {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    } else {
      System.out.println("Showing playlist: " + playlistName);

      List<Video> videos = playlistRequested.getVideos();

      if (videos.size() <= 0) {
        System.out.println("\tNo videos here yet");
      } else {
        videos.forEach(v -> System.out.println("\t" + v)); // print each video in the list
      }
    }
  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    VideoPlaylist playlistRequested = videoLibrary.getPlaylist(playlistName.toLowerCase());
    Video videoRequested = videoLibrary.getVideo(videoId);

    if (playlistRequested == null) { // if playlist doesn't exist
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    } else if (videoRequested == null) { // if video doesn't exist
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
    } else if (playlistRequested.getVideos().contains(videoRequested)) {
      playlistRequested.removeVideo(videoRequested); // add video to playlist
      System.out.println("Removed video from " + playlistName + ": " + videoRequested.getTitle());
    } else {
      System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");
    }
  }

  public void clearPlaylist(String playlistName) {
    VideoPlaylist playlistRequested = videoLibrary.getPlaylist(playlistName.toLowerCase());

    if (playlistRequested == null) { // if playlist doesn't exist
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    } else {
      playlistRequested.clearPlaylist();
      System.out.println("Successfully removed all videos from " + playlistName);
    }
  }

  public void deletePlaylist(String playlistName) {
    VideoPlaylist playlistRequested = videoLibrary.getPlaylist(playlistName.toLowerCase());

    if (playlistRequested == null) { // if playlist doesn't exist
      System.out.println("Cannot delete playlist " + playlistName + ": Playlist does not exist");
    } else {
      videoLibrary.deletePlaylist(playlistName);
      System.out.println("Deleted playlist: " + playlistName);
    }
  }

  public void searchVideos(String searchTerm) {
    boolean containsSpecialChar = false;
    String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|} ";

    // check if search term contains any whitespace or special characters
    for (int i=0; i < searchTerm.length() ; i++) {
      char ch = searchTerm.charAt(i);
      if (specialCharactersString.contains(Character.toString(ch))) {
        System.out.println(searchTerm + " contains special character");
        containsSpecialChar = true;
        break;
      }
    }

    if (containsSpecialChar) {
      System.out.println("The search term cannot contain any whitespace or special characters");
    } else {
      List<Video> searchResults = new ArrayList();
      List<Video> allVideos = videoLibrary.getVideos();

      for(Video v : allVideos) {
        if (v.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) { // if video contains search term
          searchResults.add(v); // add video to search results list
        }
      }

      searchResults.sort(new VideoSorter()); // sort on alphabetical order by title

      if (searchResults.isEmpty()) {
        System.out.println("No search results for " + searchTerm);
      } else {
        System.out.println("Here are the results for " + searchTerm + ":");

        for (int i=0; i < searchResults.size() ; i++) {
          int index = i+1;
          System.out.println("\t" + index + ") " + searchResults.get(i));
        }

        System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
        System.out.println("If your answer is not a valid number, we will assume it's a no.");

        // receive input from command line
        var s = new Scanner(System.in);
        var input = s.nextLine();
        int numberChoice = 0;

        try {
          numberChoice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          return;
        }

        // play selected video
        if (numberChoice > 0 && numberChoice <= searchResults.size()) {
          int videoIndex = numberChoice - 1;
          playVideo(searchResults.get(videoIndex).getVideoId());
        }
      }
    }
  }

  public void searchVideosWithTag(String videoTag) {
    List<Video> searchResults = new ArrayList();
    List<Video> allVideos = videoLibrary.getVideos();

    for(Video v : allVideos) {
      if (v.getTags().contains(videoTag.toLowerCase())) {
        searchResults.add(v); // add video to search results list
      }
    }

    searchResults.sort(new VideoSorter()); // sort on alphabetical order by title

    if (searchResults.isEmpty()) {
      System.out.println("No search results for " + videoTag);
    } else {
      System.out.println("Here are the results for " + videoTag + ":");

      for (int i=0; i < searchResults.size() ; i++) {
        int index = i+1;
        System.out.println("\t" + index + ") " + searchResults.get(i));
      }

      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no.");

      // receive input from command line
      var s = new Scanner(System.in);
      var input = s.nextLine();
      int numberChoice = 0;

      try {
        numberChoice = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        return;
      }

      // play selected video
      if (numberChoice > 0 && numberChoice <= searchResults.size()) {
        int videoIndex = numberChoice - 1;
        playVideo(searchResults.get(videoIndex).getVideoId());
      }
    }
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}