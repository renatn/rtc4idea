package com.renatn.rtc4idea.vcs;

import com.intellij.openapi.vcs.RepositoryLocation;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.history.VcsFileRevision;
import com.intellij.openapi.vcs.history.VcsRevisionNumber;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Date;

/**
 * User: Renat Nasyrov <renatn@gmail.com>
 * Date: 16.10.13
 * Time: 16:48
 */
public class RtcFileRevision implements VcsFileRevision {

    private final RtcRevisionNumber myRevisionNumber;
    private final Date myRevisionDate;

    public RtcFileRevision(RtcRevisionNumber revisionNumber, Date revisionDate) {
        this.myRevisionNumber = revisionNumber;
        this.myRevisionDate = revisionDate;
    }

    @Nullable
    @Override
    public String getBranchName() {
        return null;
    }

    @Nullable
    @Override
    public RepositoryLocation getChangedRepositoryPath() {
        return null;
    }

    @Override
    public byte[] loadContent() throws IOException, VcsException {
        return new byte[0];
    }

    @Nullable
    @Override
    public byte[] getContent() throws IOException, VcsException {
        return new byte[0];
    }

    @Override
    public VcsRevisionNumber getRevisionNumber() {
        return myRevisionNumber;
    }

    @Override
    public Date getRevisionDate() {
        return myRevisionDate;
    }

    @Nullable
    @Override
    public String getAuthor() {
        return myRevisionNumber.getAuthor();
    }

    @Nullable
    @Override
    public String getCommitMessage() {
        return myRevisionNumber.getCommitMessage();
    }
}
