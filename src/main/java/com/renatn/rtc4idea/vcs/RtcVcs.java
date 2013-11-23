package com.renatn.rtc4idea.vcs;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.AbstractVcs;
import com.intellij.openapi.vcs.changes.ChangeProvider;
import com.intellij.openapi.vcs.history.VcsHistoryProvider;
import com.intellij.openapi.vcs.versionBrowser.CommittedChangeList;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcsUtil.VcsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Author: Renat Nasyrov <renatn@gmail.com>
 * Date: 15.10.13
 * Time: 11:36
 */
public class RtcVcs extends AbstractVcs<CommittedChangeList> {

    public static final String VCS_NAME = "RTC";

    private Project project;

    // private RtcChangeProvider changeProvider;
    private RtcHistoryProvider myHistoryProvider;
    private Configurable myConfigurable;

    public RtcVcs(Project project, @NotNull RtcProjectSettings projectSettings) {
        super(project, VCS_NAME);
        this.project = project;
        myHistoryProvider = new RtcHistoryProvider(project);
        myConfigurable = new RtcVcsConfigurable(projectSettings);
    }

    @Override
    public String getDisplayName() {
        return VCS_NAME;
    }

    @Nullable
    @Override
    public VcsHistoryProvider getVcsHistoryProvider() {
        return myHistoryProvider;
    }

    @Override
    public boolean isVersionedDirectory(VirtualFile dir) {
        return true;
    }

    @Override
    protected void activate() {
        super.activate();
    }

    @Override
    protected void deactivate() {
        super.deactivate();
    }

    @Override
    public Project getProject() {
        return project;
    }

    @Override
    public Configurable getConfigurable() {
        return myConfigurable;
    }

    @Nullable
    @Override
    public ChangeProvider getChangeProvider() {
        return super.getChangeProvider();
    }

    @Nullable
    @Override
    public RootsConvertor getCustomConvertor() {
        return RtcRootsHandler.getInstance(myProject);
    }
}
