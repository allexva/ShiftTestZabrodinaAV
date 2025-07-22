package org.example;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FileManager {
    public static Set<Path> sourceFilesName = new HashSet<>();
    public static Set<Path> outputFilesName = new HashSet<>();
    public static List<String> resultLines = new ArrayList<>();
    public static List<Long> resultLongs = new ArrayList<>();
    public static List<Double> resultDoubles = new ArrayList<>();
    public static String outputPath = "src/main/resources/result";
    public static boolean isAppendMode = false;


    public static void parseFile() {
        for (Path path : sourceFilesName) {
            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(line -> {
                    try {
                        Type typeOfValue = checkTypeOfValue(line);
                        switch (typeOfValue) {
                            case LONG -> resultLongs.add(Long.parseLong(line));
                            case DOUBLE -> resultDoubles.add(Double.parseDouble(line));
                            case STRING -> resultLines.add(line);
                        }
                    } catch (IOException e) {
                        System.out.println("Didn't work checking type of value" + e.getMessage());
                    }
                });
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
    }

    private static Type checkTypeOfValue(String input) throws IOException {
        try {
            Long.parseLong(input);
            return Type.LONG;
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(input);
                return Type.DOUBLE;
            } catch (NumberFormatException e2) {
                return Type.STRING;
            }
        }
    }


    public static void distributeListsOnFiles() {
        writeListToFile(resultLines, "strings.txt");
        writeListToFile(resultLongs, "integers.txt");
        writeListToFile(resultDoubles, "floats.txt");
        System.out.println("\n--------------------------------------------------\n");
    }

    private static <T> void writeListToFile(List<T> list, String nameOfFile) {
        Path filePath = Path.of(outputPath + nameOfFile);

        if (!(list == null || list.isEmpty())) {
            outputFilesName.add(filePath);
            try {
                if (isAppendMode) {
                    Files.write(filePath, list.stream()
                                    .map(Object::toString).toList(), StandardOpenOption.CREATE,
                            StandardOpenOption.APPEND);
                }
                if (!isAppendMode) {
                    Files.write(filePath, list.stream()
                                    .map(Object::toString).toList(), StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING);
                }

                try (FileChannel channel = FileChannel.open(filePath, StandardOpenOption.WRITE)) {
                    channel.force(true);
                }

                System.out.println("File " + nameOfFile + " written successfully");
            } catch (IOException e) {
                System.err.println("Error : " + e.getMessage());
            }
        }
    }
}
