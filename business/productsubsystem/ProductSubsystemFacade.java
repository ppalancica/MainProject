/*
 * Created on Mar 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package business.productsubsystem;

import java.util.ArrayList;
import java.util.List;

import business.DbClassQuantity;
import business.Quantity;
import business.RuleException;
import business.rulesbeans.QuantityBean;
import business.rulesubsystem.RulesSubsystemFacade;
import business.util.*;
import business.externalinterfaces.IProductFromDb;
import business.externalinterfaces.IProductFromGui;
import business.externalinterfaces.IProductSubsystem;
import business.externalinterfaces.IRules;
import business.externalinterfaces.IRulesSubsystem;
import middleware.DatabaseException;
import middleware.EBazaarException;
import middleware.dataaccess.DataAccessUtil;

public class ProductSubsystemFacade implements IProductSubsystem {
	final String DEFAULT_PROD_DESCRIPTION="New Product";
	CatalogTypes types;
	
    public TwoKeyHashMap<Integer,String,IProductFromDb> getProductTable() throws DatabaseException {
        DbClassProduct dbClass = new DbClassProduct();
        return dbClass.readProductTable();
        
    }
	public TwoKeyHashMap<Integer,String,IProductFromDb> refreshProductTable() throws DatabaseException {
		DbClassProduct dbClass = new DbClassProduct();
        return dbClass.refreshProductTable();		
	}
	
	//This is needed by ComboListener in ManageProductsController. When you have implemented this,
	//you can remove comments from body of ComboListener.
	public List<IProductFromDb> getProductList(String catType) throws DatabaseException {
		//IMPLEMENT
		
		Integer catalogId = getCatalogIdFromType(catType);
		
		List<IProductFromDb> productList = new DbClassProduct().readProductList(catalogId);
		
		return productList;
	}
	
	public List<String[]> getCatalogNames() throws DatabaseException {
		
		DbClassCatalogTypes dbClass = new DbClassCatalogTypes();
    	
		return dbClass.getCatalogTypes().getCatalogNames();
	}
	
	public void saveNewCatalogWithName(String name) throws DatabaseException {
		DbClassCatalog dbClass = new DbClassCatalog();
		dbClass.saveNewCatalog(name);
	}
	
	public void updateCatalogName(String currentName, String newName) throws DatabaseException {
		DbClassCatalog dbClass = new DbClassCatalog();
		dbClass.updateCatalogName(currentName, newName);
	}
	
	public void deleteCatalogName(String name) throws DatabaseException {
		DbClassCatalog dbClass = new DbClassCatalog();
		dbClass.deleteCatalog(name);
	}
	
	public Integer getCatalogIdFromType(String catType) throws DatabaseException {
		Integer catalogId = new DbClassCatalogTypes().getCatalogTypes().getCatalogId(catType);
		
		return catalogId;
	}
	
	public void saveNewProduct(IProductFromGui product, String catalogType) throws DatabaseException {
		//get catalogid
		Integer catalogid = getCatalogIdFromType(catalogType); 
		//invent description
		String description = DEFAULT_PROD_DESCRIPTION;
		DbClassProduct dbclass = new DbClassProduct();
		
		dbclass.saveNewProduct(product, catalogid, description);
	}
	/* reads quantity avail and stores in the Quantity argument */
	public void readQuantityAvailable(String prodName, Quantity quantity) throws DatabaseException {
		DbClassQuantity dbclass = new DbClassQuantity();
		dbclass.setQuantity(quantity);
		dbclass.readQuantityAvail(prodName);
				
	}

	

}
