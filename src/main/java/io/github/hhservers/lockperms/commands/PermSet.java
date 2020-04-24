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
import org.spongepowered.api.text.serializer.TextSerializers;


public class PermSet implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        MainConfiguration mainConf = LockPerms.getMainConfig();
        String command = args.<String>getOne(Text.of("command")).get();
        String password = args.<String>getOne(Text.of("password")).get();
        String confPassword = LockPerms.getMainConfig().getCmdList().adminpassword;
        if(password.equals(confPassword)) {
            if(mainConf.getCmdList().getIsActive()){
                mainConf.getCmdList().setIsActive(false);
                LockPerms.getConfigLoader().saveConfig(mainConf);
                Sponge.getCommandManager().process(src, command);
                src.sendMessage(Text.of("Command processed: "+command));
                mainConf.getCmdList().setIsActive(true);
                LockPerms.getConfigLoader().saveConfig(mainConf);
            } else {
                Sponge.getCommandManager().process(src, command);
                src.sendMessage(Text.of("Command processed: "+command));
            }

        } else {src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&l&8[&r&cLock&aPerms&r&l&8] [&r&cIncorrect Password&r&l&8]&r"));}
        return CommandResult.success();

    }

    public static CommandSpec build() throws ObjectMappingException {
        return CommandSpec.builder()
                .permission("lockperms.admin")
                .arguments(GenericArguments.string(Text.of("command")), GenericArguments.string(Text.of("password")))
                .executor(new PermSet())
                .build();
    }
}