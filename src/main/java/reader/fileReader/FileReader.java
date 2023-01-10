package reader.fileReader;

public interface FileReader {
    byte[] readFile(String url);

    static FileReader selectFileReader(String url) {
        if (url.matches(TemplatesFileReader.TEMPLATE_REGEX)) {
            return new TemplatesFileReader();
        } else {
            return new StaticFileReader();
        }
    }
}
