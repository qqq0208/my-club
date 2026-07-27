package com.md.mapper;

import com.md.dto.AssetsPageDTO;
import com.md.entity.Assets;
import com.md.vo.AssetsVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
public interface AssetsMapper {

    //查询--全部记录
    @Select("select * from rms_assets where deleted=0")
    List<AssetsVO> list();
    //查询-单条
    @Select("select * from rms_assets where id=#{id} and deleted=0" )
    Assets select(Long id);

    //查询--条件分页
    List<Assets> page(AssetsPageDTO dto);
    //添加--单条
    @Insert("insert into rms_assets values (null,#{title},#{picture},#{price},#{priceUnit},#{stock},#{stockUnit},#{total},#{info},#{version},#{deleted},#{created},#{updated})")
    int insert(Assets assets);

    //删除--单条
    @Update("update rms_assets set deleted=1,updated=current_timestamp where id=#{id}")
    int delete(Long id);

    //删除--多条
    int deleteBatch(@Param("list") List<Long> ids);

    //修改--单条
    int update(Assets assets);
}