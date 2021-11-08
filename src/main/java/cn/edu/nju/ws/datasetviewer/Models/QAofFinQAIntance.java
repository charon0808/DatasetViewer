package cn.edu.nju.ws.datasetviewer.Models;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QAofFinQAIntance {

    @JSONField(name = "question")
    private String question;

    @JSONField(name = "answer")
    private String answer;

    @JSONField(name = "explanation")
    private String explanation;

    @JSONField(name = "ann_table_rows")
    private List<Integer> annTableRows;

    @JSONField(name = "ann_text_rows")
    private List<Integer> annTextRows;

    @JSONField(name = "steps")
    private List<List<String>> steps;

    @JSONField(name = "program")
    private String program;

    @JSONField(name = "gold_inds")
    private List<String> goldInds;

    @JSONField(name = "exe_ans")
    private List<Double> exeAns;

    @JSONField(name = "tfidftopn")
    private List<String> tfidfTopN;

    @JSONField(name = "model_input")
    private List<Integer> modelInput;

    @JSONField(name = "program_re")
    private String programRe;

}