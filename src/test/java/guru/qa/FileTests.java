package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import guru.qa.jsonUtils.JsonToString;
import guru.qa.jsonUtils.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class FileTests {

    String xlsxFileName = "xlsx file.xlsx";
    String pdfFileName = "pdf file.pdf";
    String csvFileName = "csv file.csv";

    @Test
    void xlsxTest() throws Exception {
        try (InputStream inputStream = getZipFile(xlsxFileName)) {
            XLS xls = new XLS(inputStream);
            String cellValue = xls.excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue();
            Assertions.assertThat(cellValue).contains("Leonid Zatulovskii");
        }
    }

    @Test
    void pdfTest() throws Exception {
        try (InputStream inputStream = getZipFile(pdfFileName)) {
            PDF pdf = new PDF(inputStream);
            com.codeborne.pdftest.assertj.Assertions.assertThat(pdf).
                    containsExactText("Меня зовут Лёня и я хочу тестировать как гуру");
        }
    }

    @Test
    void csvTest() throws Exception {
        try (InputStream inputStream = getZipFile(csvFileName);
             CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {

            List<String[]> csvContent = reader.readAll();
            Assertions.assertThat(csvContent).contains(
                    new String[]{"Student/Homework", "1", "2", "3"},
                    new String[]{"Leonid Zatulovskii", "accepted", "accepted", "declined"}
            );
        }
    }

    @Test
    void jsonTest() {
            Gson gson = new Gson();
            Student student = gson.fromJson(JsonToString.readJsonData(
                    "src/test/resources/json file.json"), Student.class);
            Assertions.assertThat(student.firstName).contains("Leonid");
            Assertions.assertThat(student.lastName).contains("Zatulovskii");
            Assertions.assertThat(student.address.streetAddress).contains("ulitsa Pushkina");
            Assertions.assertThat(student.address.city).contains("Pushkin");
            Assertions.assertThat(student.address.country).contains("Russia");
        }

    public static InputStream getZipFile(String fileName) throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/zip/zip-archive.zip"));
        ZipEntry zipEntry = zipFile.getEntry(fileName);
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        return inputStream;
    }
}
