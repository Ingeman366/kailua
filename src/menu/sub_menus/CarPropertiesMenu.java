package menu.sub_menus;

import dbm.DB_Dependencies;
import dbm.handler.DB_QueryEditingHandler;
import dbm.handler.DB_QueryRequestHandler;
import dbm.interfaces.query_interfaces.DBStandardQueries;
import menu.Menu;
import utility.UI;

import java.util.Arrays;

public class CarPropertiesMenu extends Menu implements DBStandardQueries {

    public CarPropertiesMenu(String menuHeader, String[] menuItems) {
        super(menuHeader, menuItems);
    } // End of method


    @Override
    public void showTable(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies) {
        String sql = "SELECT * FROM " + db_dependencies.TABLE_NAMES[1];

        requestHandler.printQueryResult(sql,
                db_dependencies.printFormat(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1])),
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1]));
    } // End of method

    @Override
    public void showTableOrdered(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies) {
        String sql = "SELECT * FROM " + db_dependencies.TABLE_NAMES[1] + " " +
                "ORDER BY " + db_dependencies.CAR_PROPERTIES_COLUMNS[9] + " ASC";

        requestHandler.printQueryResult(sql,
                db_dependencies.printFormat(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1])),
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1]));
    } // End of method

    @Override
    public void insertToTable(DB_QueryEditingHandler editingHandler,
                              DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {
        String selectCarPropertySelection = String.join(", ",
                Arrays.stream(db_dependencies.CAR_PROPERTIES_COLUMNS).skip(1).toArray(String[]::new));

        String insertPropertiesSQL = "INSERT INTO " + db_dependencies.TABLE_NAMES[1] + " " +
                "(" + selectCarPropertySelection + ") \n" +
                "VALUES (" +
                ui.insertInto(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1]),
                        requestHandler,
                        db_dependencies.TABLE_NAMES[1], true, false) + ")";

        editingHandler.insertQuery(insertPropertiesSQL);
    } // End of method


    /**
     * <p>This method incorporates the generic method from {@link UI#chooseWhereOptions(String columnTable, String[] columnValues, DB_QueryRequestHandler requestHandler)}
     * which allows for a SQL building block effect. <br>
     * This way the user can interact with generic seamless effect, allowing
     * for endless search options for searching specific car properties.</p>
     * <pr>The uniqueness of this method, is the double headed polymorphic setup, since insertion to car properties should
     * only happen if a new car is created. <br> <br>
     * When The method {@link CarRegistryMenu#insertToTable(DB_QueryEditingHandler, DB_QueryRequestHandler, UI, DB_Dependencies)}
     * is called, the car menu actually calls its super class, which is this. Since the carRegistryMenu
     * inherited from the carPropertyMenu. <br>
     * This allows us to use the same method but in the right order, while keeping
     * readability and smarter logic / design of the program. We don't need to call other methods that what already is
     * given through the interface {@link DBStandardQueries#insertToTable(DB_QueryEditingHandler, DB_QueryRequestHandler, UI, DB_Dependencies)}</pr>
     *
     * @param editingHandler Read {@link DB_QueryEditingHandler}
     * @param requestHandler Read {@link DB_QueryRequestHandler}
     * @param ui Read {@link UI}
     * @param db_dependencies Read {@link DB_Dependencies}
     *
     * @implNote To The user first gets prompted for choosing amount of WHERE CLAUSES to search for, after completed search
     * criteria has been done the user must choose which car /'s should be affected. <br>
     * If the user chooses the car_properties_id only 1 car gets affected, but user could also choose property which is
     * streamlined for multiple pairs, which would affect all cars which satisfy the parameter.
     */
    @Override
    public void updateTable(DB_QueryEditingHandler editingHandler,
                            DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {
        String query = "UPDATE car_properties " + "SET " +
                ui.insertInto(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1]),
                        requestHandler, db_dependencies.TABLE_NAMES[1], false, false) +

                " WHERE " + ui.chooseWhereOptions(
                db_dependencies.TABLE_NAMES[1],
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1]),
                requestHandler) + ";";

        System.out.println(query);
        editingHandler.insertQuery(query);
    } // End of method

    @Override
    public void deleteFromTable(DB_QueryEditingHandler editingHandler,
                                DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {
        System.out.println("Please enter Car registry ID to delete: ");
        String query = "DELETE FROM car_properties " +
                "WHERE " + db_dependencies.CAR_PROPERTIES_COLUMNS[0] + " = " + ui.readInteger() + ui.readLine();
        editingHandler.insertQuery(query);
    } // End of method

    /**
     * <p>This method incorporates the generic method from {@link UI#chooseWhereOptions(String columnTable, String[] columnValues, DB_QueryRequestHandler requestHandler)}
     * which allows for a SQL building block effect. <br> This way the user can interact with generic seamless effect, allowing
     * for endless search options for searching specific car properties.</p>
     *
     * @param requestHandler Read {@link DB_QueryRequestHandler}
     * @param ui Read {@link UI}
     * @param db_dependencies Read {@link DB_Dependencies}
     */
    public void searchAndShowTable(DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies) {
        String query = "SELECT * FROM " + db_dependencies.TABLE_NAMES[1] + " " +
                "WHERE " + ui.chooseWhereOptions(
                        db_dependencies.TABLE_NAMES[1],
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1]),
                requestHandler) + " " +
                "ORDER BY " + db_dependencies.CAR_PROPERTIES_COLUMNS[9];

        requestHandler.printQueryResult(query,
                db_dependencies.printFormat(requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1])),
                requestHandler.getTableColumns(db_dependencies.TABLE_NAMES[1]));
    } // End of method


    private String getGroupType(UI ui, DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies){
        String sql = ui.insertInto(db_dependencies.CAR_PROPERTIES_COLUMNS,
                requestHandler,
                db_dependencies.TABLE_NAMES[1], true, false);

        String[] sqlCheck = sql.split(",");
        if (sqlCheck[2].equalsIgnoreCase("Automatic")){
            return sql + ", 1";
        } else if(Integer.parseInt(sqlCheck[6])>200 && Integer.parseInt(sqlCheck[5])<=5) {
            return sql + ", 3";
        } else return sql + ", 2";
    } // End of method

}