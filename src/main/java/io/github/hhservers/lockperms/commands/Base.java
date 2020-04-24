package io.github.hhservers.lockperms.commands;

import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;


public class Base implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("/lockperms confirm PASSWORD\n/lockperms log\n/lockperms active TRUE/FALSE PASSWORD\n/lockperms permset \"lp user USER permission set PERMISSION\" PASSWORD"));
        return CommandResult.success();

    }

    public static CommandSpec build() throws ObjectMappingException {
        return CommandSpec.builder()
                .permission("lockperms.admin")
                .executor(new Base())
                .child(Confirm.build(), "confirm")
                .child(Log.build(), "log")
                .child(IsActive.build(), "active")
                .child(PermSet.build(), "permset")
                .build();
    }
}