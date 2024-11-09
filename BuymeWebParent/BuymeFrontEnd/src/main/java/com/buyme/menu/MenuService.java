package com.buyme.menu;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyme.common.entity.article.Article;
import com.buyme.common.entity.menu.Menu;
import com.buyme.common.entity.menu.MenuType;
import com.buyme.common.exception.MenuItemNotFoundException;

@Service
public class MenuService implements IMenuService{

    @Autowired
    private MenuRepository repo;

    @Override
    public List<Menu> getHeaderMenuItems() {
        // TODO Auto-generated method stub
        return repo.findByTypeAndEnabledOrderByPositionAsc(MenuType.HEADER, true);
    }

    @Override
    public List<Menu> getFooterMenuItems() {
        // TODO Auto-generated method stub
        return repo.findByTypeAndEnabledOrderByPositionAsc(MenuType.FOOTER, true);
    }

    @Override
    public Article getArticleBoundToMenu(String menuAlias) throws MenuItemNotFoundException {
        // TODO Auto-generated method stub
        Menu menu = repo.findByAlias(menuAlias);
        if (menu == null) {
            throw new MenuItemNotFoundException("No menu found with alias " + menuAlias);
        }

        return menu.getArticle();
    }

}

