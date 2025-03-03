import java.io.*;
import java.util.Scanner;

import Classes.StorageManager;

public class Main {

    private static String catalogLocation;
    private static StorageManager storageManager;

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Main <dbLocation> <pageSize> <bufferSize>");
            return;
        }

        String dbLocation = args[0];
        int pageSize = Integer.parseInt(args[1]);
        int bufferSize = Integer.parseInt(args[2]);

        catalogLocation = dbLocation + File.separator + "catalog";
        storageManager = new StorageManager(dbLocation, pageSize, bufferSize);

        File catalogFile = new File(catalogLocation);
        if (catalogFile.exists()) {
            storageManager.restartDatabase();
        } else {
            storageManager.createDatabase();
        }

        processCommands();
    }

    private static void processCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            
            System.out.print("Enter command: ");
            String command = getUserStatement(scanner);

            if (command == null || command.isEmpty()) {
                System.out.println("Error reading command.");
                continue;
            }

            // Convert command to lowercase except inside quotes
            command = toLowercaseExceptQuotes(command);
            // System.out.println("Command after toLowercaseExceptQuotes: " + command);

            if (command.startsWith("quit")) {
                System.out.println("Shutting down the system.");
                break;
            } else if (command.startsWith("create table ")) {
                storageManager.parseCreateTable(command);
            } else if (command.startsWith("drop table ")) {
                storageManager.parseDropTable(command);
            } else if (command.startsWith("display schema")) {
                storageManager.displaySchema();
            } else if (command.startsWith("display info ")) {
                storageManager.parseDisplayTable(command);
            } else if (command.startsWith("select ")) {
                storageManager.parseSelect(command);
            } else if (command.startsWith("insert into ")) {
                storageManager.parseInsert(command);
            } else if (command.startsWith("alter table ")) {
                storageManager.parseAlterTable(command);
            } else {
                System.out.println("Invalid command.");
            }
        }
        scanner.close();
    }

    private static String toLowercaseExceptQuotes(String str) {
        StringBuilder result = new StringBuilder();
        boolean inQuotes = false;
        for (char ch : str.toCharArray()) {
            if (ch == '"') {
                inQuotes = !inQuotes;
            }
            result.append(inQuotes ? ch : Character.toLowerCase(ch));
        }
        return result.toString();
    }

    public static String getUserStatement(Scanner scanner) {
        StringBuilder statement = new StringBuilder();
        boolean leadingSpacesRemoved = false;
        boolean spaceFlag = false;
    
        while (true) {
            String input = scanner.nextLine();
            System.out.println("DEBUG PRINT: input = " + input);
            if (!input.endsWith(";")) {
                statement.append(input).append(" ");
                continue;
            }
            for (char ch : input.toCharArray()) {
                if (ch == '\n') {
                    ch = ' '; // Convert newlines to spaces
                }
    
                // Skip leading spaces
                if (!leadingSpacesRemoved && Character.isWhitespace(ch)) {
                    continue;
                }
                leadingSpacesRemoved = true; // Start storing characters
    
                // Remove extra spaces (ensure only one space between words)
                if (Character.isWhitespace(ch)) {
                    if (spaceFlag) continue; // Skip consecutive spaces
                    spaceFlag = true;
                } else {
                    spaceFlag = false; // Reset when non-space character appears
                }
    
                statement.append(ch);
    
                // Stop reading when a semicolon is found
                if (ch == ';') {
                    // Trim trailing spaces before semicolon
                    int length = statement.length();
                    while (length > 1 && Character.isWhitespace(statement.charAt(length - 2))) {
                        statement.setCharAt(length - 2, ';'); // Move semicolon back
                        statement.setLength(--length);
                    }
                    System.out.println("DEBUG PRINT: returning statement");
                    return statement.toString();
                }
            }
        }
    }
}