package com.buyme.menu;

import java.util.List;

import com.buyme.common.entity.article.Article;
import com.buyme.common.entity.menu.Menu;
import com.buyme.common.exception.MenuItemNotFoundException;

public interface IMenuService {

    public List<Menu> getHeaderMenuItems();
    public List<Menu> getFooterMenuItems();
    public Article getArticleBoundToMenu(String menuAlias) throws MenuItemNotFoundException;
}