package io.github.hhservers.lockperms.config;

import com.google.common.reflect.TypeToken;
import io.github.hhservers.lockperms.LockPerms;
import lombok.Data;
import lombok.Getter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class ManagerConfig {

    //private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
    private ConfigurationOptions options;
    private MainConfiguration mainConfiguration;


    private static final LockPerms lockperms = LockPerms.getInstance();
    private static Path confDir = lockperms.getConfigDir();
    public static Path configFile = Paths.get(confDir + "/LockPerms.json");
    public static GsonConfigurationLoader configLoader = GsonConfigurationLoader.builder().setPath(configFile).build();
    public static ConfigurationNode configNode;

    public static MainConfiguration reloadConfig() throws IOException, ObjectMappingException {
        LockPerms instance = LockPerms.getInstance();
        Path configDir = instance.getConfigDir();
        if (!Files.exists(configDir)) {
            Files.createDirectories(configDir);
        }
        configNode = configLoader.load();
        MainConfiguration config = configNode.getValue(TypeToken.of(MainConfiguration.class), new MainConfiguration());
        if (!Files.exists(configFile)) {
            Files.createFile(configFile);
            instance.getLogger().info("Created default configuration!");
        }
        configNode.setValue(TypeToken.of(MainConfiguration.class), config);
        configLoader.save(configNode);
        instance.getLogger().info("Config loaded JSON");
        return config;
    }

    public static void saveConfig() throws IOException, ObjectMappingException {
        MainConfiguration config = configNode.getValue(TypeToken.of(MainConfiguration.class), new MainConfiguration());
        configLoader.save(configLoader.createEmptyNode().setValue(TypeToken.of(MainConfiguration.class), config));
    }





    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*public ManagerConfig(ConfigurationLoader<CommentedConfigurationNode> loader){
        configurationLoader = loader;
        options = ConfigurationOptions.defaults().setShouldCopyDefaults(true);
        update();
    }*/

    /*public void update(){
        try{
            CommentedConfigurationNode node = configurationLoader.load(options);
            MainConfiguration cfg = node.getValue(TypeToken.of(MainConfiguration.class), new MainConfiguration());
            configurationLoader.save(node);
            mainConfiguration = cfg;
        } catch (IOException | ObjectMappingException ex){
            ex.printStackTrace();
        }
    }*/



}
