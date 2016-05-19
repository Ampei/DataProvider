package com.dataprovider.loaders;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.dataiprovider.exceptions.DataProviderLoadFileException;
import com.dataprovider.loaders.filenamefilters.XmlFileNameFilter;
import com.dataprovider.model.DataObject;
import com.dataprovider.model.DataRow;
import com.dataprovider.model.DataSet;
import com.dataprovider.model.DataSetCollection;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLDataLoader extends DataLoader {

	@Override
	protected void loadTestData(String filesPath) throws DataProviderLoadFileException {
		filesPath = filesPath.trim();
		try {
			if (filesPath != null && filesPath.length() != 0) {
				XStream xstreamTC = new XStream(new DomDriver());
				xstreamTC.alias("dataSets", LinkedList.class);
				xstreamTC.alias("dataSet", DataSet.class);
				xstreamTC.alias("dataRows", java.util.LinkedList.class);
				xstreamTC.alias("dataRow", DataRow.class);
				xstreamTC.alias("dataObjects", java.util.LinkedList.class);
				xstreamTC.alias("dataObject", DataObject.class);
				String[] fileNames = getFileNames(filesPath);
				StringBuffer data = fillDataBuffer(fileNames, filesPath);
				String dataString = data.toString();
				LinkedList<DataSet> result = (LinkedList<DataSet>) xstreamTC.fromXML(dataString);
				this.dataSetCollection = new DataSetCollection(result);
			}
		} catch (Exception e) {
			throw new DataProviderLoadFileException(e.getMessage());
		}
	}

	@Override
	protected StringBuffer fillDataBuffer(final String[] fileNames, final String filePath)
			throws IOException, DataProviderLoadFileException {

		StringBuffer data = new StringBuffer();
		data.append("<dataSets>");
		for (String fileName : fileNames) {
			data.append(this.fileToString(filePath, fileName));
		}
		data.append("</dataSets>");
		return data;
	}

	@Override
	protected String[] getFileNames(final String filePath) throws IOException {
		File dir = new File(filePath);
		Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		return dir.list(new XmlFileNameFilter());
	}

}
