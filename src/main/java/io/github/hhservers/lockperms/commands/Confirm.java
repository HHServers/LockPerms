package io.github.hhservers.lockperms.commands;

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
        MainConfiguration conf = LockPerms.getMainConfig();
        String pass = conf.getCmdList().adminpassword;
        Boolean activeTrue = true;
        Boolean activeFalse = false;
        List<String> configCmds = conf.getCmdList().commands;


        if(args.<String>getOne(Text.of("password")).get().equals(pass)){
            src.sendMessage(Text.of("Password accepted"));
            conf.getCmdList().setIsActive(activeFalse);
            for (int i = 0; i < configCmds.size(); i++) {
                Sponge.getCommandManager().process(src, configCmds.get(i));
                src.sendMessage(Text.of(configCmds.get(i)));
            }
            List<String> blankList = new ArrayList<>();
            conf.getCmdList().setCommands(blankList);
        } else {Text.of("Uh oh. Ur in trouble.");}

        conf.getCmdList().setIsActive(activeTrue);
        LockPerms.getConfigLoader().saveConfig(conf);
        return CommandResult.success();
    }

    public static CommandSpec build() throws ObjectMappingException {
        return CommandSpec.builder()
                .arguments(GenericArguments.string(Text.of("password")))
                .permission("lockperms.admin")
                .executor(new Confirm())
                .build();
    }
}
