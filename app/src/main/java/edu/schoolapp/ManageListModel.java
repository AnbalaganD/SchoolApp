package edu.schoolapp;

public class ManageListModel {
    private int manageListImage;
    private String title;
    private boolean isSelected;

    public ManageListModel(int manageListImage, String title, boolean isSelected) {
        this.manageListImage = manageListImage;
        this.title = title;
        this.isSelected = isSelected;
    }

    public ManageListModel(int manageListImage, String title) {
        this.manageListImage = manageListImage;
        this.title = title;
    }

    public int getManageListImage() {
        return manageListImage;
    }

    public void setManageListImage(int manageListImage) {
        this.manageListImage = manageListImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
