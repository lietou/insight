package com.dainping.cat.consumer.dal.report;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "project", physicalName = "project", alias = "p")
public class ProjectEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "domain", nullable = false)
   public static final DataField DOMAIN = new DataField("domain");

   @Attribute(field = "project_line")
   public static final DataField PROJECT_LINE = new DataField("project-line");

   @Attribute(field = "department")
   public static final DataField DEPARTMENT = new DataField("department");

   @Attribute(field = "owner")
   public static final DataField OWNER = new DataField("owner");

   @Attribute(field = "email")
   public static final DataField EMAIL = new DataField("email");

   @Attribute(field = "creation_date", insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Attribute(field = "modify_date", insertExpr = "NOW()", updateExpr = "NOW()")
   public static final DataField MODIFY_DATE = new DataField("modify-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   public static final Readset<Project> READSET_FULL = new Readset<Project>(ID, DOMAIN, PROJECT_LINE, DEPARTMENT, OWNER, EMAIL, CREATION_DATE, MODIFY_DATE);

   public static final Updateset<Project> UPDATESET_FULL = new Updateset<Project>(ID, DOMAIN, PROJECT_LINE, DEPARTMENT, OWNER, EMAIL, MODIFY_DATE);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", ProjectEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", ProjectEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", ProjectEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", ProjectEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_ALL = new QueryDef("findAll", ProjectEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/>");

   public static final QueryDef FIND_BY_DOMAIN = new QueryDef("findByDomain", ProjectEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='domain'/> = ${domain}");

}
