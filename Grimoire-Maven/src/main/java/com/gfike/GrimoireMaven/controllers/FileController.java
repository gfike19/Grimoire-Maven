package com.gfike.GrimoireMaven.controllers;

import com.gfike.GrimoireMaven.models.Monster;
import com.gfike.GrimoireMaven.models.data.MonsterDao;
import com.gfike.GrimoireMaven.models.data.MonsterService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    MonsterDao monsterDao;

    private MonsterService monsterService;

    public FileController(MonsterService monsterService) {
        this.monsterService = monsterService;
    }

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

    @GetMapping("/download")
    public void exportCSV(HttpServletResponse response) throws Exception {

        //set file name and content type
        String filename = "monsters.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<Monster> writer = new StatefulBeanToCsvBuilder<Monster>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        //write all users to csv file
        writer.write(monsterService.listUsers());

    }
}

