package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] row = content.split(System.lineSeparator());
        String[] firstRow = row[0].split("/");
        if (firstRow[0].startsWith("POST")) {
            return new Req(firstRow[0].split(" ")[0],
                    firstRow[1], firstRow[2].split(" ")[0],
                    row[row.length - 1]);
        }
        if (firstRow[0].startsWith("GET") && firstRow.length == 5) {
            return new Req(firstRow[0].split(" ")[0],
                    firstRow[1], firstRow[2].split(" ")[0],
                    firstRow[3].split(" ")[0]);
        }
        if (firstRow[0].startsWith("GET")) {
            return new Req(firstRow[0].split(" ")[0],
                    firstRow[1], firstRow[2].split(" ")[0],
                    "");
        }
        return new Req(null, null, null, null);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }

}