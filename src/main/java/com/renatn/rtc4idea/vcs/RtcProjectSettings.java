package com.renatn.rtc4idea.vcs;

import com.intellij.openapi.components.*;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

/**
 * User: Renat Nasyrov (renat@gmail.com)
 * Date: 13.11.13
 * Time: 18:27
 */

@State(
        name = "rtc4idea.settings",
        storages = @Storage(file = StoragePathMacros.WORKSPACE_FILE)
)
public class RtcProjectSettings implements PersistentStateComponent<Element> {

    private String myHost;
    private String myLogin;
    private String myPassword;

    @Nullable
    @Override
    public Element getState() {
        Element element = new Element("Rtc4IdeaSettings");
        element.setAttribute("Login", getLogin());
        element.setAttribute("Host", getHost());
        return element;
    }

    @Override
    public void loadState(Element element) {
        setLogin(element.getAttributeValue("Login"));
        setHost(element.getAttributeValue("Host"));
    }

    public String getLogin() {
        return myLogin != null ? myLogin : "sbt-nasyrov-rv";
    }

    public void setLogin(String login) {
        myLogin = login != null ? login : "";
    }

    public String getHost() {
        return myHost != null ? myHost : "https://hs22-33.ca.sbrf.ru:9443/jazz";
    }

    public void setHost(String host) {
        myHost = host != null ? host : "";
    }

}
