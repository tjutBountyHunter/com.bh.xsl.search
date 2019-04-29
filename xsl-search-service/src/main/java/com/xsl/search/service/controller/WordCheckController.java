package com.xsl.search.service.controller;

import com.xsl.search.export.WordCheckService;
import com.xsl.search.service.common.XslResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class WordCheckController {
    @Autowired
    private WordCheckService wordCheckService;

    @RequestMapping(value="/wordcheck", method=RequestMethod.GET)
    @ResponseBody
    public XslResult search_hunter(@RequestParam String sentence) {
        //查询条件不能为空
        if (sentence.isEmpty()) {
            return XslResult.build(400, "句子不能为空");
        }
        List<String> list;
        try {
             list = wordCheckService.check(sentence);
        } catch (Exception e) {
            e.printStackTrace();
            return XslResult.build(500, e.toString());
        }
        return XslResult.ok(list);
    }
}
