package com.md.mapper;

import com.md.dto.DeptPageDTO;
import com.md.entity.Dept;
import com.md.entity.Room;
import com.md.vo.DeptVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
public interface DeptMapper {
    //查詢--全部
    @Results(id = "deMap",value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "fkRoomId",column = "fk_room_id"),
            @Result(property = "room",column = "fk_room_id",
                    one = @One(select = "com.md.mapper.RoomMapper.select"))
    })
    @Select("""
            <script>
            select * from ums_dept t
            <where>
				<if test='title != null'> title like concat('%', #{title}, '%') and </if>
                t.deleted = 0
            </where>
            </script>
            """)
    List<Dept> list(DeptPageDTO dto);


    //查询--单条记录
    @ResultMap("deMap")
    @Select("select * from ums_dept where id = #{id} and deleted = 0")
    Dept select(Long id);

    //添加--单条记录
    @Insert("insert into ums_dept values (null,#{title}, #{info}, #{fkRoomId}, #{version}, #{deleted}, #{created}, #{updated})")
    int insert(Dept dept);

    //修改--单条记录
    @Update("""
            <script>
            update ums_dept
            <set>
            <if test='title != null'> title = #{title}, </if>
            <if test='info != null'> info = #{info}, </if>
            <if test='fkRoomId != null'> fk_room_id = #{fkRoomId}, </if>
            <if test='deleted != null'> deleted = #{deleted}, </if>
            <if test='created != null'> created = #{created}, </if>
            <if test='updated != null'> updated = #{updated}, </if>
            version = version + 1
            </set>
            where id = #{id} and deleted = 0 and version = #{version}
            </script>
            """)
    int update(Dept dept);

    //删除--单条记录
    @Update("""
            update ums_dept set deleted = 1, updated = current_timestamp
            where id = #{id}
            """)
    int delete(Long id);

    //删除--多条记录
    @Update("""
            <script>
            update ums_dept set deleted = 1, updated = current_timestamp
            where id in
            <foreach collection='list' item='e' open='(' close=')' separator=','>
                ${e}
            </foreach>
            </script>
            """)
    int deleteBatch(@Param("list") List<Long> ids);
}