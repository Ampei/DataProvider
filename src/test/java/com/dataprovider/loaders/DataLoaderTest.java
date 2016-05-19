package com.dataprovider.loaders;

import com.dataiprovider.exceptions.DataProviderInvalidSearchKeysException;
import com.dataiprovider.exceptions.DataProviderLoadFileException;
import com.dataiprovider.exceptions.DataProviderNotFoundException;
import com.dataprovider.factory.DataFactory;
import com.dataprovider.interfaces.IDataLoader;
import com.dataprovider.model.DataLoaderType;
import com.dataprovider.model.SearchFilterBaseModel;
import com.dataprovider.model.SearchFiltersEnum;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataLoaderTest {

	public DataLoaderTest() {
	}

	@Test
	public void testWellFormedXMLWithXMLDataLoader() throws IOException, DataProviderNotFoundException,
			DataProviderLoadFileException, DataProviderInvalidSearchKeysException {
		IDataLoader xmlDataLoader = DataFactory.getInstance().getDataLoader(DataLoaderType.XML);
		xmlDataLoader.loadFromPath("./src/test/java/com/dataprovider/testfiles/");
		Object[][] dictionary = xmlDataLoader.getArrayFromKey("WikiSearch");
		Assert.assertEquals(dictionary.length, 2,
				String.format("The number of data collections must be %d but was %d", 2, dictionary.length));
		Assert.assertEquals(dictionary[0].length, 2,
				String.format("The number of objects must be %d but was %d", 2, dictionary[0].length));
		Assert.assertEquals(dictionary[1].length, 2,
				String.format("The number of objects must be %d but was %d", 2, dictionary[1].length));
		Assert.assertEquals(dictionary[0][0], "Ampei",
				String.format("The first element should be %s but was %s", "Ampei", dictionary[0][0]));
		Assert.assertEquals(dictionary[0][1], "Ampei",
				String.format("The second element should be %s but was %s", "Ampei", dictionary[0][1]));
		Assert.assertEquals(dictionary[1][0], "Buenos Aires",
				String.format("The first element should be %s but was %s", "Buenos Aires", dictionary[1][0]));
		Assert.assertEquals(dictionary[1][1], "Buenos Aires",
				String.format("The second element should be %s but was %s", "Buenos Aires", dictionary[1][1]));

		dictionary = xmlDataLoader.getArrayFromKey("DataSet");

		Assert.assertEquals(dictionary.length, 1,
				String.format("The number of data collections must be %d but was %d", 1, dictionary.length));
		Assert.assertEquals(dictionary[0].length, 3,
				String.format("The number of objects must be %d but was %d", 3, dictionary[0].length));

		Assert.assertEquals(dictionary[0][0], "Value1",
				String.format("The first element should be %s but was %s", "Value1", dictionary[0][0]));
		Assert.assertEquals(dictionary[0][1], "Value2",
				String.format("The second element should be %s but was %s", "Value2", dictionary[0][1]));
		Assert.assertEquals(dictionary[0][2], "Value3",
				String.format("The third element should be %s but was %s", "Value3", dictionary[0][2]));

		// Advanced filter
		Map<SearchFiltersEnum, String> filter = new HashMap<SearchFiltersEnum, String>();
		filter.put(SearchFilterBaseModel.DATASETNAME, "DataSet");
		filter.put(SearchFilterBaseModel.DATAROWNAME, "DataRow1");
		dictionary = xmlDataLoader.getArrayFromKeys(filter);
		Assert.assertEquals(dictionary[0][0], "Value1",
				String.format("The first element should be %s but was %s", "Value1", dictionary[0][0]));
		Assert.assertEquals(dictionary[0][1], "Value2",
				String.format("The second element should be %s but was %s", "Value2", dictionary[0][1]));
		Assert.assertEquals(dictionary[0][2], "Value3",
				String.format("The third element should be %s but was %s", "Value3", dictionary[0][2]));

	}

	@Test(expectedExceptions = DataProviderLoadFileException.class)
	public void testMalFormedXMLWithXMLDataLoader()
			throws DataProviderNotFoundException, DataProviderLoadFileException {
		IDataLoader xmlDataLoader = DataFactory.getInstance().getDataLoader(DataLoaderType.XML);
		xmlDataLoader.loadFromPath("./src/test/java/com/dataprovider/wrongtestFiles/");
	}

	@Test
	public void testWellFormedJSONWithXMLDataLoader() throws IOException, DataProviderNotFoundException,
			DataProviderLoadFileException, DataProviderInvalidSearchKeysException {
		IDataLoader jsonDataLoader = DataFactory.getInstance().getDataLoader(DataLoaderType.JSON);
		jsonDataLoader.loadFromPath("./src/test/java/com/dataprovider/testfiles/");
		Object[][] dictionary = jsonDataLoader.getArrayFromKey("WikiSearch");
		Assert.assertEquals(dictionary.length, 2,
				String.format("The number of data collections must be %d but was %d", 2, dictionary.length));
		Assert.assertEquals(dictionary[0].length, 2,
				String.format("The number of objects must be %d but was %d", 2, dictionary[0].length));
		Assert.assertEquals(dictionary[1].length, 2,
				String.format("The number of objects must be %d but was %d", 2, dictionary[1].length));
		Assert.assertEquals(dictionary[0][0], "Ampei",
				String.format("The first element should be %s but was %s", "Ampei", dictionary[0][0]));
		Assert.assertEquals(dictionary[0][1], "Ampei",
				String.format("The second element should be %s but was %s", "Ampei", dictionary[0][1]));
		Assert.assertEquals(dictionary[1][0], "Buenos Aires",
				String.format("The first element should be %s but was %s", "Buenos Aires", dictionary[1][0]));
		Assert.assertEquals(dictionary[1][1], "Buenos Aires",
				String.format("The second element should be %s but was %s", "Buenos Aires", dictionary[1][1]));

		dictionary = jsonDataLoader.getArrayFromKey("DataSet");

		Assert.assertEquals(dictionary.length, 1,
				String.format("The number of data collections must be %d but was %d", 1, dictionary.length));
		Assert.assertEquals(dictionary[0].length, 3,
				String.format("The number of objects must be %d but was %d", 3, dictionary[0].length));

		Assert.assertEquals(dictionary[0][0], "Value1",
				String.format("The first element should be %s but was %s", "Value1", dictionary[0][0]));
		Assert.assertEquals(dictionary[0][1], "Value2",
				String.format("The second element should be %s but was %s", "Value2", dictionary[0][1]));
		Assert.assertEquals(dictionary[0][2], "Value3",
				String.format("The third element should be %s but was %s", "Value3", dictionary[0][2]));

		// Advanced filter
		Map<SearchFiltersEnum, String> filter = new HashMap<SearchFiltersEnum, String>();
		filter.put(SearchFilterBaseModel.DATASETNAME, "DataSet");
		filter.put(SearchFilterBaseModel.DATAROWNAME, "DataRow1");
		dictionary = jsonDataLoader.getArrayFromKeys(filter);
		Assert.assertEquals(dictionary[0][0], "Value1",
				String.format("The first element should be %s but was %s", "Value1", dictionary[0][0]));
		Assert.assertEquals(dictionary[0][1], "Value2",
				String.format("The second element should be %s but was %s", "Value2", dictionary[0][1]));
		Assert.assertEquals(dictionary[0][2], "Value3",
				String.format("The third element should be %s but was %s", "Value3", dictionary[0][2]));

	}

	@Test(expectedExceptions = DataProviderLoadFileException.class)
	public void testMalFormedXMLWithJSONDataLoader()
			throws DataProviderNotFoundException, DataProviderLoadFileException {
		IDataLoader xmlDataLoader = DataFactory.getInstance().getDataLoader(DataLoaderType.JSON);
		xmlDataLoader.loadFromPath("./src/test/java/com/dataprovider/wrongtestfiles/");
	}

}
