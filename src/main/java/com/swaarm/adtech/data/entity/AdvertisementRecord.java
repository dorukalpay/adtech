package com.swaarm.adtech.data.entity;

import com.swaarm.adtech.data.entity.base.BaseEntity;
import com.swaarm.adtech.data.model.Browser;
import com.swaarm.adtech.data.model.OperatingSystem;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "advertisement_record")
public class AdvertisementRecord extends BaseEntity {

    @Column(name = "advertisement_id", updatable = false, nullable = false)
    private Long advertisementId;

    @Column(name = "browser", updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private Browser browser;

    @Column(name = "operating_system", updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private OperatingSystem operatingSystem;

    @Column(name = "site", updatable = false, nullable = false)
    private String site;

    @OneToMany(
            fetch = FetchType.LAZY,
            targetEntity = AdvertisementRecordDetail.class,
            mappedBy = "advertisementRecord",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private final Set<AdvertisementRecordDetail> details = new HashSet<>();

    @OneToOne(mappedBy = "advertisementRecord",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private AdvertisementDataCount advertisementDataCount;

}
