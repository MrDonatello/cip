package com.cip.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

@Service
public class CipService {

    public Map<Integer, List<Integer>> readConfigureFile() {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(CipService.class.getResource("/static/docs/config.txt").toURI().getPath()))))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                for (String s : data) {
                    list.add(Integer.parseInt(s));
                }
                map.put(i, new ArrayList<>(list));
               list.clear();
                i++;
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return map;
    }
}
