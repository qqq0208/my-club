package com.md.mapper;


import com.md.dto.SchoolPageDTO;
import com.md.entity.School;
import com.md.vo.SchoolVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
public interface SchoolMapper {
    //查询--全部记录
    @Select("select * from rms_school where deleted=0")
    List<SchoolVO> list();
    //查询--单条记录
    @Select("select * from rms_school where id=#{id} and deleted=0")
    School select(Long id);


    //查询--条件分页
    List<School> page(SchoolPageDTO dto);
    //添加--单条记录
    @Insert("insert into rms_school  values (null,#{title},#{college},#{major},#{clazz},#{address},#{info},#{version},#{deleted},#{created},#{updated});")
    int insert(School school);

    //删除--单条记录
    @Update("update rms_school set deleted=1,updated=current_timestamp where id=#{id}")
    int delete(Long id);

    //删除--多条
    int deleteBatch(@Param("list") List<Long> ids);

    //修改--单条
    int update(School school);
}