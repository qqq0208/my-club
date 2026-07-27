package com.md.mapper;

import com.md.dto.DirectionPageDTO;
import com.md.entity.Direction;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface DirectionMapper {
    @Insert("""
            insert into cms_direction (title, info, version, deleted, created, updated)
            values (#{title}, #{info}, #{version}, #{deleted}, #{created}, #{updated})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Direction direction);

    @Select("""
            select * from cms_direction t where t.id = #{param1} and t.deleted = 0
            """)
    Direction select(Long id);

    @Select("""
            <script>
            select * from cms_direction t
            <where>
                <if test='title != null'> title like concat('%', #{title}, '%') and </if>
                t.deleted = 0
            </where>
            </script>
            """)
    List<Direction> list(DirectionPageDTO dto);

    @Update("""
            <script>
            update cms_direction
            <set>
            <if test='title != null'> title = #{title}, </if>
            <if test='info != null'> info = #{info}, </if>
            <if test='deleted != null'> deleted = #{deleted}, </if>
            <if test='created != null'> created = #{created}, </if>
            <if test='updated != null'> updated = #{updated}, </if>
            version = version + 1
            </set>
            where id = #{id} and deleted = 0 and version = #{version}
            </script>
            """)
    int update(Direction direction);

    @Update("""
                        update cms_direction set deleted = 1, updated = current_timestamp where id = #{param1}
                        """)
    int delete(Long id);

    @Update("""
            <script>
            update cms_direction set deleted = 1, updated = current_timestamp
            where id in
            <foreach collection='list' item='e' open='(' close=')' separator=','>
                ${e}
            </foreach>
            </script>
            """)
    int deleteBatch(List<Long> ids);


}
