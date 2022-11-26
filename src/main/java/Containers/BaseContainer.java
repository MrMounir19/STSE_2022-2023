package Containers;

import Utils.Messages;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Parent of all containers, named BaseContainer.
 * New containers can be made by extending from this class.
 * @author Maxim
 * @author Thimoty
 * @version 1.0
 * @since 25/11/2022
 */
public class BaseContainer {
    protected ArrayList<AgentController> agents = new ArrayList<>();
    protected AgentContainer agentContainer = null;
    protected Properties properties = null;
    protected Runtime runtime = null;
    protected Profile profile = null;
    protected JsonObject config;

    public BaseContainer() {
        String defaultConfig = "{'agents': [], 'use_timestamp': true}";
        this.config = Messages.toJson(defaultConfig);
    }

    public void setConfigFromPath(String path) {
        String configContent;

        try {
            configContent = Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setConfig(Messages.toJson(configContent));
    }

    public void setConfig(JsonObject config) {
        this.config = config;
    }

    protected void createAgents() {
        try {
            for (JsonElement element : config.get("agents").getAsJsonArray()) {
                String agentName = element.getAsString();
                String nickname = agentName;
                if (config.get("use_timestamp").getAsBoolean()) {
                    nickname = nickname + Instant.now().getEpochSecond();
                }
                System.out.println("\t- Creating " + agentName + " (" + nickname + ")");
                agents.add(agentContainer.createNewAgent(nickname, "Agents." + agentName, new Object[]{}));
            }
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
    }

    protected void createRuntime() {
        runtime = Runtime.instance();
    }

    protected void createProperties() {
        properties = new ExtendedProperties();
        properties.setProperty(Profile.GUI, "true");
        properties.setProperty(Profile.LOCAL_HOST, "127.0.0.1");
        properties.setProperty(Profile.LOCAL_PORT, "1099");
    }

    protected void createProfile() {
        profile = new ProfileImpl(properties);
    }

    protected void createContainer(){
        agentContainer = runtime.createMainContainer(profile);
    }

    protected void startAgents(){
        for (AgentController agent : agents) {
            try {
                System.out.println("\t- Starting " + agent.getName());
                agent.start();
            } catch (StaleProxyException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void start() {
        System.out.println("[ ] Container starting...");
        try {
            System.out.println("\t[ ] Creating runtime...");
            createRuntime();
            System.out.println("\t[X] Runtime created!");
            System.out.println("\t[ ] Creating properties...");
            createProperties();
            System.out.println("\t[X] Properties created!");
            System.out.println("\t[ ] Creating profile...");
            createProfile();
            System.out.println("\t[X] Profile created!");
            System.out.println("\t[ ] Creating container...");
            createContainer();
            System.out.println("\t[X] Container created!");
            System.out.println("\t[ ] Creating agent(s)...");
            createAgents();
            System.out.println("\t[X] Agent(s) created!");
            System.out.println("\t[ ] Starting agent(s)...");
            startAgents();
            System.out.println("\t[X] Agent(s) started!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[X] Container started!");
    }
}
