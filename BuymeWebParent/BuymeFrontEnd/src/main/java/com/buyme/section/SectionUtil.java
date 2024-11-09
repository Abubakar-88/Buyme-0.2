package com.buyme.section;

import com.buyme.common.entity.section.Section;
import com.buyme.common.entity.section.SectionType;

import java.util.List;

public class SectionUtil {

    public static boolean hasAllCategoriesSection(List<Section> listSections) {
        // TODO Auto-generated method stub
        for (Section section : listSections) {
            if (section.getType().equals(SectionType.ALL_CATEGORIES)) {
                return true;
            }
        }

        return false;
    }

}