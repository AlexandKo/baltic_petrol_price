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
@Table(name = "circle", schema = "petrol_station")
public class CirclePriceEntity extends BaseEntity {
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
    private BigDecimal petrolPro;
    @Column
    private String petrolProBestPriceAddress;
    @Column
    private BigDecimal diesel;
    @Column
    private String dieselBestPriceAddress;
    @Column
    private BigDecimal dieselPro;
    @Column
    private String dieselProBestPriceAddress;
    @Column
    private BigDecimal gas;
    @Column
    private String gasBestPriceAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CirclePriceEntity that = (CirclePriceEntity) o;
        return id.equals(that.id) && country == that.country && petrol.equals(that.petrol) &&
                petrolBestPriceAddress.equals(that.petrolBestPriceAddress) && petrolPro.equals(that.petrolPro) &&
                petrolProBestPriceAddress.equals(that.petrolProBestPriceAddress) && diesel.equals(that.diesel) &&
                dieselBestPriceAddress.equals(that.dieselBestPriceAddress) && dieselPro.equals(that.dieselPro) &&
                dieselProBestPriceAddress.equals(that.dieselProBestPriceAddress) && gas.equals(that.gas) &&
                gasBestPriceAddress.equals(that.gasBestPriceAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, country, petrol, petrolBestPriceAddress, petrolPro,
                petrolProBestPriceAddress, diesel, dieselBestPriceAddress, dieselPro, dieselProBestPriceAddress,
                gas, gasBestPriceAddress);
    }
}
