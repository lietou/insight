package com.dainping.cat.consumer.dal.report;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "sqltable", physicalName = "sqltable", alias = "s")
public class SqltableEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "domain")
   public static final DataField DOMAIN = new DataField("domain");

   @Attribute(field = "sql_name")
   public static final DataField SQL_NAME = new DataField("sql-name");

   @Attribute(field = "table_name")
   public static final DataField TABLE_NAME = new DataField("table-name");

   @Attribute(field = "sql_statement")
   public static final DataField SQL_STATEMENT = new DataField("sql-statement");

   @Attribute(field = "creation_date", insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Attribute(field = "modify_date", insertExpr = "NOW()", updateExpr = "NOW()")
   public static final DataField MODIFY_DATE = new DataField("modify-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   public static final Readset<Sqltable> READSET_FULL = new Readset<Sqltable>(ID, DOMAIN, SQL_NAME, TABLE_NAME, SQL_STATEMENT, CREATION_DATE, MODIFY_DATE);

   public static final Updateset<Sqltable> UPDATESET_FULL = new Updateset<Sqltable>(ID, DOMAIN, SQL_NAME, TABLE_NAME, SQL_STATEMENT, MODIFY_DATE);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", SqltableEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", SqltableEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", SqltableEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", SqltableEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_ALL_BY_DOMAIN = new QueryDef("findAllByDomain", SqltableEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='domain'/> = ${domain}");

}
