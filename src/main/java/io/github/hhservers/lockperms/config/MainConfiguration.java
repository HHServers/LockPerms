package io.github.hhservers.lockperms.config;

import lombok.Data;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable @Data
public class MainConfiguration {

    @Setting(value = "lockperms")
    private MainConfiguration.CmdList general = new CmdList();

    @ConfigSerializable @Data
    public static class CmdList {
        @Setting(value="admin-password")
        public String adminPassword = "dEfAuLtPaSs";
        @Setting(value="is-active")
        public Boolean active = true;
        @Setting(value="pending-commands")
        public List<String> pendingCommands = new ArrayList<>();
    }
}
