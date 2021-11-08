package cn.edu.nju.ws.datasetviewer;

import cn.edu.nju.ws.datasetviewer.Methods.methods;
import cn.edu.nju.ws.datasetviewer.Models.FinQAInstance;
import cn.edu.nju.ws.datasetviewer.Models.QAofFinQAIntance;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DataAnalysisTest {

    private boolean tableContains(List<List<String>> table, String value) {
        return table.stream().flatMap(Collection::stream).anyMatch(cell -> cell.contains(value));
    }

    @Test
    public void testCount() {
        double avgGoldTextsCount =
                methods.getMethodsInstace().getFinQADataset().getAllDataset().values().stream().map(FinQAInstance::getQa).map(QAofFinQAIntance::getGoldInds).map(x -> x.stream().filter(y -> y.contains("text")).collect(Collectors.toList())).mapToDouble(List::size).average().orElse(0);
        double avgGoldRowsCount =
                methods.getMethodsInstace().getFinQADataset().getAllDataset().values().stream().map(FinQAInstance::getQa).map(QAofFinQAIntance::getGoldInds).map(x -> x.stream().filter(y -> y.contains("table")).collect(Collectors.toList())).mapToDouble(List::size).average().orElse(0);

        System.out.printf("avg texts count: %f%n", avgGoldTextsCount);
        System.out.printf("avg rows count: %f%n", avgGoldRowsCount);
    }

    @Test
    public void countOfDividePercentage() {
        long count =
                methods.getMethodsInstace().getFinQADataset().getAllDataset().values().stream().filter(x -> x.getQa().getQuestion().contains("percentage") || x.getQa().getQuestion().contains("portion")).filter(x -> this.tableContains(x.getTable(), "total")).count();

        System.out.println("percentage count: " + count + ", ratio: " + (float) count / methods.getMethodsInstace().getFinQADataset().getAllDataset().entrySet().size());
    }

    @Test
    public void oneStepDivideCount() {
        long count =
                methods.getMethodsInstace().getFinQADataset().getAllDataset().values().stream().filter(x -> x.getQa().getSteps().size() == 1 && x.getQa().getSteps().get(0).get(0).contains("divide")).count();

        System.out.println("one steps divide count: " + count + ", ratio: " + (float) count / methods.getMethodsInstace().getFinQADataset().getAllDataset().entrySet().size());
    }

    @Test
    public void oneStepDividePercentageCount() {
        long count =
                methods.getMethodsInstace().getFinQADataset().getAllDataset().values().stream().filter(x -> x.getQa().getSteps().size() == 1 && x.getQa().getSteps().get(0).get(0).contains("divide") && x.getQa().getSteps().get(0).get(3).contains("%")).count();

        System.out.println("one steps divide precentage count: " + count + ", ratio: " + (float) count / methods.getMethodsInstace().getFinQADataset().getAllDataset().entrySet().size());
    }

    private boolean goldIndsOnlyTable(List<String> goldInds) {
        return goldInds.stream().noneMatch(s -> s.contains("text"));
    }

    @Test
    public void onlyTextOrTable() {
        long onlyHasTableCount =
                methods.getMethodsInstace().getFinQADataset().getAllDataset().values().stream().filter(x -> x.getQa().getGoldInds().stream().noneMatch(s -> s.contains("text"))).count();
        long onlyHasTextCount =
                methods.getMethodsInstace().getFinQADataset().getAllDataset().values().stream().filter(x -> x.getQa().getGoldInds().stream().noneMatch(s -> s.contains("table"))).count();
        long totalCount = methods.getMethodsInstace().getFinQADataset().getAllDataset().size();

        System.out.printf("total count: %d, only has table count: %d, only has text count: %d%n", totalCount,
                onlyHasTableCount, onlyHasTextCount);
    }

}
