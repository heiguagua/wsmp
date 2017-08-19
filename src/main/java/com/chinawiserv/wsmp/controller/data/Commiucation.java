package com.chinawiserv.wsmp.controller.data;

import com.chinawiserv.wsmp.pojo.CountResult;
import com.chinawiserv.wsmp.service.StationCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@RestControllerAdvice
@RequestMapping("data/commiucation")
public class Commiucation {

    @Autowired
    StationCountService service;

    @RequestMapping("/toptable")
    public void getTopTable() {

    }

    @RequestMapping("/bottomtable")
    public Object bottomtable() {
        List<CountResult> current = service.getCurrentYearCount();
        List<CountResult> last = service.getLastYearCount();

        Map<String, CountResult> currentMap = current.stream().collect(toMap(k->k.getNetTs()+"_"+k.getOrgSystemCode(),v->v));
        Map<String, CountResult> lastMap = last.stream().collect(toMap(k->k.getNetTs()+"_"+k.getOrgSystemCode(),v->v));

        return null;
    }
}
