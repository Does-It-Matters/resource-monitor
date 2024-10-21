package com.example.resource.cpu;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("cpu")
@RequiredArgsConstructor
public class CpuController {
    private final CpuService cpuService;

    @GetMapping("/v1/stat")
    public ResponseEntity<Map<String, String>> getCpuStat(){
        try {
            return ResponseEntity.ok(cpuService.getStat());
        } catch (IOException e) {
            System.out.println(e.getMessage()); // 로그 처리 필요
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Error reading CPU stats: " + e.getMessage()));
        }
    }
}
