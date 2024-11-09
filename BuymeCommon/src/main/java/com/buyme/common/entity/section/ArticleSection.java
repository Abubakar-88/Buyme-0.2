package com.buyme.common.entity.section;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.buyme.common.entity.IdBasedEntity;
import com.buyme.common.entity.article.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sections_articles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleSection extends IdBasedEntity {

    @Column(name = "article_order")
    private int articleOrder;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

}
