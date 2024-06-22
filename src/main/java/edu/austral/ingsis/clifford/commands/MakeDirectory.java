package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import java.util.ArrayList;

public class MakeDirectory implements Command {
  private final FileSystem fileSystem;
  private final String name;

  public MakeDirectory(FileSystem fileSystem, String name) {
    this.fileSystem = fileSystem;
    this.name = name;
  }

  @Override
  public String execute() {
    try {
      if (name.isEmpty()) {
        return "Error: Directory name cannot be empty";
      }

      new Directory(name, fileSystem.getCurrentDirectory(), new ArrayList<>());

      return "'" + name + "' directory created";
    } catch (IllegalArgumentException e) {
      return "Error: " + e.getMessage();
    }
  }
}
