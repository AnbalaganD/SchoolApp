package edu.schoolapp.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class AssignmentModel {
    private String title;
    private String description;
    private String unit;
    private int mark;
    private int markObtained;

    public AssignmentModel() {
    }

    public AssignmentModel(String title, String description, String unit, int mark, int markObtained) {
        this.title = title;
        this.description = description;
        this.unit = unit;
        this.mark = mark;
        this.markObtained = markObtained;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getMarkObtained() {
        return markObtained;
    }

    public void setMarkObtained(int markObtained) {
        this.markObtained = markObtained;
    }
}
