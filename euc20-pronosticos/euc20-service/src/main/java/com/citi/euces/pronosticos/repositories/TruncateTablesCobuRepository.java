package com.citi.euces.pronosticos.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TruncateTablesCobuRepository {
	private final JdbcTemplate delete;

    public TruncateTablesCobuRepository(JdbcTemplate delete) {
        this.delete = delete;
    }

    public void deleteCifrasControl() {
        String query = "DELETE FROM PPC_PCB_CIFRAS_CONTROL";
        delete.execute(query);
    }

    public void deleteCtasVirtualesGpos() {
        String query = "DELETE FROM PPC_PCB_CTAS_VIRTUALES_GPOS";
        delete.execute(query);
    }
    
    public void deleteCtasVirtuales() {
        String query = "DELETE FROM PPC_PCB_CTAS_VIRTUALES";
        delete.execute(query);
    }
    
    public void deleteCtosUnico() {
        String query = "DELETE FROM PPC_PCB_CTOS_UNICOS";
        delete.execute(query);
    }
    
    public void deleteLayoutBe() {
        String query = "DELETE FROM PPC_PCB_LAYOUT_BE";
        delete.execute(query);
    }
    
    public void deleteLayoutMens() {
        String query = "DELETE FROM PPC_PCB_LAYOUT_MENS";
        delete.execute(query);
    }
    
    public void deleteLayoutVent() {
        String query = "DELETE FROM PPC_PCB_LAYOUT_VENT";
        delete.execute(query);
    }
    
    public void deleteProcesado() {
        String query = "DELETE FROM PPC_PCB_PROCESADO";
        delete.execute(query);
    }
    
    public void deleteQueryCtosAgrupado() {
        String query = "DELETE FROM PPC_PCB_QUERY_CTOS_AGRUPADO";
        delete.execute(query);
    }
    
    public void deleteQueryCtosCobu() {
        String query = "DELETE FROM PPC_PCB_QUERY_CTOS_COBU";
        delete.execute(query);
    }
    
    public void deleteQueryCtosDuplicados() {
        String query = "DELETE FROM PPC_PCB_QUERY_CTOS_DUPLICADOS";
        delete.execute(query);
    }
    
    public void deleteTarifas() {
        String query = "DELETE FROM PPC_PCB_TARIFAS";
        delete.execute(query);
    }
    
    public void deleteTxnsImporte() {
        String query = "DELETE FROM PPC_PCB_TXNS_IMPORTE";
        delete.execute(query);
    }
    
    public void deleteTxnsXTipo() {
        String query = "DELETE FROM PPC_PCB_TXNS_X_TIPO";
        delete.execute(query);
    }
    
    public void deleteCtasVirt() {
        String query = "DELETE FROM PPC_PCB_TXS_CTAS_VIRT";
        delete.execute(query);
    }
}
