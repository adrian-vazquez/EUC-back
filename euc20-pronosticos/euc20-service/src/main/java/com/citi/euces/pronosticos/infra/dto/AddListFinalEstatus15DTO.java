package com.citi.euces.pronosticos.infra.dto;

import java.io.Serializable;

public class AddListFinalEstatus15DTO implements Serializable {
        private String llave;
        private Integer secArch;
        private Integer secInt;
        private String keyUp;

        public String getLlave() {
            return llave;
        }

        public void setLlave(String llave) {
            this.llave = llave;
        }

        public Integer getSecArch() {
            return secArch;
        }

        public void setSecArch(Integer secArch) {
            this.secArch = secArch;
        }

        public Integer getSecInt() {
            return secInt;
        }

        public void setSecInt(Integer secInt) {
            this.secInt = secInt;
        }

        public String getKeyUp() {
            return keyUp;
        }

        public void setKeyUp(String keyUp) {
            this.keyUp = keyUp;
        }
    }

