package webserver.httpUtils.entity;

public class ResLine {
    private String version;
    private int statCode;
    private String statText;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getStatCode() {
        return statCode;
    }

    public void setStatCode(int statCode) {
        this.statCode = statCode;
    }

    public String getStatText() {
        return statText;
    }

    public void setStatText(String statText) {
        this.statText = statText;
    }
}
