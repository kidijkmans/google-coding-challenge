package com.google;

import java.util.Comparator;

public class PlaylistSorter implements Comparator<VideoPlaylist> {
    @Override
    public int compare(VideoPlaylist o1, VideoPlaylist o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}