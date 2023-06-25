package services;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestsWatcher implements TestWatcher, BeforeEachCallback {

    private List<Path> listOfVideoRecords = new ArrayList<>();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        //   listOfVideoRecords = Files.walk(Paths.get("videos")).filter(Files::isRegularFile).collect(Collectors.toList());

        try (Stream<Path> pathStream = Files.walk(Paths.get("videos/"))) {
            listOfVideoRecords = pathStream
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            // Process the list of video records
        } catch (IOException e) {
            System.err.println("Failed to walk through the directory: " + e.getMessage());
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        deleteTraceInfo(context);
        deleteVideos();
    }

    private void deleteVideos() {
        List<Path> fullListOfVideoRecords = new ArrayList<>();
        try (Stream<Path> pathStream = Files.walk(Paths.get("videos/"))) {
            fullListOfVideoRecords = pathStream
                    .filter(Files::isRegularFile)
                    .filter(s1 -> !listOfVideoRecords.contains(s1))
                    .collect(Collectors.toList());

            for (Path path : fullListOfVideoRecords) {
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            System.err.println("Failed to walk through the directory: " + e.getMessage());
        }
    }

    private void deleteTraceInfo(ExtensionContext context) {
        Path tracePath = Paths.get("traces/" + context.getDisplayName().replace("()", "") + ".zip");
        try {
            Files.deleteIfExists(tracePath);
        } catch (IOException e) {
            System.out.println("ERROR to delete file " + tracePath.getFileName());
        }
    }


}
