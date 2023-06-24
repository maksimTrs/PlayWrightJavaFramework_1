package services;

import java.util.ResourceBundle;

public class PropertyReader {

    private static String BUNDLE_TYPE = (System.getProperty("BUNDLE_TYPE") != null
            && (!System.getProperty("BUNDLE_TYPE").isEmpty())) ? System.getProperty("BUNDLE_TYPE") : "dev";

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_TYPE);

    //public static String baseUrl = resourceBundle.getString("BASE_URL");

    public static String getTestDataFromBundle(String prop) {
/*        if (System.getProperty("ENVIRONMENT") != null && (!System.getProperty("ENVIRONMENT").isEmpty())) {
            resourceBundle = ResourceBundle.getBundle(System.getProperty("ENVIRONMENT"));
            return resourceBundle.getString(prop);
        }*/
        return resourceBundle.getString(prop);
    }


}
