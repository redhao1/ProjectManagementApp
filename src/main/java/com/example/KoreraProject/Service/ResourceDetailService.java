package com.example.KoreraProject.Service;

import com.example.KoreraProject.DatabaseModels.*;

import java.util.Set;

public interface ResourceDetailService {
    public Set<String> getExtraColumns();
    public boolean addNewColumn(String columnName);
}
