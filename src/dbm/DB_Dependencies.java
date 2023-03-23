package dbm;

import java.util.Arrays;

public class DB_Dependencies {

    private static DB_Dependencies theInstance = new DB_Dependencies();

    // Login / Connection Fields ---------------------------------------------------------
    public final String database_url = "jdbc:mysql://localhost:3306/kailua_rental";
    public final String username = "root";
    public final String password = "sesame80";


    // Table INFO and Dependencies ------------------------------------------------------
    /**
     * <p>Includes all table names within the database</p>
     *
     * @implNote 0 = customer_info <br>
     * 1 = car_properties <br>
     * 2 = rental_registry <br>
     * 3 = car_registry <br>
     * 4 = car_rental_groups
     */
    public final String[] TABLE_NAMES = {
            "customer_info", "car_properties", "rental_registry", "car_registry", "car_rental_group"
    };


    // Car registry section --------------------------------------------------------------
    /**
     * <p>Includes all columns within the database which is including within the table car_registry.</p>
     *
     * @implNote 0 = car_registry_id <br>
     * 1 = car_brand <br>
     * 2 = car_model <br>
     * 3 = registration_number <br>
     * 4 = first_registration <br>
     * 5 = availability <br>
     * 6 = car_rental_group_id <br>
     * 7 = car_isRented
     */
    public final String[] CAR_REGISTRY_COLUMNS = {
            "car_registry_id", "car_brand", "car_model", "registration_number", "first_registration",
            "availability", "car_properties_id", "is_car_rented"

    };

    public final String[] CAR_REGISTRY_CAR_RENTAL_JOIN_COLUMNS = {
            "cr.car_registry_id","crg.car_rental_group_id", "crg.car_rental_group_name",
            "ccm", "gear_type", "air_condition", "seat_type", "seat_amount",
            "horsepower", "cruise_control", "odometer", "fuel_type","is_car_rented"
    };

    public final String[] CAR_REGISTRY_CAR_RENTAL_JOIN_COLUMNS_PRINT = {
            "Car Registry Id","Car Rental Group ID", "Car Rental Group Name",
            "CCM", "Gear Type", "Air Condition", "Seat Type", "Seat Amount",
            "Horsepower", "Cruise Control", "Odometer", "Fuel Type","Car Is Rented"
    };


    // Car properties section ------------------------------------------------------------
    /**
     * @implNote 0 = car_properties_id <br>
     * 1 = ccm <br>
     * 2 = gear_type <br>
     * 3 = air_condition <br>
     * 4 = seat_type <br>
     * 5 = seat_amount <br>
     * 6 = horsepower <br>
     * 7 = cruise_control <br>
     * 8 = odometer <br>
     * 9 = fuel_type <br>
     * 10 = car_rental_group_id
     */
    public final String[] CAR_PROPERTIES_COLUMNS = {
            "car_properties_id", "ccm", "gear_type", "air_condition", "seat_type", "seat_amount",
            "horsepower", "cruise_control", "odometer", "fuel_type", "car_rental_group_id"
    };


    // Customer section ------------------------------------------------------------------
    /**
     * <p>Includes all columns within the database which is including within the table customer_info.</p>
     *
     * @implNote 0 = customer_id <br>
     * 1 = customer_name <br>
     * 2 = customer_address <br>
     * 3 = customer_zip <br>
     * 4 = customer_city <br>
     * 5 = customer_phone <br>
     * 6 = customer_email <br>
     * 7 = customer_driver_license_number <br>
     * 8 = customer_driver_since <br>
     */
    public final String[] CUSTOMER_COLUMNS = {"customer_id", "customer_name",
            "customer_address", "customer_zip", "customer_city", "customer_phone", "customer_email",
            "customer_driver_license_number", "customer_driver_since"};

