package filesystem;

import exception.FileSystemException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystem {

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
                if (!line.trim().equals("<%") && !line.trim().equals("%>")) {
                    html.append(line).append(System.lineSeparator());
                }
                line = bf.readLine();
            }
            return html.toString().getBytes();
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    public static FindResource getPersonalizedResource(String url, String chunkHtml) {
        String resourcePath = PathResolver.parse(url);
        byte[] resource = readFile(resourcePath);
        resource = personalize(resource, chunkHtml);
        return new FindResource(resourcePath, resource);
    }

    private static byte[] personalize(byte[] resource, String chunkHtml) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(resource)));
            StringBuilder html = new StringBuilder();
            String line = bf.readLine();
            while (line != null) {
                if (line.trim().equals("<%")) {
                    while (!line.trim().equals("%>")) {
                        line = bf.readLine();
                    }
                    html.append(chunkHtml);
                    line = bf.readLine();
                }
                html.append(line);
                line = bf.readLine();
            }
            return html.toString().getBytes();
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    private static byte[] readFile(String resourcePath) {
        try {
            return Files.readAllBytes(Paths.get(resourcePath));
        } catch (IOException e) {
            return HtmlGenerator.getNotFoundHTML(resourcePath).getBytes();
        }
    }
}
