package com.dataprovider.interfaces;

import com.dataiprovider.exceptions.DataProviderInvalidSearchKeysException;
import com.dataiprovider.exceptions.DataProviderLoadFileException;
import com.dataprovider.model.SearchFiltersEnum;

import java.util.Map;

public interface IDataLoader {
    /**
     * Load data files from the pathToFiles directories.
     *
     * @param pathToFiles data files directory
     * @throws DataProviderLoadFileException if some error occurs when load data from files
     */ 
    public void loadFromPath(String pathToFiles) throws DataProviderLoadFileException;
        
    /**
     * 
     * @param keys A map with pairs to filter data, accepted values are SearchFilterBaseModel.DATASETNAME and SearchFilterBaseModel.DATAROWNAME 
     * @return Object[][] with the collection of values at data rows
     * @throws DataProviderInvalidSearchKeysException where keys for search are incorrect
     */
    public Object[][] getArrayFromKeys(Map<SearchFiltersEnum,String> keys) throws DataProviderInvalidSearchKeysException;
    /**
     * 
     * @param key is the string that matches with DataSetName
     * @return Object[][] with the collection of values at data rows 
     */
    public Object[][] getArrayFromKey(String key);

}
