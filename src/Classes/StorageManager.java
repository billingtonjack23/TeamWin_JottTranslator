package Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.crypto.Data;

public class StorageManager {
    private final String[] keywords = {"create", "drop", "add", "table", "default", "notnull", "primarykey", "unique", "insert", "into", "values"};

    private Catalog catalog;
    private String dbLocation;
    private int pageSize;
    private int bufferSize;

    public StorageManager(String dbLocation, int pageSize, int bufferSize) {
        this.catalog = null;
        this.dbLocation = dbLocation;
        this.pageSize = pageSize;
        this.bufferSize = bufferSize;
    }

    public void createDatabase() {
        this.catalog = new Catalog(0, pageSize, bufferSize);
        String catalogLocation = dbLocation + File.separator + "catalog";
        writeCatalog(catalogLocation, this.catalog);
    }

    public void restartDatabase() {
        String catalogLocation = dbLocation + File.separator + "catalog";
        this.catalog = readCatalog(catalogLocation);
        this.catalog.setBufferSize(bufferSize);
        writeCatalog(catalogLocation, this.catalog);
    }

    public void writeCatalog(String catalogLocation, Catalog catalog) {
        try (FileOutputStream fos = new FileOutputStream(catalogLocation)) {
            byte[] bytes;

            //write nextId
            bytes = ByteBuffer.allocate(4).putInt(catalog.getNextId()).array();
            fos.write(bytes);

            //write num tables
            bytes = ByteBuffer.allocate(4).putInt(catalog.getNumTables()).array();
            fos.write(bytes);

            //write page size
            bytes = ByteBuffer.allocate(4).putInt(catalog.getPageSize()).array();
            fos.write(bytes);

            //write buffer size
            bytes = ByteBuffer.allocate(4).putInt(catalog.getBufferSize()).array();
            fos.write(bytes);

            //for each table in catalog
            for (TableSchema table : catalog.getTables()) {
                //write num attributes
                bytes = ByteBuffer.allocate(4).putInt(table.getAttributes().size()).array();
                fos.write(bytes);

                //write name, first length then the actual string
                bytes = table.getName().getBytes();
                fos.write(ByteBuffer.allocate(4).putInt(bytes.length).array());
                fos.write(bytes);

                //write id
                bytes = ByteBuffer.allocate(4).putInt(table.getId()).array();
                fos.write(bytes);

                //write numPages
                bytes = ByteBuffer.allocate(4).putInt(table.getNumPages()).array();
                fos.write(bytes);

                //write numRecords
                bytes = ByteBuffer.allocate(4).putInt(table.getNumRecords()).array();
                fos.write(bytes);

                //write pageOrdering, first length then actual list
                bytes = ByteBuffer.allocate(4).putInt(table.getPageOrdering().size()).array();
                fos.write(bytes);
                for (Integer i : table.getPageOrdering()) {
                    bytes = ByteBuffer.allocate(4).putInt(i).array();
                    fos.write(bytes);
                }

                for (AttrSchema attr : table.getAttributes()) {
                    //write name, first length then the actual string
                    bytes = attr.getName().getBytes();
                    fos.write(ByteBuffer.allocate(4).putInt(bytes.length).array());
                    fos.write(bytes);

                    //write datatype
                    bytes = ByteBuffer.allocate(4).putInt(attr.getType().ordinal()).array();
                    fos.write(bytes);

                    //write length
                    bytes = ByteBuffer.allocate(4).putInt(attr.getLength()).array();
                    fos.write(bytes);

                    //write constraints, first length then constraints
                    bytes = ByteBuffer.allocate(4).putInt(attr.getConstraints().size()).array();
                    fos.write(bytes);
                    for (Constraint constraint : attr.getConstraints()) {
                        bytes = ByteBuffer.allocate(4).putInt(constraint.ordinal()).array();
                        fos.write(bytes);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Catalog readCatalog(String catalogLocation) {
        Catalog returnCatalog = new Catalog(0, 0, 0);
        try (FileInputStream fis = new FileInputStream(catalogLocation)) {
            byte[] intBytes = new byte[4];

            //read nextId
            fis.read(intBytes);
            returnCatalog.setNextId(ByteBuffer.wrap(intBytes).getInt());

            //read numtables
            fis.read(intBytes);
            int numTables = ByteBuffer.wrap(intBytes).getInt();

            //read page size
            fis.read(intBytes);
            returnCatalog.setPageSize(ByteBuffer.wrap(intBytes).getInt());

            //read buffer size
            fis.read(intBytes);
            returnCatalog.setBufferSize(ByteBuffer.wrap(intBytes).getInt());

            //for each table
            for (int i = 0; i < numTables; i++) {
                TableSchema newTable = new TableSchema("");

                //reading num attributes
                fis.read(intBytes);
                int numAttributes = ByteBuffer.wrap(intBytes).getInt();

                //read name, first length then actual string
                fis.read(intBytes);
                int nameLength = ByteBuffer.wrap(intBytes).getInt();
                byte[] nameBytes = new byte[nameLength];
                fis.read(nameBytes);
                newTable.setName(new String(nameBytes));

                //read id
                fis.read(intBytes);
                newTable.setId(ByteBuffer.wrap(intBytes).getInt());

                //read numpages
                fis.read(intBytes);
                newTable.setNumPages(ByteBuffer.wrap(intBytes).getInt());

                //read numrecords
                fis.read(intBytes);
                newTable.setNumRecords(ByteBuffer.wrap(intBytes).getInt());

                //read page ordering, first length then actual list
                ArrayList<Integer> newPageOrdering = new ArrayList<Integer>();
                fis.read(intBytes);
                int pageOrderingLength = ByteBuffer.wrap(intBytes).getInt();
                for (int j = 0; j < pageOrderingLength; j++) {
                    fis.read(intBytes);
                    newPageOrdering.add(ByteBuffer.wrap(intBytes).getInt());
                }
                newTable.setPageOrdering(newPageOrdering);

                //go through attributes
                for (int j = 0; j < numAttributes; j++) {
                    AttrSchema newAttr = new AttrSchema("", DataType.INTEGER, 0);

                    //read name, first length then string
                    fis.read(intBytes);
                    nameLength = ByteBuffer.wrap(intBytes).getInt();
                    nameBytes = new byte[nameLength];
                    fis.read(nameBytes);
                    String attrName = new String(nameBytes);
                    newAttr.setName(attrName);

                    //read datatype by reading int then mapping to enum
                    fis.read(intBytes);
                    int ordinal = ByteBuffer.wrap(intBytes).getInt();
                    newAttr.setType(DataType.values()[ordinal]);

                    //read length
                    fis.read(intBytes);
                    newAttr.setLength(ByteBuffer.wrap(intBytes).getInt());

                    //get constraints
                    fis.read(intBytes);
                    int constraintCount = ByteBuffer.wrap(intBytes).getInt();
                    ArrayList<Constraint> constraints = new ArrayList<Constraint>();
                    for (int k = 0; k < constraintCount; k++) {
                        fis.read(intBytes);
                        ordinal = ByteBuffer.wrap(intBytes).getInt();
                        constraints.add(Constraint.values()[ordinal]);
                    }
                    newAttr.setConstraints(constraints);

                    newTable.addAttrSchema(newAttr);
                }


                returnCatalog.addTable(newTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnCatalog;
    }

    public String getDbLocation() {
        return dbLocation;
    }

    public void setDbLocation(String dbLocation) {
        this.dbLocation = dbLocation;
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

    public void parseCreateTable(String command) {
        System.out.println("Creating table with command: " + command);
        command = command.substring("create table ".length()).trim();
        String tableName = command.substring(0, command.indexOf("(")).trim();
        for (TableSchema table : catalog.getTables()) {
            if (table.getName().equals(tableName)) {
                System.out.println("Error: A table with the name %s already exists.\\n");
                return;
            }
        }
        TableSchema newTable = new TableSchema(tableName);

        command = command.substring(command.indexOf("(") + 1).trim();
        while (!command.startsWith(");")) {
            String a_name = command.substring(0, command.indexOf(" ")).trim();
            command = command.substring(command.indexOf(" ") + 1).trim().strip();
            String a_type = command.substring(0, command.indexOf(" ")).trim();
            command = command.substring(command.indexOf(" ") + 1).trim().strip();
            DataType type;
            int length = 0;
            if (a_type.toUpperCase().equals("INTEGER")) {
                type = DataType.INTEGER;
            }
            else if (a_type.toUpperCase().equals("DOUBLE")) {
                type = DataType.DOUBLE;
            }
            else if (a_type.toUpperCase().equals("BOOLEAN")) {
                type = DataType.BOOLEAN;
            }
            else if (a_type.toUpperCase().startsWith("CHAR(")) {
                type = DataType.CHAR;
                length = Integer.parseInt(a_type.substring(5, a_type.indexOf(")")));

            }
            else if (a_type.toUpperCase().startsWith("VARCHAR(")) {
                type = DataType.VARCHAR;
                length = Integer.parseInt(a_type.substring(8, a_type.indexOf(")")));
            }
            else {
                System.out.println("Error: Invalid data type %s.\\n");
                return;
            }
            ArrayList<Constraint> constraints = new ArrayList<Constraint>();
            if (command.startsWith("primarykey")) {
                constraints.add(Constraint.PRIMARYKEY);
                command = command.substring("PRIMARYKEY".length()).trim().strip();
            }
            else if (command.startsWith("notnull")) {
                constraints.add(Constraint.NOTNULL);
                command = command.substring("NOTNULL".length()).trim().strip();
                if (command.startsWith("unique")) {
                    constraints.add(Constraint.UNIQUE);
                    command = command.substring("UNIQUE".length()).trim().strip();
                }
            }
            else if (command.startsWith("unique")) {
                constraints.add(Constraint.UNIQUE);
                command = command.substring("UNIQUE".length()).trim().strip();
                if (command.startsWith("notnull")) {
                    constraints.add(Constraint.NOTNULL);
                    command = command.substring("NOTNULL".length()).trim().strip();
                }
            }
            AttrSchema newAttr = new AttrSchema(a_name, type, length);
            newAttr.setConstraints(constraints);
            if (command.startsWith(",")) {
                command = command.substring(1).trim().strip();
            }
            newTable.addAttrSchema(newAttr);
        }

        try (FileOutputStream fos = new FileOutputStream(dbLocation + File.separator + tableName + ".bin")) {
            byte[] bytes;

            bytes = ByteBuffer.allocate(4).putInt(0).array();
            fos.write(bytes);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        catalog.addTable(newTable);
        String catalogLocation = dbLocation + File.separator + "catalog";
        writeCatalog(catalogLocation, catalog);
        System.out.println("Table " + tableName + " created successfully.");
        return;
    }

    public void parseDropTable(String command) {
        System.out.println("Dropping table with command: " + command);
        command = command.substring("drop table ".length()).trim();
        String tableName = command.substring(0, command.indexOf(";")).trim();
        for (TableSchema table : catalog.getTables()) {
            if (table.getName().equals(tableName)) {
                catalog.dropTableName(tableName);
            }
        }
    }

    public void displaySchema() {
        System.out.println("Displaying schema for catalog:\n");
        System.out.println(catalog.toString());
        return;
    }

    public void parseDisplayTable(String command) {
        System.out.println("Displaying table with command: " + command);
        String tableName = command.substring("display info ".length()).trim();
        tableName = tableName.substring(0, tableName.length() - 1);
        for (TableSchema table : catalog.getTables()) {
            if (table.getName().equals(tableName)) {
                System.out.println(table.toString());
                return;
            }
        }
    }

    public void parseSelect(String command) {
        System.out.println("Selecting with command: " + command);
        command = command.substring("select ".length()).trim().strip();
        if (command.startsWith("*")) {
            command = command.substring("*".length()).trim().strip();
            command = command.substring(command.indexOf("from") + 4).trim().strip();
            String tableName = command.substring(0, command.indexOf(";")).trim().strip();
            System.out.println(tableName);
        }
        
    }

    public void parseInsert(String command) {
        System.out.println("Inserting with command: " + command);
    }

    public void parseAlterTable(String command) {
        System.out.println("Altering table with command: " + command);
        command = command.substring("alter table ".length()).trim();
        String tableName = command.substring(0, command.indexOf(" ")).trim();
        System.out.println("Debug: " + tableName);
        command = command.substring(command.indexOf(" ")).trim();
        System.out.println("Debug: " + command);

        if(command.startsWith("drop "))
        {
            System.out.println("Debug: INSIDE DROP IF");
            command = command.substring("drop ".length()).trim();
            System.out.println("Debug: " + command);
            String attrName = command.substring(0, command.indexOf(";")).trim();
            System.out.println("Debug: " + attrName);

            for (TableSchema table : catalog.getTables()) {
                if (table.getName().equals(tableName)) {
                    // table.dropColumn(columnName);
                    // Add functionality to drop the attribute from the table
                    

                    System.out.println("Column " + attrName + " dropped from table " + tableName);
                    return;
                }
            }
            System.out.println("Error: Table " + tableName + " not found.");
        }
        else if(command.startsWith("add "))
        {
            System.out.println("Debug: INSIDE ADD IF");
            command = command.substring("add ".length()).trim();
            System.out.println("Debug: " + command);
            String[] parts = command.split("\\s+");
            if (parts.length < 2) {
                System.out.println("Error: Invalid add column command.");
                return;
            }

            String attr_name = parts[0];
            String attr_type = parts[1].replace(";", "");
            System.out.println("Debug: " + attr_name);
            System.out.println("Debug: " + attr_type);

            // String attr_name = command.substring(0, command.indexOf(" ")).trim();
            // String attr_type  = command.substring(command.indexOf(" ")).trim().replace(";", "");;
            // System.out.println("Debug: " + attr_name);
            // System.out.println("Debug: " + attr_type);
            
            DataType type;
            if (attr_type.toUpperCase().equals("INTEGER")) {
                type = DataType.INTEGER;
            }
            else if (attr_type.toUpperCase().equals("DOUBLE")) {
                type = DataType.DOUBLE;
            }
            else if (attr_type.toUpperCase().equals("BOOLEAN")) {
                type = DataType.BOOLEAN;
            }
            else if (attr_type.toUpperCase().startsWith("CHAR(")) {
                type = DataType.CHAR;
            }
            else if (attr_type.toUpperCase().startsWith("VARCHAR(")) {
                type = DataType.VARCHAR;
            }
            else {
                System.out.println("Error: Invalid data type %s.\\n");
                return;
            }
            String defaultValue = null;
            if (parts.length > 3 && parts[2].equalsIgnoreCase("default")) {
                System.out.println("Debug: INSIDE IF" + parts[3]);
                defaultValue = parts[3].replace(";", "");
            }

            // Add column logic here
            for (TableSchema table : catalog.getTables()) {
                if (table.getName().equals(tableName)) {
                    // table.addColumn(attr_name, type, defaultValue);
                    System.out.println("Column " + attr_name + " added to table " + tableName);
                    return;
                }
            }
            System.out.println("Error: Table " + tableName + " not found.");
            return;
        }
        else
        {
            System.out.println("Error: Unsupported alter table command.");
            return;
        }
    

        // command = command.substring(command.indexOf(" ")).trim();
        // System.out.println("Debug: " + command);

        // if (command.startsWith("drop ")) {
        //     command = command.substring("drop ".length()).trim();
        //     String columnName = command.substring(0, command.indexOf(";")).trim();

        //     for (TableSchema table : catalog.getTables()) {
        //         if (table.getName().equals(tableName)) {
        //             //table.dropColumn(columnName);
        //             System.out.println("Column " + columnName + " dropped from table " + tableName);
        //             return;
        //         }
        //     }
        //     System.out.println("Error: Table " + tableName + " not found.");
        // } else {
        //     System.out.println("Error: Unsupported alter table command.");
        // }
        }


        // public void dropColumn(String columnName) {
        //     for (Iterator<AttrSchema> iterator = attributes.iterator(); iterator.hasNext();) {
        //         AttrSchema attr = iterator.next();
        //         if (attr.getName().equals(columnName)) {
        //             iterator.remove();
        //             System.out.println("Column " + columnName + " removed.");
        //             return;
        //         }
        //     }
        //     System.out.println("Error: Column " + columnName + " not found.");
        // }

        // public void addColumn(String columnName, DataType columnType, String defaultValue) {
        //     AttrSchema newAttr = new AttrSchema(columnName, columnType, 0);
        //     if (defaultValue != null) {
        //         newAttr.setDefaultValue(defaultValue);
        //     }
        //     attributes.add(newAttr);
        //     System.out.println("Column " + columnName + " added.");
        // }
}

