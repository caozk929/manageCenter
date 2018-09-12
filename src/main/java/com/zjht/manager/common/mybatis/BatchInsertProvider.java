package com.zjht.manager.common.mybatis;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

/**
 * ${DESCRIPTION}
 *
 * @outhor caozk
 * @create 2017-09-14 11:18
 */
public class BatchInsertProvider extends MapperTemplate {

    public BatchInsertProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String batchInsert(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //获取表的各项属性
        EntityTable table = EntityHelper.getEntityTable(entityClass);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ");
        sql.append(table.getName());
        sql.append("(");
        boolean first = true;
        for (EntityColumn column : table.getEntityClassColumns()) {
            if(!first) {
                sql.append(",");
            }
            sql.append(column.getColumn());
            first = false;
        }
        sql.append(") values ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("(");
        first = true;
        for (EntityColumn column : table.getEntityClassColumns()) {
            if(!first) {
                sql.append(",");
            }
            sql.append("#{record.").append(column.getProperty()).append("}");
            first = false;
        }
        sql.append(")");
        sql.append("</foreach>");
        return sql.toString();
    }
}
