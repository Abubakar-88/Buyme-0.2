package com.Buyme.admin.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Buyme.common.entity.setting.Setting;
import com.Buyme.common.entity.setting.SettingCategory;

public interface SettingRepository extends CrudRepository<Setting, String> {
	public List<Setting> findByCategory(SettingCategory category);
}
