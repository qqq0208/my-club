package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.RoleInsertDTO;
import com.md.dto.RolePageDTO;
import com.md.dto.RoleUpdateDTO;
import com.md.entity.Role;
import com.md.excel.RoleExcel;
import com.md.vo.RoleVO;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
public interface RoleService {
    int insert(RoleInsertDTO dto);
    Role select(Long id);
    List<RoleVO> list();
    PageInfo<Role> page(RolePageDTO dto);
    int update(RoleUpdateDTO dto);
    int delete(Long id);
    int deleteBatch(List<Long> ids);

    /**
     * 导出角色记录的Excel数据
     *
     * @return 角色记录的Excel数据列表
     */
    List<RoleExcel> getExcelData();

    /**
     * 根据员工主键查询该员工的全部角色列表
     *
     * @param empId 员工主键
     * @return 该员工的全部角色列表
     */
    List<Role> listByEmpId(Long empId);

    /**
     * 根据员工主键修改该员工的角色列表
     *
     * @param empId   员工主键
     * @param roleIds 角色主键列表
     * @return 影响条目数
     */
    int updateByEmpId(Long empId, List<Long> roleIds);
}