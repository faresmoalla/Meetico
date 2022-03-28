package tn.esprit.meetico.entity;

import java.util.Objects;

import com.twilio.type.Endpoint;


public class PhoneNumber implements Endpoint {
    private final String rawNumber;

    public PhoneNumber(String number) {
        this.rawNumber = number;
    }

    @Override
    public String getEndpoint() {
        return this.rawNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhoneNumber other = (PhoneNumber) o;
        return Objects.equals(this.rawNumber, other.rawNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.rawNumber);
    }

    @Override
    public String toString() {
        return this.rawNumber;
    }
}