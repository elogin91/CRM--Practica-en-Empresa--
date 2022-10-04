package CRM.servicios;

public class QuerysBuilding {
    public static String searchQuery(String value, String kindOfValue) {
        String consulta="Select * from clientespotenciales where " +kindOfValue +" like '%" +value+"%'";

        return consulta;
    }
}
