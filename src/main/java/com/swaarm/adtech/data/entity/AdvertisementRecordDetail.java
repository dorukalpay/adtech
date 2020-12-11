package com.swaarm.adtech.data.entity;


import com.swaarm.adtech.data.entity.base.BaseEntity;
import com.swaarm.adtech.data.model.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "advertisement_record_detail")
public class AdvertisementRecordDetail extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_record_id", referencedColumnName = "id")
    private AdvertisementRecord advertisementRecord;

    @Column(name = "advertisement_record_id", insertable = false, updatable = false, nullable = false)
    private UUID advertisementRecordId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "time", nullable = false)
    private OffsetDateTime time;

    @Column(name = "delivery_id", nullable = false)
    private UUID deliveryId;

    @Column(name = "click_id")
    private UUID clickId;

    @Column(name = "install_id")
    private UUID installId;

}
