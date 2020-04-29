package io.github.hhservers.lockperms;

import com.google.inject.Inject;
import io.github.hhservers.lockperms.command.BaseCommand;
import io.github.hhservers.lockperms.config.MainConfiguration;
import io.github.hhservers.lockperms.config.ConfigurationManager;
import lombok.Getter;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Getter
    private MainConfiguration mainConfig;

    @Getter
    private ConfigurationManager configurationManager;

    @Inject @Getter
    private Logger logger;

    @Inject
    @Getter
    @ConfigDir(sharedRoot = false)
    private File configDir;

    @Inject
    @Getter
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;

    @Listener
    public void onGamePreInit(GamePreInitializationEvent e) {
        instance = this;
        configurationManager = new ConfigurationManager();

        /* Load the configuration */
        if(configurationManager.loadConfig()){
            this.mainConfig = configurationManager.getMainConf();
            this.logger.debug("Successfully loaded the configuration");
        } else {
            this.logger.error("There was an issue while loading the configuration!");
        }
    }

    @Listener
    public void onGameInit(GameInitializationEvent e) throws ObjectMappingException {
        Sponge.getCommandManager().register(instance, BaseCommand.build(), "lockperms", "lockp");
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {}

    @Listener
    public void onGameReload(GameReloadEvent e) {
        this.configurationManager.loadConfig();
        this.logger.debug("Successfully reloaded LockPerms configuration");
    }

    @Listener
    public void onCommandSendEvent(SendCommandEvent e, @First CommandSource src) {
        String cmd = e.getCommand() + " " + e.getArguments();
        List<String> cmdSegments = new ArrayList<>(Arrays.asList(cmd.split(" ")));

        if(cmd.startsWith("luckperms") || cmd.startsWith("lp") || cmd.startsWith("perm")){
            if(mainConfig.getGeneral().active){
                if(cmd.contains("--password")){
                    int param = cmdSegments.indexOf("--password");
                    String password = cmdSegments.get(param + 1);
                    String newCommand = cmd.replace(" --password", "").replace(" " + password, "");
                    if(password.equals(mainConfig.getGeneral().adminPassword)){
                        e.setCommand(newCommand);
                        return;
                    } else {
                        cmd = newCommand;
                        src.sendMessage(Text.of(TextColors.RED, "The password specified is incorrect!"));
                    }
                }
                e.setCancelled(true);
                this.sendInfoMessage(src, "Logged LP command for confirmation by admin");
                mainConfig.getGeneral().pendingCommands.add(cmd);
                this.configurationManager.saveConfig(mainConfig);
                this.mainConfig = configurationManager.getMainConf();

            }
        }
    }

    public void removeLogEntry(Integer index){
        mainConfig.getGeneral().pendingCommands.remove(mainConfig.getGeneral().pendingCommands.get(index));
        configurationManager.saveConfig(mainConfig);
        mainConfig = configurationManager.getMainConf();
    }

    public static LockPerms getInstance(){
        return instance;
    }

    public void sendInfoMessage(CommandSource source, String message){
        source.sendMessage(Text.builder()
                .append(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "["))
                .append(Text.of(TextStyles.RESET, TextColors.AQUA, message))
                .append(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "]"))
                .build());
    }

    public void sendErrorMessage(CommandSource source, String message){
        source.sendMessage(Text.builder()
                .append(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "["))
                .append(Text.of(TextStyles.RESET, TextColors.RED, message))
                .append(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "]"))
                .build());
    }

    public void sendSuccessMessage(CommandSource source, String message){
        source.sendMessage(Text.builder()
                .append(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "["))
                .append(Text.of(TextStyles.RESET, TextColors.GREEN, message))
                .append(Text.of(TextColors.DARK_GRAY, TextStyles.BOLD, "]"))
                .build());
    }
}
