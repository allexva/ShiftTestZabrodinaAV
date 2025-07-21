package org.example;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.lang.System.exit;


public class App {
    public static ArrayList<Path> sourceFilesName = new ArrayList<>();
    public static ArrayList<Path> outputFilesName = new ArrayList<>();
    public static ArrayList<String> resultLines = new ArrayList<>();
    public static ArrayList<Integer> resultIntegers = new ArrayList<>();
    public static ArrayList<Double> resultDoubles = new ArrayList<>();
    public static String outputPath = "src/main/resources/result";


    public static void writeSourceFileNamesFromTheConsole() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("To read files, enter their paths from each new line, for example \"src/main/resources/in1.txt \" " +
                            "as soon as you're done, enter END");

        while (true) {
            String line = bufferedReader.readLine();
            if (line == null || line.isEmpty() || line.equalsIgnoreCase("END")) {
                break;
            }

            File file = new File(line);
            if (file.exists() && file.isFile()) {
                sourceFilesName.add(Paths.get(line));
                System.out.println("The file name was entered successfully");
                continue;
            }

            System.out.println("File " + line + " does not exist or is not a file\n");
        }

        if (sourceFilesName.isEmpty()) {
            System.out.println("No files were found");
            exit(1);
        }

        System.out.println("The file names were entered successfully");
        System.out.println("The file names are: " + sourceFilesName);
    }

    public static void writeOutputFolderNameFromTheConsole() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("""
                    For the default path, enter -d.
                    To set the path, enter -o.
                    To specify a prefix in the name of the resulting file, type -p.
                    To save the settings, enter -s.
                    """);

            String command = bufferedReader.readLine();
            String prefix = "";

            switch (command) {
                case "-d": {
                    outputPath = "src/main/resources/result";
                    System.out.println("The default path is selected - " + outputPath + "\n");
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
                case "-s": {
                    outputPath = outputPath + "/" + prefix;
                    System.out.println("The path is selected - " + outputPath + "\n");
                    System.out.println("The path settings will be saved successfully.\n");
                    return;
                }
                default: {
                    System.out.println("Invalid command\n");
                }
            }
        }
    }

    public static void distributeDataFromFile() {

    }


    public static void checkTypeOfValue() throws IOException {

    }

    public static void main(String[] args) throws IOException {
        writeSourceFileNamesFromTheConsole();

        //todo: распределение текста файла по разным спискам
        //todo: возможность выводить статистику краткую и полную

        writeOutputFolderNameFromTheConsole();
    }
}
