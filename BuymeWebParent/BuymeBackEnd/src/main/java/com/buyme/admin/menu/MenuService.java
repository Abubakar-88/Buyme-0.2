package com.buyme.admin.menu;


import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import com.buyme.admin.error.MenuUnmoveableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.buyme.common.entity.menu.Menu;
import com.buyme.common.exception.MenuItemNotFoundException;

@Service
@Transactional
public class MenuService implements IMenuService{

    @Autowired
    private MenuRepository repo;

    @Override
    public List<Menu> listAll() {
        // TODO Auto-generated method stub
        return repo.findAllByOrderByTypeAscPositionAsc();
    }

    @Override
    public void save(Menu menu) {
        // TODO Auto-generated method stub
        MenuUtil.setDefaultAlias(menu);

        if (menu.getId() == null) {
            MenuUtil.setPositionForNewMenu(repo,menu);
        } else {
            MenuUtil.setPositionForEditedMenu(repo,menu);
        }

        repo.save(menu);
    }

    @Override
    public Menu get(Integer id) throws MenuItemNotFoundException {
        // TODO Auto-generated method stub
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new MenuItemNotFoundException("Could not find any menu item with ID " + id);
        }
    }

    @Override
    public void updateEnabledStatus(Integer id, boolean enabled) throws MenuItemNotFoundException {
        // TODO Auto-generated method stub
        if (!repo.existsById(id)) {
            throw new MenuItemNotFoundException("Could not find any menu item with ID " + id);
        }
        repo.updateEnabledStatus(id, enabled);
    }

    @Override
    public void delete(Integer id) throws MenuItemNotFoundException {
        // TODO Auto-generated method stub
        if (!repo.existsById(id)) {
            throw new MenuItemNotFoundException("Could not find any menu item with ID " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public void moveMenu(Integer id, MenuMoveDirection direction)
            throws MenuUnmoveableException, MenuItemNotFoundException {
        // TODO Auto-generated method stub

        if (direction.equals(MenuMoveDirection.UP)) {

            MenuUtil.moveMenuUp(repo,id);
        } else {

            MenuUtil.moveMenuDown(repo,id);
        }
    }
    public Long getTotalMenu() {
        return repo.count();
    }

}

