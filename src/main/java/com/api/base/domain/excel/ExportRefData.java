package com.api.base.domain.excel;

import java.util.List;

public class ExportRefData {
    private List<ExportRefValidation> validations;
    private List<?> objects;
    private List<String> fields;

    public List<ExportRefValidation> getValidations() {
        return validations;
    }

    public void setValidations(List<ExportRefValidation> validations) {
        this.validations = validations;
    }

    public List<?> getObjects() {
        return objects;
    }

    public void setObjects(List<?> objects) {
        this.objects = objects;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }
}
