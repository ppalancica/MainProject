package business.productsubsystem;

import java.sql.ResultSet;

import middleware.DatabaseException;
import middleware.DbConfigProperties;
import middleware.dataaccess.DataAccessSubsystemFacade;
import middleware.dataaccess.DataAccessUtil;
import middleware.externalinterfaces.IDataAccessSubsystem;
import middleware.externalinterfaces.IDbClass;
import middleware.externalinterfaces.DbConfigKey;

public class DbClassCatalog implements IDbClass {
	
	private String catalogName;
	private String catalogNameToUpdate;
	
	private String query;
    private String queryType;
    private final String SAVE = "Save";
    private final String UPDATE = "Update";
    private final String DELETE = "Delete";
    
    private IDataAccessSubsystem dataAccessSS = new DataAccessSubsystemFacade();
    
    public void saveNewCatalog(String name) throws DatabaseException {
    	//IMPLEMENT
    	catalogName = name;
    	queryType = SAVE;
    	
    	dataAccessSS.saveWithinTransaction(this);
    }
    
    public void updateCatalogName(String currentName, String newName) throws DatabaseException {
    	//IMPLEMENT
    	catalogName = currentName;
    	catalogNameToUpdate = newName;
    	queryType = UPDATE;
    	
    	dataAccessSS.saveWithinTransaction(this);
    }
    
    public void deleteCatalog(String name) throws DatabaseException {
    	//IMPLEMENT
    	catalogName = name;
    	queryType = DELETE;
    	
    	dataAccessSS.deleteWithinTransaction(this);
    }
    
	public void buildQuery() throws DatabaseException {
		if (queryType.equals(SAVE)) {
			buildSaveQuery();
		} else if (queryType.equals(UPDATE)) {
			buildUpdateQuery();
		} else if (queryType.equals(DELETE)) {
			buildDeleteQuery();
		}
	}
	
	void buildSaveQuery() throws DatabaseException {
		//IMPLEMENT
		query = String.format("INSERT INTO CatalogType VALUES (NULL, '%s')", catalogName);
	}
	
	void buildUpdateQuery() throws DatabaseException {
		//IMPLEMENT
		query = String.format("UPDATE CatalogType SET catalogName='%s' WHERE catalogName='%s'", catalogNameToUpdate, catalogName);
	}
	
	void buildDeleteQuery() throws DatabaseException {
		//IMPLEMENT
		query = String.format("DELETE FROM CatalogType WHERE catalogName='%s'", catalogName);
	}

	public String getDbUrl() {
		//IMPLEMENT
		DbConfigProperties props = new DbConfigProperties();	
		
    	return props.getProperty(DbConfigKey.PRODUCT_DB_URL.getVal());
	}

	public String getQuery() {
		//IMPLEMENT
		return query;
	}

	public void populateEntity(ResultSet resultSet) throws DatabaseException {
		// do nothing
		
	}
	
}
