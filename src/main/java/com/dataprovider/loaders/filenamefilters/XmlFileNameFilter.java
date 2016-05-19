package com.dataprovider.loaders.filenamefilters;

import java.io.File;
import java.io.FilenameFilter;

public class XmlFileNameFilter implements FilenameFilter {

	private final String okFileExtensions = "xml";

	public boolean accept(File dir, String name) {
		return name.endsWith(okFileExtensions);
	}
}
