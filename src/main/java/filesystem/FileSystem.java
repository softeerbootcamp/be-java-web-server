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
            String html = "";
            String line = bf.readLine();
            while (line != null) {
                if (!line.trim().equals("<%") && !line.trim().equals("%>")) {
                    html += line + System.lineSeparator();
                }
                line = bf.readLine();
            }
            return html.getBytes();
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }

    public static byte[] readFile(String resourcePath) {
        try {
            return Files.readAllBytes(Paths.get(resourcePath));
        } catch (IOException e) {
            return new byte[0];
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
            String html = "";
            String line = bf.readLine();
            while (line != null) {
                if (line.trim().equals("<%")) {
                    while (!line.trim().equals("%>")) {
                        line = bf.readLine();
                    }
                    html += chunkHtml;
                    line = bf.readLine();
                }
                html += line;
                line = bf.readLine();
            }
            return html.getBytes();
        } catch (IOException e) {
            throw new FileSystemException(e);
        }
    }
}
