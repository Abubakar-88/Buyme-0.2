package com.buyme.section;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyme.common.entity.section.Section;

@Service
@Transactional
public class SectionService {

    @Autowired
    private SectionRepository repo;

    public List<Section> listEnabledSections() {
        return repo.findAllByEnabledOrderBySectionOrderAsc(true);
    }


}
