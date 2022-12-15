package bpp.entity;

import bpp.util.Country;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gotika", schema = "petrol_station")
public class GotikaPriceEntity extends BaseEntity {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Enumerated(value = EnumType.STRING)
    @Column
    private Country country;
    @Column
    private BigDecimal petrol;
    @Column
    private String petrolBestPriceAddress;
    @Column
    private BigDecimal diesel;
    @Column
    private String dieselBestPriceAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GotikaPriceEntity that = (GotikaPriceEntity) o;
        return id.equals(that.id) && petrol.equals(that.petrol) &&
                petrolBestPriceAddress.equals(that.petrolBestPriceAddress) && diesel.equals(that.diesel)
                && dieselBestPriceAddress.equals(that.dieselBestPriceAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, petrol, petrolBestPriceAddress, diesel, dieselBestPriceAddress);
    }
}
