package com.dainping.cat.home.dal.user;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "dp-admin-login", physicalName = "DP_AdminLogin", alias = "da")
public class DpAdminLoginEntity {

   @Attribute(field = "LoginID", primaryKey = true)
   public static final DataField LOGIN_ID = new DataField("login-id");

   @Attribute(field = "AdminID")
   public static final DataField ADMIN_ID = new DataField("admin-id");

   @Attribute(field = "LoginName")
   public static final DataField LOGIN_NAME = new DataField("login-name");

   @Attribute(field = "Password")
   public static final DataField PASSWORD = new DataField("password");

   @Attribute(field = "Email")
   public static final DataField EMAIL = new DataField("email");

   @Attribute(field = "RealName")
   public static final DataField REAL_NAME = new DataField("real-name");

   @Attribute(field = "MobileNo")
   public static final DataField MOBILE_NO = new DataField("mobile-no");

   @Variable
   public static final DataField KEY_LOGIN_ID = new DataField("key-login-id");

   public static final Readset<DpAdminLogin> READSET_FULL = new Readset<DpAdminLogin>(LOGIN_ID, ADMIN_ID, LOGIN_NAME, PASSWORD, EMAIL, REAL_NAME, MOBILE_NO);

   public static final Updateset<DpAdminLogin> UPDATESET_FULL = new Updateset<DpAdminLogin>(LOGIN_ID, ADMIN_ID, LOGIN_NAME, PASSWORD, EMAIL, REAL_NAME, MOBILE_NO);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", DpAdminLoginEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='login-id'/> = ${key-login-id}");

   public static final QueryDef INSERT = new QueryDef("insert", DpAdminLoginEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>)");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", DpAdminLoginEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/>");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", DpAdminLoginEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/>");

   public static final QueryDef FIND_BY_EMAIL = new QueryDef("findByEmail", DpAdminLoginEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='email'/> = ${email} AND <FIELD name='password'/> = UPPER(md5(${password}))");

   public static final QueryDef FIND_BY_LOGIN_NAME = new QueryDef("findByLoginName", DpAdminLoginEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='login-name'/> = ${login-name} AND <FIELD name='password'/> = UPPER(md5(${password}))");

}
