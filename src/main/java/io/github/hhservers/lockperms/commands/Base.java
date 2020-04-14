package io.github.hhservers.lockperms.commands;

import com.google.common.reflect.TypeToken;
import io.github.hhservers.lockperms.config.ConfigManager;
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

public class Base implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        return CommandResult.success();

    }

    public static CommandSpec build() throws ObjectMappingException {
        return CommandSpec.builder()
                .permission("butils.user.base")
                .executor(new Base())
                .child(Confirm.build(), "confirm")
                .child(Log.build(), "log")
                .build();
    }
}