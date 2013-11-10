package com.renatn.rtc4idea.vcs;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.AbstractVcs;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.actions.StandardVcsGroup;
import org.jetbrains.annotations.Nullable;

/**
 * User: Renat Nasyrov <renatn@gmail.com>
 * Date: 11.10.13
 * Time: 18:02
 */
public class RtcGroup extends StandardVcsGroup {
    @Override
    public AbstractVcs getVcs(Project project) {
        return ProjectLevelVcsManager.getInstance(project).findVcsByName("RTC");
    }

    @Nullable
    @Override
    public String getVcsName(Project project) {
        return  RtcVcs.VCS_NAME;
    }
}
