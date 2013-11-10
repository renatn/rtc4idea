package com.renatn.rtc4idea;

import com.ibm.team.filesystem.client.internal.rest.util.LoginUtil;
import com.ibm.team.repository.client.ITeamRepository;
import com.ibm.team.repository.client.TeamPlatform;
import org.eclipse.core.runtime.IProgressMonitor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 22.10.13
 * Time: 16:59
 */
public class RtcClientFactory {

    private static final AtomicInteger initialized = new AtomicInteger(0);
    private static RtcConnection connection;

    public static RtcConnection login(String repoUrl, String userName, String password, IProgressMonitor monitor) throws Exception {

        if (repoUrl == null || userName == null || password == null) {
            throw new IllegalArgumentException("All input fields is required.");
        }

        if (initialized.compareAndSet(0, 1)) {
            TeamPlatform.startup();
        }

        ITeamRepository repo = TeamPlatform.getTeamRepositoryService().getTeamRepository(repoUrl);
        if (repo.loggedIn()) {
            repo.logout();
        }

        repo.registerLoginHandler(new LoginUtil.LoginHandler(userName, password));
        repo.login(monitor);

        connection = new RtcConnection(repo);
        return connection;
    }

    public static RtcConnection currentConnection() {
        return connection;
    }

    public static void shutdown() {
        if (initialized.compareAndSet(1, 0)) {
            TeamPlatform.shutdown();
        }
    }


}
