package io.github.hhservers.lockperms.command;

import io.github.hhservers.lockperms.command.base.Confirm;
import io.github.hhservers.lockperms.command.base.IsActive;
import io.github.hhservers.lockperms.command.base.Log;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class BaseCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {
        Text.Builder builder = Text.builder();
        builder.append(Text.of(TextColors.GREEN, "LockPerms Commands:")).append(Text.NEW_LINE)
                .append(Text.of(TextColors.YELLOW, "/lockperms confirm <password>")).append(Text.NEW_LINE)
                .append(Text.of(TextColors.YELLOW, "/lockperms active <true|false> <password>")).append(Text.NEW_LINE)
                .append(Text.of(TextColors.YELLOW, "/lockperms log")).append(Text.NEW_LINE);
        src.sendMessage(builder.build());
        return CommandResult.success();

    }

    public static CommandSpec build() throws ObjectMappingException {
        return CommandSpec.builder()
                .permission("lockperms.admin")
                .executor(new BaseCommand())
                .child(Confirm.build(), "confirm")
                .child(Log.build(), "log")
                .child(IsActive.build(), "active")
                .build();
    }
}