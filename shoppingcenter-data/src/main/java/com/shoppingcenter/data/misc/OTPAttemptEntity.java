package com.shoppingcenter.data.misc;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "OTPAttemptEntity")
@Table(name = Constants.TABLE_PREFIX + "otp_attempt")
public class OTPAttemptEntity implements Serializable {

    @EmbeddedId
    private OTPAttemptEntity.ID id;

    private int attempt;

    public OTPAttemptEntity() {
        this.id = new ID();
    }

    @PrePersist
    private void prePersist() {
        this.id.setDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Getter
    @Setter
    @Embeddable
    public static class ID implements Serializable {

        private String date;

        private String phone;

        public ID() {
        }

        public ID(String date, String phone) {
            this.date = date;
            this.phone = phone;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((date == null) ? 0 : date.hashCode());
            result = prime * result + ((phone == null) ? 0 : phone.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ID other = (ID) obj;
            if (date == null) {
                if (other.date != null)
                    return false;
            } else if (!date.equals(other.date))
                return false;
            if (phone == null) {
                if (other.phone != null)
                    return false;
            } else if (!phone.equals(other.phone))
                return false;
            return true;
        }

    }
}
