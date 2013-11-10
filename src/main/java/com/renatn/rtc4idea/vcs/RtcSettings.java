package com.renatn.rtc4idea.vcs;

/**
 * Created with IntelliJ IDEA.
 * User: Renat Nasyrov <renatn@gmail.com>
 * Date: 11.10.13
 * Time: 17:40
 */
public class RtcSettings {

    public static final String REPO_URL = "https://<jazz server>:9443/jazz";
    public static final String PROJECT_NAME = "<Project Name>";
    public static final String WORKSPACE_PATH = "<Path to .jazz5>";

    public static String getRepoUrl() {
        return REPO_URL;
    }

    public static String getUsername() {
        return "username";
    }

    public static String getPassword() {
        return "password";
    }

}
