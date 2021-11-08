package cn.edu.nju.ws.datasetviewer.Methods;

import cn.edu.nju.ws.datasetviewer.Config;
import cn.edu.nju.ws.datasetviewer.Models.DataIds;
import cn.edu.nju.ws.datasetviewer.Models.FinQAInstance;
import cn.edu.nju.ws.datasetviewer.Models.QAofFinQAIntance;
import lombok.Getter;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class methods {
    @Getter
    private static methods methodsInstace = new methods();

    @Getter
    private final FinQADataset finQADataset = new FinQADataset(Config.getDatasetDir());

    public List<String> getTrainIds() {
        return this.finQADataset.getTrainIds();
    }

    public List<String> getDevIds() {
        return this.finQADataset.getDevIds();
    }

    public List<String> getTestIds() {
        return this.finQADataset.getTestIds();
    }

    public List<DataIds> getAllIds() {
        return new ArrayList<DataIds>(Arrays.asList(new DataIds(this.getTrainIds(), "train"),
                new DataIds(this.getDevIds(), "dev"), new DataIds(this.getTestIds(), "test")));
    }

    public String getRandomId() {
        return this.finQADataset.getAllIds().get((int) (Math.random() * this.finQADataset.getAllIds().size()));
    }

    public List<String> getTextsById(String id, boolean filtered) {
        List<String> preText = this.finQADataset.getAllDataset().get(id).getPreText();
        List<String> postText = this.finQADataset.getAllDataset().get(id).getPostText();
        if (filtered)
            return Stream.concat(preText.stream(), postText.stream()).filter(x -> !x.trim().equals(".")).collect(Collectors.toList());
        else
            return Stream.concat(preText.stream(), postText.stream()).collect(Collectors.toList());
    }

    public List<String> getGoldTextsById(String id) {
        List<String> texts = this.getTextsById(id, false);
        List<Integer> goldTextsIndex =
                this.finQADataset.getAllDataset().get(id).getQa().getGoldInds().stream().filter(x -> x.contains("text"
                )).map(x -> x.split("_")[1]).map(Integer::parseInt).collect(Collectors.toList());
        return goldTextsIndex.stream().map(texts::get).collect(Collectors.toList());
    }

    public List<Integer> getGoldRowsById(String id) {
        return this.finQADataset.getAllDataset().get(id).getQa().getGoldInds().stream().filter(x -> x.contains("table"
        )).map(x -> x.split("_")[1]).map(Integer::parseInt).collect(Collectors.toList());
    }

    public List<String> getQuestionAndAnswer(String id) {
        String question = this.finQADataset.getAllDataset().get(id).getQa().getQuestion();
        String answer = this.finQADataset.getAllDataset().get(id).getQa().getAnswer();
        return new ArrayList<>(Arrays.asList(question, answer));
    }

    public List<List<String>> getSteps(String id) {
        return this.finQADataset.getAllDataset().get(id).getQa().getSteps();
    }
}