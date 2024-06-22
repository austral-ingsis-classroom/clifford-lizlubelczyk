package edu.austral.ingsis.clifford;

import java.util.List;

public class Directory implements Node {
  private String name;
  private Directory parent;
  private List<Node> children;

  public Directory(String name, Directory parent, List<Node> children) {
    this.name = name;
    this.parent = parent;
    this.children = children;
    if (parent != null) {
      parent.addChild(this);
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Directory getParent() {
    return parent;
  }

  public List<Node> getChildren() {
    return children;
  }

  public void addChild(Node node) {
    if (node instanceof Directory directory) {
      if (directory.getParent() == this) {
        children.add(node);
      }
    } else {
      children.add(node);
    }
  }

  public void removeChild(Node node) {
    children.remove(node);
  }
}
