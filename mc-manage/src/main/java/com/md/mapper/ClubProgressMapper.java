package com.md.mapper;

import com.md.dto.ClubProgressPageDTO;
import com.md.entity.ClubProgress;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ClubProgressMapper {
    @Insert("insert into cms_club_progress values (null,#{idx}, #{fkClubId}, #{fkRoomId}, #{fkManagerId}, #{fkAssistantId}, #{fkTeacherId}, #{fkCourseId}, #{startTime}, #{expectedEndTime}, #{endTime}, #{info}, #{version}, #{deleted}, #{created}, #{updated})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ClubProgress clubProgress);

    @Results(id = "clubProgressResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "fkClubId", column = "fk_club_id"),
            @Result(property = "fkRoomId", column = "fk_room_id"),
            @Result(property = "fkManagerId", column = "fk_manager_id"),
            @Result(property = "fkAssistantId", column = "fk_assistant_id"),
            @Result(property = "fkTeacherId", column = "fk_teacher_id"),
            @Result(property = "fkCourseId", column = "fk_course_id"),
            @Result(property = "club", column = "fk_club_id", one = @One(select =
                    "com.md.mapper.ClubMapper.select")),
            @Result(property = "room", column = "fk_room_id", one = @One(select =
                    "com.md.mapper.RoomMapper.select")),
            @Result(property = "manager", column = "fk_manager_id", one = @One(select =
                    "com.md.mapper.EmpMapper.select")),
            @Result(property = "assistant", column = "fk_assistant_id", one = @One(select =
                    "com.md.mapper.EmpMapper.select")),
            @Result(property = "teacher", column = "fk_teacher_id", one = @One(select =
                    "com.md.mapper.EmpMapper.select")),
            @Result(property = "course", column = "fk_course_id", one = @One(select =
                    "com.md.mapper.CourseMapper.select")),
    })
    @Select("""
            select * from cms_club_progress t
            where t.id = #{param1} and t.deleted = 0
            """)
    ClubProgress select(Long id);

    @ResultMap("clubProgressResult")
    @Select("""
            <script>
            select * from cms_club_progress t
            <where>
                <if test='fkClubId != null'> fk_club_id = #{fkClubId} and </if>
                <if test='fkCourseId != null'> fk_course_id = #{fkCourseId} and </if>
                t.deleted = 0
            </where>
            order by t.fk_club_id, t.idx
            </script>
            """)
    List<ClubProgress> list(ClubProgressPageDTO dto);

    @Update("""

            <script>
            update cms_club_progress
            <set>
            <if test='idx != null'> idx = #{idx}, </if>
            <if test='fkClubId != null'> fk_club_id = #{fkClubId}, </if>
            <if test='fkRoomId != null'> fk_room_id = #{fkRoomId}, </if>
            <if test='fkManagerId != null'> fk_manager_id = #{fkManagerId}, </if>
            <if test='fkAssistantId != null'> fk_assistant_id = #{fkAssistantId}, </if>
            <if test='fkTeacherId != null'> fk_teacher_id = #{fkTeacherId}, </if>
            <if test='fkCourseId != null'> fk_course_id = #{fkCourseId}, </if>
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
    int update(ClubProgress clubProgress);

    @Update("""
            update cms_club_progress set deleted = 1, updated = current_timestamp
            where id = #{param1}
            """)
    int delete(Long id);

    @Update("""
            <script>
            update cms_club_progress set deleted = 1, updated = current_timestamp
            where id in
            <foreach collection='list' item='e' open='(' close=')' separator=','>
                ${e}
            </foreach>
            </script>
            """)
    int deleteBatch(List<Long> ids);
}
