package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;

import static org.example.FileManager.*;

public class ConsoleManager {
    public static void writeSourceFileNamesFromTheConsole() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("To read files, enter their paths from each new line, for example \"src/main/resources/in1.txt\" " +
                "as soon as you're done, enter END or press ENTER twice");

        while (true) {
            String line = bufferedReader.readLine();

            if (line == null || line.isEmpty() || line.equalsIgnoreCase("END")) {
                break;
            }
            line = line.trim();

            File file = new File(line);
            boolean isFile = file.exists() && file.isFile();

            if (!isFile) {
                System.out.println("File " + line + " does not exist or is not a file\n");
                continue;
            }

            if (sourceFilesName.add(Paths.get(line))) {
                System.out.println("The file name was entered successfully");
                continue;
            }
            System.out.println("The file name was entered successfully earlier");
        }

        if (sourceFilesName.isEmpty()) {
            System.out.println("No files were found");
            System.exit(1);
        }

        System.out.println("The file names were entered successfully");
        System.out.println("The file names are: " + sourceFilesName + "\n");
        System.out.println("--------------------------------------------------\n");
    }

    public static void writeOutputFolderNameFromTheConsole() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String prefix = "";

        while (true) {
            System.out.println("""
                    For the default path, enter -d.
                    To set the path, enter -o.
                    To specify a prefix in the name of the resulting file, enter -p.
                    To continue writing to an existing file, enter -a.
                    To save the settings, enter -s.
                    """);

            String command = bufferedReader.readLine().trim().toLowerCase();

            switch (command) {
                case "-d": {
                    outputPath = "src/main/resources/result";
                    System.out.println("The default path is selected - " + outputPath + "\n");
                    System.out.print("If there are no more changes planned, enter -s\n");
                    if (bufferedReader.readLine().trim().equalsIgnoreCase("-s")) {
                        outputPath = outputPath + "/";
                        return;
                    }
                    System.out.println("A different value has been entered, we return to the menu");
                    break;
                }
                case "-p": {
                    System.out.println("Enter the prefix");
                    prefix = bufferedReader.readLine();
                    System.out.println("The prefix is selected - " + prefix + "\n");
                    break;
                }
                case "-o": {
                    System.out.println("Enter the path in the format \"src/main/resources/result\" just without the quotes.");
                    String path = bufferedReader.readLine();

                    if (path.isEmpty()) {
                        System.out.println("The path is empty\n");
                        continue;
                    }

                    File folder = new File(path);
                    if (folder.exists() && folder.isDirectory()) {
                        outputPath = path;
                        System.out.println("The path is selected - " + outputPath + "\n");
                        continue;
                    }
                    System.out.println("There is no such path or it is not a folder. Try again.\n");
                    break;
                }
                case "-a": {
                    isAppendMode = true;
                    break;
                }
                case "-s": {
                    outputPath = (outputPath.charAt(outputPath.length()-1) == '/' || outputPath.charAt(outputPath.length()-1) == '\\') ?
                            outputPath + prefix :
                            outputPath + File.separator + prefix;

                    System.out.println("The path is selected - " + outputPath + "\n");
                    System.out.println("The path settings will be saved successfully.\n");
                    System.out.println("--------------------------------------------------\n");
                    return;
                }
                default: {
                    System.out.println("Invalid command\n");
                }
            }
            System.out.println("--------------------------------------------------\n");
        }
    }

    public static void workWithStatistic() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("""
                    To get the short statistics, enter -s.
                    To get the full statistics, enter -f.
                    To exit, enter END.
                    """);
            String command = bufferedReader.readLine().trim().toLowerCase();

            switch (command) {
                case "-s": {
                    makeShortStatistic();
                    break;
                }
                case "-f": {
                    makeFullStatistic();
                    break;
                }
                case "end": {
                    return;
                }
                default: {
                    System.out.println("Invalid command = " + command + "\n");
                    break;
                }
            }
            System.out.println("\n--------------------------------------------------\n");
        }
    }

    private static void makeShortStatistic() {
        if (!(resultLines == null || resultLines.isEmpty())) {
            System.out.println("Count of string elements = " + resultLines.size());
        }
        if (!(resultLongs == null || resultLongs.isEmpty())) {
            System.out.println("Count of integer elements = " + resultLongs.size());
        }
        if (!(resultDoubles == null || resultDoubles.isEmpty())) {
            System.out.println("Count of float elements = " + resultDoubles.size());
        }
    }

    private static void makeFullStatistic() {
        if (!(resultLines == null || resultLines.isEmpty())) {
            String maxLine = resultLines.stream().max(Comparator.comparingInt(String::length)).orElse("");
            String minLine = resultLines.stream().min(Comparator.comparingInt(String::length)).orElse("");

            System.out.println("Count of string elements = " + resultLines.size());
            System.out.println("A string element with the maximum length = " + maxLine);
            System.out.println("A string element with the minimum length = " + minLine);

        }
        if (!(resultLongs == null || resultLongs.isEmpty())) {
            Optional<Long> max = resultLongs.stream().max(Comparator.naturalOrder());
            Optional<Long> min = resultLongs.stream().min(Comparator.naturalOrder());

            System.out.println("Count of integer elements = " + resultLongs.size());
            System.out.println("A maximum integer element = " + max);
            System.out.println("A minimum integer element = " + min);
        }
        if (!(resultDoubles == null || resultDoubles.isEmpty())) {
            Optional<Double> max = resultDoubles.stream().max(Comparator.naturalOrder());
            Optional<Double> min = resultDoubles.stream().min(Comparator.naturalOrder());

            System.out.println("Count of float elements = " + resultDoubles.size());
            System.out.println("A maximum float element = " + max);
            System.out.println("A minimum float element = " + min);
        }
    }
}
