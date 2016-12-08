package com.microsoft.azuresample.sqlkeyvault.controller;

import com.microsoft.azuresample.sqlkeyvault.model.ToDo;
import com.microsoft.azuresample.sqlkeyvault.model.ToDoDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SqlKeyVaultController {

    ToDoDAO dao=new ToDoDAO();

    @RequestMapping(value = "/api/ToDoes", method = { RequestMethod.POST })
    public
    @ResponseBody
    ToDo insertToDoes(@RequestBody ToDo item) {
        return dao.create(item);
    }

    @RequestMapping(value = "/api/ToDoes", method = { RequestMethod.GET })
    public
    @ResponseBody
    List<ToDo> queryToDoes() {
        return dao.query();
    }

    @RequestMapping(value = "/api/Test", method = { RequestMethod.GET })
    public
    @ResponseBody
    String testToDoes() {
        return dao.test();
    }
}

