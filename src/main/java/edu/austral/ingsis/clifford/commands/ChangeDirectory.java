package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.Node;
import java.util.Objects;

public class ChangeDirectory implements Command {
  private final FileSystem fileSystem;
  private final String path;

  public ChangeDirectory(FileSystem fileSystem, String path) {
    this.fileSystem = fileSystem;
    this.path = path;
  }

  @Override
  public String execute() {
    if (isRootPath()) {
      changeToRootDirectory();
      return "moved to directory '/'";
    }
    Directory directory = navigateToDirectory(); // Remove the argument here

    if (directory != null) {
      changeToDirectory(directory);
      return "moved to directory '" + getDirectoryPath(directory) + "'";
    } else {
      return "'" + path + "'" + " directory does not exist";
    }
  }

  private boolean isRootPath() {
    return Objects.equals(path, "/");
  }

  private void changeToRootDirectory() {
    fileSystem.setCurrentDirectory(fileSystem.getRoot());
  }

  private Directory navigateToDirectory() {
    Directory currentDirectory = fileSystem.getCurrentDirectory();
    String localPath = path;
    if (localPath.startsWith("/")) {
      currentDirectory = fileSystem.getRoot();
      localPath = localPath.substring(1);
    }

    if (localPath.isEmpty()) {
      return currentDirectory;
    }

    String[] components = localPath.split("/");

    for (String component : components) {
      currentDirectory = navigateToComponent(currentDirectory, component);
      if (currentDirectory == null) {
        return null;
      }
    }
    return currentDirectory;
  }

  private Directory navigateToComponent(Directory currentDirectory, String component) {
    if (component.equals("..")) {
      return navigateToParent(currentDirectory);
    } else if (!component.equals(".") && !component.isEmpty()) {
      return findChildDirectory(currentDirectory, component);
    }
    return currentDirectory;
  }

  private Directory navigateToParent(Directory currentDirectory) {
    if (currentDirectory.getParent() != null) {
      return currentDirectory.getParent();
    } else {
      return fileSystem.getRoot();
    }
  }

  private Directory findChildDirectory(Directory directory, String name) {
    for (Node node : directory.getChildren()) {
      if (node instanceof Directory && node.getName().equals(name)) {
        return (Directory) node;
      }
    }
    return null;
  }

  private void changeToDirectory(Directory directory) {
    fileSystem.setCurrentDirectory(directory);
  }

  private String getDirectoryPath(Directory directory) {
    if (directory == fileSystem.getRoot()) {
      return "/";
    }
    return directory.getName();
  }
}
