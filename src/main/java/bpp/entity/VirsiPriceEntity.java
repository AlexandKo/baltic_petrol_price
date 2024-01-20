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
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "virsi", schema = "petrol_station")
public class VirsiPriceEntity extends BaseEntity {
    @Id
    @UuidGenerator
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
    private BigDecimal petrolPro;
    @Column
    private String petrolProBestPriceAddress;
    @Column
    private BigDecimal diesel;
    @Column
    private String dieselBestPriceAddress;
    @Column
    private BigDecimal gas;
    @Column
    private String gasBestPriceAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VirsiPriceEntity that = (VirsiPriceEntity) o;
        return id.equals(that.id) && petrol.equals(that.petrol) &&
                petrolBestPriceAddress.equals(that.petrolBestPriceAddress) && petrolPro.equals(that.petrolPro)
                && petrolProBestPriceAddress.equals(that.petrolProBestPriceAddress) && diesel.equals(that.diesel)
                && dieselBestPriceAddress.equals(that.dieselBestPriceAddress) && gas.equals(that.gas)
                && gasBestPriceAddress.equals(that.gasBestPriceAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, petrol, petrolBestPriceAddress, petrolPro, petrolProBestPriceAddress,
                diesel, dieselBestPriceAddress, gas, gasBestPriceAddress);
    }
}
