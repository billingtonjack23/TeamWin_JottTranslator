package Classes;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private int id;
    private List<Record> records;

    public Page(int id) {
        this.id = id;
        this.records = new ArrayList<>();
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public int getId() {
        return id;
    }
}