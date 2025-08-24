package org.fish.anticlout.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

import java.io.*;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class Config {
    public boolean blockSkin = true;
    public boolean blockName = true;
    public String name = "lil pup";
    public boolean blockMap = true;
    public double mapDistance = 30.0;


    public static void createFile() {
        Path configPath = FabricLoader.getInstance().getConfigDir();
        File uuidFile = configPath.resolve("AntiClout.json").toFile();

        try {
            if (!uuidFile.exists()) {
                System.out.println("Creating UUID file");
                uuidFile.createNewFile();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        Path configPath = FabricLoader.getInstance().getConfigDir();
        File uuidFile = configPath.resolve("AntiClout.json").toFile();
        try (FileWriter writer = new FileWriter(uuidFile)) {
            Config config = new Config();
            config.blockSkin = AntiCloutScreen.blockSkin;
            config.blockName = AntiCloutScreen.blockName;
            config.name = AntiCloutScreen.name;
            config.blockMap = AntiCloutScreen.blockMaps;
            config.mapDistance = AntiCloutScreen.mapBlockDistance;

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(config, writer);
        } catch (IOException e) {
            System.err.println("Failed to write to file: " + e.getMessage());
        }
    }

    public static void loadConfig() {
        Path configPath = FabricLoader.getInstance().getConfigDir();
        File uuidFile = configPath.resolve("AntiClout.json").toFile();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader(uuidFile)) {
            Config config = gson.fromJson(reader, Config.class);
            if (config == null)
            {
                AntiCloutScreen.blockSkin = true;
                AntiCloutScreen.blockName = true;
                AntiCloutScreen.name = "lil pup";
                AntiCloutScreen.blockMaps = true;
                AntiCloutScreen.mapBlockDistance = 30.0;
                return;
            }
            AntiCloutScreen.blockSkin = config.blockSkin;
            AntiCloutScreen.blockName = config.blockName;
            AntiCloutScreen.name = config.name;
            AntiCloutScreen.blockMaps = config.blockMap;
            AntiCloutScreen.mapBlockDistance = config.mapDistance;
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
        }

    }

}
