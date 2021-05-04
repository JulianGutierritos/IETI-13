package co.edu.eci.ieti.android.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "Task")
public class Task
{
    @PrimaryKey
    private long id;

    private String description;

    private int priority;

    @Embedded
    private User assignedTo;

    @TypeConverters(DateConverter.class)
    private Date dueDate;

    public Task()
    {
    }

    public Task( long id, String description, int priority, Date dueDate, User assignedTo )
    {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.assignedTo = assignedTo;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority( int priority )
    {
        this.priority = priority;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public void setDueDate( Date dueDate )
    {
        this.dueDate = dueDate;
    }

    public void setAssignedTo( User assignedTo )
    {
        this.assignedTo = assignedTo;
    }

    public User getAssignedTo()
    {
        return assignedTo;
    }

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Task{" + "description='" + description + '\'' + ", priority=" + priority + ", dueDate=" + dueDate + '}';
    }
}


