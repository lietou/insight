package com.dainping.cat.consumer.dal.report;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "hostinfo", physicalName = "hostinfo", alias = "h")
public class HostinfoEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "ip", nullable = false)
   public static final DataField IP = new DataField("ip");

   @Attribute(field = "domain", nullable = false)
   public static final DataField DOMAIN = new DataField("domain");

   @Attribute(field = "creation_date", nullable = false, insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Attribute(field = "last_modified_date", nullable = false, insertExpr = "NOW()", updateExpr = "NOW()")
   public static final DataField LAST_MODIFIED_DATE = new DataField("last-modified-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   public static final Readset<Hostinfo> READSET_FULL = new Readset<Hostinfo>(ID, IP, DOMAIN, CREATION_DATE, LAST_MODIFIED_DATE);

   public static final Updateset<Hostinfo> UPDATESET_FULL = new Updateset<Hostinfo>(ID, IP, DOMAIN, LAST_MODIFIED_DATE);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", HostinfoEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", HostinfoEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/> (<FIELDS/>) VALUES(<VALUES/>) ON DUPLICATE KEY UPDATE <FIELD name='domain'/> = ${domain}, <FIELD name='last-modified-date'/> = NOW()");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", HostinfoEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", HostinfoEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_BY_IP = new QueryDef("findByIp", HostinfoEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='ip'/> = ${ip}");

   public static final QueryDef FIND_ALL_IP = new QueryDef("findAllIp", HostinfoEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/>");

}
