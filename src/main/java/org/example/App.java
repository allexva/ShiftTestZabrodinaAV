package org.example;

import java.io.*;


public class App {
    public static void main(String[] args) throws IOException {
        ConsoleManager.writeSourceFileNamesFromTheConsole(); //enter path of input files from console
        FileManager.parseFile();

        ConsoleManager.writeOutputFolderNameFromTheConsole(); //enter path of output files from console
        FileManager.distributeListsOnFiles();

        ConsoleManager.workWithStatistic();
    }
}
