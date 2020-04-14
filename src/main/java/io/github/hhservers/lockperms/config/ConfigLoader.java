package io.github.hhservers.lockperms.config;

import com.google.common.reflect.TypeToken;
import io.github.hhservers.lockperms.LockPerms;
import lombok.Data;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;



import java.io.File;



@Data
public class ConfigLoader {

  private static final LockPerms instance = LockPerms.getInstance();
  private final LockPerms plugin;

  private MainConfiguration mainConf;
  private ConfigurationLoader<CommentedConfigurationNode> configLoad;

    public ConfigLoader(LockPerms pl) {
        this.plugin = pl;
        if (!plugin.getConfigDir().exists()) {
            plugin.getConfigDir().mkdirs();
        }
    }

    public boolean loadConfig() {
        try {
            File file = new File(plugin.getConfigDir(), "LockPerms.conf");
            if (!file.exists()) {
                file.createNewFile();
            }
            configLoad = HoconConfigurationLoader.builder().setFile(file).build();
            CommentedConfigurationNode config = configLoad.load(ConfigurationOptions.defaults().setObjectMapperFactory(plugin.getFactory()).setShouldCopyDefaults(true));
            mainConf = config.getValue(TypeToken.of(MainConfiguration.class), new MainConfiguration());
            configLoad.save(config);
            return true;
        } catch (Exception e) {
            plugin.getLogger().error("Could not load config.", e);
            return false;
        }
    }

    public void saveConfig(MainConfiguration newConfig) {
        try {
            File file = new File(plugin.getConfigDir(), "LockPerms.conf");
            if (!file.exists()) {
                file.createNewFile();
            }
            CommentedConfigurationNode config = configLoad.load(ConfigurationOptions.defaults().setObjectMapperFactory(plugin.getFactory()).setShouldCopyDefaults(true));
            config.setValue(TypeToken.of(MainConfiguration.class), newConfig);
            configLoad.save(config);
        } catch (Exception e) {
            LockPerms.getInstance().getLogger().info("Error"+e);
        }
    }

    public ConfigurationLoader<CommentedConfigurationNode> getLoader(){
        try {
            File file = new File(plugin.getConfigDir(), "LockPerms.conf");
            if (!file.exists()) {
                file.createNewFile();
            }
        configLoad = HoconConfigurationLoader.builder().setFile(file).build();
        } catch (Exception e) {
            LockPerms.getInstance().getLogger().info("Error"+e);
        }
        return configLoad;
    }


















    /*
    //a mess a huge big MESS/////////////////////////
    //private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
    private ConfigurationOptions options;
    private MainConfiguration mainConfiguration;


    private static final LockPerms lockperms = LockPerms.getInstance();
    private static Path confDir = lockperms.getConfigDir();
    public static Path configFile = Paths.get(confDir + "/LockPerms.json");
    public static GsonConfigurationLoader configLoader = GsonConfigurationLoader.builder().setPath(configFile).build();
    public static ConfigurationNode configNode;

    public static MainConfiguration reloadConfig() throws IOException, ObjectMappingException {
        LockPerms instance = LockPerms.getInstance();
        Path configDir = instance.getConfigDir();
        if (!Files.exists(configDir)) {
            Files.createDirectories(configDir);
        }
        configNode = configLoader.load();
        MainConfiguration config = configNode.getValue(TypeToken.of(MainConfiguration.class), new MainConfiguration());
        if (!Files.exists(configFile)) {
            Files.createFile(configFile);
            instance.getLogger().info("Created default configuration!");
        }
        configNode.setValue(TypeToken.of(MainConfiguration.class), config);
        configLoader.save(configNode);
        instance.getLogger().info("Config loaded JSON");
        return config;
    }

    public static void saveConfig() throws IOException, ObjectMappingException {
        MainConfiguration config = configNode.getValue(TypeToken.of(MainConfiguration.class), new MainConfiguration());
        configLoader.save(configLoader.createEmptyNode().setValue(TypeToken.of(MainConfiguration.class), config));
    }

*/



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*public ManagerConfig(ConfigurationLoader<CommentedConfigurationNode> loader){
        configurationLoader = loader;
        options = ConfigurationOptions.defaults().setShouldCopyDefaults(true);
        update();
    }*/

    /*public void update(){
        try{
            CommentedConfigurationNode node = configurationLoader.load(options);
            MainConfiguration cfg = node.getValue(TypeToken.of(MainConfiguration.class), new MainConfiguration());
            configurationLoader.save(node);
            mainConfiguration = cfg;
        } catch (IOException | ObjectMappingException ex){
            ex.printStackTrace();
        }
    }*/



}
