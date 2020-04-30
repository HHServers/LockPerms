package io.github.hhservers.lockperms.command.base;

import io.github.hhservers.lockperms.LockPerms;
import io.github.hhservers.lockperms.config.MainConfiguration;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.List;

public class Log implements CommandExecutor {
    private static final LockPerms lockperms = LockPerms.getInstance();
    private MainConfiguration mainConfig;
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            mainConfig=lockperms.getMainConfig();
            List<String> cmdList = mainConfig.getGeneral().pendingCommands;
            List<Text> pageList = new ArrayList<>();
            pageList.add(TextSerializers.FORMATTING_CODE.deserialize("&l&8[&r&aINDEX &b~~ &dENTRY&l&8]&r"));
            for (int i = 0; i < cmdList.size() ; i++) {
                pageList.add(TextSerializers.FORMATTING_CODE.deserialize("&l&8-&r&a"+i+"&r&b ~~ &r&d"+cmdList.get(i)+"&r"));
            }
        PaginationList finishedPagList = PaginationList.builder()
                .padding(TextSerializers.FORMATTING_CODE.deserialize("&a=&d="))
                .title(TextSerializers.FORMATTING_CODE.deserialize("&l&8[&r&cLock&aPerms &bLog&r&l&8]&r"))
                .contents(pageList)
                .build();
            finishedPagList.sendTo(src);
        return CommandResult.success();
    }

    public static CommandSpec build() throws ObjectMappingException {
        return CommandSpec.builder()
                .permission("lockperms.admin")
                .child(LogRemove.build(), "remove")
                .executor(new Log())
                .build();
    }
}
