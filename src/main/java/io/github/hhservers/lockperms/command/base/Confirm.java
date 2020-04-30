package io.github.hhservers.lockperms.command.base;

import io.github.hhservers.lockperms.LockPerms;
import io.github.hhservers.lockperms.config.MainConfiguration;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Confirm implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        MainConfiguration conf = LockPerms.getInstance().getMainConfig();
        String pass = conf.getGeneral().adminPassword;
        List<String> configCmds = conf.getGeneral().pendingCommands;

        if(args.<String>getOne(Text.of("password")).get().equals(pass)){
            LockPerms.getInstance().sendSuccessMessage(src, "Password Accepted! Your pending commands will now run.");
            conf.getGeneral().setActive(false);
            for(String command : configCmds){
                Sponge.getCommandManager().process(src, command.replace("/", ""));
            }
            conf.getGeneral().setActive(true);
            conf.getGeneral().setPendingCommands(new ArrayList<>());
            LockPerms.getInstance().getConfigurationManager().saveConfig(conf);
        } else {
            LockPerms.getInstance().sendErrorMessage(src, "The password specified is incorrect!");
        }
        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .arguments(GenericArguments.string(Text.of("password")))
                .permission("lockperms.admin")
                .executor(new Confirm())
                .build();
    }
}
