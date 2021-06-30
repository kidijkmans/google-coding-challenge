package com.google;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A class used to represent a Video Library.
 */
class VideoLibrary {

  private final HashMap<String, Video> videos;
  private final HashMap<String, VideoPlaylist> playlists;

  VideoLibrary() {
    this.videos = new HashMap<>();
    this.playlists = new HashMap<>();

    // import videos from videos.txt file
    try {
      File file = new File(this.getClass().getResource("/videos.txt").getFile());

      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("\\|");
        String title = split[0].strip();
        String id = split[1].strip();
        List<String> tags;
        if (split.length > 2) {
          tags = Arrays.stream(split[2].split(",")).map(String::strip).collect(
              Collectors.toList());
        } else {
          tags = new ArrayList<>();
        }
        this.videos.put(id, new Video(title, id, tags));
      }
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't find videos.txt");
      e.printStackTrace();
    }
  }

  /**
   * Get a list of all videos.
   */
  List<Video> getVideos() {
    return new ArrayList<>(this.videos.values());
  }

  /**
   * Get a video by id. Returns null if the video is not found.
   */
  Video getVideo(String videoId) {
    return this.videos.get(videoId);
  }

  /**
   * Get a list of all playlists.
   */
  List<VideoPlaylist> getPlaylists() {
    return new ArrayList<>(this.playlists.values());
  }

  /**
   * Get a playlist by name. Returns null if the playlist is not found.
   */
  VideoPlaylist getPlaylist(String name) {
    return this.playlists.get(name);
  }

  /**
   * Add a playlist.
   */
  void addPlaylist(String name) {
    String playlistId = name.toLowerCase();
    playlists.put(playlistId, new VideoPlaylist(name));
  }

  /**
   * Delete a playlist.
   */
  void deletePlaylist(String name) {
    playlists.remove(name);
  }
}