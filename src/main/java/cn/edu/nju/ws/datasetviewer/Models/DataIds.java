package cn.edu.nju.ws.datasetviewer.Models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataIds {

    @Getter
    private String label;

    @Getter
    private List<Map<String, String>> children;

    public DataIds(List<String> ids, String dataType) {
        this.label = dataType;
        this.children = ids.stream().map(x -> new HashMap<String, String>() {{
            put("label", x);
        }}).collect(Collectors.toList());
    }

}
