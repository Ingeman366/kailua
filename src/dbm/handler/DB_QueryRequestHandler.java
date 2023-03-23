package dbm.handler;

import dbm.DB_Dependencies;


import utility.UI;
import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class DB_QueryRequestHandler {

    /**
     * This method queries the DB to figure out the data type. This is to make sure the correct UI input method
     * for the requested change matches the correct data ype to be processed within the DB.
     *
     * @param tableName The SQL Schema table
     * @param columnName The column name of specific Table / Schema
     * @return Returns if data-type of the queried column is {int, varchar, tinyint, date, double etc.}
     */
    public String getColumnDataType(String tableName, String columnName) {
        String dataTypeQuery = "SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableName +"' AND COLUMN_NAME = '" + columnName + "'";
        try (
                Connection connection = DriverManager.getConnection(
                DB_Dependencies.getInstance().database_url,
                DB_Dependencies.getInstance().username,
                DB_Dependencies.getInstance().password);

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(dataTypeQuery)
        ) {

            if (resultSet.next()) {
                return resultSet.getString("DATA_TYPE");
            } else {
                System.out.println("Error: Column not found in the table");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving the data type of the column: " + e.getMessage());
            return null;
        }
    }

    /**
     * This method is a generic way to print out the result from a SQL query
     * @param query The SQL statement which queries the DB
     * @param printColumns  The formatted print version for console of the columns from SQL query
     * @param dbColumnNames The exact column names which the DB columns of the table are named
     * @implNote Whenever a request for print is needed, this method should be called with given three arguments.
     */
    public void printQueryResult(String query, String[] printColumns, String[] dbColumnNames) {
        try (
                Connection connection = DriverManager.getConnection(
                        DB_Dependencies.getInstance().database_url,
                        DB_Dependencies.getInstance().username,
                        DB_Dependencies.getInstance().password
                );

                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            printResultSet(resultSet, printColumns);
        } catch (SQLException e) {
            System.out.println("Error with SQL Print request " + e);
        } // End of try - catch block
    } // End of method

    private void printResultSet(ResultSet resultSet, String[] printColumns) throws SQLException {
        int[] lengthArray = checkColumnLength(resultSet,printColumns);
        // Print column headers
        for (int i = 0; i < printColumns.length; i++) {
            System.out.printf("%-" +lengthArray[i] + "s","[" + printColumns[i] + "]");
        } // End of for loop
        System.out.println();

        // Print rows
        while (resultSet.next()) {
            for (int i = 1; i <= printColumns.length; i++) {
                Object value = resultSet.getObject(i);
                if(i==1){
                System.out.printf("%s%-" + lengthArray[i-1] +"s","#", value);}
                else {System.out.printf("%-" + lengthArray[i-1] +"s", value);}
            } // End of for loop
            System.out.println();
        } // End of while loop
        System.out.println();
    } // End of method

    /**
     * <p>This method is a helper method for the method in the
     * {@link UI#insertInto(String[], DB_QueryRequestHandler, String, boolean, boolean)}. It helps when the case of usage
     * is for rental registry, to validate cars available for renting, while securing user cant change cars which
     * already are rented out.</p>
     * @param query         The SQL statement which request some kind of result set
     * @param valueID       The value ID which is requested for change (Specifically for the rental registry method)
     * @param columnForID   The ID which must indicate a valid object for query change within the rental registry method
     * @return              This method returns a boolean expression if given query has a valid Car to be rented out for
     */
    public boolean checkIfExists(String query, int valueID, int columnForID) {
        try (
                Connection connection = DriverManager.getConnection(
                        DB_Dependencies.getInstance().database_url,
                        DB_Dependencies.getInstance().username,
                        DB_Dependencies.getInstance().password
                );

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                if (resultSet.getObject(columnForID).equals(valueID)) {
                    return true;
                } // End of if statement
            } // End of while loop
        } catch (SQLException e) {
            System.out.println("Error with SQL Print request ");
        } // End of try - catch block
        return false;
    } // End of method


    /**
     * This method is another helper method for the {@link UI#insertInto(String[], DB_QueryRequestHandler, String, boolean, boolean)}
     * It helps checking if the result set returned has at least one value.
     * @param query The SQL statement which request some kind of result set
     * @return This method returns a boolean expression if the result set returned is null or has at least one value.
     */
    public boolean checkIfEmpty(String query) {
        try (
                Connection connection = DriverManager.getConnection(
                        DB_Dependencies.getInstance().database_url,
                        DB_Dependencies.getInstance().username,
                        DB_Dependencies.getInstance().password
                );

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (resultSet.next()) {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error with SQL Print request");
        } // End of try - catch block
        return true;
    } // End of method


    public String[] getTableColumns(String tableName) {
        List<String> tableColumns = new ArrayList<>();

        String getColumnNameQuery = "SELECT * FROM " + tableName + " WHERE 1 = 0;";
        try (
                Connection connection = DriverManager.getConnection(
                        DB_Dependencies.getInstance().database_url,
                        DB_Dependencies.getInstance().username,
                        DB_Dependencies.getInstance().password)
                ) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getColumnNameQuery);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int columnCount = resultSetMetaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                tableColumns.add(resultSetMetaData.getColumnName(i));
            }
            return tableColumns.toArray(String[]::new);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } // End of try-catch block
    } // End of method





    public ArrayList<Integer> getAllIDs(String query, String columnName) {
        ArrayList<Integer> iDs = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(
                        DB_Dependencies.getInstance().database_url,
                        DB_Dependencies.getInstance().username,
                        DB_Dependencies.getInstance().password
                );

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                iDs.add((Integer) resultSet.getObject(columnName));
            }
            return iDs;
        } catch (SQLException e) {
            System.out.println("Error with SQL Print request " + e);
        } // End of try - catch block
        return iDs;
    }

    // Method to check datalength in ResultSet
    private int[] checkColumnLength(ResultSet resultSet, String[] printColumns) {
        try {
            int[] returnArray = new int[printColumns.length]; // Array to be returned with max datalength pr. column
            for (int i = 0; i < returnArray.length; i++) {
                returnArray[i] = (printColumns[i].length() + 6); // Adds input columnlength to returnArray
            } // End for loop

            for (int i = 1; i <= returnArray.length; i++) { // Iterate through resultSet-columns
                while (resultSet.next()) { // Iterate through resultSet-rows
                    String value = resultSet.getString(i);     // Get current string in cell
                    if (value.length()>=returnArray[i-1]-3){   // Checks if current data in cell is longer
                        returnArray[i-1] = value.length() + 4; // Updates the max length of data in returnArray
                    }
                } // End of while loop
                resultSet.beforeFirst();
            } // End of for loop
            return returnArray;

        } catch (SQLException e){
            System.out.println(e);
            return null;
        }

    }
}
