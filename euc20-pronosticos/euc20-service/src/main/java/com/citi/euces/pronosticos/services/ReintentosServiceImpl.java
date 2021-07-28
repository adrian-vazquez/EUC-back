package com.citi.euces.pronosticos.services;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.citi.euces.pronosticos.infra.dto.Cat_Clave_ProductoBEDTO;
import com.citi.euces.pronosticos.infra.dto.Cat_Serv_Ondemand_ProBEDTO;
import com.citi.euces.pronosticos.infra.dto.CuentasAlternasDTO;
import com.citi.euces.pronosticos.infra.dto.GenArcPFECyTCDTO;
import com.citi.euces.pronosticos.infra.dto.Maestro_ComisionesBEDTO;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.dto.ReintentosMaestroComiDTO;
import com.citi.euces.pronosticos.infra.dto.TbBloqueosBEDTO;
import com.citi.euces.pronosticos.infra.exceptions.GenericException;
import com.citi.euces.pronosticos.infra.utils.ConstantUtils;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.CuentasAlternasJDBCRepository;
import com.citi.euces.pronosticos.repositories.ReintentosJDBCRepository;
import com.citi.euces.pronosticos.services.api.ReintentosService;

@Service
@Transactional
public class ReintentosServiceImpl implements ReintentosService {
	static final Logger log = LoggerFactory.getLogger(ReintentosServiceImpl.class);

	@Autowired
	private ReintentosJDBCRepository reintentosJDBCRepository;

	@Autowired
	private CuentasAlternasJDBCRepository cuentasAlternasJDBCRepository;

	@Override
	public MensajeDTO limpiarTabla() throws GenericException {
		// TODO Auto-generated method stub
		log.info("limpiarReintentos:: ");
		try {
			reintentosJDBCRepository.truncateTableReintentos();
		} catch (Exception e) {
			throw new GenericException("Error al limpiar tabla Reintentos :: ", HttpStatus.NOT_FOUND.toString());
		}

		MensajeDTO response = new MensajeDTO();
		response.setMensajeConfirm("Tabla vacía.");
		response.setMensajeInfo("La tabla Reintentos se encuentra sin registros.");
		return response;
	}

