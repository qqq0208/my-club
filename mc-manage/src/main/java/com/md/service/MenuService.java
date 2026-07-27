package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.MenuInsertDTO;
import com.md.dto.MenuPageDTO;
import com.md.dto.MenuUpdateDTO;
import com.md.entity.Menu;
import com.md.excel.MenuExcel;
import com.md.vo.MenuVO;

import java.util.List;

/** @author  */
public interface MenuService { 
    int insert(MenuInsertDTO dto);
    Menu select(Long id);
    List<MenuVO> list();
    PageInfo<Menu> page(MenuPageDTO dto);
    int update(MenuUpdateDTO dto);
    int delete(Long id); 
    int deleteBatch(List<Long> ids);


    /**
     * 导出菜单记录的Excel数据
     *
     * @return 菜单记录的Excel数据列表
     */
    List<MenuExcel> getExcelData();
    /**
     * 根据角色主键查询该角色的全部菜单列表
     *
     * @param roleId 角色主键
     * @return 该角色的全部菜单列表
     */
    List<Menu> listByRoleId(Long roleId);

    int updateByRoleId(Long roleId, List<Long> menuIds);
    List<Menu> listByEmpId(Long empId);

} 