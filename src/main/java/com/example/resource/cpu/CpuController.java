package com.example.resource.cpu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("cpu")
public class CpuController {
    private static final String PROC_STAT = "/proc/stat";

    @GetMapping("/v1/stat")
    public ResponseEntity<Map<String, String>> getCpuStat(){
        try (var reader = new BufferedReader(new FileReader(PROC_STAT))) {
            var cpuStat = getCpuStat(reader);
            return ResponseEntity.ok(cpuStat);
        } catch (IOException e) {
            System.out.println(e.getMessage()); // 로그 처리 필요
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error reading CPU stats: " + e.getMessage()));
        }
    }

    private Map<String, String> getCpuStat(BufferedReader reader) throws IOException {
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
