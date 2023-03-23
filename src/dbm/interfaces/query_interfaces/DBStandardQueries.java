package dbm.interfaces.query_interfaces;

import dbm.DB_Dependencies;
import dbm.handler.DB_QueryEditingHandler;
import dbm.handler.DB_QueryRequestHandler;
import utility.UI;

/**
 * This interface is acting as a standard skeleton for DB queries regarding - create, edit, delete, show etc. concept.
 * @implNote {@link #showTable(DB_QueryRequestHandler, DB_Dependencies)} mirrors the intention of SELECT * FROM query within MySQL <br>
 * {@link #showTableOrdered(DB_QueryRequestHandler, DB_Dependencies)} mirrors the intention of SELECT * FROM ORDERED BY query within MySQL <br>
 * {@link #insertToTable(DB_QueryEditingHandler,DB_QueryRequestHandler, UI, DB_Dependencies)} mirrors the intention of creating / adding new rows INSERT TO query within MySQL <br>
 * {@link #updateTable(DB_QueryEditingHandler, DB_QueryRequestHandler, UI, DB_Dependencies)} mirrors the intention of editing / updating a row data set UPDATE SET query within MySQL <br>
 * {@link #deleteFromTable(DB_QueryEditingHandler, DB_QueryRequestHandler, UI, DB_Dependencies)} mirrors the intention of deleting a row set DELETE query within MySQL <br>
 */

public interface DBStandardQueries {


    /**
     * <p>This method is part of the standard library of the DBStandardQueries. Specifically showing all data within a given table</p>
     * @param requestHandler    Read javaDoc {@link DB_QueryRequestHandler}
     * @param db_dependencies   Read javaDoc {@link DB_Dependencies}
     */
    void showTable(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies);


    /**
     * <p>This method is part of the standard library of the DBStandardQueries. Specifically showing all data
     * within a given table in ordered output. </p>
     * @param requestHandler    Read javaDoc {@link DB_QueryRequestHandler}
     * @param db_dependencies   Read javaDoc {@link DB_Dependencies}
     */
    void showTableOrdered(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies);


    /**
     * <p>This method is part of the standard library of the DBStandardQueries. Specifically for inserting data within a given table</p>
     * {@link UI#insertInto(String[] columnValues, DB_QueryRequestHandler requestHandler, String tableName, boolean, boolean)}
     * @param editingHandler    Read javaDoc {@link DB_QueryEditingHandler}
     * @param requestHandler    Read javaDoc {@link DB_QueryRequestHandler}
     * @param ui                Read javaDoc {@link UI}
     * @param db_dependencies   Read javaDoc {@link DB_Dependencies}
     */
    void insertToTable(DB_QueryEditingHandler editingHandler, DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies);

    /**
     * <p>This method is part of the standard library of the DBStandardQueries. Specifically updating data within a given table. <br>
     * This method is very similarly to {@link #insertToTable(DB_QueryEditingHandler, DB_QueryRequestHandler, UI, DB_Dependencies)},
     * but has another functionality, which is to skip parameters, user isn't interested in changing.  </p>
     *
     * @param editingHandler    Read javaDoc {@link DB_QueryEditingHandler}
     * @param requestHandler    Read javaDoc {@link DB_QueryRequestHandler}
     * @param ui                Read javaDoc {@link UI}
     * @param db_dependencies   Read javaDoc {@link DB_Dependencies}
     */
    void updateTable(DB_QueryEditingHandler editingHandler,  DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies);


    /**
     *  <p>This method is part of the standard library of the DBStandardQueries. Specifically deleting data within a given table.</p>
     * @param editingHandler    Read javaDoc {@link DB_QueryEditingHandler}
     * @param requestHandler    Read javaDoc {@link DB_QueryRequestHandler}
     * @param ui                Read javaDoc {@link UI}
     * @param db_dependencies   Read JavaDoc {@link DB_Dependencies}
     */
    void deleteFromTable(DB_QueryEditingHandler editingHandler,  DB_QueryRequestHandler requestHandler, UI ui, DB_Dependencies db_dependencies);

}