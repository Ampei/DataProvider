package com.dataprovider.factory;

import com.dataiprovider.exceptions.DataProviderNotFoundException;
import com.dataprovider.interfaces.IDataLoader;
import com.dataprovider.loaders.JSONDataLoader;
import com.dataprovider.loaders.XMLDataLoader;
import com.dataprovider.model.DataLoaderType;

/**
 * This class serialize a group of XML files to feed the Test Cases with the
 * required data.
 */
public final class DataFactory {

	/**
	 * variable singleton.
	 */
	private static DataFactory dataProviderManager = new DataFactory();

	/**
	 * Default constructor.
	 */

	private DataFactory() {
	}

	/**
	 * Returns DataProviderManager instance.
	 *
	 * @return DataProviderManager instance
	 */
	public static DataFactory getInstance() {
		return dataProviderManager;
	}

	public IDataLoader getDataLoader(DataLoaderType dataLoader) throws DataProviderNotFoundException {

		IDataLoader returnLoader;

		switch (dataLoader) {
		case XML:
			returnLoader = new XMLDataLoader();
			break;

		case JSON:
			returnLoader = new JSONDataLoader();
			break;
		default:
			throw new DataProviderNotFoundException(
					String.format("Invalid Data Loader Type: %s", dataLoader.toString()));
		}
		return returnLoader;

	}
}
