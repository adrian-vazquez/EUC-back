package com.citi.euces.pronosticos.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TruncateTablesCobuRepository {
	private final JdbcTemplate truncate;

    public TruncateTablesCobuRepository(JdbcTemplate truncate) {
        this.truncate = truncate;
    }

    public void truncateCifrasControl() {
        String query = "Truncate table PPC_PCB_CIFRAS_CONTROL";
        truncate.execute(query);
    }

    public void truncateCtasVirtualesGpos() {
        String query = "Truncate table PPC_PCB_CTAS_VIRTUALES_GPOS";
        truncate.execute(query);
    }
    
    public void truncateCtasVirtuales() {
        String query = "Truncate table PPC_PCB_CTAS_VIRTUALES";
        truncate.execute(query);
    }
    
    public void truncateCtosUnico() {
        String query = "Truncate table PPC_PCB_CTOS_UNICOS";
        truncate.execute(query);
    }
    
    public void truncateLayoutBe() {
        String query = "Truncate table PPC_PCB_LAYOUT_BE";
        truncate.execute(query);
    }
    
    public void truncateLayoutMens() {
        String query = "Truncate table PPC_PCB_LAYOUT_MENS";
        truncate.execute(query);
    }
    
    public void truncateLayoutVent() {
        String query = "Truncate table PPC_PCB_LAYOUT_VENT";
        truncate.execute(query);
    }
    
    public void truncateProcesado() {
        String query = "Truncate table PPC_PCB_PROCESADO";
        truncate.execute(query);
    }
    
    public void truncateQueryCtosAgrupado() {
        String query = "Truncate table PPC_PCB_QUERY_CTOS_AGRUPADO";
        truncate.execute(query);
    }
    
    public void truncateQueryCtosCobu() {
        String query = "Truncate table PPC_PCB_QUERY_CTOS_COBU";
        truncate.execute(query);
    }
    
    public void truncateQueryCtosDuplicados() {
        String query = "Truncate table PPC_PCB_QUERY_CTOS_DUPLICADOS";
        truncate.execute(query);
    }
    
    public void truncateTarifas() {
        String query = "Truncate table PPC_PCB_TARIFAS";
        truncate.execute(query);
    }
    
    public void truncateTxnsImporte() {
        String query = "Truncate table PPC_PCB_TXNS_IMPORTE";
        truncate.execute(query);
    }
    
    public void trucateTxnsXTipo() {
        String query = "Truncate table PPC_PCB_TXNS_X_TIPO";
        truncate.execute(query);
    }
    
    public void truncateCtasVirt() {
        String query = "Truncate table PPC_PCB_TXS_CTAS_VIRT";
        truncate.execute(query);
    }
}
