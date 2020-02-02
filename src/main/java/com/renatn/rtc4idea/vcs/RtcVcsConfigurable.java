package com.renatn.rtc4idea.vcs;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 13.11.13
 * Time: 18:30
 */
public class RtcVcsConfigurable implements Configurable {

    private RtcProjectSettings myProjectSettings;
    private RtcProjectSettingsPanel myPanel;

    public RtcVcsConfigurable(RtcProjectSettings projectSettings) {
        this.myProjectSettings = projectSettings;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return RtcVcs.VCS_NAME;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (myPanel == null) {
            myPanel = new RtcProjectSettingsPanel(myProjectSettings);
        }
        return myPanel;
    }

    @Override
    public boolean isModified() {
        return (myPanel != null)
                && ((!myProjectSettings.getLogin().equals(myPanel.getLogin()))
                || (!myProjectSettings.getHost().equals(myPanel.getHost())));
    }

    @Override
    public void apply() throws ConfigurationException {
        if (myPanel != null) {
            myProjectSettings.setLogin(myPanel.getLogin());
            myProjectSettings.setHost(myPanel.getHost());
        }
    }

    @Override
    public void reset() {
        if (myPanel != null) {
            myPanel.setLogin(myProjectSettings.getLogin());
            myPanel.setHost(myProjectSettings.getHost());
        }
    }

    @Override
    public void disposeUIResources() {
    }
}
