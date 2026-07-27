package com.md.service;

import com.github.pagehelper.PageInfo;
import com.md.dto.*;
import com.md.entity.Emp;
import com.md.excel.EmpExcel;
import com.md.vo.EmpVO;
import com.md.vo.LoginVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmpService {

    //查询--全部
    List<EmpVO> list();

    //查询--条件分页
    PageInfo<Emp> page(EmpPageDTO dto);

    //查询--单条
    Emp select(Long id);
    //新增--单条
    int insert(EmpInsertDTO dto);
    //修改--单体
    int update(EmpUpdateDTO dto);
    //删除--单条
    int delete(Long id);
    //删除--多条
    int deleteBatch(List<Long> ids);

    //查询--打印报表
    List<EmpExcel> getExcelData();

    /**
     * 上传员工头像
     *
     * @param newFile 上传员工头像DTO
     * @param id      员工主键
     * @return 文件名
     */
    String uploadAvatar(MultipartFile newFile, Long id);

    /**
     * 修改登录密码
     *
     * @param dto 登录密码修改DTO
     * @return 影响条目数
     */
    int updatePassword(EmpUpdatePasswordDTO dto);


    LoginVO loginBuAccount(LoginDTO dto);
}



