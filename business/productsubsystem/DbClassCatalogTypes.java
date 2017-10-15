package business.productsubsystem;

import java.sql.ResultSet;
import java.sql.SQLException;


import middleware.DatabaseException;
import middleware.DbConfigProperties;
import middleware.dataaccess.DataAccessSubsystemFacade;
import middleware.externalinterfaces.IDataAccessSubsystem;
import middleware.externalinterfaces.IDbClass;
import middleware.externalinterfaces.DbConfigKey;

/**
 * @author pcorazza
 * <p>
 * Class Description: 
 */
public class DbClassCatalogTypes implements IDbClass {

    private String query;
    private String queryType;
    final String GET_TYPES = "GetTypes";
    private CatalogTypes types;
    
    public CatalogTypes getCatalogTypes() throws DatabaseException {
    	//IMPLEMENT
    	
    	queryType = GET_TYPES;
    	
    	IDataAccessSubsystem system = new DataAccessSubsystemFacade();
    	
    	system.atomicRead(this);
    	
        return types;       
    }
    
    public void buildQuery() {
        if(queryType.equals(GET_TYPES)){
            buildGetTypesQuery();
        }
    }

    void buildGetTypesQuery() {
        query = "SELECT * FROM CatalogType";       
    }
    /**
     * This is activated when getting all catalog types.
     */
    public void populateEntity(ResultSet resultSet) throws DatabaseException {
        types = new CatalogTypes();
        //IMPLEMENT
        
        try {
        	while (resultSet.next()) {
    			Integer id = resultSet.getInt("catalogid");
    			String name = resultSet.getString("catalogname");
    			
    			types.addCatalog(id, name);
    		}
		} catch (SQLException e) {
			throw new DatabaseException("CatalogType could not be populated with database data");
		}
    }

    public String getDbUrl() {
    	DbConfigProperties props = new DbConfigProperties();	
    	return props.getProperty(DbConfigKey.PRODUCT_DB_URL.getVal());
    }

    public String getQuery() {

        return query;
    }

}
