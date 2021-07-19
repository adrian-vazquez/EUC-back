/**
 *
 */
package com.citi.euces.pronosticos.services.test;

import com.citi.euces.pronosticos.configuration.PersistanceContext;
import com.citi.euces.pronosticos.configuration.SpringContext;
import com.citi.euces.pronosticos.infra.dto.MensajeDTO;
import com.citi.euces.pronosticos.repositories.SpRebajaMaestroDeComusionesRepository;
import com.citi.euces.pronosticos.services.RebajasServiceImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {PersistanceContext.class, SpringContext.class})
public class RebajaServiceTest {

    @InjectMocks
    private RebajasServiceImp rebajasService;
    @Mock
    private SpRebajaMaestroDeComusionesRepository spRebajaMaestroDeComusionesRepository;

    @Test
    public void testFindAll() {
        try {
            Integer expectedR  = 0;
            Mockito.when(spRebajaMaestroDeComusionesRepository.spRebajaMaestroComisiones()).thenReturn(expectedR);
            MensajeDTO actual = rebajasService.aplicarRebaja();
            MensajeDTO expected = mensajeDTODummy();
            assertEquals(expected.getMensajeInfo(), actual.getMensajeInfo());
        } catch (Exception e) {
            fail("Prueba incorrecta.");
        }

    }

    private MensajeDTO mensajeDTODummy() {
        return new MensajeDTO("Confirmando, Actualizando maestro de comisiones: ".concat("0").concat(" rebajados"), "OK");
    }

}
