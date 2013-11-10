package com.renatn.rtc;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 22.10.13
 * Time: 17:57
 */
public class ConsoleProgressMonitor implements IProgressMonitor {
    @Override
    public void beginTask(String s, int i) {
        if (s != null && !"".equals(s.trim())) {
            System.out.println(s);
        }
    }

    @Override
    public void done() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void internalWorked(double v) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isCanceled() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setCanceled(boolean b) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setTaskName(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void subTask(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void worked(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
