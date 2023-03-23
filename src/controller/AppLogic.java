package controller;

import dbm.DB_Dependencies;
import dbm.handler.DB_QueryEditingHandler;
import dbm.handler.DB_QueryRequestHandler;
import utility.*;
import menu.handler.*;

/**
 * The AppLogic class is defining and running all the logic within the app.
 * Think about it as the controller of the whole application, requesting and handling queries
 * from each class.
 * <p>In essence different menus will be calling queries on the database for the Kailua Rental Group.</p>
 * @implNote {@link #runMainMenu()} Will be showing the different sections in categorized fashion. <br>
 * {@link #runCustomerMenu()} Will be handling all customer queries (adding new customer, delete, edit etc.) <br>
 * {@link #runRentalRegistryMenu()} Will be handling all leasing agreements while query details on cars and specifications <br>
 * {@link #runCarRegistryMenu()} Will be handling all queries when user wants to create unique search criteria on car info <br>
 * {@link #runCarPropertyMenu()} Will be handling all queries specific for properties belonging within a car <br>
 * {@link #runAnalysisMenu()} Will be handling detailed analysis queries if employees want to get some detailed information about customer behaviour <br>
 */
public class AppLogic {
    // Fields ----------------------------------------------------------------------------
    private final UI ui;
    private final MenuHandler menuHandler;
    private final DB_QueryRequestHandler queryRequestHandler;
    private final DB_QueryEditingHandler editingHandler;
    private final DB_Dependencies db_dependencies = dbm.DB_Dependencies.getInstance();



    // Constructor -----------------------------------------------------------------------

    /**
     * The AppLogic constructor.
     */
    public AppLogic() {
        ui = new UI();
        menuHandler = new MenuHandler();
        queryRequestHandler = new DB_QueryRequestHandler();
        editingHandler = new DB_QueryEditingHandler();
    }


    // Methods ---------------------------------------------------------------------------

    /**
     *
     */
    public void runMainMenu() {
        boolean isRunning = true;
        while (isRunning) {
            menuHandler.mainMenu.printMenu();

            // This method updates is_car_rented on all cars which rental_end_date has been surpassed.
            editingHandler.insertQuery(db_dependencies.setCarToIsRented);

            switch (ui.readInteger()) {
                case 1 -> runCustomerMenu();
                case 2 -> runCarRegistryMenu();
                case 3 -> runCarPropertyMenu();
                case 4 -> runRentalRegistryMenu();
                case 5 -> runAnalysisMenu();
                case 0 -> isRunning = false;
                default -> System.out.println(ui.invalidChoiceInput());
            } // End of switch case
        } // End of while loop
        System.exit(1);
    } // End of method

    private void runCustomerMenu() {
        boolean isRunning = true;
        while (isRunning) {
            menuHandler.customerMenu.printMenu();
            switch (ui.readInteger()) {
                case 1 -> menuHandler.customerMenu.showTable(queryRequestHandler, db_dependencies);
                case 2 -> menuHandler.customerMenu.showTableOrdered(queryRequestHandler, db_dependencies);
                case 3 -> menuHandler.customerMenu.insertToTable(editingHandler, queryRequestHandler,ui, db_dependencies);
                case 4 -> menuHandler.customerMenu.updateTable(editingHandler, queryRequestHandler,ui, db_dependencies);
                case 5 -> menuHandler.customerMenu.deleteFromTable(editingHandler, queryRequestHandler,ui, db_dependencies);
                case 0 -> isRunning = false;
                default -> System.out.println(ui.invalidChoiceInput());
            } // End of switch case
        } // End of while loop
    } // End of method

    private void runRentalRegistryMenu() {
        boolean isRunning = true;
        while (isRunning){
            menuHandler.rentalRegistryMenu.printMenu();
            switch (ui.readInteger()) {
                case 1 -> menuHandler.rentalRegistryMenu.showTable(queryRequestHandler, db_dependencies);
                case 2 -> menuHandler.rentalRegistryMenu.showTableOrdered(queryRequestHandler,db_dependencies);
                case 3 -> menuHandler.rentalRegistryMenu.insertToTable(editingHandler,queryRequestHandler,ui,db_dependencies);
                case 4 -> menuHandler.rentalRegistryMenu.updateTable(editingHandler,queryRequestHandler,ui,db_dependencies);
                case 5 -> menuHandler.rentalRegistryMenu.returnRentedCar(editingHandler, queryRequestHandler, ui, db_dependencies);
                case 6 -> menuHandler.rentalRegistryMenu.showOverdueCarRent(queryRequestHandler,db_dependencies);
                case 0 -> isRunning = false;
                default -> System.out.println(ui.invalidChoiceInput());
            } // End of switch case
        } // End of while loop
    } // End of method

    private void runCarRegistryMenu() {
        boolean isRunning = true;
        while (isRunning) {
            menuHandler.carRegistryMenu.printMenu();
            switch (ui.readInteger()) {
                case 1 -> menuHandler.carRegistryMenu.showTable(queryRequestHandler,db_dependencies);
                case 2 -> menuHandler.carRegistryMenu.showTableOrdered(queryRequestHandler,db_dependencies);
                case 3 -> menuHandler.carRegistryMenu.insertToTable(editingHandler, queryRequestHandler,ui,db_dependencies);
                case 4 -> menuHandler.carRegistryMenu.updateTable(editingHandler, queryRequestHandler, ui,db_dependencies);
                case 5 -> menuHandler.carRegistryMenu.deleteFromTable(editingHandler, queryRequestHandler, ui,db_dependencies);
                case 0 -> isRunning = false;
                default -> System.out.println(ui.invalidChoiceInput());
            } // End of switch case
        } // End of while loop
    } // End of method

    public void runCarPropertyMenu() {
        boolean isRunning = true;
        while (isRunning) {
            menuHandler.carPropertiesMenu.printMenu();
            switch (ui.readInteger()) {
                case 1 -> menuHandler.carPropertiesMenu.showTable(queryRequestHandler,db_dependencies);
                case 2 -> menuHandler.carPropertiesMenu.showTableOrdered(queryRequestHandler,db_dependencies);
                case 3 -> menuHandler.carPropertiesMenu.searchAndShowTable(queryRequestHandler, ui,db_dependencies);
                case 4 -> menuHandler.carPropertiesMenu.updateTable(editingHandler,queryRequestHandler,ui,db_dependencies);
                case 5 -> menuHandler.carPropertiesMenu.deleteFromTable(editingHandler,queryRequestHandler,ui,db_dependencies);
                case 0 -> isRunning = false;
                default -> System.out.println(ui.invalidChoiceInput());
            } // End of switch case
        } // End of while loop
    } // End of method

    private void runAnalysisMenu() {
        boolean isRunning = true;
        while (isRunning) {
            menuHandler.analysisMenu.printMenu();
            switch (ui.readInteger()) {
                case 1 -> {menuHandler.analysisMenu.showTable(queryRequestHandler,db_dependencies);}
                case 4 -> {menuHandler.analysisMenu.showCityInfo(ui,queryRequestHandler,db_dependencies);}
                case 0 -> {isRunning = false;}
                default -> System.out.println(ui.invalidChoiceInput());
            } // End of switch case
        } // End of while loop
    } // End of method


}