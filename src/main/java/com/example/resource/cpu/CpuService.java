package com.example.resource.cpu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CpuService {
    private final CpuStat cpuStat;

    public Map<String, String> getStat() throws IOException {
        return cpuStat.getStat("cpu");
    }
}
