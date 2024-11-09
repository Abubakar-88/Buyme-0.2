package com.buyme.common.entity.section;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.buyme.common.entity.Category;
import com.buyme.common.entity.IdBasedEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sections_categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategorySection extends IdBasedEntity {

    @Column(name = "category_order")
    private int categoryOrder;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
