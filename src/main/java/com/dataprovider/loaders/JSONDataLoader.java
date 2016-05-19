package com.dataprovider.loaders;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.dataiprovider.exceptions.DataProviderLoadFileException;
import com.dataprovider.loaders.filenamefilters.JSONFileNameFilter;
import com.dataprovider.model.DataObject;
import com.dataprovider.model.DataRow;
import com.dataprovider.model.DataSet;
import com.dataprovider.model.DataSetCollection;

public class JSONDataLoader extends DataLoader {

	@Override
	protected void loadTestData(String filesPath) throws DataProviderLoadFileException {
		filesPath = filesPath.trim();
		try {
			if (filesPath != null && filesPath.length() != 0) {
				String[] fileNames = getFileNames(filesPath);
				StringBuffer data = fillDataBuffer(fileNames, filesPath);
				String dataString = data.toString();
				JSONParser parser = new JSONParser();

				// initialize jsonObject with all datasets
				JSONObject jsonObject = (JSONObject) parser.parse(dataString);
				JSONArray jsonDataSets = (JSONArray) jsonObject.get("dataSets");
				Iterator<JSONObject> iteratorDataSets = jsonDataSets.iterator();

				LinkedList<DataSet> dataSets = new LinkedList<DataSet>();
				// iterate over DataSets
				while (iteratorDataSets.hasNext()) {
					JSONObject jsonDataSet = (JSONObject) iteratorDataSets.next();
					DataSet dataSet = new DataSet((String) jsonDataSet.get("name"));
					JSONArray jsonDataRows = (JSONArray) jsonDataSet.get("dataRows");
					Iterator<JSONObject> iteratorDataRows = jsonDataRows.iterator();
					LinkedList<DataRow> dataRows = new LinkedList<DataRow>();
					while (iteratorDataRows.hasNext()) {
						JSONObject jsonDataRow = (JSONObject) iteratorDataRows.next();
						DataRow dataRow = new DataRow((String) jsonDataRow.get("name"));
						JSONArray jsonDataObjects = (JSONArray) jsonDataRow.get("dataObjects");
						Iterator<JSONObject> iteratorDataObjects = jsonDataObjects.iterator();
						LinkedList<DataObject> dataObjects = new LinkedList<DataObject>();
						while (iteratorDataObjects.hasNext()) {
							JSONObject jsonDataObject = (JSONObject) iteratorDataObjects.next();
							DataObject dataObject = new DataObject();
							dataObject.setName((String) jsonDataObject.get("name"));
							dataObject.setValue((String) jsonDataObject.get("value"));
							dataObjects.add(dataObject);
						}
						dataRow.setDataObjects(dataObjects);
						dataRows.add(dataRow);
					}
					dataSet.setDataRows(dataRows);
					dataSets.add(dataSet);
				}
				this.dataSetCollection = new DataSetCollection(dataSets);

			}
		} catch (Exception e) {
			throw new DataProviderLoadFileException(e.getMessage());
		}
	}

	@Override
	protected StringBuffer fillDataBuffer(final String[] fileNames, final String filePath)
			throws IOException, DataProviderLoadFileException {

		StringBuffer data = new StringBuffer();
		data.append("{\"dataSets\":[");
		for (int i = 0; i < fileNames.length; i++) {
			data.append(this.fileToString(filePath, fileNames[i]));
			if (i != fileNames.length - 1) {
				data.append(", ");
			}
		}
		data.append("]}");
		return data;
	}

	@Override
	protected String[] getFileNames(final String filePath) throws IOException {
		File dir = new File(filePath);
		Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		return dir.list(new JSONFileNameFilter());
	}

}
