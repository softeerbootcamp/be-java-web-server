package filesystem;

import exception.FileSystemException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystem {

    private static final String START_SYMBOL = "<%";
    private static final String END_SYMBOL = "%>";

    private FileSystem() {
    }

    public static FindResource getStaticResource(String url) {
        String resourcePath = PathResolver.parse(url);
        byte[] resource = readFile(resourcePath);
        resource = trimSpecialSymbol(resource);
        return new FindResource(resourcePath, resource);
    }

    private static byte[] trimSpecialSymbol(byte[] resource) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(resource)));
            StringBuilder html = new StringBuilder();
            String line = bf.readLine();
            while (line != null) {
                if (!line.trim().equals(START_SYMBOL) && !line.trim().equals(END_SYMBOL)) {
                    html.append(line).append(System.lineSeparator());
                }
                line = bf.readLine();
            }
            return html.toString().getBytes();
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    public static FindResource getPersonalizedResource(String url, String... chunkHtmls) {
        String resourcePath = PathResolver.parse(url);
        byte[] resource = readFile(resourcePath);
        for (String chunk : chunkHtmls) {
            resource = personalize(resource, chunk);
        }
        return new FindResource(resourcePath, resource);
    }

    private static byte[] personalize(byte[] resource, String chunkHtml) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(resource)));
            StringBuilder sb = new StringBuilder();
            String line = bf.readLine();
            boolean inserted = false;

            while (line != null) {
                if (!inserted && line.trim().contains(START_SYMBOL)) {
                    line = bf.readLine();
                    while (line != null && !line.trim().contains(END_SYMBOL)) {
                        line = bf.readLine();
                    }
                    sb.append(chunkHtml + System.lineSeparator());
                    inserted = true;
                } else {
                    sb.append(line + System.lineSeparator());
                }
                line = bf.readLine();
            }
            return sb.toString().getBytes();
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    private static byte[] readFile(String resourcePath) {
        try {
            return Files.readAllBytes(Paths.get(resourcePath));
        } catch (IOException e) {
            return HtmlGenerator.getNotFoundHTML().getBytes();
        }
    }
}
