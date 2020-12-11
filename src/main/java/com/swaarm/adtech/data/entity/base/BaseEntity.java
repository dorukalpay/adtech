package com.swaarm.adtech.data.entity.base;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    private UUID id;

    @PrePersist
    protected void onPrePersist() {
        this.id = UUID.randomUUID();
    }

}
