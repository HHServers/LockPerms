package io.github.hhservers.lockperms.commands;

import io.github.hhservers.lockperms.LockPerms;
import io.github.hhservers.lockperms.config.MainConfiguration;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class IsActive implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        LockPerms lockPerms = LockPerms.getInstance();
        MainConfiguration mainConf = LockPerms.getMainConfig();
        Boolean playerChoice = args.<Boolean>getOne(Text.of("active")).get();
        String password = args.<String>getOne(Text.of("password")).get();
        String confPassword = LockPerms.getMainConfig().getCmdList().adminpassword;
        if(password.equals(confPassword)) {
            mainConf.getCmdList().setIsActive(playerChoice);
            LockPerms.getConfigLoader().saveConfig(mainConf);
            mainConf = LockPerms.getConfigLoader().getMainConf();
        } else {src.sendMessage(TextSerializers.FORMATTING_CODE.deserialize("&l&8[&r&cLock&aPerms&r&l&8] [&r&cIncorrect Password&r&l&8]&r"));}

        return CommandResult.success();
    }

    public static CommandSpec build(){
        return CommandSpec.builder()
                .permission("lockperms.admin")
                .arguments(GenericArguments.bool(Text.of("active")), GenericArguments.string(Text.of("password")))
                .executor(new IsActive())
                .build();
    }
}
