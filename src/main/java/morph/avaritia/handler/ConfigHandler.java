package morph.avaritia.handler;

import codechicken.lib.configuration.ConfigFile;
import codechicken.lib.configuration.ConfigTag;
import com.google.common.collect.Lists;
import morph.avaritia.util.Lumberjack;
import org.apache.logging.log4j.Level;

import java.io.File;

public class ConfigHandler {

    public static final String CONFIG_VERSION = "1.0";
    public static ConfigFile config;

    private static String current_version;

    public static boolean endStone = true;
    public static boolean bedrockBreaker = true;
    public static boolean boringFood = false;
    public static boolean fracturedOres = false;
    public static boolean verboseCraftTweekerLogging = false;

    public static boolean copper = true;
    public static boolean tin = true;
    public static boolean silver = true;
    public static boolean lead = true;
    public static boolean steel = true;
    public static boolean nickel = true;
    public static boolean fluxed = true;
    public static boolean enderium = true;
    public static boolean darkSteel = true;
    public static boolean platinum = true;
    public static boolean iridium = true;

    public static int modifier = 0;
    public static int multiplier = 0;

    public static void init(File file) {
        config = new ConfigFile(file, false);
        try {
            config.load();
        } catch (Exception e) {
            Lumberjack.big(Level.WARN, "Found forge based Avaritia config, the config will be wiped and defaults will be used.");
            config.clear();
            current_version = CONFIG_VERSION;
        }
        config.setTagVersion(CONFIG_VERSION);
        loadConfig();
    }

    public static void loadConfig() {

        ConfigTag tag;

        /* GENERAL */
        {
            ConfigTag general = config.getTag("general");
            general.setComment("General configuration of Avaritia components.");

            tag = general.getTag("use_end_stone");
            tag.setComment("Disable to take end stone out of recipes some of Avaritia's recipes.");
            endStone = tag.setDefaultBoolean(true).getBoolean();

            tag = general.getTag("break_bedrock");
            tag.setComment("Disable if you don't want the World Breaker to break unbreakable blocks.");
            bedrockBreaker = tag.setDefaultBoolean(true).getBoolean();

            tag = general.getTag("boring_food");
            tag.setComment("Enable to keep the Ultimate Stew and Cosmic Meatballs from grabbing more ingredients.");
            boringFood = tag.setDefaultBoolean(false).getBoolean();

            tag = general.getTag("fractured_ores");
            tag.setComment("Enable if you don't have RotaryCraft installed and want some buggy fractured ores.");
            fracturedOres = tag.setDefaultBoolean(false).getBoolean();

            tag = general.getTag("aoe_trash_defaults");
            tag.setComment("These are the OreDictionary ID's for default trashed items. These are synced from the server to the client. And will appear as defaults on the client also. Clients can override these, They are defaults not Musts.");
            String[] defaults = { "dirt", "sand", "gravel", "cobblestone", "netherrack", "stoneGranite", "stoneDiorite", "stoneAndesite" };
            tag.setDefaultStringList(Lists.newArrayList(defaults));
            AvaritiaEventHandler.defaultTrashOres.clear();
            AvaritiaEventHandler.defaultTrashOres.addAll(tag.getStringList());

            tag = general.getTag("verbose_craft_tweaker_logging");
            tag.setComment("Enables verbose logging of actions taken on avaritia by CraftTweaker scripts. Only effects CraftTweakers script log file.");
            verboseCraftTweekerLogging = tag.setDefaultBoolean(false).getBoolean();
        }

        {
            ConfigTag materials = config.getTag("materials");
            materials.setComment("Disable to stop using that material in recipes. Useful if a mod adds unobtainable placeholder ores.");

            copper = materials.getTag("copper").setDefaultBoolean(true).getBoolean();
            tin = materials.getTag("tin").setDefaultBoolean(true).getBoolean();
            silver = materials.getTag("silver").setDefaultBoolean(true).getBoolean();
            lead = materials.getTag("lead").setDefaultBoolean(true).getBoolean();
            nickel = materials.getTag("nickel").setDefaultBoolean(true).getBoolean();
            steel = materials.getTag("steel").setDefaultBoolean(true).getBoolean();
            fluxed = materials.getTag("fluxed").setDefaultBoolean(true).getBoolean();
            enderium = materials.getTag("enderium").setDefaultBoolean(true).getBoolean();
            darkSteel = materials.getTag("dark_steel").setDefaultBoolean(true).getBoolean();
            platinum = materials.getTag("platinum").setDefaultBoolean(false).getBoolean();
            iridium = materials.getTag("iridium").setDefaultBoolean(false).getBoolean();
        }

        {
            ConfigTag balance = config.getTag("balance");
            balance.setComment("Balance modifications for the Compressor.");

            tag = balance.getTag("cost_modifier");
            tag.setComment("Added to the existing modifier to make prices more expensive or cheaper. Can be negative.");
            modifier = tag.setDefaultInt(0).getInt();

            tag = balance.getTag("cost_multiplier");
            tag.setComment("Added to the existing multiplier to make prices more expensive or cheaper. Can be negative.");
            multiplier = tag.setDefaultInt(0).getInt();
        }

        config.save();
    }
}
