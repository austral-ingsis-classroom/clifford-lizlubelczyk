package edu.austral.ingsis.clifford.commands;

import edu.austral.ingsis.clifford.FileSystem;
import edu.austral.ingsis.clifford.Node;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListChildren implements Command {
  private final FileSystem fileSystem;
  private final String order;

  public ListChildren(FileSystem fileSystem, String order) {
    this.fileSystem = fileSystem;
    this.order = order;
  }

  @Override
  public String execute() {
    List<Node> children = fileSystem.getCurrentDirectory().getChildren();

    List<String> names =
        children.stream().filter(Objects::nonNull).map(Node::getName).collect(Collectors.toList());

    sortNames(names);

    return String.join(" ", names);
  }

  private void sortNames(List<String> names) {
    if (Objects.equals(order, "desc")) {
      names.sort(Collections.reverseOrder());
    } else if (Objects.equals(order, "asc")) {
      Collections.sort(names);
    }
  }
}
