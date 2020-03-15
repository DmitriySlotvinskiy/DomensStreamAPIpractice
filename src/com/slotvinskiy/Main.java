package com.slotvinskiy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("resources/urls.txt");
        Map<String, Long> statistics = Files.lines(path)
                .map(s -> s.trim())
                .filter(s -> s.contains("."))
                .filter(s -> s.contains("/"))
                .map(s -> s.substring(0, s.indexOf('/')))
                .map(s -> s.replace("m.", ""))
                .map(s -> s.replace("www.", ""))
                .filter(s -> !s.equals(""))
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        Map<String, Long> top10 = statistics.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        print(top10);

    }

    private static void print(Map<String, Long> top10) {
        for (Map.Entry<String, Long> entry : top10.entrySet()) {
            System.out.println(entry.getValue() + " - " + entry.getKey());
        }
    }
}
