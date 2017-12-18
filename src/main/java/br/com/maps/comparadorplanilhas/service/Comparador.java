package br.com.maps.comparadorplanilhas.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class Comparador {

    private static int erros = 0;

    public List<String> compare(String filePath1, String filePath2) {
        List<String> msg = new ArrayList<>();
        try {
            //HSSF para .xls e XSSF para .xlsx
            Workbook workbook1;
            if (filePath1.contains(".xlsx")) {
                workbook1 = new XSSFWorkbook(new FileInputStream(new File(filePath1)));
            } else {
                workbook1 = new HSSFWorkbook(new FileInputStream(new File(filePath1)));
            }

            Workbook workbook2;
            if (filePath2.contains(".xlsx")) {
                workbook2 = new XSSFWorkbook(new FileInputStream(new File(filePath2)));
            } else {
                workbook2 = new HSSFWorkbook(new FileInputStream(new File(filePath2)));
            }

            Map<String, String> mapa1 = criaMapa(workbook1);
            Map<String, String> mapa2 = criaMapa(workbook2);
            msg.addAll(executaComparacao(mapa1, mapa2));
            if (erros == 0) {
                msg.add("As planilhas são idênticas.");
            }
            workbook1.close();
            workbook2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    private Map<String, String> criaMapa(Workbook workbook) {
        Map<String, String> mapa = new LinkedHashMap<String, String>();
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        Cell cell;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            String linha = "";
            if (row != null) {
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (null == cell) continue;
                    if (cell.getCellType() == 1)
                        linha = cell.getStringCellValue();
                    else {
                        linha = String.valueOf(cell.getNumericCellValue());
                    }
                    mapa.put(row.getCell(j).getAddress().toString(), linha);
                }
            }
        }
        return mapa;
    }

    private List<String> executaComparacao(Map<String, String> mapa1, Map<String, String> mapa2) {
        List<String> msg = new ArrayList<>();
        msg.add("Comparando primeira planilha com a segunda.");
        for (String key : mapa1.keySet()) {
            if (mapa2.containsKey(key)) {
                if (!mapa2.get(key).equals(mapa1.get(key))) {
                    msg.add("Célula " + key + " é diferente na segunda planilha.");
                    erros++;
                }
            } else {
                msg.add("Célula " + key + " não existe na segunda planilha.");
                erros++;
            }
        }
        msg.add("Comparando segunda planilha com a primeira.");
        for (String key : mapa2.keySet()) {
            if (mapa1.containsKey(key)) {
                if (!mapa1.get(key).equals(mapa2.get(key))) {
                    msg.add("Célula " + key + " é diferente na primeira planilha.");
                    erros++;
                }
            } else {
                msg.add("Célula " + key + " não existe na primeira planilha.");
                erros++;
            }
        }
        return msg;
    }
}
