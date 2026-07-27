package com.md.mapper;

import com.md.dto.AssetsBorrowPageDTO;
import com.md.dto.AssetsPageDTO;
import com.md.dto.CoursePageDTO;
import com.md.entity.Assets;
import com.md.entity.AssetsBorrow;
import com.md.entity.Course;
import com.md.vo.AssetsVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CourseMapper {


    @Insert(""" 
            insert into cms_course 
            values (null,#{title}, #{category}, #{idx}, #{hours}, #{info}, #{version}, #{deleted}, #{created}, #{updated}) 
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Course course);

    @Select(""" 
            select * from cms_course t 
            where t.id = #{param1} and t.deleted = 0 
            """)
    Course select(Long id);

    @Select(""" 
            <script> 
            select * from cms_course t 
            <where> 
                <if test='title != null'> title like concat('%', #{title}, '%') and </if> 
                <if test='category != null'> category like concat('%', #{category}, '%') and 
</if> 
                t.deleted = 0 
            </where> 
            order by t.category, t.idx 
            </script> 
            """)
    List<Course> list(CoursePageDTO dto);

    @Update(""" 
            <script> 
            update cms_course 
            <set> 
            <if test='title != null'> title = #{title}, </if> 
            <if test='category != null'> category = #{category}, </if> 
            <if test='idx != null'> idx = #{idx}, </if> 
            <if test='hours != null'> hours = #{hours}, </if> 
            <if test='info != null'> info = #{info}, </if> 
            <if test='deleted != null'> deleted = #{deleted}, </if> 
            <if test='created != null'> created = #{created}, </if> 
            <if test='updated != null'> updated = #{updated}, </if> 
            version = version + 1 
            </set> 
            where id = #{id} and deleted = 0 and version = #{version} 
            </script> 
            """)
    int update(Course course);

    @Update(""" 
            update cms_course set deleted = 1, updated = current_timestamp 
            where id = #{param1} 
            """)
    int delete(Long id);

    @Update(""" 
            <script> 
            update cms_course set deleted = 1, updated = current_timestamp 
            where id in 
            <foreach collection='list' item='e' open='(' close=')' separator=','> 
                ${e} 
            </foreach> 
            </script> 
            """)
    int deleteBatch(List<Long> ids);
}

