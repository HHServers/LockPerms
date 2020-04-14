package io.github.hhservers.lockperms.commands;

import io.github.hhservers.lockperms.LockPerms;
import io.github.hhservers.lockperms.config.MainConfiguration;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public class Log implements CommandExecutor {
    private static final LockPerms lockperms = LockPerms.getInstance();
    private MainConfiguration mainConfig;
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of(TextColors.GREEN, "LOG:"));
            mainConfig=lockperms.getMainConfig();
            List<String> cmdList = mainConfig.getCmdList().commands;
            for (int i = 0; i < cmdList.size() ; i++) {
                src.sendMessage(Text.of("Index:"+i+" ~~ "+cmdList.get(i)));
            }

        return CommandResult.success();
    }

    public static CommandSpec build() throws ObjectMappingException {
        return CommandSpec.builder()
                .permission("butils.admin")
                .child(LogRemove.build(), "remove")
                .executor(new Log())
                .build();
    }
}