    //Analysis section ---------------------------------------
    /**
     * <p>Includes all columns within the database which is including within the table customer_info.</p>
     *
     * @implNote 0 = Customer Name <br>
     * 1 = Customer ID <br>
     * 2 = Total Days Rented <br>
     */
    public final String[] BEST_COSTUMER_PRINT = {"Customer Name", "Customer ID", "Total Days Rented"};

    /**
     * <p>Includes all columns within the database which is including within the table customer_info.</p>
     *
     * @implNote 0 = customer_name <br>
     * 1 = customer_id <br>
     * 2 = total_days_rented <br>
     */
    public final String[] BEST_CUSTOMER_COLUMNS = {"customer_name", "customer_id", "total_days_rented"};


    public final String[] SHOW_CITY_AND_CAR_INFO = {"cu.customer_city", "rg.rental_registry_id",
            "cr.car_brand", "cr.car_model", "cp.gear_type", "crg.car_rental_group_name"};

    public final String[] SHOW_CITY_AND_CAR_INFO_PRINT = {"Customer City", "Rental Registry ID",
            "Car Brand", "Car Model", "Gear Type", "Rental Group Name"};


    // Rental Registry section --------------------------------------

    public final String[] RENTAL_REGISTRY_COLUMNS = {
            "rental_registry_id", "rental_start_date", "rental_end_date", "car_registry_id", "customer_id"};


    // Update rented cars to no longer rented---------------------------------------------
    public String setCarToIsRented = """
            UPDATE car_registry\s
            SET is_car_rented = 1\s
            WHERE car_registry.car_registry_id IN\s
            (SELECT rental_registry.car_registry_id\s
            FROM rental_registry\s
            WHERE rental_registry.car_registry_id = car_registry.car_registry_id\s
            AND rental_registry.rental_end_date < CURDATE() AND rental_registry.rental_start_date >= CURDATE());""";


    public final String[] JOIN_FOR_CAR_ISRENTED = {"cr.car_registry_id","cu.customer_id", "cu.customer_name", "cu.customer_phone",
            "cu.customer_email", "cr.car_brand", "rg.rental_start_date", "rg.rental_registry_id","rg.rental_end_date"};


    public final String[] JOIN_FOR_CAR_ISRENTED_PRINT = {"Car Registry ID","Customer ID", "Customer Name", "Customer Phone",
            "Customer Email", "Car Brand","Rental Start Date","Rental Registry ID" ,"Rental End Date"};

    public final String[] JOIN_FOR_CAR_ISRENTED_W_OVERDUE = {"cr.car_registry_id","days_overdue","cu.customer_id", "cu.customer_name", "cu.customer_phone",
            "cu.customer_email", "cr.car_brand", "rg.rental_start_date", "rg.rental_registry_id","rg.rental_end_date" };


    public final String[] JOIN_FOR_CAR_ISRENTED_W_OVERDUE_PRINT = {"Car Registry ID","Days Overdue","Customer ID", "Customer Name", "Customer Phone",
            "Customer Email", "Car Brand","Rental Start Date","Rental Registry ID" ,"Rental End Date" };




    // Constructor -----------------------------------------------------------------------
    private DB_Dependencies() {
    }

    // Getter ----------------------------------------------------------------------------
    public static DB_Dependencies getInstance() {
        if (theInstance == null) {
            theInstance = new DB_Dependencies();
        }
        return theInstance;
    }


    // Print method for columns ----------------------------------------------------------
    public String[] printFormat(String[] columnArray) {
        return Arrays.stream(columnArray)
                .map(columnValue -> columnValue.replace("_", " "))
                .map(columnValue -> {
                    String[] words = columnValue.split(" ");
                    for (int i = 0; i < words.length; i++) {
                        words[i] = words[i].substring(0, 1).toUpperCase() +
                                words[i].substring(1).toLowerCase();
                    }
                    return String.join(" ", words) + checkForVerb(words);
                })
                .toArray(String[]::new);
    }

    private String checkForVerb(String[] words){
        return (words.length > 0 && words[0].toLowerCase().matches("^(is|are|am).*")) ? " ?" : "";
    }
}