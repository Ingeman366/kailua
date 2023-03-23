package menu.sub_menus;

import dbm.DB_Dependencies;
import dbm.handler.DB_QueryEditingHandler;
import dbm.handler.DB_QueryRequestHandler;
import dbm.interfaces.query_interfaces.DBStandardQueries;
import menu.Menu;
import utility.UI;

import java.util.Arrays;

public class CustomerMenu extends Menu implements DBStandardQueries {
    /**
     * @param menuHeader    Title of the menu
     * @param menuItems     Menu elements
     */

    public CustomerMenu(String menuHeader, String[] menuItems) {
        super(menuHeader, menuItems);
    }

    // Methods ---------------------------------------------------------------------

    @Override
    public void showTable(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies) {
        String query = "SELECT * FROM customer_info;";
        requestHandler.printQueryResult(query,
                db_dependencies.printFormat(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[0])),
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[0]));
    } // End of method

    @Override
    public void showTableOrdered(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies) {
        String query = "SELECT * FROM customer_info ORDER BY customer_name;";
        requestHandler.printQueryResult(query,
                db_dependencies.printFormat(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[0])),
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[0]));
    } // End of method


    @Override
    public void insertToTable(DB_QueryEditingHandler editingHandler,
                              DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {

        String selectSection = String.join(", ",
                Arrays.stream(DB_Dependencies.getInstance().CUSTOMER_COLUMNS).skip(1).toArray(String[]::new));

        String sql = "INSERT INTO " + db_dependencies.TABLE_NAMES[0] + " " +
                "(" + selectSection + ") \n" +
                "VALUES (" +
                ui.insertInto(
                        requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[0]),
                        requestHandler,
                        db_dependencies.TABLE_NAMES[0],true, false) + ")";

        editingHandler.insertQuery(sql);
    } // End of method


    @Override
    public void updateTable(DB_QueryEditingHandler editingHandler,
                            DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {
        String query = "UPDATE customer_info " + "SET " +
                ui.insertInto(db_dependencies.CUSTOMER_COLUMNS,
                        requestHandler,
                        db_dependencies.TABLE_NAMES[0], false, false) +
                " WHERE " + ui.chooseWhereOptions(
                        db_dependencies.TABLE_NAMES[0],
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[0]),
                requestHandler);
        System.out.println(query);
        editingHandler.insertQuery(query);
    } // End of method


    @Override
    public void deleteFromTable(DB_QueryEditingHandler editingHandler,
                                DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {
        String query = "DELETE FROM customer_info " +
                "WHERE " + ui.chooseWhereOptions(
                        db_dependencies.TABLE_NAMES[0],
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[0]),
                requestHandler);

        editingHandler.insertQuery(query);
    } // End of method
}
