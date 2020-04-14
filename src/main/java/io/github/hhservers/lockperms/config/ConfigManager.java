package io.github.hhservers.lockperms.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    public static final String confName = "LockPerms.conf";
    public static ConfigurationLoader<CommentedConfigurationNode> loader;
    public static CommentedConfigurationNode confNode;
    private static Path dir, conf;

    public static void setupThings(Path folder) {

        dir = folder;
        conf = dir.resolve(confName);
        doThings();

    }

    public static void doThings() {
        try {
            if(Files.notExists(dir)){
                Files.createDirectory(dir);
            }
            Asset asset = Sponge.getAssetManager().getAsset(Sponge.getPluginManager().getPlugin("lockperms").get(), confName).get();
            asset.copyToDirectory(dir);
            loader = HoconConfigurationLoader.builder().setPath(conf).build();
            confNode = loader.load();
            saveThings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveThings() {
        try {
            loader.save(confNode);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
