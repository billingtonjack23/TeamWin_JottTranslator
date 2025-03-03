package Classes;

import java.util.ArrayList;
import java.util.List;

public class Record {
    private String name;
    private List<String> values;

    public Record(String name) {
        this.name = name;
        this.values = new ArrayList<>();
    }

    public void addValue(String value) {
        values.add(value);
    }

    public String getName() {
        return name;
    }
}