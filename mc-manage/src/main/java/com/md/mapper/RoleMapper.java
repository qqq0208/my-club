package com.md.mapper;

import com.md.dto.RolePageDTO;
import com.md.entity.EmpRole;
import com.md.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: CM
 * @Description TODO
 */
public interface RoleMapper {

    @Insert("insert into ums_role values (null,#{title}, #{info}, #{version}, #{deleted}, #{created}, #{updated})")
    int insert(Role role);

    @Select(" select * from ums_role  where id = #{id} and deleted = 0 ")
    Role select(Long id);

    @Select(""" 
            <script> 
            select * from ums_role  
            <where> 
                            <if test='title != null'> title like concat('%', #{title}, '%') and </if> 
                            deleted = 0 
                        </where> 
                        </script> 
                        """)
    List<Role> list(RolePageDTO dto);

    @Update(""" 
            <script> 
            update ums_role 
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
    int update(Role role);

    @Update("update ums_role set deleted = 1, updated = current_timestamp where id = #{id} ")
    int delete(Long id);

    @Update(""" 
            <script> 
            update ums_role set deleted = 1, updated = current_timestamp 
            where id in 
            <foreach collection='list' item='e' open='(' close=')' separator=','> 
                ${e} 
            </foreach> 
            </script> 
            """)
    int deleteBatch(List<Long> ids);


    @Select(""" 
            select * from ums_role  where id in ( 
            select fk_role_id from ums_emp_role  
            where fk_emp_id = #{empId} and deleted = 0 
            ) and deleted = 0 
            """)
    List<Role> listByEmpId(Long empId);


    @Update("update ums_emp_role set deleted = 1, updated = now() where fk_emp_id = #{empId} ")
    int deleteEmpRoleByEmpId(Long empId);

    @Insert("""
        <script>
            insert into ums_emp_role values 
            <foreach collection='list' item='e' separator=','>
            (null,#{e.fkEmpId},#{e.fkRoleId},#{e.version},#{e.deleted},#{e.created},#{e.updated})
            </foreach>
        </script>
    """)

    int insertEmpRoleBatch(List<EmpRole> empRoles);

}