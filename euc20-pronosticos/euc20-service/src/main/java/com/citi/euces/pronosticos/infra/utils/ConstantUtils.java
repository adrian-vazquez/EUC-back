package com.citi.euces.pronosticos.infra.utils;

public class ConstantUtils {

    //Cat_Franquicia
    final public static String FRANQUICIA_COMERCIAL = "COMERCIAL";
    final public static String FRANQUICIA_CORPORATIVA = "CORPORATIVA";
    final public static String FRANQUICIA_EMPRESARIAL = "EMPRESARIAL";
    final public static String FRANQUICIA_PATRIMONIAL = "PATRIMONIAL";

    //CatalogadaGc
    final public static String CAT_GC_COBRADA = "COBRADA";
    final public static String CAT_GC_REINTENTOS = "REINTENTOS";
    final public static String CAT_GC_MORA_MAYOR_90 = "MORA MAYOR 90";
    final public static String CAT_GC_NO_PROBABLE = "NO PROBABLE";
    final public static String CAT_GC_PE_REINTENTO = "PE REINTENTO";
    final public static String CAT_GC_COBRO_ESPECIAL = "COBRO ESPECIAL";
    final public static String CAT_GC_COBRO_MANUAL = "COBRO MANUAL";

    //Titles ReporteRebaja
    final public static String NUM_CLIENTE = "Número Cliente";
    final public static String BLANCO = "Blanco";
    final public static String CUENTA = "Cuenta";
    final public static String CUENTA_COBRO = "Cuenta Cobro";
    final public static String ESTATUS = "Estatus Cuenta Cobro";
    final public static String IMPORTE = "Importe";
    final public static String IVA = "IVA";
    final public static String CAUSA_RECHAZO = "Causa del Rechazo";
    final public static String MES = "Mes";
    final public static String ANIO = "Año";
    final public static String SERVICIO = "Servicio";
    final public static String CSI = "CSI";
    final public static String COM = "COM";
    final public static String COMISION_SIN_IVA = "Comision Sin IVA";
    final public static String IVAA = "IVAA";
    final public static String TOTAL = "Total";
    final public static String COM_P = "COM(p)";
    final public static String LLAVE = "Llave";
    final public static String NUM_PROTECCION = "Número de Protección";
    final public static String FRANQUICIA = "Franquicia";
    final public static String CATALOGADA = "Catalogada";
    final public static String FECH_REAL = "Fecha Real";
    final public static String FECH_CONTABLE = "Fecha Contable";
    final public static String CUENTA_PRODUCTO = "Cuenta Producto";
    final public static String CONTRATO = "Contrato";
    final public static String OPEN_ITEM = "OPEN_ITEM";

    final public static String NO_CLIENTES = "No_Clientes";
    final public static String CUENTA_CONTABLE = "Cuenta contable";



    //ReporteRebaja encabezado txt
    final public static String ENCABEZADO_REP_REBAJA = NUM_CLIENTE + "\t" + BLANCO + "\t" + CUENTA + "\t"
            + CUENTA_COBRO + "\t" + ESTATUS + "\t" + IMPORTE + "\t" + IVA + "\t" + CAUSA_RECHAZO + "\t" + MES + "\t"
            + ANIO + "\t" + SERVICIO + "\t" + CSI + "\t" + COM + "\t" + COMISION_SIN_IVA + "\t" + IVAA + "\t"
            + TOTAL + "\t" + COM_P + "\t" + LLAVE + "\t" + NUM_PROTECCION + "\t" + FRANQUICIA + "\t"
            + CATALOGADA + "\t" + FECH_REAL + "\t" + FECH_CONTABLE + "\t" + CUENTA_PRODUCTO + "\t" + CONTRATO + "\n";

    //ReporteRebaja encabezado EXCEL
    final public static String[] TITLE_REP_REBAJA_EXCEL = {NUM_CLIENTE,BLANCO,CUENTA,CUENTA_COBRO,ESTATUS,IMPORTE,IVA,
            CAUSA_RECHAZO,MES,ANIO,SERVICIO,CSI,COM,COMISION_SIN_IVA,IVAA,TOTAL,COM_P,LLAVE,NUM_PROTECCION,FRANQUICIA,
            CATALOGADA,FECH_REAL,OPEN_ITEM,FECH_CONTABLE,CUENTA_PRODUCTO,CONTRATO};

    //ReporteMora encabezado EXCEL
    final public static String[] TITLE_REP_MORA_EXCEL = {NO_CLIENTES,CUENTA.toUpperCase(),MES.toUpperCase(),ANIO.toUpperCase(),SERVICIO.toUpperCase(),
            COMISION_SIN_IVA.toUpperCase(),IVAA.toUpperCase(),TOTAL.toUpperCase(), LLAVE.toUpperCase(), FRANQUICIA, CUENTA_CONTABLE,OPEN_ITEM.toLowerCase()};

}
