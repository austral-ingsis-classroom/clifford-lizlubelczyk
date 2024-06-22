package edu.austral.ingsis;

import edu.austral.ingsis.clifford.FileSystem;
import java.util.ArrayList;
import java.util.List;

public class CommandRunner implements FileSystemRunner {
  @Override
  public List<String> executeCommands(List<String> commands) {
    FileSystem fileSystem = new FileSystem();
    List<String> results = new ArrayList<>();

    for (String command : commands) {
      String result = fileSystem.execute(command);
      results.add(result);
    }

    return results;
  }
}
