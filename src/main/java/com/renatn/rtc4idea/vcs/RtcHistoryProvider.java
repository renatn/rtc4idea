package com.renatn.rtc4idea.vcs;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.history.*;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.vcsUtil.VcsUtil;
import com.renatn.rtc4idea.RtcClientFactory;
import com.renatn.rtc4idea.RtcConnection;
import com.renatn.rtc4idea.RtcScmClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Renat Nasyrov <renatn@gmail.com>
 * Date: 15.10.13
 * Time: 12:03
 */
public class RtcHistoryProvider implements VcsHistoryProvider {

    private Project project;
    private RtcScmClient scmClient;

    public RtcHistoryProvider(Project project) {
        this.project = project;
    }

    @Override
    public VcsDependentHistoryComponents getUICustomization(VcsHistorySession vcsHistorySession, JComponent jComponent) {
        return VcsDependentHistoryComponents.createOnlyColumns(new ColumnInfo[0]);
    }

    @Override
    public AnAction[] getAdditionalActions(Runnable runnable) {
        return new AnAction[0];
    }

    @Override
    public boolean isDateOmittable() {
        return false;
    }

    @Nullable
    @Override
    public String getHelpId() {
        return null;
    }

    @Nullable
    @Override
    public VcsHistorySession createSessionFor(FilePath filePath) throws VcsException {
        return createSession(filePath);
    }

    @Override
    public void reportAppendableHistory(FilePath path, VcsAppendableHistorySessionPartner partner) throws VcsException {
        final VcsAbstractHistorySession emptySession = createSession(path);
        partner.reportCreatedEmptySession(emptySession);
    }

    private VcsAbstractHistorySession createSession(final FilePath filePath) {
        ArrayList<VcsFileRevision> revisions = new ArrayList<VcsFileRevision>();

        try {
            String path = getRelativePath(filePath);
            List<RtcFileRevision> history = getScmClient().historyFor(path, 100);
            revisions.addAll(history);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new VcsAbstractHistorySession(revisions) {

            @Nullable
            @Override
            protected VcsRevisionNumber calcCurrentRevisionNumber() {
                return new RtcRevisionNumber("1", "-+-+-+", "idea");  // TODO: Return actual version
            }

            @Override
            public VcsHistorySession copy() {
                return createSession(filePath);
            }

            @Nullable
            @Override
            public HistoryAsTreeProvider getHistoryAsTreeProvider() {
                return null;
            }
        };

    }

    @Override
    public boolean supportsHistoryForDirectories() {
        return false;
    }

    @Nullable
    @Override
    public DiffFromHistoryHandler getHistoryDiffHandler() {
        return null;
    }

    @Override
    public boolean canShowHistoryFor(@NotNull VirtualFile virtualFile) {
        return true;
    }

    private String getRelativePath(FilePath filePath) {
        VirtualFile root = VcsUtil.getVcsRootFor(project, filePath);
        if (root == null) {
            return filePath.getPath();
        }
        return filePath.getPath().substring(root.getPath().length()+1);
    }

    private synchronized RtcScmClient getScmClient() throws Exception {
        if (scmClient == null) {
            RtcConnection connection = RtcClientFactory.currentConnection();
            if (connection == null) {
                connection = RtcClientFactory.login(RtcSettings.REPO_URL, RtcSettings.getUsername(), RtcSettings.getPassword(), null);
            }
            scmClient = connection.scmClient(RtcSettings.WORKSPACE_PATH);
        }
        return scmClient;
    }

}
