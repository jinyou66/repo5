package com.itheima.demo.mapper.sql;

/**
 * @Author Aaron
 * @CreateTime 2019/3/24 11:09
 * @Description TODO
 */
public class UserMapperSqlProvider {


    private void  publicSqlStr(StringBuffer sql){
            sql.append(" a.user_id  as userId,");
            sql.append(" a.user_name as userName,");
            sql.append(" a.email  as email,");
            sql.append(" a.password  as password");
    }

    /**
     *   提供 sql
     * @param userName
     * @return
     */
    public String selectUsersByUserName(String userName){
            StringBuffer  sql= new StringBuffer();
            sql.append(" select");
            publicSqlStr(sql);
            sql.append(" from");
            sql.append(" t_user as a");
            sql.append(" where");
            sql.append(" a.user_name = #{userName}");

            return sql.toString();
        }
}
