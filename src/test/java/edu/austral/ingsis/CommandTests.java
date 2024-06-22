package edu.austral.ingsis;

import static org.junit.jupiter.api.Assertions.*;

import edu.austral.ingsis.clifford.*;
import edu.austral.ingsis.clifford.commands.ListChildren;
import edu.austral.ingsis.clifford.commands.Remove;
import edu.austral.ingsis.clifford.commands.Touch;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandTests {

  private FileSystem fileSystem;

  @BeforeEach
  public void setUp() {
    fileSystem = new FileSystem();
  }

  @Test
  public void testLSEmpty() {
    assertEquals("", new ListChildren(fileSystem, null).execute());
  }

  @Test
  public void testLSDirectories() {
    Directory root = new Directory("", null, new ArrayList<>());
    new Directory("directory", root, new ArrayList<>());
    new Directory("directory1", root, new ArrayList<>());
    fileSystem = new FileSystem(root);

    assertEquals("directory directory1", new ListChildren(fileSystem, null).execute());
  }

  @Test
  public void testLSDescending() {
    Directory root = new Directory("", null, new ArrayList<>());
    new Directory("directory", root, new ArrayList<>());
    new Directory("directory1", root, new ArrayList<>());
    fileSystem = new FileSystem(root);

    assertEquals("directory1 directory", new ListChildren(fileSystem, "desc").execute());
  }

  @Test
  public void testTouchFile() {
    assertEquals("'file' file created", new Touch(fileSystem, "file").execute());
    assertEquals("file", fileSystem.getRoot().getChildren().get(0).getName());
  }

  @Test
  public void testTouchFileInDirectory() {
    Directory root = new Directory("", null, new ArrayList<>());
    new Directory("directory", root, new ArrayList<>());
    fileSystem = new FileSystem(root);

    assertEquals("'file' file created", new Touch(fileSystem, "file").execute());
    assertEquals("directory", fileSystem.getRoot().getChildren().get(0).getName());
    assertEquals("file", fileSystem.getRoot().getChildren().get(1).getName());
  }

  @Test
  public void testRemoveChild() {
    Directory root = new Directory("", null, new ArrayList<>());
    Directory directory = new Directory("directory", root, new ArrayList<>());
    Directory directory1 = new Directory("directory1", root, new ArrayList<>());
    fileSystem = new FileSystem(root);

    assertEquals("directory1", fileSystem.getCurrentDirectory().getChildren().get(1).getName());

    new Remove(fileSystem, "directory", true).execute();

    assertEquals(List.of(directory1), fileSystem.getCurrentDirectory().getChildren());
  }

  @Test
  public void testRemoveNonExistingFile() {
    assertEquals(
        "Error: File or directory not found",
        new Remove(fileSystem, "non-existing-file", false).execute());
  }

  @Test
  public void testRemoveDirectoryWithoutRecursive() {
    Directory root = new Directory("", null, new ArrayList<>());
    Directory directory = new Directory("directory", root, new ArrayList<>());
    fileSystem = new FileSystem(root);

    assertEquals(
        "cannot remove 'directory', is a directory",
        new Remove(fileSystem, "directory", false).execute());
  }

  @Test
  public void testRemoveDirectoryWithRecursive() {
    Directory root = new Directory("", null, new ArrayList<>());
    Directory directory = new Directory("directory", root, new ArrayList<>());
    Directory subdirectory = new Directory("subdirectory", directory, new ArrayList<>());
    FileSystem fileSystem = new FileSystem(root);

    new Remove(fileSystem, "directory", true).execute();

    assertEquals(List.of(), root.getChildren());
  }

  @Test
  public void testRemoveFile() {
    Directory root = new Directory("", null, new ArrayList<>());
    File file = new File("file", root);
    fileSystem = new FileSystem(root);

    new Remove(fileSystem, "file", false).execute();

    assertEquals(List.of(), root.getChildren());
  }
}
