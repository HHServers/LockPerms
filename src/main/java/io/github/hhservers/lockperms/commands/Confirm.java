package io.github.hhservers.lockperms.commands;

import com.google.common.reflect.TypeToken;
import io.github.hhservers.lockperms.LockPerms;
import io.github.hhservers.lockperms.config.ConfigManager;
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
        String pass = ConfigManager.confNode.getNode("Commands", "adminpassword").getString();
        Boolean activeTrue = true;
        Boolean activeFalse = false;
        List<String> configCmds = null;
        //LockPerms baseInst = new LockPerms();
        try {
            configCmds = ConfigManager.confNode.getNode("Commands", "CommandList").getList(TypeToken.of(String.class));
        if(args.<String>getOne(Text.of("password")).get().equals(pass)){
            src.sendMessage(Text.of("Password accepted"));
            ConfigManager.confNode.getNode("Commands", "isActive").setValue(activeFalse);
            ConfigManager.saveThings();
            for (int i = 0; i < configCmds.size(); i++) {
                Sponge.getCommandManager().process(Sponge.getServer().getConsole().getCommandSource().get(), configCmds.get(i));
                src.sendMessage(Text.of("IsActive:"+ConfigManager.confNode.getNode("Commands", "isActive").getBoolean()));
                src.sendMessage(Text.of(configCmds.get(i)));
            }
            List<String> blankList = new ArrayList<>();
            ConfigManager.confNode.getNode("Commands", "CommandList").setValue(blankList);
            ConfigManager.saveThings();
        } else {Text.of("Uh oh. Ur in trouble.");}
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        ConfigManager.confNode.getNode("Commands", "isActive").setValue(activeTrue);
        ConfigManager.saveThings();
        return CommandResult.success();
    }

    public static CommandSpec build() throws ObjectMappingException {
        return CommandSpec.builder()
                .arguments(GenericArguments.string(Text.of("password")))
                .permission("butils.user")
                .executor(new Confirm())
                .build();
    }
}
