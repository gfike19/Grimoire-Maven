package com.gfike.GrimoireMaven.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gfike.GrimoireMaven.models.Monster;
import com.gfike.GrimoireMaven.models.data.MonsterDao;
import com.gfike.GrimoireMaven.models.data.MonsterService;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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

    @PostMapping("/upload")
    public void mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile)
    throws IOException {

        List<Monster> tempMonsList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i = 0 ; i < worksheet.getRow(0).getPhysicalNumberOfCells() ;i++) {
            XSSFRow row = worksheet.getRow(i);

            boolean biped = (row.getCell(0).getBooleanCellValue());
            int count = ((int)row.getCell(1).getNumericCellValue());
            boolean fly = (row.getCell(2).getBooleanCellValue());
            String name = (row.getCell(3).getStringCellValue());
            boolean quad = row.getCell(4).getBooleanCellValue();
            boolean swim = (row.getCell(5).getBooleanCellValue());
            Monster tempMons = new Monster(name, biped, quad, swim, fly);
            tempMonsList.add(tempMons);
        }

        for(Monster m: tempMonsList){
            monsterDao.save(m);
        }



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

