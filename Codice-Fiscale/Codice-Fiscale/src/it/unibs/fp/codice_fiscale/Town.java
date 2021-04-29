package it.unibs.fp.codice_fiscale;

public class Town extends Parsable {
    private String name;
    private String code;
    public static final String START_STRING = "comune";

    public Town() {
        methods.put("nome", this::setName);
        methods.put("codice", this::setCode);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Town{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public String getStartString() {
        return START_STRING;
    }
}
