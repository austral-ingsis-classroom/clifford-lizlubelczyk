package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.File;
import edu.austral.ingsis.clifford.FileSystem;

public class Touch implements Command {
  private final FileSystem fileSystem;
  private final String name;

  public Touch(FileSystem fileSystem, String name) {
    this.fileSystem = fileSystem;
    this.name = name;
  }

  @Override
  public String execute() {
    try {
      if (name.isEmpty()) {
        return "Error: File name cannot be empty";
      }

      new File(name, fileSystem.getCurrentDirectory());

      return "'" + name + "' file created";
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    }
  }
}
