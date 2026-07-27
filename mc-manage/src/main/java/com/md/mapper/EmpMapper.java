package com.md.mapper;

import com.md.dto.EmpPageDTO;
import com.md.dto.EmpUpdatePasswordDTO;
import com.md.dto.LoginDTO;
import com.md.entity.Emp;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EmpMapper {

    //查询--全部
    @Results(id = "eMap",value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "fkDeptId",column = "fk_dept_id"),
            @Result(property = "dept",column = "fk_dept_id",one = @One(select = "com.md.mapper.DeptMapper.select"))

    })
    @Select("""
        <script>
            select * from ums_emp
            <where>
                <if test='username != null'> username like concat('%',#{username},'%') and</if>
                <if test='realname != null'> realname like concat('%',#{realname},'%') and</if>
                <if test='phone != null'> phone = #{phone} and</if>
                <if test='idcard != null'> idcard = #{idcard} and</if>
                <if test='fkDeptId != null'> fk_dept_id = #{fkDeptId} and</if>
                deleted = 0
            </where>
        </script>
    """)
    List<Emp> list(EmpPageDTO dto);

    //查询--单条
    @ResultMap("eMap")
    @Select("select * from ums_emp where id = #{id} and deleted = 0")
    Emp select(Long id);



    @Insert(" insert into ums_emp values (null,#{username}, #{password}, #{avatar}, #{phone}, #{wechat}, #{email}, #{gender}, #{age}, #{province}, #{address}, #{realname}, #{idcard},#{fkDeptId} ,#{info},#{hiredate}, #{version}, #{deleted}, #{created}, #{updated}) ")
    int insert(Emp emp);


    @Update("""
            <script>
            update ums_emp
            <set>
            <if test='username != null'> username = #{username}, </if>
            <if test='password != null'> password = #{password}, </if>
            <if test='avatar != null'> avatar = #{avatar}, </if>
            <if test='phone != null'> phone = #{phone}, </if>
            <if test='wechat != null'> wechat = #{wechat}, </if>
            <if test='email != null'> email = #{email}, </if>
            <if test='gender != null'> gender = #{gender}, </if>
            <if test='age != null'> age = #{age}, </if>
            <if test='province != null'> province = #{province}, </if>
            <if test='address != null'> address = #{address}, </if>
            <if test='realname != null'> realname = #{realname}, </if>
            <if test='idcard != null'> idcard = #{idcard}, </if>
            <if test='fkDeptId != null'> fk_dept_id = #{fkDeptId}, </if>
            <if test='info != null'> info = #{info}, </if>
            <if test='hiredate != null'> hiredate = #{hiredate}, </if>
            <if test='deleted != null'> deleted = #{deleted}, </if>
            <if test='created != null'> created = #{created}, </if>
            <if test='updated != null'> updated = #{updated}, </if>
            version = version + 1
            </set>
            where id = #{id} and deleted = 0 and version = #{version}
            </script>
            """)
    int update(Emp emp);

    @Update("update ums_emp set deleted = 1, updated = current_timestamp where id = #{id}")
    int delete(Long id);

    @Update("""
            <script>
            update ums_emp set deleted = 1, updated = current_timestamp
            where id in
            <foreach collection='list' item='e' open='(' close=')' separator=','>
                ${e}
            </foreach>
            </script>
            """)
    int deleteBatch(List<Long> ids);

    @Update("update ums_emp set password = #{newPassword}, updated = now() where password = #{oldPassword} and id = #{id} and deleted = 0")
    int updatePassword(EmpUpdatePasswordDTO dto);

    //查询--用户登录
    @Select("select * from ums_emp where username=#{username} and password=#{password} and deleted=0")
    Emp selectByAccount(LoginDTO dto);
}

