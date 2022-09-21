package bpp.entity;

import bpp.util.Country;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "virsi", schema = "petrol_station")
public class VirsiPriceEntity extends BaseEntity {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Country country;
    @Column(nullable = false)
    private BigDecimal petrol;
    @Column(nullable = false)
    private String petrolBestPriceAddress;
    @Column(nullable = false)
    private BigDecimal petrolPro;
    @Column(nullable = false)
    private String petrolProBestPriceAddress;
    @Column(nullable = false)
    private BigDecimal diesel;
    @Column(nullable = false)
    private String dieselBestPriceAddress;
    @Column(nullable = false)
    private BigDecimal gas;
    @Column(nullable = false)
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
