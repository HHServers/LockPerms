package io.github.hhservers.lockperms.config;

import lombok.Data;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable @Data
public class MainConfiguration {

    @Setting(value = "Commands")
    private MainConfiguration.CmdList cmdList = new CmdList();

    @ConfigSerializable @Data
    public class CmdList {
        @Setting(value="adminpassword")
        public String adminpassword = "defaultpass";
        @Setting(value="isActive")
        public Boolean isActive = true;
        @Setting(value="CommandList")
        public List<String> commands = new ArrayList<>();
    }
}
