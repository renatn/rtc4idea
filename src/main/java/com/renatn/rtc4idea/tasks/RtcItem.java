package com.renatn.rtc4idea.tasks;

import com.ibm.team.workitem.common.model.IWorkItem;

import java.util.Date;

/**
 * User: Renat Nasyrov <renatn@gmail.com>
 * Date: 21.10.13
 * Time: 15:55
 */
public class RtcItem {

    private final String id;
    private final String summary;
    private final String description;
    private final Date created;
    private final String type;
    private final boolean closed;

    public RtcItem(IWorkItem item) {
        this.id = Integer.toString(item.getId());
        this.summary = item.getHTMLSummary().getPlainText();
        this.description = item.getHTMLDescription().getPlainText();
        this.created = new Date(item.getCreationDate().getTime());
        this.type = item.getWorkItemType();
        this.closed = item.getResolutionDate() != null;
    }

    public String getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreated() {
        return created;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return "/resource/itemName/com.ibm.team.workitem.WorkItem/"+id;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public String toString() {
        return summary;
    }

}
