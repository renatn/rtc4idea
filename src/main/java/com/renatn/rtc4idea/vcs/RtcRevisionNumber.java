package com.renatn.rtc4idea.vcs;

import com.intellij.openapi.vcs.history.VcsRevisionNumber;

/**
 * Author: Renat Nasyrov <renatn@gmail.com>
 * Date: 15.10.13
 * Time: 12:17
 */
public class RtcRevisionNumber implements VcsRevisionNumber {

    private final String id;
    private final String commitMessage;
    private final String author;

    public RtcRevisionNumber(String id, String commitMessage, String author) {
        this.id = id;
        this.commitMessage = commitMessage;
        this.author = author;
    }

    @Override
    public String asString() {
        return id;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public int compareTo(VcsRevisionNumber o) {
        return 0;
    }
}
