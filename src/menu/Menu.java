package menu;

import java.util.Arrays;

/**
 * The idea of this class is to behave as a skeleton for different menus within the app.
 * Different sub-menus to inherit this class to ensure the same setup always is followed.
 */
public abstract class Menu {

    private final String menuHeader;
    private final String[] menuItems;
    private final String leadText;

    public void printMenu() {
        System.out.println(menuHeader);
        Arrays.stream(menuItems).forEach(System.out::println);
        System.out.println(leadText);
    }

    /**
     *
     * @param menuHeader
     * @param menuItems
     */
    public Menu(String menuHeader, String[] menuItems) {
        this.menuHeader = menuHeader;
        this.menuItems = menuItems;
        this.leadText = "PLEASE CHOOSE";
    }

}