package com.renatn.rtc4idea.vcs;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.AbstractVcs;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcsUtil.VcsUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 22.11.13
 * Time: 17:00
 */
public class RtcRootsHandler implements AbstractVcs.RootsConvertor {

    private Project myProject;

    public RtcRootsHandler(Project project) {
        myProject = project;
    }

    @Override
    public List<VirtualFile> convertRoots(List<VirtualFile> original) {
        HashSet<VirtualFile> result = new HashSet<VirtualFile>();
        for (VirtualFile f : original) {
            VirtualFile currentDir = VcsUtil.getVcsRootFor(myProject, f);
            while (currentDir != null) {
                if (currentDir.findChild(".jazz5") != null) {
                    result.add(currentDir);
                    break;
                }
                currentDir = currentDir.getParent();
            }
        }
        return new ArrayList<VirtualFile>(result);
    }

    public static AbstractVcs.RootsConvertor getInstance(Project project) {
        return ServiceManager.getService(project, RtcRootsHandler.class);
    }

}
