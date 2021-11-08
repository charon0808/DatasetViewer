package cn.edu.nju.ws.datasetviewer.Methods;

import cn.edu.nju.ws.datasetviewer.Models.FinQAInstance;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FinQADataset {

    private final Map<String, FinQAInstance> trainDataset;
    private final Map<String, FinQAInstance> devDataset;
    private final Map<String, FinQAInstance> testDataset;

    @Getter
    private final List<String> trainIds;
    @Getter
    private final List<String> devIds;
    @Getter
    private final List<String> testIds;

    @Getter
    private final List<String> allIds;

    @Getter
    private final Map<String, FinQAInstance> allDataset;

    public FinQADataset(String datasetDir) {
        this.trainDataset = this.readJson(datasetDir + "/" + "train.json");
        this.devDataset = this.readJson(datasetDir + "/" + "dev.json");
        this.testDataset = this.readJson(datasetDir + "/" + "test.json");

        this.trainIds = new ArrayList<>(this.trainDataset.keySet());
        this.devIds = new ArrayList<>(this.devDataset.keySet());
        this.testIds = new ArrayList<>(this.testDataset.keySet());

        this.allIds = Stream.concat(this.trainIds.stream(), Stream.concat(this.devIds.stream(), this.testIds.stream())).collect(Collectors.toList());
        this.allDataset = Stream.concat(this.trainDataset.entrySet().stream(), Stream.concat(this.devDataset.entrySet().stream(), this.testDataset.entrySet().stream())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public FinQAInstance getDataById(String id, String dataType) {
        try {
            Field field = this.getClass().getDeclaredField(dataType + "Dataset");
            field.setAccessible(true);
            return ((Map<String, FinQAInstance>) field.get(this)).get(id);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            System.err.printf("data type must be one of [train, dev, test], got %s%n", dataType);
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, FinQAInstance> readJson(String jsonFileName) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            JSONArray datas = JSONArray.parseArray(content);
            ValueFilter valueFilter = new ValueFilter() {
                @Override
                public Object process(Object object, String name, Object value) {
                    switch (name) {
                        case "steps":
                            assert value instanceof JSONArray;
                            return new JSONArray(((JSONArray) value).stream().map(JSONObject.class::cast).map(x -> Arrays.asList(x.get("op"), x.get("arg1"), x.get("arg2"), x.get("res"))).collect(Collectors.toList()));
                        case "gold_inds":
                        case "tfidftopn":
                            assert value instanceof JSONObject;
                            //return new JSONArray(((JSONObject) value).keySet().stream().map(x -> x.split("_")[1]).map(Integer::parseInt).collect(Collectors.toList()));
                            return new JSONArray(new ArrayList<>(((JSONObject) value).keySet()));
                        case "model_input":
                            assert value instanceof JSONArray;
                            return new JSONArray(((JSONArray) value).stream().map(JSONArray.class::cast).map(x -> x.get(0)).map(String.class::cast).map(x -> x.split("_")[1]).map(Integer::parseInt).collect(Collectors.toList()));
                        case "exe_ans":
                            if (value.equals("yes")) {
                                return 1;
                            } else if (value.equals("no")) {
                                return 0;
                            }
                            return value;
                    }
                    return value;
                }
            };
            return datas.stream().map(JSONObject.class::cast).map(x -> JSON.parseObject(JSONObject.toJSONString(x, valueFilter), FinQAInstance.class)).collect(Collectors.toMap(FinQAInstance::getId, Function.identity()));
        } catch (IOException e) {
            System.err.printf("read json file %s error!%n", jsonFileName);
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }

}
