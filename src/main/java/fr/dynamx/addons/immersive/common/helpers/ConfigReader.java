package fr.dynamx.addons.immersive.common.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class ConfigReader {
    public static ArrayList<RadioFrequency> frequencies;

    public static void readFromFile() throws IOException {
        File json = new File("config/radiofrequencies.json");
        RadioFrequency[] freqJson = new Gson().fromJson(FileUtils.readFileToString(json, StandardCharsets.UTF_8), RadioFrequency[].class);
        frequencies = new ArrayList<>(Arrays.asList(freqJson));
        frequencies.sort((o1, o2) -> Float.compare(o1.getFrequency(), o2.getFrequency()));
    }

    public static void writeToFile() throws IOException {
        File json = new File("config/radiofrequencies.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileUtils.writeStringToFile(json, gson.toJson(frequencies));
    }

    public static String getFileContent() throws IOException {
        File json = new File("config/radiofrequencies.json");
        return FileUtils.readFileToString(json, StandardCharsets.UTF_8);
    }

    public static void writeToFileString(String string) throws IOException {
        File json = new File("config/radiofrequencies.json");
        FileUtils.writeStringToFile(json, string);
    }
}
