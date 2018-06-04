package edu.schoolapp;

public class PrimaryMenuModel {
    private int menuImageResource;
    private String menuName;
    private PrimaryMenuType menuType;

    public PrimaryMenuModel(int menuImageResource, String menuName, PrimaryMenuType menuType) {
        this.menuImageResource = menuImageResource;
        this.menuName = menuName;
        this.menuType = menuType;
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
}
