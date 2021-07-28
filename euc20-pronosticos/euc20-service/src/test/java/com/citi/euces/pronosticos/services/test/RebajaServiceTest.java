/**
 *
 */
package com.citi.euces.pronosticos.services.test;

import com.citi.euces.pronosticos.configuration.PersistanceContext;
import com.citi.euces.pronosticos.configuration.SpringContext;
import com.citi.euces.pronosticos.entities.MaestroDeComisiones;
import com.citi.euces.pronosticos.entities.MaestroDeComisionesId;
import com.citi.euces.pronosticos.entities.MaestroDeComisionesView;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.infra.dto.ReporteCuadreDTO;
import com.citi.euces.pronosticos.infra.dto.ReporteRebajaDTO;
import com.citi.euces.pronosticos.infra.dto.ReporteRebajaPageDTO;
import com.citi.euces.pronosticos.infra.utils.FormatUtils;
import com.citi.euces.pronosticos.repositories.*;
import com.citi.euces.pronosticos.services.RebajasServiceImp;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {PersistanceContext.class, SpringContext.class})
public class RebajaServiceTest {
    
    @InjectMocks
    private RebajasServiceImp rebajasService;
    @Mock
    private SpRebajaMaestroDeComusionesRepository spRebajaMaestroDeComusionesRepository;
    @Mock
    private RebNumProtectJDBCRepository rebNumProtectJDBCRepository;
    @Mock
    private RebajaPronosticoJDBCRepository rebajaPronosticoJDBCRepository;
    @Mock
    private MaestroDeComisionesRepository maestroDeComisionesRepository;
    @Mock
    private MaestroDeComisionesViewRepository maestroDeComisionesViewRepository;
    @Mock
    private CatCausaRechazoRepository catCausaRechazoRepository;
    @Mock
    private CatServiciosPronosticoJDBCRepository catServiciosPronosticoRepository;
    @Mock
    private CuentasContablesJDBCRepository cuentasContablesJDBCRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private Query query2;
    
    
    @Test // /aplicarRebajaloadFile
    public void testAplicarRebajaloadFile() {
        try {
            String fechaContable = "01/06/2021";
            String fechaMovimiento = "01/05/2021";
            Double sumaImporte = 4997.2;
            Integer total = 59;
            Integer contentInint = 32;
            Mockito.when(rebNumProtectJDBCRepository.batchInsert(null, 0)).thenReturn(null);
            MensajeDTO actual = rebajasService.aplicarRebajaloadFile(ConstantTest.APLICAR_REBAJA_FILE, fechaContable, fechaMovimiento);
            MensajeDTO expected = mensajeDTODummy("Se actualizará con fecha movimiento: " + fechaMovimiento
                                                          + " y fecha reg. contable: " + fechaContable, "Abono Total: " + sumaImporte.toString()
                                                                                                                + " Elementos totales a cargar:" + total + " de un total de:" + contentInint + " elementos.");
            assertEquals(expected.getMensajeInfo(), actual.getMensajeInfo());
        } catch (Exception e) {
            fail("Error Test");
        }
        
    }
    
