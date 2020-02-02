package com.renatn.rtc4idea.tasks;

import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.tasks.Comment;
import com.intellij.tasks.Task;
import com.intellij.tasks.TaskType;
import com.intellij.tasks.impl.BaseRepository;
import com.intellij.tasks.impl.BaseRepositoryImpl;
import com.intellij.util.xmlb.annotations.Tag;
import com.renatn.rtc4idea.RtcClientFactory;
import com.renatn.rtc4idea.RtcConnection;
import com.renatn.rtc4idea.RtcTaskClient;
import com.renatn.rtc4idea.RtcProgressMonitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.util.Date;
import java.util.List;

/**
 * User: Renat Nasyrov <renatn@gmail.com>
 * Date: 21.10.13
 * Time: 15:10
 */

@Tag("RTC")
public class RtcWorkItemRepository extends BaseRepositoryImpl {

    private RtcTaskClient client;
    private String projectId;
    private final RtcProgressMonitor monitor = new RtcProgressMonitor();

    @SuppressWarnings({"UnusedDeclaration"})
    public RtcWorkItemRepository() {
    }

    public RtcWorkItemRepository(RtcWorkItemRepository other) {
        super(other);
        setProjectId(other.getProjectId());
    }

    public RtcWorkItemRepository(RtcWorkItemRepositoryType other) {
        super(other);
    }

    @Nullable
    @Override
    public CancellableConnection createCancellableConnection() {

        return new CancellableConnection() {

            @Override
            protected void doTest() throws Exception {
                RtcConnection connection = RtcClientFactory.login(getUrl(), getUsername(), getPassword(), monitor);
                connection.taskClient(getProjectId());
            }

            @Override
            public void cancel() {
                monitor.setCanceled(true);
            }
        };

    }

    @Override
    public Task[] getIssues(@Nullable String query, int max, long since) throws Exception {

        List<RtcItem> items = getTaskClient().findTasksAssignedToMe(max);

        int i = 0;
        Task[] tasks = new Task[items.size()];
        for (RtcItem item : items) {
            tasks[i++] = itemToTask(item);
        }

        return tasks;
    }

    @Nullable
    @Override
    public Task findTask(String id) throws Exception {
        RtcItem item = getTaskClient().findTaskById(id);
        return itemToTask(item);
    }

    @Override
    public BaseRepository clone() {
        return new RtcWorkItemRepository(this);
    }

    @Override
    public boolean isConfigured() {
        return super.isConfigured() && !StringUtil.isEmpty(projectId);
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (!(o instanceof RtcWorkItemRepository)) return false;

        RtcWorkItemRepository that = (RtcWorkItemRepository) o;
        return !(getProjectId() != null ? !getProjectId().equals(that.getProjectId()) : that.getProjectId() != null);
    }

    private Task itemToTask(final RtcItem item) {
        return new Task() {
            @NotNull
            @Override
            public String getId() {
                return item.getId();
            }

            @NotNull
            @Override
            public String getSummary() {
                return item.getSummary();
            }

            @Nullable
            @Override
            public String getDescription() {
                return item.getDescription();
            }

            @NotNull
            @Override
            public Comment[] getComments() {
                return Comment.EMPTY_ARRAY;
            }

            @NotNull
            @Override
            public Icon getIcon() {
                return IconLoader.getIcon("/rtc.png");
            }

            @NotNull
            @Override
            public TaskType getType() {
                if ("defect".equals(item.getType())) {
                    return TaskType.BUG;
                }
                return TaskType.OTHER;
            }

            @Nullable
            @Override
            public Date getUpdated() {
                return item.getCreated();  // TODO: Actual
            }

            @Nullable
            @Override
            public Date getCreated() {
                return item.getCreated();
            }

            @Override
            public boolean isClosed() {
                return item.isClosed();
            }

            @Override
            public boolean isIssue() {
                return true;
            }

            @Nullable
            @Override
            public String getIssueUrl() {
                return getUrl() + item.getPath();
            }
        };
    }

    private synchronized RtcTaskClient getTaskClient() throws Exception {
        if (client == null) {
            RtcConnection connection = RtcClientFactory.currentConnection();
            if (connection == null) {
                connection = RtcClientFactory.login(getUrl(), getUsername(), getPassword(), monitor);
            }
            client = connection.taskClient(getProjectId());
        }
        return client;
    }

}
