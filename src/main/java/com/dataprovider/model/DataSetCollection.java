package com.dataprovider.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import com.dataiprovider.exceptions.DataProviderInvalidSearchKeysException;

/**
 * This class represents a test case group with their data sets.
 */
public class DataSetCollection {


    /**
     * the DataSet ArrayList
     */
    private LinkedList<DataSet> dataSets;

    /**
     * Default Constructor.
     */
    public DataSetCollection() {
    }

    /**
     * Constructor with a ArrayList to set the dataSets list.
     *
     * @param theDataSet
     */
    public DataSetCollection(LinkedList<DataSet> theDataSet) {
        dataSets = theDataSet;
    }

    /**
     * This method return this list of DataSet contained on it self.
     *
     * @return a list of dataSets
     */
    public LinkedList<DataSet> getDataSets() {
        return dataSets;
    }

    /**
     *
     * @param theDataSets the list of DataSet to be set.
     */
    public void setDataSets(LinkedList<DataSet> theDataSets) {
        dataSets = theDataSets;
    }

    public Object[][] getArrayFromKey(String dataSetName) {
        return this.getArrayFromDataSet(dataSetName);
    }
    
    public Object[][] getArrayFromKeys(Map<SearchFilterBaseModel, String> keys) throws DataProviderInvalidSearchKeysException{
       if (keys.size() > 2 || keys.size() ==0) {
           throw new DataProviderInvalidSearchKeysException("Incorrect number of parameters");
       }
       if (keys.containsKey(SearchFilterBaseModel.DATASETNAME) && keys.containsKey(SearchFilterBaseModel.DATAROWNAME)) {
           return this.getArrayFromDataSetAndDataRow(keys.get(SearchFilterBaseModel.DATASETNAME), keys.get(SearchFilterBaseModel.DATAROWNAME));
       } else if (keys.containsKey(SearchFilterBaseModel.DATAROWNAME) && keys.size() == 1) {
           return this.getArrayFromDataRow(keys.get(SearchFilterBaseModel.DATAROWNAME)); 
       } else if (keys.containsKey(SearchFilterBaseModel.DATASETNAME) && keys.size() == 1) {
           return this.getArrayFromDataSet(keys.get(SearchFilterBaseModel.DATASETNAME));
       }
       //TODO: Add Exception to throw
       throw new DataProviderInvalidSearchKeysException("Invalid search arguments");
        
    }

    /**
     * This method returns the first match of dataRowName contained in
     * testCaseGroup's dataset.
     *
     * @param dataRowName The datarow name to retrieve the values.
     * @return A two dimensions array of values of each DataObject contained on
     * the data row given by parameter.If no dataset present return Object[0][0]
     */
    public Object[][] getArrayFromDataRow(String dataRowName) {
        Object[][] dataRowArray = new Object[0][0];
        if (this.dataSets != null) {
            for (DataSet oneDataSet : dataSets) {
                for (DataRow oneDataRow : oneDataSet.getDataRows()) {
                    if (oneDataRow.getName().equals(dataRowName)) {
                        Object[] dataR = oneDataRow.getDataObjectsValuesAsArray();
                        if (dataR.length > 0) {
                            int i;
                            dataRowArray = new Object[1][dataR.length];
                            for (i = 0; i < dataR.length; i++) {
                                dataRowArray[0][i] = dataR[i];
                            }
                        }
                        break;
                    }
                }
                if (dataRowArray.length > 0) {
                    break;
                }
            }
        }
        return dataRowArray;
    }

    /**
     * This method return the values of the entire data set name given by
     * parameter.
     *
     * @param dateSetName The dataset name to retrieve his values.
     * @return A two dimensions array of values of each DataObject contained on
     * the data set given by parameter. If no dataset present return
     * Object[0][0]
     */
    public Object[][] getArrayFromDataSet(String dateSetName) {
        Object[][] dataRowArray = new Object[0][0];
        DataSet dataSet = null;
        for (DataSet dataSetAux : dataSets) {
            if (dataSetAux.getName().equals(dateSetName)) {
                dataSet = dataSetAux;
                break;
            }
        }
        int j = 0;
        if (dataSet != null) {
            if (!dataSet.getDataRows().isEmpty()) {
                Collection<DataRow> dataRowList = dataSet.getDataRows();
                int howManyDataRows = dataRowList.size();
                int howManyDataObjects = dataRowList.iterator().next().getDataObjects().size();
                dataRowArray = new String[howManyDataRows][howManyDataObjects];
                for (DataRow oneDataRow : dataSet.getDataRows()) {
                    int i;
                    Object[] dataValues = oneDataRow.getDataObjectsValuesAsArray();
                    for (i = 0; i < dataValues.length; i++) {
                        dataRowArray[j][i] = dataValues[i];
                    }
                    j++;
                }
            }
        }
        return dataRowArray;
    }

    public Object[][] getArrayFromDataSetAndDataRow(String dateSetName, String dataRowName) {
        Object[][] dataRowArray = new Object[0][0];
        DataSet dataSet = null;
        for (DataSet dataSetAux : dataSets) {
            if (dataSetAux.getName().equals(dateSetName)) {
                dataSet = dataSetAux;
                break;
            }
        }
        int j = 0;
        if (dataSet != null) {
            if (!dataSet.getDataRows().isEmpty()) {
                Collection<DataRow> dataRowList = dataSet.getDataRows();
                int howManyDataRows = dataRowList.size();
                int howManyDataObjects = dataRowList.iterator().next().getDataObjects().size();
                dataRowArray = new String[howManyDataRows][howManyDataObjects];
                for (DataRow oneDataRow : dataSet.getDataRows()) {
                    if (oneDataRow.getName().equals(dataRowName)) {
                        int i;
                        Object[] dataValues = oneDataRow.getDataObjectsValuesAsArray();
                        for (i = 0; i < dataValues.length; i++) {
                            dataRowArray[j][i] = dataValues[i];
                        }
                        j++;
                    }    
                }
            }
        }
        return dataRowArray;
    }
}
