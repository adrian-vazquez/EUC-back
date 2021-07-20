package com.citi.euces.pronosticos.infra.utils;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FormatUtils {

    public static String formatDateymd(Date fecha) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formatFecha = simpleDateFormat.format(fecha);
        return formatFecha;

    }

    public static String formatDatedmy(Date fecha) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formatFecha = simpleDateFormat.format(fecha);
        return formatFecha;
    }

    public static String formatDateSinEspacios(Date fecha) {
        String pattern = "yyyyMMddd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formatFecha = simpleDateFormat.format(fecha);
        return formatFecha;
    }

    public static String formatDateSinEspacios1(Date fecha) {
        String pattern = "yyMMdd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formatFecha = simpleDateFormat.format(fecha);
        return formatFecha;
    }

    public static Date stringToDate(String fecha) throws ParseException {
        String pattern = "dd/MM/yyyy";
        Date formatFecha = new SimpleDateFormat(pattern).parse(fecha);
        return formatFecha;
    }
    
    public static String formatFecActual () {
    	String pattern = "dd/MM/yyyy";
    	Date fecha = new Date();
    	SimpleDateFormat anio = new SimpleDateFormat(pattern);
    	return anio.format(fecha);
    }

    public static String formatMes(Date fecha) {
    	SimpleDateFormat formatFecha = new SimpleDateFormat("MMM");
    	return formatFecha.format(fecha).toUpperCase();
    }
    
    public static String formatAnio(Date fecha) {
    	SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy");
    	return formatFecha.format(fecha);
    }
    
    public static String formatFecFin(Date fecha) {
    	SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
    	return formatoSalida.format(fecha);
    }


    public static String formatFateFileExcel(){
        SimpleDateFormat df = new SimpleDateFormat("dd_MM_yyyy hh_mm_ss a");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"));
        calendar.setTime(new Date());
        return df.format(calendar.getTime());
    }

    public static String validFechaMes(Integer mes) {
        String valid = "00";
        if (mes != null) {
            Map<Integer, String> malidMes = new TreeMap<Integer, String>();
            malidMes.put(1, "ENE");
            malidMes.put(2, "FEB");
            malidMes.put(3, "MAR");
            malidMes.put(4, "ABR");
            malidMes.put(5, "MAY");
            malidMes.put(6, "JUN");
            malidMes.put(7, "JUL");
            malidMes.put(8, "AGO");
            malidMes.put(9, "SEP");
            malidMes.put(10, "OCT");
            malidMes.put(11, "NOV");
            malidMes.put(12, "DIC");
            valid = malidMes.get(mes) == null ? "00" : malidMes.get(mes);
        }
        return valid;
    }

    public static Map<Integer, String> getCatFranquicias() {
        Map<Integer, String> listaFranquicias = new TreeMap<Integer, String>();
        listaFranquicias.put(1, ConstantUtils.FRANQUICIA_COMERCIAL);
        listaFranquicias.put(2, ConstantUtils.FRANQUICIA_CORPORATIVA);
        listaFranquicias.put(3, ConstantUtils.FRANQUICIA_EMPRESARIAL);
        listaFranquicias.put(4, ConstantUtils.FRANQUICIA_PATRIMONIAL);
        return listaFranquicias;
    }

    public static String validCatalogadaGc(Integer id) {
        String valid = "";
        if (id != null) {
            Map<Integer, String> listaCatGc = new TreeMap<Integer, String>();
            listaCatGc.put(0, ConstantUtils.CAT_GC_COBRADA);
            listaCatGc.put(1, ConstantUtils.CAT_GC_REINTENTOS);
            listaCatGc.put(2, ConstantUtils.CAT_GC_MORA_MAYOR_90);
            listaCatGc.put(3, ConstantUtils.CAT_GC_NO_PROBABLE);
            listaCatGc.put(4, ConstantUtils.CAT_GC_PE_REINTENTO);
            listaCatGc.put(5, ConstantUtils.CAT_GC_COBRO_ESPECIAL);
            listaCatGc.put(6, ConstantUtils.CAT_GC_COBRO_MANUAL);
            valid = listaCatGc.get(id) == null ? "" : listaCatGc.get(id);
        }
        return valid;
    }

    public static Path convertZip(Path fileReporteRebaja) throws IOException {
        Path fileZip = Files.createTempFile("FileZip", ".zip");
        fileZip.toFile().deleteOnExit();
        byte[] buffer = new byte[1024];
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileZip.toFile()));
            ZipEntry ze = new ZipEntry(fileReporteRebaja.toFile().getName());
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(fileReporteRebaja.toFile());
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();
            zos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileZip;
    }

    public static Path convertLayoutZip(Path fileLayoutPrevio) throws IOException {
        Path fileZip = Files.createTempFile(/*Paths.get("C:/"),*/"FileZip", ".zip");
        fileZip.toFile().deleteOnExit();
        byte[] buffer = new byte[1024];
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileZip.toFile()));
            ZipEntry ze = new ZipEntry(fileLayoutPrevio.toFile().getName());
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(fileLayoutPrevio.toFile());
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            in.close();
            zos.closeEntry();
            zos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileZip;
    }

    public static Integer obtenerMes(Integer mes) throws ParseException {
        LocalDate now = LocalDate.now();
        Integer month = now.minusMonths(mes).getMonth().getValue();
        return month;
    }

    public static Integer obtenerYear(Integer year) throws ParseException {
        LocalDate now = LocalDate.now();
        Integer anio = now.minusMonths(year).getYear();
        return anio;
    }

    public static String validaString(String dato) {
        dato = dato == null ? " " : dato;
        return dato;
    }


    public static Path createExcel(List<String> titulos, List<List<String>> renglones, String nameFile ) throws IOException {
        Path testFile = Files.createTempFile("fileExcel", ".xlsx");
        testFile.toFile().deleteOnExit();
        try(XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet(nameFile);
            int colHeader = 0;
            Row rowheader = sheet.createRow(colHeader++);
            int colCell = 0;
            for (String field : titulos) {
                Cell cell = rowheader.createCell(colCell++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                }
            }
            int rowNum = 1;
            for (List<String> key : renglones) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                for (String field : key) {
                    Cell cell = row.createCell(colNum++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    }
                }
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            bos.close();
            Files.write(testFile, bos.toByteArray());
        }
        return testFile;
    }


}
