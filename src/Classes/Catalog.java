package Classes;

import java.util.ArrayList;

public class Catalog {

    private int nextId;
    private int numTables;
    private int pageSize;
    private int bufferSize;
    private ArrayList<TableSchema> tables;

    public Catalog(int numTables, int pageSize, int bufferSize) {
        nextId = 0;
        this.numTables = numTables;
        this.pageSize = pageSize;
        this.bufferSize = bufferSize;
        tables = new ArrayList<TableSchema>();
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public int getNumTables() {
        return numTables;
    }

    public void setNumTables(int numTables) {
        this.numTables = numTables;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public ArrayList<TableSchema> getTables() {
        return tables;
    }

    public void addTable(TableSchema table) {
        nextId++;
        table.setId(nextId);
        tables.add(table);
        numTables++;
    }

    public int getIdFromName(String tableName) {
        for (TableSchema table : tables) {
            if (table.getName().equals(tableName)) {
                return table.getId();
            }
        }
        return -1;
    }

    public void dropTableName(String tableName) {
        for (TableSchema table : tables) {
            if (table.getName().equals(tableName)) {
                tables.remove(table);
                numTables--;
            }
        }
    }

    public void dropTableId(int tableId) {
        for (TableSchema table : tables) {
            if (table.getId() == tableId) {
                tables.remove(table);
                numTables--;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of Tables: ").append(numTables).append("\n");
        sb.append("Page Size: ").append(pageSize).append("\n");
        sb.append("Buffer Size: ").append(bufferSize).append("\n");
        for (TableSchema table : tables) {
            sb.append(table.toString()).append("\n");
        }
        return sb.toString();
    }

}