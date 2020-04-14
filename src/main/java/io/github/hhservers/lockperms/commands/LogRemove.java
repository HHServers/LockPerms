package io.github.hhservers.lockperms.commands;

import io.github.hhservers.lockperms.LockPerms;
import io.github.hhservers.lockperms.config.MainConfiguration;
import io.github.hhservers.lockperms.config.ConfigLoader;
import lombok.SneakyThrows;
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
    private MainConfiguration conf;
    private ConfigLoader loader;

    @SneakyThrows
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Integer removeIndex = args.<Integer>getOne(Text.of("index")).get();
            src.sendMessage(Text.of("Command with index "+removeIndex+" removed from list."));
            conf=LockPerms.getInstance().getMainConfig();
            loader=LockPerms.getInstance().getConfigLoader();
            conf.getCmdList().commands.remove(removeIndex);
            loader.saveConfig(conf);
            conf = loader.getMainConfig();


            //mainConfig.getCmdList().commands.remove(removeIndex);
        return CommandResult.success();
    }

    public static CommandSpec build() throws ObjectMappingException {
        return CommandSpec.builder()
                .permission("butils.admin")
                .arguments(GenericArguments.integer(Text.of("index")))
                .executor(new LogRemove())
                .build();
    }
}
