package repository.mongoRepository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DBConnection {
    //Habría que pasar la url a un .env pero me da paja
    private static final String COONECTION_URL = "mongodb+srv://pds:xgwetgaMa2UcPcn8@cluster0.mzrhq3s.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static MongoClient mongoClient = null; //Singletno
    private static String database_name = "PDS";

    public static MongoDatabase getDBConnection() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(COONECTION_URL);
        }
        return mongoClient.getDatabase(database_name);
    }

}
