package com.google;

import java.util.Collections;
import java.util.List;

/** A class used to represent a video. */
class Video {

  private final String title;
  private final String videoId;
  private final List<String> tags;
  private boolean flagged = false;
  private String flagReason;

  Video(String title, String videoId, List<String> tags) {
    this.title = title;
    this.videoId = videoId;
    this.tags = Collections.unmodifiableList(tags);
  }

  /** Returns the title of the video. */
  String getTitle() {
    return title;
  }

  /** Returns the video id of the video. */
  String getVideoId() {
    return videoId;
  }

  /** Returns a readonly collection of the tags of the video. */
  List<String> getTags() {
    return tags;
  }

  /** Set the the video as flagged. */
  void setFlagged(boolean flag) {
    this.flagged = flag;
  }

  /** Set the reason for flagging the video. */
  void setFlagReason(String flagReason) {
    this.flagReason = flagReason;
  }

  /** Returns whether or not the video is flagged. */
  boolean getFlagged() {
    return flagged;
  }

  /** Returns the reason of why the video is flagged. */
  String getFlagReason() {
    return flagReason;
  }

  @Override
  public String toString() {
    StringBuilder bld = new StringBuilder();
    for (int i = 0; i < tags.size(); ++i) {
      if (i == 0) {
        bld.append(tags.get(i));
      } else {
        bld.append(" " + tags.get(i));
      }
    }
    String tagsString = bld.toString();

    String flagString = (flagged ? " - FLAGGED (reason: " + flagReason + ")" : "");

    return title + " (" + videoId + ") " + "[" + tagsString + "]" + flagString;
  }
}