package edu.austral.ingsis.clifford;

public class File implements Node {
  private String name;
  private Directory parent;

  public File(String name, Directory parent) {
    this.name = name;
    this.parent = parent;
    if (!parent.getChildren().contains(this)) {
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
}
