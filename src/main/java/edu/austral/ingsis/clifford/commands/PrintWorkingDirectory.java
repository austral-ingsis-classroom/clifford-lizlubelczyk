package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;

public class PrintWorkingDirectory implements Command {
  private final FileSystem fileSystem;

  public PrintWorkingDirectory(FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  @Override
  public String execute() {
    StringBuilder path = new StringBuilder();
    Directory currentDirectory = fileSystem.getCurrentDirectory();

    while (currentDirectory != null) {
      if (path.length() > 0) {
        path.insert(0, "/");
      }
      path.insert(0, currentDirectory.getName());
      currentDirectory = currentDirectory.getParent();
    }
    return path.toString();
  }
}
