package edu.schoolapp;

public class PrimaryMenuModel {
    private int menuImageResource;
    private String menuName;
    private PrimaryMenuType menuType;
    private boolean isSelected = true;

    public PrimaryMenuModel(int menuImageResource, String menuName, PrimaryMenuType menuType, boolean isSelected) {
        setupData(menuImageResource, menuName, menuType, isSelected);
    }

    public PrimaryMenuModel(int menuImageResource, String menuName, PrimaryMenuType menuType) {
        setupData(menuImageResource, menuName, menuType, true);
    }

    private void setupData(int menuImageResource, String menuName, PrimaryMenuType menuType, boolean isSelected) {
        this.menuImageResource = menuImageResource;
        this.menuName = menuName;
        this.menuType = menuType;
        this.isSelected = isSelected;
    }

    public int getMenuImageResource() {
        return menuImageResource;
    }

    public void setMenuImageResource(int menuImageResource) {
        this.menuImageResource = menuImageResource;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public PrimaryMenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(PrimaryMenuType menuType) {
        this.menuType = menuType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
