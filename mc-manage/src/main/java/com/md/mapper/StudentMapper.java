package com.md.mapper;

import com.md.dto.StudentPageDTO;
import com.md.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StudentMapper {
    @Insert("""
            insert into cms_student (number, realname, avatar, phone, gender, wechat, age, 
province, address, fk_school_id, fk_salesman_id, fk_club_id, idcard, study_date, emergency_name, 
emergency_phone, status, info, version, deleted, created, updated)
            values (#{number}, #{realname}, #{avatar}, #{phone}, #{gender}, #{wechat}, #{age}, #{province}, #{address}, #{fkSchoolId}, #{fkSalesmanId}, #{fkClubId}, #{idcard}, #{studyDate}, #{emergencyName}, #{emergencyPhone}, #{status}, #{info}, #{version}, #{deleted}, #{created}, #{updated})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);

    @Results(id = "studentResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "fkSchoolId", column = "fk_school_id"),
            @Result(property = "fkSalesmanId", column = "fk_salesman_id"),
            @Result(property = "fkClubId", column = "fk_club_id"),
            @Result(property = "school", column = "fk_school_id", one = @One(select =
                    "com.md.mapper.SchoolMapper.select")),
            @Result(property = "salesman", column = "fk_salesman_id", one = @One(select =
                    "com.md.mapper.EmpMapper.select")),
            @Result(property = "club", column = "fk_club_id", one = @One(select =
                    "com.md.mapper.ClubMapper.select")),
    })
    @Select("""
            select * from cms_student t
            where t.id = #{param1} and t.deleted = 0
            """)
    Student select(Long id);

    @ResultMap("studentResultMap")
    @Select("""
            <script>
            select * from cms_student t
            <where>
                <if test='number != null'> number like concat('%', #{number}, '%') and </if>
                <if test='realname != null'> realname like concat('%', #{realname}, '%') and 
</if>
                <if test='fkSchoolId != null'> fk_school_id = #{fkSchoolId} and </if>
                <if test='fkSalesmanId != null'> fk_salesman_id = #{fkSalesmanId} and </if>
                <if test='fkClubId != null'> fk_club_id = #{fkClubId} and </if>
                <if test='status != null'> status = #{status} and </if>
                t.deleted = 0
            </where>
            </script>
            """)
    List<Student> list(StudentPageDTO dto);

    @Update("""
            <script>
            update cms_student
            <set>
            <if test='number != null'> number = #{number}, </if>
            <if test='realname != null'> realname = #{realname}, </if>
            <if test='avatar != null'> avatar = #{avatar}, </if>
            <if test='phone != null'> phone = #{phone}, </if>
            <if test='gender != null'> gender = #{gender}, </if>
            <if test='wechat != null'> wechat = #{wechat}, </if>
            <if test='age != null'> age = #{age}, </if>
            <if test='province != null'> province = #{province}, </if>
            <if test='address != null'> address = #{address}, </if>
            <if test='fkSchoolId != null'> fk_school_id = #{fkSchoolId}, </if>
            <if test='fkSalesmanId != null'> fk_salesman_id = #{fkSalesmanId}, </if>
            <if test='fkClubId != null'> fk_club_id = #{fkClubId}, </if>
            <if test='idcard != null'> idcard = #{idcard}, </if>
            <if test='studyDate != null'> study_date = #{studyDate}, </if>
            <if test='emergencyName != null'> emergency_name = #{emergencyName}, </if>
            <if test='emergencyPhone != null'> emergency_phone = #{emergencyPhone}, </if>
            <if test='status != null'> status = #{status}, </if>
            <if test='info != null'> info = #{info}, </if>
            <if test='deleted != null'> deleted = #{deleted}, </if>
            <if test='created != null'> created = #{created}, </if>
            <if test='updated != null'> updated = #{updated}, </if>
            version = version + 1
            </set>
            where id = #{id} and deleted = 0 and version = #{version}
            </script>
            """)
    int update(Student student);

    @Update("""
            update cms_student set deleted = 1, updated = current_timestamp
            where id = #{param1}
            """)
    int delete(Long id);

    @Update("""
            <script>
            update cms_student set deleted = 1, updated = current_timestamp
            where id in
            <foreach collection='list' item='e' open='(' close=')' separator=','>
                ${e}
            </foreach>
            </script>
            """)
    int deleteBatch(List<Long> ids);
}
