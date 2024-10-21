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
        var result = new HashMap<String, String>();
        try (var reader = new BufferedReader(new FileReader(PROC_STAT))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (!line.startsWith("cpu")) {
                    break;
                }
                String[] tokens = line.split("\\s+", 2);
                if (tokens.length >= 2) {
                    result.put(tokens[0], tokens[1]);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error reading CPU stats: " + e.getMessage()));
        }
        return ResponseEntity.ok(result);
    }
}
