package com.md.mapper;

import com.md.dto.ClubPageDTO;
import com.md.entity.Club;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ClubMapper {


    @Insert("""
            insert into cms_club 
            values (null,#{title}, #{tag}, #{fkDirectionId}, #{startTime}, #{expectedEndTime}, #{endTime}, #{info}, #{version}, #{deleted}, #{created}, #{updated})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Club club);

    @Results(id = "clubResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "fkDirectionId", column = "fk_direction_id"),
            @Result(property = "direction", column = "fk_direction_id", one = @One(select =
                    "com.md.mapper.DirectionMapper.select")),
    })
    @Select("""
            select * from cms_club t
            where t.id = #{param1} and t.deleted = 0
            """)
    Club select(Long id);

    @ResultMap("clubResultMap")
    @Select("""
            <script>
            select * from cms_club t
            <where>
                <if test='title != null'> title like concat('%', #{title}, '%') and </if>
                <if test='tag != null'> tag like concat('%', #{tag}, '%') and </if>
                <if test='fkDirectionId != null'> fk_direction_id = #{fkDirectionId} and </if>

                t.deleted = 0
            </where>
            </script>
            """)
    List<Club> list(ClubPageDTO dto);

    @Update("""
            <script>
            update cms_club
            <set>
            <if test='title != null'> title = #{title}, </if>
            <if test='tag != null'> tag = #{tag}, </if>
            <if test='fkDirectionId != null'> fk_direction_id = #{fkDirectionId}, </if>
            <if test='startTime != null'> start_time = #{startTime}, </if>
            <if test='expectedEndTime != null'> expected_end_time = #{expectedEndTime}, </if>
            <if test='endTime != null'> end_time = #{endTime}, </if>
            <if test='info != null'> info = #{info}, </if>
            <if test='deleted != null'> deleted = #{deleted}, </if>
            <if test='created != null'> created = #{created}, </if>
            <if test='updated != null'> updated = #{updated}, </if>
            version = version + 1
            </set>
            where id = #{id} and deleted = 0 and version = #{version}
            </script>
            """)
    int update(Club club);

    @Update("""
            update cms_club set deleted = 1, updated = current_timestamp
            where id = #{param1}
            """)
    int delete(Long id);

    @Update("""
            <script>
            update cms_club set deleted = 1, updated = current_timestamp
            where id in
            <foreach collection='list' item='e' open='(' close=')' separator=','>
                ${e}
            </foreach>
            </script>
            """)
    int deleteBatch(List<Long> ids);
}
