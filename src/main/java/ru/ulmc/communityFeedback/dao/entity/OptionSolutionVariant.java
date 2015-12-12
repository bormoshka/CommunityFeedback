package ru.ulmc.communityFeedback.dao.entity;

import javax.persistence.Column;

/**
 * Created by 45 on 12.12.2015.
 */
public class OptionSolutionVariant extends BaseEntity<Long> {
    @Column
    String text;
}
