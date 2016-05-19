package com.dataprovider.loaders;

import com.dataiprovider.exceptions.DataProviderInvalidSearchKeysException;
import com.dataiprovider.exceptions.DataProviderLoadFileException;
import com.dataprovider.interfaces.IDataLoader;
import com.dataprovider.model.DataSetCollection;
import com.dataprovider.model.SearchFilterBaseModel;
import com.dataprovider.model.SearchFiltersEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Map;

public abstract class DataLoader implements IDataLoader {

    protected DataSetCollection dataSetCollection;

    public DataLoader() {
        this.dataSetCollection = new DataSetCollection();
    }

    @Override
    public void loadFromPath(String projectPathToFiles) throws DataProviderLoadFileException {
        loadTestData(projectPathToFiles);
    }

    @Override
    public Object[][] getArrayFromKey(String key) {
        return this.dataSetCollection.getArrayFromDataSet(key);
    }

    @Override
    public Object[][] getArrayFromKeys(Map<SearchFiltersEnum, String> keys) throws DataProviderInvalidSearchKeysException {

        if (keys.size() > 2 || keys.isEmpty()) {
            throw new DataProviderInvalidSearchKeysException("Incorrect number of parameters");
        }
        if (keys.containsKey(SearchFilterBaseModel.DATASETNAME) && keys.containsKey(SearchFilterBaseModel.DATAROWNAME)) {
            return this.dataSetCollection.getArrayFromDataSetAndDataRow(keys.get(SearchFilterBaseModel.DATASETNAME), keys.get(SearchFilterBaseModel.DATAROWNAME));
        } else if (keys.containsKey(SearchFilterBaseModel.DATAROWNAME) && keys.size() == 1) {
            return this.dataSetCollection.getArrayFromDataRow(keys.get(SearchFilterBaseModel.DATAROWNAME));
        } else if (keys.containsKey(SearchFilterBaseModel.DATASETNAME) && keys.size() == 1) {
            return this.dataSetCollection.getArrayFromDataSet(keys.get(SearchFilterBaseModel.DATASETNAME));
        }
        //TODO: Add Exception to throw
        throw new DataProviderInvalidSearchKeysException("Invalid search arguments");

    }

    /**
     * Initializes the testCaseGroup.
     *
     * @param filesPath The relative path which inside contains the xml files to
     * load the data.
     * @throws IOException if the path given by parameter is not found
     */
    abstract void loadTestData(String filesPath) throws DataProviderLoadFileException;

    /**
     * Reads the Test data from the XML files and adds the information to a
     * buffer.
     *
     * @param fileNames the files to read from
     * @param filePath the path where the files resides
     * @return a String Buffer with all the test data
     * @throws IOException if the data file is not found
     */
    abstract StringBuffer fillDataBuffer(final String[] fileNames,
            final String filePath) throws IOException, DataProviderLoadFileException;

    protected String fileToString(String filePath, String fileName) throws IOException, DataProviderLoadFileException {
        FileInputStream stream = new FileInputStream(new File(filePath + fileName));
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            /*
             * Instead of using default, pass in a decoder.
             */
            return Charset.defaultCharset().decode(bb).toString();
        } catch (Exception e) {
            throw new DataProviderLoadFileException(String.format("Can't load file %s%s", filePath, fileName));
        } finally {
            stream.close();
        }

    }

    /**
     * Reads the files names in the given path.
     *
     * @param filePath where the files resides.
     * @return an array of file names.
     * @throws IOException if the data file is not found.
     */
    abstract String[] getFileNames(final String filePath) throws IOException;
}
