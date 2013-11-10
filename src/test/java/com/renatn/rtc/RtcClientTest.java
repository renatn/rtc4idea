package com.renatn.rtc;

import com.renatn.rtc4idea.*;
import com.renatn.rtc4idea.tasks.RtcItem;
import com.renatn.rtc4idea.vcs.RtcFileRevision;
import com.renatn.rtc4idea.vcs.RtcSettings;

import java.util.List;
import java.util.Set;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 22.10.13
 * Time: 17:48
 */
public class RtcClientTest {

    public static void main(String[] args) {

        try {
            System.out.println("Connecting to " + RtcSettings.REPO_URL + "...");
            RtcConnection connection = RtcClientFactory.login(
                    RtcSettings.REPO_URL,
                    "username",
                    "password",
                    new ConsoleProgressMonitor()
            );

            System.out.println("Testing TASKS client...");
            RtcTaskClient taskClient = connection.taskClient(RtcSettings.PROJECT_NAME);
            List<RtcItem> tasksAssignedToMe = taskClient.findTasksAssignedToMe(25);
            System.out.println(" - found " + tasksAssignedToMe.size() + " tasks assigned to me.");

            System.out.println("Testing SCM client...");
            RtcScmClient scmClient = connection.scmClient(RtcSettings.WORKSPACE_PATH);
            Set<String> workspaces = scmClient.findMyWorkspaces();
            System.out.println(" - my workspaces: " + workspaces);

            System.out.println(" - fetching file history for ...");
            List<RtcFileRevision> history = scmClient.historyFor("<source.java>", 100);
            System.out.println(" - " + history.size() + " changesets");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RtcClientFactory.shutdown();
        }
    }

}
