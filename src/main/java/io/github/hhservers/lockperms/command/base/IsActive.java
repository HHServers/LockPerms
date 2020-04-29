package io.github.hhservers.lockperms.command.base;

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

public class IsActive implements CommandExecutor {
    
    private final LockPerms lockPerms = LockPerms.getInstance();
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        MainConfiguration mainConf = lockPerms.getMainConfig();
        boolean active = args.<Boolean>getOne(Text.of("active")).get();
        String password = args.<String>getOne(Text.of("password")).get();
        String confPassword = lockPerms.getMainConfig().getGeneral().adminPassword;
        if(password.equals(confPassword)) {
            mainConf.getGeneral().setActive(active);
            lockPerms.getConfigurationManager().saveConfig(mainConf);
            if(active){
                lockPerms.sendSuccessMessage(src, "You have enabled LockPerms!");
            } else {
                lockPerms.sendErrorMessage(src, "You have disabled LockPerms!");
            }
            lockPerms.getLogger().debug("Updated the status of LockPerms");
        } else {
            lockPerms.sendErrorMessage(src, "The password specified is incorrect!");
        }

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
