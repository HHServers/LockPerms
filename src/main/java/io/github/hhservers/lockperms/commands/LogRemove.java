package io.github.hhservers.lockperms.commands;

import io.github.hhservers.lockperms.LockPerms;
import io.github.hhservers.lockperms.config.MainConfiguration;
import io.github.hhservers.lockperms.config.ConfigLoader;
import lombok.SneakyThrows;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class LogRemove implements CommandExecutor {

    @SneakyThrows
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Integer removeIndex = args.<Integer>getOne(Text.of("index")).get();
            src.sendMessage(Text.of("Command with index "+removeIndex+" removed from list."));
        LockPerms.getInstance().removeLogEntry(removeIndex);
            /*ConfigLoader loader = LockPerms.getConfigLoader();
        MainConfiguration conf = LockPerms.getMainConfig();
            LockPerms.getInstance().getLogger().info(loader.toString()+ conf.toString());
            conf.getCmdList().commands.remove(removeIndex);
            LockPerms.getInstance().getLogger().info("After Removal attempt:"+ loader.toString()+ conf.toString());
            loader.saveConfig(conf);
            conf =LockPerms.getMainConfig();*/
        return CommandResult.success();
    }

    public static CommandSpec build() throws ObjectMappingException {
        return CommandSpec.builder()
                .permission("lockperms.admin")
                .arguments(GenericArguments.integer(Text.of("index")))
                .executor(new LogRemove())
                .build();
    }
}
