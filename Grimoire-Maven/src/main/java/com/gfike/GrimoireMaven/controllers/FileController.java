package com.gfike.GrimoireMaven.controllers;

import com.gfike.GrimoireMaven.models.Monster;
import com.gfike.GrimoireMaven.models.data.MonsterDao;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    MonsterDao monsterDao;

    @RequestMapping(value = "/upload",method = RequestMethod.GET)
        public String uploadGet() {
            return "upload";
        }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
        public String mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile,
    Model model) {

                List<Monster> tempMonsList = new ArrayList<>();
        XSSFWorkbook workbook = null;
                try {
                    workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
                }
                catch(Exception e){
                    String msg = e.toString();
                    model.addAttribute("msg", msg);
                        return "redirect:/";
                }

                XSSFSheet worksheet = workbook.getSheetAt(0);

                for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                        Monster tempMons = new Monster();

                        XSSFRow row = worksheet.getRow(i);

                    tempMons.setId((int) row.getCell(0).getNumericCellValue());
                    tempMons.setCount((int) row.getCell(1).getNumericCellValue());
                    tempMonsList.add(tempMons);
                }
            for(Monster m : tempMonsList) {
                monsterDao.save(m);
            }
            return "redirect:/";
        }
}

