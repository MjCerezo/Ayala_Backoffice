package com.etel.sigcard.form;

import com.coopdb.data.Tbbatchfile;

/**
 * Created by saoDG on 9/17/2018.
 */
public class BulkForm {

    private byte[] fileData;
    private Tbbatchfile data;
    
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	public Tbbatchfile getData() {
		return data;
	}
	public void setData(Tbbatchfile data) {
		this.data = data;
	}

    
}
