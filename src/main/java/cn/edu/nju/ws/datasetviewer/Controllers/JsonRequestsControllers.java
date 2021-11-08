package cn.edu.nju.ws.datasetviewer.Controllers;

import cn.edu.nju.ws.datasetviewer.Methods.methods;
import cn.edu.nju.ws.datasetviewer.Models.DataIds;
import cn.edu.nju.ws.datasetviewer.Models.FinQAInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class JsonRequestsControllers {

    @RequestMapping(value = "/getTrainIds", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getTrainIds() {
        return methods.getMethodsInstace().getTrainIds();
    }

    @RequestMapping(value = "/getDevIds", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getDevIds() {
        return methods.getMethodsInstace().getDevIds();
    }

    @RequestMapping(value = "/getTestIds", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getTestIds() {
        return methods.getMethodsInstace().getTestIds();
    }

    @RequestMapping(value = "/getItem", method = RequestMethod.GET)
    @ResponseBody
    public FinQAInstance getItem(String id, String dataType) {
        return methods.getMethodsInstace().getFinQADataset().getDataById(id, dataType);
    }

    @RequestMapping(value = "/getTable", method = RequestMethod.GET)
    @ResponseBody
    public List<List<String>> getTable(String id, String dataType) {
        FinQAInstance ret;
        id = id.replace("(train)|(dev)|(test) / ", "");
        if (dataType.equals("all")) {
            if ((ret = methods.getMethodsInstace().getFinQADataset().getDataById(id, "train")) != null) {
                return ret.getTable();
            } else if ((ret = methods.getMethodsInstace().getFinQADataset().getDataById(id, "dev")) != null) {
                return ret.getTable();
            }
            return methods.getMethodsInstace().getFinQADataset().getDataById(id, "test").getTable();
        }
        return methods.getMethodsInstace().getFinQADataset().getDataById(id, dataType).getTable();
    }

    @RequestMapping(value = "/getAllIds", method = RequestMethod.GET)
    @ResponseBody
    public List<DataIds> getAllIds() {
        return methods.getMethodsInstace().getAllIds();
    }

    @RequestMapping(value = "/getRandomId", method = RequestMethod.GET)
    @ResponseBody
    public String getRandomId() {
        return methods.getMethodsInstace().getRandomId();
    }

    @RequestMapping(value = "/getTextsById", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getTextsById(String id) {
        return methods.getMethodsInstace().getTextsById(id, true);
    }

    @RequestMapping(value = "/getGoldTextsById", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getGoldTextsById(String id) {
        return methods.getMethodsInstace().getGoldTextsById(id);
    }

    @RequestMapping(value = "/getGoldRowsById", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> getGoldRowsById(String id) {
        return methods.getMethodsInstace().getGoldRowsById(id);
    }

    @RequestMapping(value = "/getQuestionAndAnswer", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getQuestionAndAnswer(String id) {
        return methods.getMethodsInstace().getQuestionAndAnswer(id);
    }

    @RequestMapping(value = "/getSteps", method = RequestMethod.GET)
    @ResponseBody
    public List<List<String>> getSteps(String id) {
        return methods.getMethodsInstace().getSteps(id);
    }

}
