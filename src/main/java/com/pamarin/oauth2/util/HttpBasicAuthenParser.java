/*
 * Copy right 2017 Pamarin.com
 */
package com.pamarin.oauth2.util;

import java.util.Objects;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/09/26
 */
public interface HttpBasicAuthenParser {

    Output parse(String authorization);

    public static class Output {

        private final String username;

        private final String password;

        public Output(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 59 * hash + Objects.hashCode(this.username);
            hash = 59 * hash + Objects.hashCode(this.password);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Output other = (Output) obj;
            if (!Objects.equals(this.username, other.username)) {
                return false;
            }
            if (!Objects.equals(this.password, other.password)) {
                return false;
            }
            return true;
        }

    }

}
