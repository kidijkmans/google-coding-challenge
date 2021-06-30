package com.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {

    private final String name;
    private final String playlistId;
    //private final List<Video> videos;
    private final LinkedHashMap<String, Video> videos;

    VideoPlaylist(String name) {
        this.name = name;
        this.playlistId = name.toLowerCase();
        //this.videos = new ArrayList<Video>();
        this.videos = new LinkedHashMap<>();
    }

    /** Returns the name of the playlist. */
    String getName() {
        return name;
    }

    /** Returns the id of the playlist. */
    String getPlaylistId() {
        return playlistId;
    }

    /** Get a list of all videos in the playlist. */
    List<Video> getVideos() {
        return new ArrayList<>(this.videos.values());
    }

    /** Add a video to the playlist. */
    void addVideo(Video video) {
        this.videos.put(video.getVideoId(), video);
    }

    /** Remove a video from the playlist. */
    void removeVideo(Video video) {
        this.videos.remove(video.getVideoId());
    }

    /** Remove all videos from the playlist. */
    void clearPlaylist() {
        this.videos.clear();
    }

    @Override
    public String toString() {
        return name + " (" + videos + ")";
    }
}