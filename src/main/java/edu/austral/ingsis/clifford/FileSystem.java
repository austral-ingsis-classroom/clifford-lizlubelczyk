package edu.austral.ingsis.clifford;

import edu.austral.ingsis.clifford.commands.*;
import java.util.ArrayList;

public class FileSystem {
  private Directory root;
  private Directory currentDirectory;

  public FileSystem() {
    root = new Directory("", null, new ArrayList<>());
    currentDirectory = root;
  }

  public FileSystem(Directory root) {
    this.root = root;
    currentDirectory = root;
  }

  public Directory getRoot() {
    return root;
  }

  public Directory getCurrentDirectory() {
    return currentDirectory;
  }

  public void setCurrentDirectory(Directory currentDirectory) {
    this.currentDirectory = currentDirectory;
  }

  public String execute(String command) {
    try {
      String[] singleCommands = command.split(" ");
      if (singleCommands.length == 0) {
        return "Error: Empty command";
      }
      String name = singleCommands[0];
      Command currentCommand = null;

      switch (name) {
        case "ls":
          String order = singleCommands.length > 1 ? singleCommands[1].split("=")[1] : null;
          currentCommand = new ListChildren(this, order);
          break;
        case "cd":
          if (singleCommands.length < 2) {
            return "Error: Missing argument for cd command";
          }
          String path = singleCommands[1];
          currentCommand = new ChangeDirectory(this, path);
          break;
        case "touch":
          if (singleCommands.length < 2) {
            return "Error: Missing argument for touch command";
          }
          String fileName = singleCommands[1];
          currentCommand = new Touch(this, fileName);
          break;
        case "mkdir":
          if (singleCommands.length < 2) {
            return "Error: Missing argument for mkdir command";
          }
          String dirName = singleCommands[1];
          currentCommand = new MakeDirectory(this, dirName);
          break;
        case "rm":
          if (singleCommands.length < 2) {
            return "Error: Missing argument for rm command";
          }
          String removeName;
          boolean recursive = false;
          if (singleCommands.length > 2) {
            name = singleCommands[2];
            recursive = singleCommands[1].equals("--recursive");
          } else {
            name = singleCommands[1];
          }
          currentCommand = new Remove(this, name, recursive);
          break;
        case "pwd":
          currentCommand = new PrintWorkingDirectory(this);
          break;
        default:
          return "Error: Unknown command";
      }

      return currentCommand.execute();
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }
}
