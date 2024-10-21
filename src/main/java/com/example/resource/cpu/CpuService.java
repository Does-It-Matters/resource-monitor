package com.example.resource.cpu;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class CpuService {
    private static final String PROC_STAT = "/proc/stat";

    public Map<String, String> getStat() throws IOException {
        var reader = new BufferedReader(new FileReader(PROC_STAT));
        var cpuStat = new HashMap<String, String>();
        for (String line = reader.readLine(); startWith(line, "cpu"); line = reader.readLine()) {
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
