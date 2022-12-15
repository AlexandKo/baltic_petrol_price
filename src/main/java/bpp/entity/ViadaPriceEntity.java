package bpp.entity;

import bpp.util.Country;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
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
@Table(name = "viada", schema = "petrol_station")
public class ViadaPriceEntity extends BaseEntity {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;
    @Enumerated(value = EnumType.STRING)
    @Column
    private Country country;
    @Column
    private BigDecimal petrolEcto;
    @Column
    private String petrolEctoBestPriceAddress;
    @Column
    private BigDecimal petrolEctoPlus;
    @Column
    private String petrolEctoPlusBestPriceAddress;
    @Column
    private BigDecimal petrolPro;
    @Column
    private String petrolProBestPriceAddress;
    @Column
    private BigDecimal diesel;
    @Column
    private String dieselBestPriceAddress;
    @Column
    private BigDecimal dieselEcto;
    @Column
    private String dieselEctoBestPriceAddress;
    @Column
    private BigDecimal gas;
    @Column
    private String gasBestPriceAddress;
    @Column
    private BigDecimal petrolEco;
    @Column
    private String petrolEcoBestPriceAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ViadaPriceEntity that = (ViadaPriceEntity) o;
        return id.equals(that.id) && country == that.country && petrolEcto.equals(that.petrolEcto) &&
                petrolEctoBestPriceAddress.equals(that.petrolEctoBestPriceAddress) &&
                petrolEctoPlus.equals(that.petrolEctoPlus) &&
                petrolEctoPlusBestPriceAddress.equals(that.petrolEctoPlusBestPriceAddress) &&
                petrolPro.equals(that.petrolPro) && petrolProBestPriceAddress.equals(that.petrolProBestPriceAddress) &&
                diesel.equals(that.diesel) && dieselBestPriceAddress.equals(that.dieselBestPriceAddress) &&
                dieselEcto.equals(that.dieselEcto) &&
                dieselEctoBestPriceAddress.equals(that.dieselEctoBestPriceAddress) && gas.equals(that.gas) &&
                gasBestPriceAddress.equals(that.gasBestPriceAddress) && petrolEco.equals(that.petrolEco) &&
                petrolEcoBestPriceAddress.equals(that.petrolEcoBestPriceAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, country, petrolEcto, petrolEctoBestPriceAddress, petrolEctoPlus,
                petrolEctoPlusBestPriceAddress, petrolPro, petrolProBestPriceAddress, diesel, dieselBestPriceAddress,
                dieselEcto, dieselEctoBestPriceAddress, gas, gasBestPriceAddress, petrolEco, petrolEcoBestPriceAddress);
    }
}
