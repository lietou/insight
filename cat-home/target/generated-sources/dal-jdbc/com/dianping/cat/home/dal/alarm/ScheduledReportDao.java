package com.dianping.cat.home.dal.alarm;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class ScheduledReportDao extends AbstractDao {
   public ScheduledReport createLocal() {
      ScheduledReport proto = new ScheduledReport();

      return proto;
   }

   public int deleteByPK(ScheduledReport proto) throws DalException {
      return getQueryEngine().deleteSingle(
            ScheduledReportEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<ScheduledReport> findAll(Readset<ScheduledReport> readset) throws DalException {
      ScheduledReport proto = new ScheduledReport();

      List<ScheduledReport> result = getQueryEngine().queryMultiple(
            ScheduledReportEntity.FIND_ALL, 
            proto,
            readset);
      
      return result;
   }
   
   public ScheduledReport findByPK(int keyId, Readset<ScheduledReport> readset) throws DalException {
      ScheduledReport proto = new ScheduledReport();

      proto.setKeyId(keyId);

      ScheduledReport result = getQueryEngine().querySingle(
            ScheduledReportEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { ScheduledReportEntity.class };
   }

   public int insert(ScheduledReport proto) throws DalException {
      return getQueryEngine().insertSingle(
            ScheduledReportEntity.INSERT,
            proto);
   }
   
   public int updateByPK(ScheduledReport proto, Updateset<ScheduledReport> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            ScheduledReportEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
