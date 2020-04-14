package io.github.hhservers.lockperms;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import io.github.hhservers.lockperms.commands.Base;
import io.github.hhservers.lockperms.config.ConfigManager;
import io.github.hhservers.lockperms.config.MainConfiguration;
import io.github.hhservers.lockperms.config.ManagerConfig;
import lombok.Getter;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "lockperms",
        name = "LockPerms",
        description = "blvxr",
        authors = {
                "blvxr"
        }
)
public class LockPerms {

    private static LockPerms instance;

    public static Boolean isActive;

    private MainConfiguration mainConfig;
    private ManagerConfig managerConfig;

    @Inject
    @Getter
    @ConfigDir(sharedRoot = false)
    private Path defaultConfig;
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    @Inject
    private Logger logger;

    public Logger getLogger(){
        return logger;
    }


    @Listener
    public void onGamePreInit(GamePreInitializationEvent e) {

    }

    @Listener
    public void onGameInit(GameInitializationEvent e) throws ObjectMappingException {
        instance = this;
        Sponge.getCommandManager().register(instance, Base.build(), "lockperms", "lockp");
        isActive=true;
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        //managerConfig = new ManagerConfig(loader);
    }

    @SuppressWarnings("warningToken")
    public void reload() throws IOException, ObjectMappingException {
        mainConfig = ManagerConfig.reloadConfig();
    }

    @Listener
    public void onGameReload(GameReloadEvent e) throws IOException, ObjectMappingException {
        reload();
    }

    @Listener
    public void commandSentEvent(SendCommandEvent e, @First CommandSource src) throws ObjectMappingException, IOException {
        String cmd = e.getCommand();
        String[] cmdArray = cmd.split(" ");

        if(cmdArray[0].equals("lp") || cmdArray.equals("luckperms:lp")){
            MainConfiguration config = new MainConfiguration();
            if(config.getCmdList().isActive) {
                e.setCancelled(true);
                src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&l&8[&bLogged LP command for confirmation by admin.&r&l&8]"));
                String finalCmdString = e.getCommand() + " " + e.getArguments();
                config.getCmdList().commands.add(finalCmdString);
                ManagerConfig.saveConfig();
            }
        }
    }

    public static LockPerms getInstance(){
        return instance;
    }

    public Path getConfigDir() {
        return defaultConfig;
    }
}
