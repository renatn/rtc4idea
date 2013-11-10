package com.renatn.rtc4idea.tasks;

import com.intellij.openapi.project.Project;
import com.intellij.tasks.config.BaseRepositoryEditor;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.Consumer;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 22.10.13
 * Time: 11:56
 */
public class RtcRepositoryEditor extends BaseRepositoryEditor<RtcWorkItemRepository> {

    private JTextField myProjectId;
    private JBLabel myProjectIdLabel;

    public RtcRepositoryEditor(Project project, RtcWorkItemRepository repository, Consumer<RtcWorkItemRepository> changeListener) {
        super(project, repository, changeListener);
        myProjectId.setText(repository.getProjectId());
    }

    @Override
    public void apply() {
        myRepository.setProjectId(myProjectId.getText().trim());
        super.apply();
    }

    @Nullable
    @Override
    protected JComponent createCustomPanel() {
        myProjectIdLabel = new JBLabel("Project ID:", SwingConstants.RIGHT);
        myProjectId = new JTextField();
        installListener(myProjectId);
        return FormBuilder.createFormBuilder().addLabeledComponent(myProjectIdLabel, myProjectId).getPanel();
    }

    @Override
    public void setAnchor(@Nullable JComponent anchor) {
        super.setAnchor(anchor);
        myProjectIdLabel.setAnchor(anchor);
    }
}
