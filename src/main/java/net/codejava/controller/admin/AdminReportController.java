package net.codejava.controller.admin;

import net.codejava.controller.AbstractRestController;
import net.codejava.dto.Report;
import net.codejava.repository.ReportRepository;
import net.codejava.response.StatusResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/report")
public class AdminReportController extends AbstractRestController {

    @Autowired
    ReportRepository reportRepository;

    @GetMapping("/inventoryByBrand")
    public StatusResp inventoryByBrand(){
        StatusResp resp = new StatusResp();
        List<Report> list = reportRepository.getInventoryDataByBrand();
        resp.setData(list);
        return resp;
    }

    @GetMapping("/inventoryByCate")
    public StatusResp inventoryByCate(){
        StatusResp resp = new StatusResp();
        List<Report> list = reportRepository.getInventoryDataByCate();
        resp.setData(list);
        return resp;
    }

}
