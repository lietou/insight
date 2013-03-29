package com.dianping.cat.job.sql.dal;

import org.unidal.dal.jdbc.DataField;
import org.unidal.dal.jdbc.QueryDef;
import org.unidal.dal.jdbc.QueryType;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;
import org.unidal.dal.jdbc.annotation.Attribute;
import org.unidal.dal.jdbc.annotation.Entity;
import org.unidal.dal.jdbc.annotation.Variable;

@Entity(logicalName = "location", physicalName = "location", alias = "l")
public class LocationRecordEntity {

   @Attribute(field = "id", nullable = false, primaryKey = true, autoIncrement = true)
   public static final DataField ID = new DataField("id");

   @Attribute(field = "lat", nullable = false)
   public static final DataField LAT = new DataField("lat");

   @Attribute(field = "lng", nullable = false)
   public static final DataField LNG = new DataField("lng");

   @Attribute(field = "total", nullable = false)
   public static final DataField TOTAL = new DataField("total");

   @Attribute(field = "transaction_date", nullable = false)
   public static final DataField TRANSACTION_DATE = new DataField("transaction-date");

   @Attribute(field = "creation_date", nullable = false, insertExpr = "NOW()")
   public static final DataField CREATION_DATE = new DataField("creation-date");

   @Variable
   public static final DataField KEY_ID = new DataField("key-id");

   @Variable
   public static final DataField FROM_DATE = new DataField("from-date");

   @Variable
   public static final DataField TO_DATE = new DataField("to-date");

   @Variable
   public static final DataField FROM_LAT = new DataField("from-lat");

   @Variable
   public static final DataField TO_LAT = new DataField("to-lat");

   @Variable
   public static final DataField FROM_LNG = new DataField("from-lng");

   @Variable
   public static final DataField TO_LNG = new DataField("to-lng");

   public static final Readset<LocationRecord> READSET_FULL = new Readset<LocationRecord>(ID, LAT, LNG, TOTAL, TRANSACTION_DATE, CREATION_DATE);

   public static final Readset<LocationRecord> READSET_LAT_LNG_TOTAL = new Readset<LocationRecord>(LAT, LNG, TOTAL);

   public static final Updateset<LocationRecord> UPDATESET_FULL = new Updateset<LocationRecord>(ID, LAT, LNG, TOTAL, TRANSACTION_DATE);

   public static final QueryDef FIND_BY_PK = new QueryDef("findByPK", LocationRecordEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef INSERT = new QueryDef("insert", LocationRecordEntity.class, QueryType.INSERT, 
      "INSERT INTO <TABLE/>(<FIELDS/>) VALUES(<VALUES/>) ON DUPLICATE KEY UPDATE <FIELD name='total'/> = ${total}");

   public static final QueryDef UPDATE_BY_PK = new QueryDef("updateByPK", LocationRecordEntity.class, QueryType.UPDATE, 
      "UPDATE <TABLE/> SET <FIELDS/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef DELETE_BY_PK = new QueryDef("deleteByPK", LocationRecordEntity.class, QueryType.DELETE, 
      "DELETE FROM <TABLE/> WHERE <FIELD name='id'/> = ${key-id}");

   public static final QueryDef FIND_ALL_BY_TRANSACTION_DATE_LAT_LNG_RANGE = new QueryDef("findAllByTransactionDateLatLngRange", LocationRecordEntity.class, QueryType.SELECT, 
      "SELECT <FIELDS/> FROM <TABLE/> WHERE (<FIELD name='transaction-date'/> BETWEEN ${from-date} AND ${to-date}) AND (<FIELD name='lat'/> BETWEEN ${from-lat} AND ${to-lat}) AND (<FIELD name='lng'/> BETWEEN ${from-lng} AND ${to-lng})");

}
