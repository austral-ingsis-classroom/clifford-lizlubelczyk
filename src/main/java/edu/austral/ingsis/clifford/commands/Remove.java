package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.Directory;
import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.Node;
import java.util.List;

public class Remove implements Command {
  private final FileSystem fileSystem;
  private final String name;
  private final boolean recursive;

  public Remove(FileSystem fileSystem, String name, boolean recursive) {
    this.fileSystem = fileSystem;
    this.name = name;
    this.recursive = recursive;
  }

  @Override
  public String execute() {
    if (name.isEmpty()) {
      return "Error: Missing argument for rm command";
    }

    List<Node> children = fileSystem.getCurrentDirectory().getChildren();
    Node node = null;

    for (Node child : children) {
      if (child.getName().equals(name)) {
        node = child;
        break;
      }
    }

    if (node != null) {
      if (node instanceof Directory) {
        if (!recursive) {
          return "cannot remove '" + name + "', is a directory";
        } else {
          recursiveRemove((Directory) node);
        }
      } else {
        fileSystem.getCurrentDirectory().removeChild(node);
      }
      return "'" + name + "' removed";
    }
    return "Error: File or directory not found";
  }

  private void recursiveRemove(Directory directory) {
    for (Node child : directory.getChildren()) {
      if (child instanceof Directory) {
        recursiveRemove((Directory) child);
      }
      fileSystem.getCurrentDirectory().removeChild(child);
    }
    fileSystem.getCurrentDirectory().removeChild(directory);
  }
}
