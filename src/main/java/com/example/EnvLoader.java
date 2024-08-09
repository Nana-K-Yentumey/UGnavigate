package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    private Map<String, String> envVars = new HashMap<>();

    public EnvLoader(String filePath) throws IOException {
        loadEnvFile(filePath);
    }

    private void loadEnvFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                envVars.put(parts[0], parts[1]);
            }
        }
        reader.close();
    }

    public String get(String key) {
        return envVars.get(key);
    }
}