    @Test ///aplicarRebaja
    public void testAplicarRebaja() {
        try {
            Integer expectedR = 0;
            Mockito.when(spRebajaMaestroDeComusionesRepository.spRebajaMaestroComisiones()).thenReturn(expectedR);
            MensajeDTO actual = rebajasService.aplicarRebaja();
            MensajeDTO expected = mensajeDTODummy("Confirmando, Actualizando maestro de comisiones: ".concat("0")
                                                          .concat(" rebajados"), "");
            assertEquals(expected.getMensajeInfo(), actual.getMensajeInfo());
        } catch (Exception e) {
            fail("Error Test");
        }
    }
    
    
    @Test ///reporteCuadre
    public void testReporteCuadre() {
        try {
            Integer mes1 = FormatUtils.obtenerMes(3);
            Integer mes2 = FormatUtils.obtenerMes(2);
            Integer mes3 = FormatUtils.obtenerMes(1);
            Integer anio1 = FormatUtils.obtenerYear(3);
            Integer anio2 = FormatUtils.obtenerYear(2);
            Integer anio3 = FormatUtils.obtenerYear(1);
            LocalDate now = LocalDate.now();
            Integer mesActual = now.getYear();
            List<ReporteCuadreDTO> expectedRepCuadre1 = new ArrayList<>();
            List<ReporteCuadreDTO> expectedRepCuadre2 = new ArrayList<>();
            List<ReporteCuadreDTO> expectedRepCuadre3 = new ArrayList<>();
            List<ReporteCuadreDTO> expectedRepCuadre4 = new ArrayList<>();
            
            List<ReporteCuadreDTO> expected = expectedReporteCuadre(expectedRepCuadre1, expectedRepCuadre2, expectedRepCuadre3, expectedRepCuadre4);
            Mockito.when(maestroDeComisionesRepository.getFunctionRepCuadre1(mes1, mes2, mes3, anio1)).thenReturn(expectedRepCuadre1);
            Mockito.when(maestroDeComisionesRepository.getFunctionRepCuadre2(mes1, mes2, mes3, anio1, anio2, anio3)).thenReturn(expectedRepCuadre2);
            Mockito.when(maestroDeComisionesRepository.getFunctionRepCuadre3(mesActual)).thenReturn(expectedRepCuadre3);
            Mockito.when(maestroDeComisionesRepository.getFunctionRepCuadre4(mesActual)).thenReturn(expectedRepCuadre4);
            
            List<ReporteCuadreDTO> actual = rebajasService.reporteCuadre();
            assertEquals(expected.size(), actual.size());
        } catch (Exception e) {
            fail("Error Test");
        }
    }
    
    @Test ///reporteRebaja
    public void testReporteRebaja() {
        try {
            String fecha = "01/06/2021";
            Pageable pageable = PageRequest.of(0, 50);
            Page<MaestroDeComisiones> data = Mockito.mock(Page.class);
            Mockito.when(maestroDeComisionesRepository.findByFechaMovimeiento(fecha, pageable)).thenReturn(data);
            ReporteRebajaDTO actual = rebajasService.reporteRebaja(fecha, 0);
            ReporteRebajaDTO expected = expectedReporteRebaja();
            assertEquals(new Integer(actual.getReporteRebajaPageDTO().getNumber()), new Integer(expected.getReporteRebajaPageDTO().getNumber()));
            assertEquals(new Integer(actual.getReporteRebajaPageDTO().getPageable().getPageSize()), new Integer(expected.getReporteRebajaPageDTO().getPageable().getPageSize()));
            
        } catch (Exception e) {
            fail("Error Test");
        }
    }
    
    @Test ///reporteRebajaSearch
    public void testReporteRebajaSearch() {
        try {
            String fecha = "01/06/2021";
            Pageable pageable = PageRequest.of(0, 50);
            String search = "Buscar";
            Page<MaestroDeComisionesView> data = Mockito.mock(Page.class);
            Mockito.when(maestroDeComisionesViewRepository.searchData(fecha, search, pageable)).thenReturn(data);
            ReporteRebajaDTO actual = rebajasService.reporteRebajaSearch(fecha, 0, search);
            ReporteRebajaDTO expected = expectedReporteRebaja();
            assertEquals(new Integer(actual.getReporteRebajaPageDTO().getNumber()), new Integer(expected.getReporteRebajaPageDTO().getNumber()));
            assertEquals(new Integer(actual.getReporteRebajaPageDTO().getPageable().getPageSize()), new Integer(expected.getReporteRebajaPageDTO().getPageable().getPageSize()));
            
        } catch (Exception e) {
            fail("Error Test");
        }
        
    }
    
    @Test ///reporteRebajaFile
    public void testReporteRebajaFileEmpty() {
        String fecha = "01/05/21";
        try {
            List<MaestroDeComisiones> data = new ArrayList<>();
            Mockito.when(maestroDeComisionesRepository.findByAllFechaMovimeiento(fecha)).thenReturn(data);
            ReporteRebajaDTO actual = rebajasService.reporteRebajaFile(fecha);
            
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals(e.getMessage().toString(), "No hay registros que coincidan con fecha Movimiento   :: " + fecha);
        }
    }
    
