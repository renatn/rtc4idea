package com.renatn.rtc4idea.tasks;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.tasks.TaskRepository;
import com.intellij.tasks.config.TaskRepositoryEditor;
import com.intellij.tasks.impl.BaseRepositoryType;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * User: Renat Nasyrov <renatn@gmail.com>
 * Date: 21.10.13
 * Time: 14:53
 */
public class RtcWorkItemRepositoryType extends BaseRepositoryType<RtcWorkItemRepository> {
    @NotNull
    @Override
    public String getName() {
        return "RTC";
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/rtc.png");
    }

    @NotNull
    @Override
    public TaskRepository createRepository() {
        return new RtcWorkItemRepository(this);
    }

    @Override
    public Class<RtcWorkItemRepository> getRepositoryClass() {
        return RtcWorkItemRepository.class;
    }

    @NotNull
    @Override
    public TaskRepositoryEditor createEditor(RtcWorkItemRepository repository, Project project, Consumer<RtcWorkItemRepository> changeListener) {
        return new RtcRepositoryEditor(project, repository, changeListener);
    }
}
