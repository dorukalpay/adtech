package com.swaarm.adtech.data.entity;

import com.swaarm.adtech.data.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "advertisement_data_count")
public class AdvertisementDataCount extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_record_id", referencedColumnName = "id")
    private AdvertisementRecord advertisementRecord;

    @Column(name = "advertisement_record_id", insertable = false, updatable = false, nullable = false)
    private UUID advertisementRecordId;

    private Long delivered = 0L;

    private Long clicked = 0L;

    private Long installed = 0L;
}
