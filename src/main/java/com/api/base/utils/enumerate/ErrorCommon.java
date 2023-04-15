package com.api.base.utils.enumerate;

public enum ErrorCommon {

      ERR_DATE("DateTime not valid!")
    , ERR_FILE_UPLOAD("Upload file error!")
    , ERR_FILE_DELETE("Delete file error!")
    , ERR_FILE_COPY("Copy file error!");
    
    private String value;

    public String getValue() {
        return value;
    }

    private ErrorCommon(String value) {
        this.value = value;
    }
}