	@Override
	public MensajeDTO cuentasAltloadFile(String file) throws GenericException, IOException, ParseException {
		MensajeDTO msg = new MensajeDTO();
		String message = null;

		log.info("cuentasAltloadFile ::  init");
		log.info("File :: " + file);
		Path testFile = Files.createTempFile("cuantasAltZip",".zip");
		testFile.toFile().deleteOnExit();
		byte[] decoder = Base64.getDecoder().decode(file);
		Files.write(testFile, decoder);
		System.out.println(testFile.toFile().getAbsoluteFile());
		System.out.println("ZIP File Saved");

		ZipFile zipFile = new ZipFile(testFile.toFile());
		Enumeration<?> enu = zipFile.entries();

		try {
			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();

				String name = zipEntry.getName();
				long size = zipEntry.getSize();
				long compressedSize = zipEntry.getCompressedSize();

				System.out.printf("name: %-20s | size: %6d | compressed size: %6d\n", name, size, compressedSize);
				InputStream is = zipFile.getInputStream(zipEntry);
				Path tempFile = Files.createTempFile("cuentasAltTXT", ".txt");
				tempFile.toFile().deleteOnExit();
				try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
					IOUtils.copy(is, fos);
					message = leerArchivo(tempFile);
				}
			}
			zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		msg.setMensajeInfo("Aviso");
		msg.setMensajeConfirm(message);
		return msg;

	}

	public String leerArchivo(Path tempFile) throws IOException, GenericException, ParseException {
		String linea, n = null;
		String[] ArrayAux;
		int ini = 1;
		FileReader f = new FileReader(tempFile.toFile());
		BufferedReader b = new BufferedReader(f);
		List<CuentasAlternasDTO> listaRespuesta = new ArrayList<CuentasAlternasDTO>();

		while ((linea = b.readLine()) != null) {
			log.info(linea);
			if (ini != 1) {
				if (!linea.equals("") && !linea.equals("\t\t\t\t\t\t\t\t")) {
					ArrayAux = linea.split("\t");
					log.info(ArrayAux.toString());
					log.info(ArrayAux.length+"");
					log.info(Arrays.toString(ArrayAux));
					log.info(ArrayAux[6].equals(null) || ArrayAux[6].equals("") ? "0" : ArrayAux[6]);
					
					CuentasAlternasDTO data = new CuentasAlternasDTO();
					data.setNum_Cliente(Long.parseLong(ArrayAux[0].equals(null) || ArrayAux[0].equals("") ? "0" : ArrayAux[0]));
					data.setNum_Producto(Long.parseLong(ArrayAux[1].equals(null) || ArrayAux[1].equals("") ? "0" : ArrayAux[1]));
					data.setCve_Instrumento(Long.parseLong(ArrayAux[2].equals(null) || ArrayAux[2].equals("") ? "0" : ArrayAux[2]));
					data.setNum_Contrato(Long.parseLong(ArrayAux[3].equals(null) || ArrayAux[3].equals("") ? "0" : ArrayAux[3]));
					data.setCve_Estatus(Long.parseLong(ArrayAux[4].equals(null) || ArrayAux[4].equals("") ? "0" : ArrayAux[4]));
					data.setSdo_Actual(Double.parseDouble(ArrayAux[5].equals(null) || ArrayAux[5].equals("") ? "0" : ArrayAux[5]));
					data.setPrefmda(Long.parseLong(ArrayAux[6].equals(null) || ArrayAux[6].equals("") ? "0" : ArrayAux[6]));
					data.setCuentamda(Long.parseLong(ArrayAux[7].equals(null) || ArrayAux[7].equals("") ? "0" : ArrayAux[7]));
					listaRespuesta.add(data);
				}
			}
            ini = 2;
		}
		b.close();
		log.info("CuentasAlternasDTO content init  ::  " + listaRespuesta.size());

		try {
			cuentasAlternasJDBCRepository.TruncateTbCuentasAlternas();
		} catch (Exception e) {
			throw new GenericException("Error al limpiar la tabla cuentas alternas:: ",
					HttpStatus.NOT_FOUND.toString());
		}

		try {
			cuentasAlternasJDBCRepository.batchInsert(listaRespuesta, 500);
		} catch (Exception e) {
			throw new GenericException("Error al guardar en Respuestas Pronosticos :: ",
					HttpStatus.NOT_FOUND.toString());
		}

		return "Se importaron existosamente: " + listaRespuesta.size() + " registros de cuentas alternas.";
	}
	
	 private String validaNull(String cad){
         if (cad == null || cad.equals(""))
         {
             cad = "0";
         }
         return cad;
     }

	@Override
	public MensajeDTO genArchPFService(Integer mes1, Integer anio1, Integer dias1, Integer mes2, Integer anio2, Integer dias2,
			Integer mes3, Integer anio3, Integer dias3) throws GenericException, IOException, ParseException {
		MensajeDTO response = new MensajeDTO();
		Integer conteo=0;
		try {
			List<GenArcPFECyTCDTO> result = reintentosJDBCRepository.ConteoReintentosSemanalesPersonaFisica(mes1, anio1, dias1, mes2, anio2, dias2, mes3, anio3, dias3);
			conteo = result.get(0).getConteo() == null ? 0 : result.get(0).getConteo();
		} catch (Exception e) {
			throw new GenericException("Error al consultar tabla en genArchPFService :: ",
					HttpStatus.NOT_FOUND.toString());
		}
		response.setMensajeConfirm("Aviso");
		response.setMensajeInfo("Se generaran: " + conteo + " registros.");
		return response;
	}

	@Override
	public MensajeDTO genArchECService(Integer mes1, Integer anio1, Integer dias1, Integer mes2, Integer anio2,
			Integer dias2, Integer mes3, Integer anio3, Integer dias3)
			throws GenericException, IOException, ParseException {
		MensajeDTO response = new MensajeDTO();
		Integer conteo=0;
		try {
			List<GenArcPFECyTCDTO> result = reintentosJDBCRepository.ConteoReintentosSemanalesExtraContable(mes1, anio1, dias1, mes2, anio2, dias2, mes3, anio3, dias3);
			conteo = result.get(0).getConteo() == null ? 0 : result.get(0).getConteo();
		} catch (Exception e) {
			throw new GenericException("Error al consultar tabla en genArchPFService :: ",
					HttpStatus.NOT_FOUND.toString());
		}
		response.setMensajeConfirm("Aviso");
		response.setMensajeInfo("Se generaran: " + conteo + " registros.");
		return response;
	}

	@Override
	public MensajeDTO genArchTCService() throws GenericException, IOException, ParseException {
		MensajeDTO response = new MensajeDTO();
		Integer conteo=0;
		try {
			List<GenArcPFECyTCDTO> result = reintentosJDBCRepository.ConteoReintentosSemanalesTasaCero();
			conteo = result.get(0).getConteo() == null ? 0 : result.get(0).getConteo();
		} catch (Exception e) {
			throw new GenericException("Error al consultar tabla en genArchPFService :: ",
					HttpStatus.NOT_FOUND.toString());
		}
		response.setMensajeConfirm("Aviso");
		response.setMensajeInfo("Se generaran: " + conteo + " registros.");
		return response;
	}

	
	@Override
	public MensajeDTO BtnSave(Integer mes1, Integer anio1, Integer dias1, Integer mes2, Integer anio2,
			Integer dias2, Integer mes3, Integer anio3, Integer dias3, String operacion, Integer secuencial, Integer dias)
					throws GenericException, IOException, ParseException {
		MensajeDTO response = new MensajeDTO();
		if (operacion == "PF")
        {
			reintentosJDBCRepository.TruncateReintentosPerFis();
			List<ReintentosMaestroComiDTO> reintentoPer = reintentosJDBCRepository.ReintentosSemanalesPersonaFisicaSelect(mes1, anio1, mes2, anio2, mes3, anio3);
			reintentosJDBCRepository.ReintentosSemanalesPersonaFisicaInsert(reintentoPer, 500);
//            GenerarLayoutDeCargaReintentosSemanales(DateTime.Today, "PF", objDALCat.ObtenerListaProductos(), objCatSer.ObtenerListaServicios(), 1, dias1, mes1, anio1, dias2, mes2, anio2, dias3, mes3, anio3);
        }

        if (operacion == "EC")
        {
        	reintentosJDBCRepository.TruncateReintentosPerFis();
//            objDA.ReintentosSemanalesExtraContable(mes1, anio1, mes2, anio2, mes3, anio3);
//            GenerarLayoutDeCargaReintentosSemanales(DateTime.Today, "EC", objDALCat.ObtenerListaProductos(), objCatSer.ObtenerListaServicios(), 1, dias1, mes1, anio1, dias2, mes2, anio2, dias3, mes3, anio3);
        }

        if (operacion == "TC")
        {
//            string Fecha = String.Format("{0:yyyy-MM-dd}", DateTime.Today);
//            GenerarLayoutDeCargaReintentosSemanales(objDA.ReintentosSemanalesTasaCero(), DateTime.Today, "TC", objDALCat.ObtenerListaProductos(), objCatSer.ObtenerListaServicios(), 0, dias);
        }
		return null;
	}
	
	
	private void GenerarLayoutDeCargaReintentosSemanales(Date fecha_carga, String reintento, List<Cat_Clave_ProductoBEDTO> ListaProductos, List<Cat_Serv_Ondemand_ProBEDTO> listaCatServicios, int cuentaAlterna, int dia1, int mes1, int anio1, int dia2, int mes2, int anio2, int dia3, int mes3, int anio3, String txtSecu_Arch){
        
		String noEmisor = ConstantUtils.EMISOR_PRONOSTICOS;
        String refEmisor = ConstantUtils.REF_EMISOR_PERFIL;
        String ctaAbono = ConstantUtils.CTA_ABONO_PERFIL;
        StringBuilder strBuild = new StringBuilder();
        String strFecha = FormatUtils.formatDateSinEspacios(fecha_carga);
        System.out.println("fechaXX :: " + strFecha);
//        MySqlConnection connMySQL = null;

        try
        {
            List<Maestro_ComisionesBEDTO> Lista1 = new ArrayList<Maestro_ComisionesBEDTO>();
            List<Maestro_ComisionesBEDTO> Lista2 = new ArrayList<Maestro_ComisionesBEDTO>();
            List<Maestro_ComisionesBEDTO> Lista3 = new ArrayList<Maestro_ComisionesBEDTO>();

            //List<RebajaBE> ListaAbono = ListaCarga.Where(x => x.Movimiento == "ABO").ToList();
            String fileName = "";
            String outTxtFile = "";
            //calculamos los diferentes archivos que vamos a realizar
            List<Integer> listaArchivos = new ArrayList<Integer>();
            int countCuentaAlterna = 0;
            int numChequeraFija = 0;
            //int numBloqueos = 0;
            int sec_int = 2;
            int sec_arch = 0;
            sec_arch = Integer.parseInt(txtSecu_Arch); //se debe de mandar en el request el sec_arch***********************************
            List<Maestro_ComisionesBEDTO> ListaFinal = new ArrayList<Maestro_ComisionesBEDTO>();

            List<TbBloqueosBEDTO> ListaBloqueos = null;
            //List<PronosticosBE> ListaItemsBloqueados = null;
//            DAL.TbBloqueosDA objBloqueos = new TbBloqueosDA();++++++++++++++++++++++++++++++++++
            List<Maestro_ComisionesBEDTO> SublistaArchivos = new ArrayList<Maestro_ComisionesBEDTO>();
            List<CuentasAlternasDTO> ListaCuentasAlternas = new ArrayList<CuentasAlternasDTO>();
            List<CuentasAlternasDTO> ListaCuentAltEncontradas = new ArrayList<CuentasAlternasDTO>();
//            DAL.TbCuentasAlternasDA objCuenAlt = new TbCuentasAlternasDA();+++++++++++++++++++++++++++++++++
//            DAL.ReintentosDA objDA = new ReintentosDA();++++++++++++++++++++++++++++++++++++

            //region asignacionAlternas
            if (cuentaAlterna == 1)
            {
                if(reintento.equals("PF"))
                   countCuentaAlterna = Integer.parseInt(reintentosJDBCRepository.PlanchaAlternasReintentoPIF());

                if (reintento.equals("EC"))
                    countCuentaAlterna = Integer.parseInt(reintentosJDBCRepository.PlanchaAlternasReintentoextra());
                /*
                ListaCuentasAlternas = objCuenAlt.ObtenerCuentasAlternas();//Se obtinene cuentas alternas
                List<long> noclientes = ListaCuentasAlternas.Select(x => x.Num_Cliente).ToList();//Filtran numero s de clientes en lista de alternas
                List<Maestro_ComisionesBE> listaAlt = ListaCarga.Where(x => noclientes.Contains(x.no_cliente)).ToList();//Filtran de la carga los  clientes con alternas
                countCuentaAlterna = listaAlt.Count();
                ListaCarga.RemoveAll(x => listaAlt.Contains(x));//se eliminan los elementos con alternas
                
                foreach (Maestro_ComisionesBE item in listaAlt)
                {
                    if (ListaCuentasAlternas.Where(x => x.Num_Cliente == item.no_cliente).Count() > 0)
                    {
                        item.sucursal = ListaCuentasAlternas.Where(x => x.Num_Cliente == item.no_cliente).First().Prefmda.ToString();
                        item.cuenta = ListaCuentasAlternas.Where(x => x.Num_Cliente == item.no_cliente).First().Cuentamda.ToString();
                    }
                }
                //ListaCarga.AddRange(listaAlt);//Se agregan los elementos con alternas a la lista original
                 */
            }
            //endregion

            numChequeraFija = Integer.parseInt(reintentosJDBCRepository.PlanchaCheuqeraFija());

            List<Maestro_ComisionesBEDTO> ListaCarga = reintentosJDBCRepository.ReintentosSemanalesPersonaFisicaQuery();

            int numElementosOriginal = ListaCarga.size(), bloqueados = 0;
            numElementosOriginal = 0;

            //region bloqueos
            //Obteniendo lista de bloqueos
            ListaBloqueos = reintentosJDBCRepository.ObtenerListaBloqueos();
            List<Long> noclientesListBloq = null;  //ListaBloqueos.Select(x => x.no_cliente).ToList();
            for (TbBloqueosBEDTO bloqueosBE : ListaBloqueos) {
            	noclientesListBloq.add(bloqueosBE.no_cliente);
			}
            List<Maestro_ComisionesBEDTO> listaBloqueados = null;  //ListaCarga.Where(x => noclientesListBloq.Contains(x.no_cliente)).ToList();//Bloqueo por numero de Clientes
            for (Maestro_ComisionesBEDTO maestroCarga : ListaCarga) {
        		boolean existe = noclientesListBloq.contains(maestroCarga.no_cliente);
        		if (existe) {
        			listaBloqueados.add(maestroCarga);
        			System.out.println("El elemento SÍ existe en la lista");
        		} else {
        			System.out.println("El elemento no existe");
        		}
			}
            ListaCarga.removeAll(listaBloqueados);//Se eliminan los elementos bloqueados por numero de cliente
            for(Maestro_ComisionesBEDTO item : ListaCarga)
            {
            	for (TbBloqueosBEDTO bloqueos : ListaBloqueos) {
                    if (bloqueos.sucursal+"" == item.sucursal && bloqueos.cuenta+"" == item.cuenta) {
                        listaBloqueados.add(item);
                    }
				}
            }
            ListaCarga.removeAll(listaBloqueados);;//Se eliminan los elemenos bloqueados por sucursal y cuenta
            bloqueados = listaBloqueados.size();
            //endregion

            ListaFinal = ListaCarga;
            if(dia1 > 0){   
                //Lista1 = ListaFinal.Where(x => x.anio == anio1 && x.mes == mes1).ToList();
                for (Maestro_ComisionesBEDTO lis1 : ListaFinal) {
            		if (lis1.anio == anio1 && lis1.mes == mes1) {
            			Lista1.add(lis1);
                    }
    			}
                //Lista1 = Lista1.OrderBy(x => x.no_cliente).ToList();
                for (Maestro_ComisionesBEDTO item : Lista1) {
                    item.dias = dia1;
                    if (item.dias == 0){
                        item.dias = dia1;
                    }
                }
            }

            if (dia2 > 0)
            {
                //Lista2 = ListaFinal.Where(x => x.anio == anio2 && x.mes == mes2).ToList();
                for (Maestro_ComisionesBEDTO lis2 : ListaFinal) {
            		if (lis2.anio == anio2 && lis2.mes == mes2) {
            			Lista2.add(lis2);
                    }
    			}
                //Lista2 = Lista2.OrderBy(x => x.no_cliente).ToList();
                for (Maestro_ComisionesBEDTO item : Lista2){
                    item.dias = dia2;
                    if (item.dias == 0){
                        item.dias = dia2;
                    }
                }
            }
            if (dia3 > 0)
            {
                //Lista3 = ListaFinal.Where(x => x.anio == anio3 && x.mes == mes3).ToList();
            	for (Maestro_ComisionesBEDTO lis3 : ListaFinal) {
             		if (lis3.anio == anio3 && lis3.mes == mes3) {
             			Lista3.add(lis3);
                     }
     			}
            	//Lista3 = Lista3.OrderBy(x => x.no_cliente).ToList();
                for (Maestro_ComisionesBEDTO item : Lista3) {
                    item.dias = dia3;
                    if (item.dias == 0) {
                        item.dias = dia3;
                    }
                }
            }

            ListaFinal = new ArrayList<Maestro_ComisionesBEDTO>();

            if(dia1 >0) {
                numElementosOriginal = numElementosOriginal + numElementosOriginal;
                ListaFinal.addAll(Lista1);
            }
            if (dia2 > 0) { 
                numElementosOriginal = numElementosOriginal + numElementosOriginal;
                 ListaFinal.addAll(Lista2);
            }

            if (dia3 > 0) {
                numElementosOriginal = numElementosOriginal + numElementosOriginal;
                ListaFinal.addAll(Lista3);
            }

            /*if (reintento.Equals("EC"))
           {
               List<string> llaves = GetLlavesEvaluar();
               List<Maestro_ComisionesBE> listaFin = ListaFinal.Where(x => llaves.Contains(x.Llave)).ToList();
               ListaFinal = listaFin;
           }*/

            // reintento 
            for (Maestro_ComisionesBEDTO item : ListaFinal){
                //if (!reintento.Equals("PF"))
                //{
                //    if (sec_int == 65002)
                //    {
                //        sec_arch++; sec_int = 2;
                //    }
                //}
                if (sec_int == 65002)
                {
                    sec_arch++; sec_int = 2;
                    //txtSecu_Arch.Text = sec_arch.ToString();++++++++++++++++++++++++++++++++++++++++++++++++++
                }

                item.SEC_ARC = sec_arch;

                if (item.SEC_ARC == 0) { item.SEC_ARC = sec_arch; }

                item.SEC_INT = sec_int;
                if (item.SEC_INT == 0) { item.SEC_INT = sec_int; }
                sec_int++;
            }

            //ListaCarga = ListaCarga.OrderBy(x => x.mes).ThenBy(n => n.no_cliente).ToList();

            //listaArchivos = (from i in ListaFinal select i.SEC_ARC).Distinct().ToList();++++++++++++++++++++++
            Set<Integer> listaArchivo = new HashSet<>();
            /*listaFinal.removeIf(c -> !listaArchivo.add(c.getSEC_ARC()));
            for (Integer noArch : listaArchivo) {
                strBuild = new StringBuilder();

                fileName = "Protec_REINTENTO_" + reintento + "_" + noArch + "_" + strFecha;

                String ruta = Server.MapPath("~/ArchivosPronosticos/");

                string NamTxt = ruta + fileName;
                outTxtFile = string.Format(@"{0}.txt", NamTxt);
                String newLine = null;
                //crea el Archivo txt
                var stream = File.CreateText(outTxtFile);

                SublistaArchivos = ListaFinal.Where(x => x.SEC_ARC == noArch).OrderBy(x => x.SEC_INT).ToList();

                //region header
                //se reemplaza strBuild.Append() por newLine +=
                newLine = "01" + "0000001" + "01" + "002" + "E" + "3" + "0000000" + strFecha + "01" + "00";
                for (int i = 1; i <= 25; i++) { newLine += ' '; }
                //razón social, 40 espacios= nombre+10
                newLine += "BANCO NACIONAL DE MEXICO, S.A."; for (int i = 1; i <= 10; i++) { newLine += ' '; }
                //rfc + 6 espacios 18 espacios
                newLine += "BNM840515VB1"; for (int i = 1; i <= 6; i++) { newLine += ' '; }
                //uso futuro
                for (int i = 1; i <= 182; i++) { newLine += ' '; }
                //emisor
                newLine += "000000" + noEmisor;
                //secuencia de archivo, el de la cabecera siempre empieza en 01 y va progresando por archivo
                newLine += noArch.ToString("00");

                for (int i = 1; i <= 226; i++) { newLine += ' '; }

                stream.WriteLine(newLine);
                #endregion

                string strImporte = string.Empty, strIVAa = string.Empty;
                //int DIAS = dias;



                #region detalle
                foreach (Maestro_ComisionesBE item in SublistaArchivos)
                {
                    //Bloque 1
                    newLine = "02" + item.SEC_INT.ToString("0000000") + "01" + "01";
                    //importe de operacion
                    strImporte = item.m_comision.ToString().Split('.')[0].ToString();
                    if (item.m_comision.ToString().Contains("."))
                    {
                        int contdecim = item.m_comision.ToString().Split('.')[1].Length;
                        if (contdecim == 1) { strImporte += item.m_comision.ToString().Split('.')[1].Substring(0, 1) + "0"; }
                        if (contdecim > 1) { strImporte += item.m_comision.ToString().Split('.')[1].Substring(0, 2); }
                    }
                    if (!item.m_comision.ToString().Contains(".")) { strImporte += "00"; }

                    newLine += double.Parse(strImporte).ToString("000000000000000");

                    //periodo de proteccion
                    newLine += strFecha + item.dias.ToString("000");

                    //numero de cliente
                    newLine += item.no_cliente.ToString("000000000000");
                    for (int i = 1; i <= 9; i++) { newLine += ' '; }

                    //Bloque 2
                    newLine += "52";
                    string aux = item.cuenta;

                    if (aux.Contains("   "))
                    {
                        string[] sucuen = item.cuenta.Split(' ');
                        int i = 0, sucursal = 0;
                        Int64 cuenta = 0;
                        foreach (string cad in sucuen)
                        {
                            i++;
                            if (i == 1)
                            {
                                sucursal = Convert.ToInt32(cad);
                            }
                            else
                            {
                                if (i > 1 && !cad.Equals(""))
                                {
                                    cuenta = Convert.ToInt64(cad);
                                }
                            }
                        }
                        newLine += "00000000" + "002" + "01" + "000000000" + Convert.ToInt64(sucursal).ToString("0000") + Convert.ToInt64(cuenta).ToString("0000000");  //item.CTA_ORIGEN.Replace("/" , "" ) );
                    }
                    else
                    {
                        newLine += "00000000" + "002" + "01" + "000000000" + Convert.ToInt64(item.sucursal).ToString("0000") + Convert.ToInt64(item.cuenta).ToString("0000000");  //item.CTA_ORIGEN.Replace("/" , "" ) );
                    }

                    //Bloque 3
                    //nombre de cliente
                    for (int i = 1; i <= 40; i++) { newLine += ' '; }
                    //referencia de emisor
                    newLine += "0000000000000000" + refEmisor;
                    //20 uso futuro, 40 nombre de titular
                    for (int i = 1; i <= 60; i++) { newLine += ' '; }

                    //Bloque 4
                    //saldo de proteccion
                    newLine += "000000000000000";
                    //ref numerica del emisor
                    newLine += (item.SEC_INT - 1).ToString("0000000");

                    string ClaveServicio = string.Empty;

                    if (reintento.Equals("TC"))
                    {
                        newLine += "TASA CERO";
                        newLine += "    ";
                        for (int i = 1; i <= 27; i++) { newLine += ' '; }
                    }
                    else
                    {
                        string servicio = string.Empty;
                        string auxiliar = item.com_ec.Trim().ToString();

                        if (auxiliar.Contains("DOMI") || auxiliar.Contains("COBU"))
                        {
                            ClaveServicio = item.com_ec.Trim().ToString();
                            ClaveServicio = Regex.Replace(ClaveServicio, @"[\d-]", string.Empty);
                        }
                        else
                        {
                            List<Cat_Serv_Ondemand_ProBE> listaCatServiciosAux = new List<Cat_Serv_Ondemand_ProBE>();
                            listaCatServiciosAux = listaCatServicios.Where(x => x.id_servicio == item.id_servicio && x.id_ondemand == item.id_ondemand).ToList();
                            foreach (Cat_Serv_Ondemand_ProBE itemcatser in listaCatServiciosAux)
                            {
                                servicio = itemcatser.servicio;
                            }
                            ClaveServicio = RetornaClaveServicio(servicio.ToString(), ListaProductos);
                        }

                        if(auxiliar.Contains("DOM"))
                        {
                            ClaveServicio = auxiliar;
                        } 
                        newLine += ClaveServicio;//Servicio
                        

                        //newLine += item.com_ec.Trim().ToString();
                        if (!auxiliar.Contains("DOM"))
                        {
                            if (!auxiliar.Contains("DOMI") || !auxiliar.Contains("COBU"))
                            {
                                newLine += item.mes.ToString("00");
                                newLine += RetornaAnio(item.anio.ToString());
                            }
                        }

                        //newLine += item.mes.ToString("00");
                        //newLine += RetornaAnio(item.anio.ToString());

                        string cad = ClaveServicio + item.mes.ToString("00") + RetornaAnio(item.anio.ToString());

                        if (auxiliar.Contains("DOM"))
                            cad = ClaveServicio;

                        int tam = 40 - cad.Length;
                        for (int i = 1; i <= tam; i++) { newLine += ' '; }
                    }

                    //for (int i = 1; i <= 30; i++) { newLine += ' '; }

                    //Bloque 5
                    //motivo de rechazo
                    newLine += "0000";
                    //uso futuro
                    for (int i = 1; i <= 7; i++) { newLine += ' '; }

                    //Bloque 6
                    //no de proteccion
                    newLine += "000000000000";
                    //uso futuro
                    for (int i = 1; i <= 11; i++) { newLine += ' '; }

                    //Bloque 7
                    //fecha inicio proteccion
                    newLine += strFecha;
                    //Secuencial del archivo
                    newLine += noArch.ToString("00");

                    // ref1 20+ ref2 20+ ref3 20+ desc rechazo 40
                    for (int i = 1; i <= 100; i++) { newLine += ' '; }

                    //Bloque 8
                    //contrato del cliente
                    newLine += "000000000000";
                    //numero de concepto 
                    newLine += refEmisor;
                    //subconcepto lleva un 01 no más por que si (si es en protec de cobros)
                    newLine += "01";
                    //Número de mes de cobro 
                    newLine += fecha_carga.Year.ToString("0000") + fecha_carga.Month.ToString("00");

                    //secuencial del registro original
                    newLine += (item.SEC_INT - 1).ToString("0000000");
                    //cta concentradora 
                    newLine += "00000" + ctaAbono;


                    //tipo de cta de abono
                    newLine += "01";

                    //concepto de cobro y subconcepto de cobro, el subconcepto es 01 no más x q si
                    newLine += "5501" + "01";

                    //importe 15 ceros del IVA
                    strIVAa = item.m_iva.ToString().Split('.')[0].ToString();
                    //strIVAa = item.Ivaa.ToString().Split('.')[0].ToString();
//                    strIVAa = item.m_iva.ToString();
//                    if (strIVAa.Contains("."))
//                    {
//                        strIVAa = strIVAa.Replace(".", "");
//                    }

                    if (item.m_iva.ToString().Contains("."))
                    {
                        int contdecim = item.m_iva.ToString().Split('.')[1].Length;
                        if (contdecim == 1) { strIVAa += item.m_iva.ToString().Split('.')[1].Substring(0, 1) + "0"; }
                        if (contdecim > 1) { strIVAa += item.m_iva.ToString().Split('.')[1].Substring(0, 2); }
                    }
                    if (!item.m_iva.ToString().Contains("."))
                    {
                        strIVAa += "00";
                    }

                    
                    newLine += double.Parse( strIVAa).ToString("000000000000000");

                    //uso futuro 49 espacios
                    for (int i = 1; i <= 49; i++) { newLine += ' '; }

                    stream.WriteLine(newLine);
                }
                #endregion

                #region footer
                //tipo de registro
                newLine = "09";
                //secuencial del ultimo registro como 8001
                newLine += (1 + SublistaArchivos.Max(x => x.SEC_INT)).ToString("0000000");
                //codigo de operacion
                newLine += "00";
                //numero de bloque
                newLine += "0000000";
                //numero de protecciones
                newLine += SublistaArchivos.Count.ToString("0000000");
                //TOTAL DE IMPORTE POR OPERACIONES
                double total = SublistaArchivos.Sum(x => x.m_total);
                total = Math.Round(total, 2);
                strImporte = total.ToString().Split('.')[0].ToString();
                if (total.ToString().Contains("."))
                {
                    int contdecim = total.ToString().Split('.')[1].Length;
                    if (contdecim == 1) { strImporte += total.ToString().Split('.')[1].Substring(0, 1) + "0"; }
                    if (contdecim > 1) { strImporte += total.ToString().Split('.')[1].Substring(0, 2); }
                }
                if (!total.ToString().Contains(".")) { strImporte += "00"; }
                newLine += double.Parse(strImporte).ToString("000000000000000000");

                //uso futuro
                for (int i = 1; i <= 17; i++) { newLine += ' '; }
                //uso futuro
                for (int i = 1; i <= 240; i++) { newLine += ' '; }
                //uso futuro
                for (int i = 1; i <= 240; i++) { newLine += ' '; }

                stream.WriteLine(newLine);
                #endregion


                stream.Close();
                newLine = null;
                stream = null;
                strBuild = null;
            }

            using (System.IO.StreamWriter fileR = new System.IO.StreamWriter(Server.MapPath("~/ListaDatosReintentos.txt")))
            {
                foreach (Maestro_ComisionesBE item in ListaFinal)
                {
                    fileR.WriteLine(
                                    item.Llave + "\t" +
                                    item.SEC_ARC + "\t" +
                                    item.SEC_INT + "\t" +
                                    item.SEC_ARC.ToString() + "_" + item.SEC_INT.ToString()
                    );
                }
                fileR.Close();
            }

            connMySQL = new MySqlConnection(ConfigurationManager.ConnectionStrings["DefaultMySQLMIS"].ConnectionString);
            connMySQL.Open();
            var bl = new MySqlBulkLoader(connMySQL);
            bl.TableName = "tb_reintentos";
            bl.FieldTerminator = "\t";
            bl.LineTerminator = "\r\n";
            bl.FileName = Server.MapPath("~/ListaDatosReintentos.txt");
            bl.NumberOfLinesToSkip = 0;

            var inserted = bl.Load();
            connMySQL.Close();

            
            txtSecu_Arch.Text = (sec_arch++).ToString();
            mpMsg.Show("Aviso", "El archivo se generó exitosamente ", "Total: " + numElementosOriginal.ToString() + " con " + countCuentaAlterna.ToString() + " cuentas alternas y " + bloqueados.ToString() + " bloqueos, Chequera Fija: " + numChequeraFija.ToString(), 1);
        */}
        catch (Exception ex)
        {
           // throw;
        }
        finally
        {
           // connMySQL = null;
        }

    }

}
