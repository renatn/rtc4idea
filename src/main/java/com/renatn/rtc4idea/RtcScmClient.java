package com.renatn.rtc4idea;

import com.ibm.team.filesystem.client.internal.FileItemInfo;
import com.ibm.team.filesystem.client.internal.ISharingMetadata;
import com.ibm.team.filesystem.client.internal.RelativeLocation;
import com.ibm.team.filesystem.client.internal.core.SharingMetadata2;
import com.ibm.team.repository.client.IItemManager;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.common.IContributor;
import com.ibm.team.repository.common.IContributorHandle;
import com.ibm.team.repository.common.TeamRepositoryException;
import com.ibm.team.scm.client.IChangeHistory;
import com.ibm.team.scm.client.IWorkspaceConnection;
import com.ibm.team.scm.client.IWorkspaceManager;
import com.ibm.team.scm.common.IChangeHistoryEntryChange;
import com.ibm.team.scm.common.IChangeSet;
import com.ibm.team.scm.common.IComponentHandle;
import com.ibm.team.scm.common.IVersionableHandle;
import com.renatn.rtc4idea.vcs.RtcFileRevision;
import com.renatn.rtc4idea.vcs.RtcRevisionNumber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 25.10.13
 * Time: 12:14
 */
public class RtcScmClient {

    private final ITeamRepository repo;
    private final IWorkspaceConnection workspaceConnection;
    private final IWorkspaceManager workspaceManager;
    private final SharingMetadata2 metadata2;

    public RtcScmClient(SharingMetadata2 metadata2, ITeamRepository repo, IWorkspaceManager workspaceManager, IWorkspaceConnection workspaceConnection) {
        this.repo = repo;
        this.workspaceManager = workspaceManager;
        this.workspaceConnection = workspaceConnection;
        this.metadata2 = metadata2;
    }

    public Set<String> findMyWorkspaces() throws TeamRepositoryException {
        IContributor me = repo.loggedInContributor();
        return workspaceManager.findAllWorkspaceNames(me, null);
    }

    public List<RtcFileRevision> historyFor(String filePath, int limit) throws TeamRepositoryException {

        ArrayList<RtcFileRevision> result = new ArrayList<RtcFileRevision>(0);

        FileItemInfo fileInfo = metadata2.getFileItemInfo(new RelativeLocation(filePath.split(Pattern.quote("/"))));
        if (fileInfo == null) {
            return result;
        }

        Collection<ISharingMetadata.IConnectionComponent> locations = metadata2.getLocations(fileInfo.getVersionableHandle());
        IComponentHandle component = locations.iterator().next().getComponent();

        IVersionableHandle fileHandle = fileInfo.getVersionableHandle();
        if (fileHandle == null) {
            return result;
        }

        IChangeHistory history = workspaceConnection.changeHistory(component);
        List<IChangeHistoryEntryChange> fileHistory = history.getHistoryFor(fileHandle, limit, true, null);

        for (IChangeHistoryEntryChange changeEntry : fileHistory) {
            IChangeSet changeSet = (IChangeSet) repo.itemManager().fetchCompleteItem(changeEntry.changeSet(), IItemManager.DEFAULT, null);

            IContributorHandle authorHandle = changeSet.getAuthor();
            IContributor author = (IContributor) repo.itemManager().fetchCompleteItem(authorHandle, IItemManager.DEFAULT, null);
/*
            List<ILink> links = ChangeSetLinks.findLinks(
                    (ProviderFactory) repo.getClientLibrary(ProviderFactory.class),
                    changeSet,
                    new String[]{ILinkConstants.CHANGESET_WORKITEM_LINKTYPE_ID},
                    new NullProgressMonitor()
            );
            System.out.println("Links: " + links.size());
*/
            RtcFileRevision fileRevision = new RtcFileRevision(
                    new RtcRevisionNumber(
                            changeSet.getItemId().toString(),
                            changeSet.getComment(),
                            author.getName()),
                    changeSet.getLastChangeDate()
            );
            result.add(fileRevision);
        }
        return result;
    }


}
