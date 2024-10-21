package com.example.resource.cpu;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class CpuStat {
    private static final String PROC_STAT = "/proc/stat";

    public Map<String, String> getStat(String prefix) throws IOException {
        var reader = new BufferedReader(new FileReader(PROC_STAT));
        var cpuStat = new HashMap<String, String>();
        for (String line = reader.readLine(); startWith(line, prefix); line = reader.readLine()) {
            String[] tokens = line.split("\\s+", 2);
            if (tokens.length >= 2) {
                cpuStat.put(tokens[0], tokens[1]);
            }
        }
        return cpuStat;
    }

    private boolean startWith(String line, String word) {
        return line != null && line.startsWith(word);
    }
}
