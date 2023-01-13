package util;

import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileIoUtilsTest {

    @Test
    void loadFileFromClasspath() throws URISyntaxException, IOException, NullPointerException {

        final String fileName = "templates/index.html";

//        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(fileName).toURI());
        ClassLoader classLoader = this.getClass().getClassLoader();
        String name = classLoader.getName();
        URL url = this.getClass().getClassLoader().getResource(fileName);
        String tempUri = url.toURI().toString();

        System.out.println("name : " + name);
        System.out.println("tempUri : " + tempUri);

        final URL fileUrl = this.getClass()
                .getClassLoader()
                .getResource(fileName);
        final Path path = Paths.get(fileUrl.toURI());

        byte[] bytes = Files.readAllBytes(path);



        final List<String> actual = Files.readAllLines(path);

        assertThat(actual).containsOnly("templates/index");
    }
}
