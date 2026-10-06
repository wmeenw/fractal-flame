package org.example;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {
    public Config load(String[] args) throws IOException {
        Config config = Config.defaultConfig();
        String configPath = getArgValue(args, "--config");
        if (configPath != null) {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(configPath)) {
                Config fileConfig = gson.fromJson(reader, Config.class);
                merge(config, fileConfig);
            }
        }
        parseCommandLine(args, config);
        return config;
    }

    private String getArgValue(String[] args, String key) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals(key)) return args[i + 1];
        }
        return null;
    }

    private void merge(Config target, Config source) {
        if (source.getWidth() != 0) target.setWidth(source.getWidth());
        if (source.getHeight() != 0) target.setHeight(source.getHeight());
        if (source.getIterationCount() != 0) target.setIterationCount(source.getIterationCount());
        if (source.getSeed() != 0L) target.setSeed(source.getSeed());
        if (source.getOutputPath() != null) target.setOutputPath(source.getOutputPath());
        if (source.getThreads() != 0) target.setThreads(source.getThreads());
        if (source.getSymmetryLevel() != 0) target.setSymmetryLevel(source.getSymmetryLevel());
        if (!source.getAffineParams().isEmpty()) target.setAffineParams(source.getAffineParams());
        if (!source.getFunctions().isEmpty()) target.setFunctions(source.getFunctions());
    }

    private void parseCommandLine(String[] args, Config config) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-w", "--width" -> config.setWidth(Integer.parseInt(args[++i]));
                case "-h", "--height" -> config.setHeight(Integer.parseInt(args[++i]));
                case "-i", "--iteration-count" -> config.setIterationCount(Integer.parseInt(args[++i]));
                case "-o", "--output-path" -> config.setOutputPath(args[++i]);
                case "-t", "--threads" -> config.setThreads(Integer.parseInt(args[++i]));
                case "--seed" -> config.setSeed(Long.parseLong(args[++i]));
                case "-s", "--symmetry-level" -> config.setSymmetryLevel(Integer.parseInt(args[++i]));
                case "-ap", "--affine-params" -> config.setAffineParams(ParamParser.parseAffineParams(args[++i]));
                case "-f", "--functions" -> config.setFunctions(ParamParser.parseFunctions(args[++i]));
                default -> {}
            }
        }
    }
}