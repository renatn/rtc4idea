package com.renatn.rtc4idea;

import com.intellij.openapi.diagnostic.Logger;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 22.10.13
 * Time: 11:39
 */
public class RtcProgressMonitor implements IProgressMonitor {

    private static final Logger LOG = Logger.getInstance("#com.renatn.rtc4idea.RtcProgressMonitor");

    private boolean cancelled;
    private String taskName;

    @Override
    public void beginTask(String s, int i) {
        if (s != null && !"".equals(s.trim())) {
            setTaskName(s);
        }
    }

    @Override
    public void done() {
        if (taskName != null) {
            LOG.info(taskName + " [DONE]");
        }
        taskName = "";
    }

    @Override
    public void internalWorked(double v) {
    }

    @Override
    public boolean isCanceled() {
        LOG.warn(taskName + " [CANCEL]");
        return cancelled;
    }

    @Override
    public void setCanceled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public void setTaskName(String s) {
        this.taskName = s;
        cancelled = false;
        LOG.info(taskName);
    }

    @Override
    public void subTask(String s) {
    }

    @Override
    public void worked(int i) {
        LOG.debug(taskName + "["+i+"]");
    }
}
