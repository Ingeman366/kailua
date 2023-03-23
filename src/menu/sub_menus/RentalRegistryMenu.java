package menu.sub_menus;

import dbm.DB_Dependencies;
import dbm.handler.DB_QueryEditingHandler;
import dbm.handler.DB_QueryRequestHandler;
import dbm.interfaces.query_interfaces.DBStandardQueries;
import menu.Menu;
import utility.UI;

import java.util.ArrayList;
import java.util.Arrays;

public class RentalRegistryMenu extends Menu implements DBStandardQueries {
    /**
     * The CarInfoMenu constructor
     */
    public RentalRegistryMenu(String menuHeader, String[] menuItems) {
        super(menuHeader, menuItems);
    }


    @Override
    public void showTable(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies) {
        String sql = "SELECT * FROM " + db_dependencies.TABLE_NAMES[2];
        requestHandler.printQueryResult(
                sql,
                db_dependencies.printFormat(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[2])),
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[2]));

    } // End of method

    @Override
    public void showTableOrdered(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies) {
        String sql = "SELECT * " +
                "FROM " + db_dependencies.TABLE_NAMES[2] + " " +
                "ORDER BY " + db_dependencies.RENTAL_REGISTRY_COLUMNS[1] + " ASC";

        requestHandler.printQueryResult(
                sql,
                db_dependencies.printFormat(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[2])),
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[2]));
    } // End of method

    @Override
    public void insertToTable(DB_QueryEditingHandler editingHandler,
                              DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {

        if (isCarsAvailable(requestHandler, db_dependencies)) {
            String selectLeasingAgreementSelection = String.join(", ",
                    Arrays.stream(db_dependencies.RENTAL_REGISTRY_COLUMNS)
                            .skip(1)
                            .toArray(String[]::new));

            String insertNewLeasingAgreementSQL = "INSERT INTO " + db_dependencies.TABLE_NAMES[2] + " " +
                    "(" + selectLeasingAgreementSelection + ") \n" +
                    "VALUES (" +
                    ui.insertInto(
                            requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[2]),
                            requestHandler,
                            db_dependencies.TABLE_NAMES[2], true, true) + ")";

            editingHandler.insertQuery(insertNewLeasingAgreementSQL);
        } // End of if statement
    } // ENd of method

    @Override
    public void updateTable(DB_QueryEditingHandler editingHandler,
                            DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {
        String query = "UPDATE car_registry " + "SET " +
                ui.insertInto(db_dependencies.RENTAL_REGISTRY_COLUMNS,
                        requestHandler, db_dependencies.TABLE_NAMES[2], false, false) +

                " WHERE " + ui.chooseWhereOptions(
                db_dependencies.TABLE_NAMES[2],
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[2]),
                requestHandler) + ";";

        System.out.println(query);
        editingHandler.insertQuery(query);
    } // End of method

    @Override
    public void deleteFromTable(DB_QueryEditingHandler editingHandler,
                                DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {

    }

    public static void getAvailableCarsByGroup(boolean isRegistryMenu, String columnElement, DB_Dependencies db_dependencies,
                                               DB_QueryRequestHandler requestHandler, UI ui) {

        if (isRegistryMenu && (columnElement.equals(db_dependencies.CUSTOMER_COLUMNS[0]) ||
                columnElement.equals(db_dependencies.CAR_REGISTRY_COLUMNS[0]))) {

            if (columnElement.equals(db_dependencies.CUSTOMER_COLUMNS[0])) {
                requestHandler.printQueryResult("SELECT * FROM " + db_dependencies.TABLE_NAMES[0],
                        db_dependencies.printFormat(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[0])),
                        requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[0]));

            } else {
                requestHandler.printQueryResult("SELECT car_registry_id, crg.car_rental_group_id, crg.car_rental_group_name," +
                                String.join(", ", Arrays.asList(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1])).subList(1, 10)) + ", is_car_rented\n" +
                                "        FROM car_rental_group crg\n" +
                                "        JOIN car_properties USING(car_rental_group_id)\n" +
                                "        JOIN car_registry USING (car_properties_id)\n" +
                                "        WHERE crg.car_rental_group_id = " + ui.getRentalGroup() + " AND NOT is_car_rented;",
                        db_dependencies.CAR_REGISTRY_CAR_RENTAL_JOIN_COLUMNS_PRINT,
                        db_dependencies.CAR_REGISTRY_CAR_RENTAL_JOIN_COLUMNS);

            } // End of inner if-else statement
        } // End of outer if statement
    } // End of method

    public void returnRentedCar(DB_QueryEditingHandler editingHandler,
                                DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {
        requestHandler.printQueryResult("SELECT cr.car_registry_id,cu.customer_id, cu.customer_name, cu.customer_phone, " +
                        "cu.customer_email,rg.rental_registry_id, cr.car_brand, " +
                        "rg.rental_start_date, rg.rental_end_date\n" +
                        "FROM customer_info cu\n" +
                        "JOIN rental_registry rg USING (customer_id)\n" +
                        "JOIN car_registry cr USING (car_registry_id)\n" +
                        "WHERE cr.is_car_rented = 1;",
                db_dependencies.JOIN_FOR_CAR_ISRENTED_PRINT,
                db_dependencies.JOIN_FOR_CAR_ISRENTED);
        ArrayList<Integer> possibleIDS = requestHandler.getAllIDs("SELECT car_registry_id " +
                "FROM car_registry cr\n" +
                "WHERE cr.is_car_rented = 1;", "car_registry_id");
        System.out.print("Please enter car ID for rented car to return: ");

        int carID = ui.readInteger();
        if (possibleIDS.contains(carID)) {
            editingHandler.insertQuery("UPDATE car_registry\n" +
                    "JOIN rental_registry USING (car_registry_id)\n" +
                    "SET is_car_rented = 0, rental_registry.rental_end_date = CURDATE()\n" +
                    "WHERE " + carID + " = rental_registry.rental_registry_id");
        } else {
            System.out.println("The car ID you wrote is not possible to return...");
        } // End of if-else statement
    } // End of method

    public void showOverdueCarRent(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies) {
        requestHandler.printQueryResult("SELECT cu.customer_id, DATEDIFF( CURDATE(), " +
                        "rg.rental_end_date) AS days_overdue, cu.customer_name, " +
                        "cu.customer_phone, cu.customer_email, cr.car_registry_id, " +
                        "cr.car_brand,rg.rental_registry_id, rg.rental_start_date, " +
                        "rg.rental_end_date " +
                "FROM customer_info cu " +
                "JOIN rental_registry rg USING (customer_id) " +
                "JOIN car_registry cr USING (car_registry_id) " +
                "WHERE rg.rental_end_date < CURDATE() AND cr.is_car_rented = 1 " +
                "ORDER BY days_overdue DESC;",
                db_dependencies.JOIN_FOR_CAR_ISRENTED_W_OVERDUE_PRINT,
                db_dependencies.JOIN_FOR_CAR_ISRENTED_W_OVERDUE);
    } // End of method

    private boolean isCarsAvailable(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies) {
        if (requestHandler.checkIfEmpty("SELECT * FROM " + db_dependencies.TABLE_NAMES[3] +
                " WHERE " + db_dependencies.CAR_REGISTRY_COLUMNS[7] + " = 0")) {
            System.out.println("All cars are rented at the very moment");
            return false;
        } else {
            return true;
        } // End of if-else statement
    } // End of method

}
