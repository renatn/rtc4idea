package com.renatn.rtc4idea.vcs;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 22.11.13
 * Time: 17:36
 */
public class RtcProjectSettingsPanel extends JPanel {

    private JTextField myLoginInput = new JTextField();
    private JTextField myHostInput = new JTextField();

    public RtcProjectSettingsPanel(RtcProjectSettings projectSettings) {
        myLoginInput.setText(projectSettings.getLogin());
        myHostInput.setText(projectSettings.getHost());

        add(new JLabel("RTC settings"));
        add(new JLabel("Login:"));
        add(myLoginInput);
        add(new JLabel("Host:"));
        add(myHostInput);
    }

    public String getLogin() {
        return myLoginInput.getText().trim();
    }

    public void setLogin(String login) {
        myLoginInput.setText(login);
    }

    public String getHost() {
        return myHostInput.getText().trim();
    }

    public void setHost(String host) {
        myHostInput.setText(host);
    }

}
