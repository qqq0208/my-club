package com.md.mapper;

import com.md.dto.RoomPageDto;
import com.md.entity.Room;
import com.md.vo.RoomVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RoomMapper {

    @Select("select * from rms_room where deleted=0")
    List<RoomVo> selectAllRoom();

    //查询-单条
    @Select("select * from rms_room where id=#{id} and deleted=0" )
    Room select(Long id);

    //查询--条件分页
    List<Room> list(RoomPageDto dto);
    //添加--单条
    @Insert("insert into rms_room values (null,#{title},#{address},#{info},#{capacity},#{version},#{deleted},#{created},#{updated})")
    int insert(Room room);

    //删除--单条
    @Update("update rms_room set deleted=1,updated=current_timestamp where id=#{id}")
    int delete(Long id);

    //删除--多条
    int deleteBatch(@Param("list") List<Long> ids);

    //修改--单条
    int update(Room room);
}
