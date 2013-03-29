package com.dianping.cat.job.sql.dal;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class SqlReportRecordDao extends AbstractDao {
   public SqlReportRecord createLocal() {
      SqlReportRecord proto = new SqlReportRecord();

      return proto;
   }

   public int deleteByPK(SqlReportRecord proto) throws DalException {
      return getQueryEngine().deleteSingle(
            SqlReportRecordEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<SqlReportRecord> findAllByDomainAndDate(String domain, java.util.Date transactionDate, Readset<SqlReportRecord> readset) throws DalException {
      SqlReportRecord proto = new SqlReportRecord();

      proto.setDomain(domain);
      proto.setTransactionDate(transactionDate);

      List<SqlReportRecord> result = getQueryEngine().queryMultiple(
            SqlReportRecordEntity.FIND_ALL_BY_DOMAIN_AND_DATE, 
            proto,
            readset);
      
      return result;
   }
   
   public List<SqlReportRecord> findAllDistinctByDate(java.util.Date transactionDate, Readset<SqlReportRecord> readset) throws DalException {
      SqlReportRecord proto = new SqlReportRecord();

      proto.setTransactionDate(transactionDate);

      List<SqlReportRecord> result = getQueryEngine().queryMultiple(
            SqlReportRecordEntity.FIND_ALL_DISTINCT_BY_DATE, 
            proto,
            readset);
      
      return result;
   }
   
   public SqlReportRecord findByPK(int keyId, Readset<SqlReportRecord> readset) throws DalException {
      SqlReportRecord proto = new SqlReportRecord();

      proto.setKeyId(keyId);

      SqlReportRecord result = getQueryEngine().querySingle(
            SqlReportRecordEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { SqlReportRecordEntity.class };
   }

   public int[] insert(SqlReportRecord[] protos) throws DalException {
      return getQueryEngine().insertBatch(
            SqlReportRecordEntity.INSERT,
            protos);
   }
   
   public int insert(SqlReportRecord proto) throws DalException {
      return getQueryEngine().insertSingle(
            SqlReportRecordEntity.INSERT,
            proto);
   }
   
   public int updateByPK(SqlReportRecord proto, Updateset<SqlReportRecord> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            SqlReportRecordEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
