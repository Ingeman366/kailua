package menu.sub_menus;

import dbm.DB_Dependencies;
import dbm.handler.DB_QueryRequestHandler;
import menu.Menu;
import utility.UI;

public class AnalysisMenu extends Menu {
    public AnalysisMenu(String menuHeader, String[] menuItems) {
        super(menuHeader, menuItems);
    }


    // Methods ---------------------------------------------------------------------
    public void showTable(DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies) {
        String sql = "SELECT " +"customer_info." + DB_Dependencies.getInstance().CUSTOMER_COLUMNS[1] + ", " +
                "rental_registry."+db_dependencies.RENTAL_REGISTRY_COLUMNS[4] + ", " + "SUM(DATEDIFF(" +
                "rental_registry." + db_dependencies.RENTAL_REGISTRY_COLUMNS[2] + "," +
                "rental_registry." + db_dependencies.RENTAL_REGISTRY_COLUMNS[1] + ")) AS total_days_rented FROM " +
                db_dependencies.TABLE_NAMES[2] + " JOIN " +
                db_dependencies.TABLE_NAMES[0] + " ON " +
                "rental_registry." + db_dependencies.RENTAL_REGISTRY_COLUMNS[4] + " = " +
                "customer_info." + db_dependencies.CUSTOMER_COLUMNS[0] + " GROUP BY " +
                "rental_registry." + db_dependencies.RENTAL_REGISTRY_COLUMNS[4] + " ORDER BY " +
                "total_days_rented DESC;";
        requestHandler.printQueryResult(
                sql,
                db_dependencies.BEST_COSTUMER_PRINT,
                db_dependencies.BEST_CUSTOMER_COLUMNS);
    } // End of method

    public void showCityInfo(UI ui, DB_QueryRequestHandler requestHandler, DB_Dependencies db_dependencies) {
        String sql = "SELECT cu.customer_city, rg.rental_registry_id, cr.car_brand, cr.car_model, cp.gear_type, crg.car_rental_group_name\n" +
                "FROM customer_info cu\n" +
                "JOIN rental_registry rg USING (customer_id)\n" +
                "JOIN car_registry cr USING (car_registry_id)\n" +
                "JOIN car_properties cp USING (car_properties_id) \n" +
                "JOIN car_rental_group crg USING (car_rental_group_id) \n" +
                "WHERE cu.customer_city = "+ "\""+ ui.getCityName() +"\"" +" \n" +
                "ORDER BY crg.car_rental_group_name ASC";

        requestHandler.printQueryResult(sql,db_dependencies.SHOW_CITY_AND_CAR_INFO_PRINT,db_dependencies.SHOW_CITY_AND_CAR_INFO);
    } // End of method


}
