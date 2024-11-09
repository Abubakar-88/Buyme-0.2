package com.buyme.common.entity.section;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.buyme.common.entity.Brand;
import com.buyme.common.entity.IdBasedEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sections_brands")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandSection extends IdBasedEntity {

    @Column(name = "brand_order")
    private int brandOrder;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

}
