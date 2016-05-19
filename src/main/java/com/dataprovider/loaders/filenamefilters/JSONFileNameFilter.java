package com.dataprovider.loaders.filenamefilters;

import java.io.File;
import java.io.FilenameFilter;

public class JSONFileNameFilter implements FilenameFilter {

	private final String okFileExtensions = "json";

	public boolean accept(File dir, String name) {
		return name.endsWith(okFileExtensions);
	}
}