package edu.schoolapp.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class PrimaryMenuDao {

    private String name;
    private String type;
    private boolean isEnable;

    public PrimaryMenuDao() {
    }

    public PrimaryMenuDao(String name, String type, boolean isEnable) {
        this.name = name;
        this.type = type;
        this.isEnable = isEnable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