    @Test ///reporteRebajaFile
    public void testReporteRebajaFile() {
        String fecha = "01/05/21";
        try {
            List<MaestroDeComisiones> listData = new ArrayList<>();
            MaestroDeComisiones data = new MaestroDeComisiones();
            data.setContrato("2");
            data.setFechaMovimiento(FormatUtils.stringToDate(fecha));
            data.setFechaIngreso(new Date());
            MaestroDeComisionesId id = new MaestroDeComisionesId();
            id.setLlave(1L);
            id.setNoCliente(1L);
            id.setmTotal(222.3);
            data.setId(id);
            data.setIdCausaRechazo(1);
            listData.add(data);
            Mockito.when(maestroDeComisionesRepository.findByAllFechaMovimeiento(fecha)).thenReturn(listData);
            ReporteRebajaDTO actual = rebajasService.reporteRebajaFile(fecha);
            assert (!actual.getFile().isEmpty());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error Test");
        }
        
    }
    
    @Test ///addMora
    public void testAddMora() {
        String fecha = "01/06/2021";
        try {
            int response = 10;
            Mockito.when(maestroDeComisionesRepository.updateCatalogadaGc(2021, 6)).thenReturn(response);
            MensajeDTO actual = rebajasService.addMora(fecha);
            MensajeDTO expected = mensajeDTODummy("", "Confirmando, Actualizando maestro de comisiones: " + 10 + " rebajados");
            assertEquals (actual.getMensajeConfirm(), expected.getMensajeConfirm());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error Test");
        }
        
    }
    
    @Ignore
    @Test ///reporteMora
    public void testReporteMora() {
        String fecha = "01/06/2021";
        try {
            String query  = "SELECT mc from PPC_MIS_MAESTRO_COMISIONES mc ";
    
            List<Object[]> first = new ArrayList<>();
            first.add(new Object[]{"test","test","test"});
            Mockito.when(entityManager.createNativeQuery(Mockito.any(String.class)))
                    .thenReturn(query2);
            Mockito.when(query2.getResultList()).thenReturn(first);
            ReporteRebajaDTO actual = rebajasService.reporteMoraFile(fecha);
            assertTrue("OK", !actual.getFile().isEmpty());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error Test");
        }
        
    }
    
    
    private ReporteRebajaDTO expectedReporteRebajaSearch() {
        List<ReporteRebajaPageDTO> expectedList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 50);
        Page<ReporteRebajaPageDTO> pagedTasks = new PageImpl<>(expectedList, pageable, 1);
        ReporteRebajaDTO response = new ReporteRebajaDTO();
        response.setReporteRebajaPageDTO(pagedTasks);
        return response;
        
    }
    
    private ReporteRebajaDTO expectedReporteRebaja() {
        List<ReporteRebajaPageDTO> expectedList = new ArrayList<>();
        String file = "";
        for (Integer i = 0; i <= 5; i++) {
            ReporteRebajaPageDTO mc = new ReporteRebajaPageDTO();
            mc.setNumeroCliente(i.toString());
            mc.setContrato("Contraro" + i);
            expectedList.add(mc);
            if (i > 6000) {
                file = "FileBase64";
            }
        }
        Pageable pageable = PageRequest.of(0, 50);
        Page<ReporteRebajaPageDTO> pagedTasks = new PageImpl<>(expectedList, pageable, 1);
        ReporteRebajaDTO response = new ReporteRebajaDTO();
        response.setReporteRebajaPageDTO(pagedTasks);
        response.setFile(file);
        return response;
        
    }
    
    private List<ReporteCuadreDTO> expectedReporteCuadre(List<ReporteCuadreDTO> list1, List<ReporteCuadreDTO> list2, List<ReporteCuadreDTO> list3, List<ReporteCuadreDTO> list4) {
        List<ReporteCuadreDTO> expected = new ArrayList<>();
        expected.addAll(list1);
        expected.addAll(list2);
        expected.addAll(list3);
        expected.addAll(list4);
        return expected;
    }
    
    private MensajeDTO mensajeDTODummy(String mensajeInfo, String mensajeConfirm) {
        return new MensajeDTO(mensajeInfo, mensajeConfirm);
    }
    
    
}
