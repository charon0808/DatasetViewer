package cn.edu.nju.ws.datasetviewer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;


@Getter
@Data
@AllArgsConstructor
public class FinQAInstance {

    @JSONField(name = "id", serialize = true)
    private String id;

    @JSONField(name = "filename", serialize = true)
    private String filename;

    @JSONField(name = "pre_text")
    private List<String> preText;

    @JSONField(name = "post_text")
    private List<String> postText;

    @JSONField(name = "table_ori")
    private List<List<String>> tableOri;

    @JSONField(name = "table")
    private List<List<String>> table;

    @JSONField(name = "qa")
    private QAofFinQAIntance qa;

}



