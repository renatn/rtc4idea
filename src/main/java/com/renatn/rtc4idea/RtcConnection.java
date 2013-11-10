package com.renatn.rtc4idea;

import com.ibm.team.filesystem.client.ILocation;
import com.ibm.team.filesystem.client.internal.PathLocation;
import com.ibm.team.filesystem.client.internal.core.SharingMetadata2;
import com.ibm.team.filesystem.client.internal.utils.LoadedConfigurationDescriptor;
import com.ibm.team.process.client.IProcessItemService;
import com.ibm.team.process.common.IProjectArea;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.common.IItemHandle;
import com.ibm.team.scm.client.IWorkspaceConnection;
import com.ibm.team.scm.client.IWorkspaceManager;
import com.ibm.team.scm.client.SCMPlatform;
import com.ibm.team.scm.common.IWorkspaceHandle;
import org.eclipse.core.runtime.NullProgressMonitor;

import java.net.URI;
import java.net.URLEncoder;
import java.util.Collection;


/**
 * Author: Renat Nasyrov <renatn@gmail.com>
 * Date: 16.10.13
 * Time: 17:00
 */
public class RtcConnection {

    private final ITeamRepository repo;

    public RtcConnection(ITeamRepository repo) {
        this.repo = repo;
    }

    public RtcTaskClient taskClient(String projectName) throws Exception {

        IProcessItemService itemService = (IProcessItemService) repo.getClientLibrary(IProcessItemService.class);
        URI uri = URI.create(URLEncoder.encode(projectName, "UTF-8").replaceAll("\\+", "%20"));

        IProjectArea projectArea = (IProjectArea) itemService.findProcessArea(uri, null, null);
        if (projectArea == null) {
            throw new IllegalArgumentException("Project not found.");
        }

        return new RtcTaskClient(repo, projectArea);
    }

    public RtcScmClient scmClient(String pathRepo) throws Exception {

        ILocation repoLocation = new PathLocation(pathRepo);
        SharingMetadata2 metadata2 = new SharingMetadata2(repoLocation, false, repoLocation);

        // Find current workspace
        Collection<LoadedConfigurationDescriptor> descriptors = metadata2.allLoadedComponents(new NullProgressMonitor());
        if (descriptors.size() == 0) {
            throw new IllegalArgumentException("Path is not RTC workspace");
        }
        IItemHandle itemHandle = descriptors.iterator().next().getConnectionHandle();

        IWorkspaceHandle workspace = (IWorkspaceHandle) itemHandle;// (IWorkspaceHandle) IWorkspace.ITEM_TYPE.createItemHandle(UUID.valueOf(workspaceId), null);
        IWorkspaceManager workspaceManager = SCMPlatform.getWorkspaceManager(repo);
        IWorkspaceConnection workspaceConnection = workspaceManager.getWorkspaceConnection(workspace, null);
        return new RtcScmClient(metadata2, repo, workspaceManager, workspaceConnection);
    }


    /*
        IWorkspaceSearchCriteria criteria = IWorkspaceSearchCriteria.FACTORY.newInstance();
        criteria.setKind(IWorkspaceSearchCriteria.WORKSPACES);
        criteria.setExactName(workspaceName);

        List<IWorkspaceHandle> workspaces = workspaceManager.findWorkspaces(criteria, 1, null);
        if (workspaces.size() == 0) {
            throw new IllegalArgumentException("Workspace not found");
        }

        IWorkspaceHandle workspace = workspaces.get(0);
        System.out.println("WS handle: " + workspace.getItemId());


*/

}
