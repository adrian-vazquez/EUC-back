package com.citi.euces.pronosticos.infra.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
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
    	return formatFecha.format(fecha);
    }
    
    public static String formatAnio(Date fecha) {
    	SimpleDateFormat formatFecha = new SimpleDateFormat("yyyy");
    	return formatFecha.format(fecha);
    }
    
    public static String formatFecFin(Date fecha) {
    	SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
    	return formatoSalida.format(fecha);
    }

    public static String validFechaMes(Integer mes) {
        String valid = "00";
        if (mes != null) {
            Map<Integer, String> malidMes = new TreeMap<Integer, String>();
            malidMes.put(1, "ENE");malidMes.put(2, "FEB");
            malidMes.put(3, "MAR");malidMes.put(4, "ABR");
            malidMes.put(5, "MAY");malidMes.put(6, "JUN");
            malidMes.put(7, "JUL");malidMes.put(8, "AGO");
            malidMes.put(9, "SEP");malidMes.put(10, "OCT");
            malidMes.put(11, "NOV");malidMes.put(12, "DIC");
            valid = malidMes.get(mes) == null ? "00" : malidMes.get(mes);
        }
        return valid;
    }

    public static Map<Integer, String> getCatFranquicias(){
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
        Path fileZip = Files.createTempFile(Paths.get("C:/"),"FileZip", ".zip");
        //fileZip.toFile().deleteOnExit();
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
}