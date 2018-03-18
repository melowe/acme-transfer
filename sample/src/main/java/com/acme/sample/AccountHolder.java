
package com.acme.sample;

import java.util.Objects;

public class AccountHolder {

    private final String id;

    public AccountHolder(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final AccountHolder other = (AccountHolder) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "AccountHolder{" + "id=" + id + '}';
    }

}
