package github.hmasum18.architecture.service.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//step 1
//this is where we create the table
@Entity(tableName = "note_table")
//if we don't define table name it will be 'note'
public  class Note {
    //this field will be the table column and column name will be as field name

    //id will auto incremented
    @PrimaryKey(autoGenerate = true)
    private int id ;
    //@ColumnInfo(name = "note_title") //rename the column name
    private String title;
    @ColumnInfo(name = "note_description")
    private String description;
   // @Ignore //we can ingnore the field this won't add the field as column in the table
    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    //we only need this setter to used by Room as it is note in constructor
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
