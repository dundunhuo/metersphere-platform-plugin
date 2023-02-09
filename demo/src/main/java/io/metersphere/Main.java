package io.metersphere;

import io.metersphere.plugin.loader.PluginManager;
import io.metersphere.plugin.storage.LocalStorageStrategy;
import io.metersphere.platform.api.Platform;
import io.metersphere.platform.domain.GetOptionRequest;
import io.metersphere.platform.domain.PlatformRequest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String jiraPath = "D:\\source\\metersphere-platform-plugin\\metersphere-zentao-plugin\\target\\metersphere-zentao-plugin-2.5.0-jar-with-dependencies.jar";

        PluginManager pluginManager = new PluginManager();

        FileInputStream in = new FileInputStream(jiraPath);

        try {
            pluginManager
                    .loadJar("JIRA", in, new LocalStorageStrategy("D://plugin" + "/JIRA"));
            pluginManager.getClassLoader("JIRA").getStorageStrategy().delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GetOptionRequest getOptionRequest = new GetOptionRequest();
        getOptionRequest.setOptionMethod("getIssueTypes");
        getOptionRequest.setProjectConfig("");
        PlatformRequest request = new PlatformRequest();
        request.setIntegrationConfig("");
        System.out.println(pluginManager.getImplInstance("JIRA", Platform.class, request)
                .getProjectOptions(getOptionRequest));

        pluginManager.getImplInstance("JIRA", Platform.class, request)
                .validateProjectConfig("");
    }

}
