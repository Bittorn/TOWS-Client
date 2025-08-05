package net.bittorn.towsclient.data;

import com.bladecoder.ink.runtime.Story;
import net.bittorn.towsclient.TOWSClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class StoryManager {
    public Story loadStory(String filename) {
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(filename);

        // try-with-resources !!
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(systemResourceAsStream), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return new Story(sb.toString());
        } catch (Exception e) {
            TOWSClient.LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
}
