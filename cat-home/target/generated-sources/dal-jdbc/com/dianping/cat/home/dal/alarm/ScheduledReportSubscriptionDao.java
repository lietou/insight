package com.dianping.cat.home.dal.alarm;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class ScheduledReportSubscriptionDao extends AbstractDao {
   public ScheduledReportSubscription createLocal() {
      ScheduledReportSubscription proto = new ScheduledReportSubscription();

      return proto;
   }

   public int deleteByPK(ScheduledReportSubscription proto) throws DalException {
      return getQueryEngine().deleteSingle(
            ScheduledReportSubscriptionEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<ScheduledReportSubscription> findByUserId(int userId, Readset<ScheduledReportSubscription> readset) throws DalException {
      ScheduledReportSubscription proto = new ScheduledReportSubscription();

      proto.setUserId(userId);

      List<ScheduledReportSubscription> result = getQueryEngine().queryMultiple(
            ScheduledReportSubscriptionEntity.FIND_BY_USER_ID, 
            proto,
            readset);
      
      return result;
   }
   
   public List<ScheduledReportSubscription> findByScheduledReportId(int scheduledReportId, Readset<ScheduledReportSubscription> readset) throws DalException {
      ScheduledReportSubscription proto = new ScheduledReportSubscription();

      proto.setScheduledReportId(scheduledReportId);

      List<ScheduledReportSubscription> result = getQueryEngine().queryMultiple(
            ScheduledReportSubscriptionEntity.FIND_BY_SCHEDULED_REPORT_ID, 
            proto,
            readset);
      
      return result;
   }
   
   public ScheduledReportSubscription findByPK(int keyScheduledReportId, int keyUserId, Readset<ScheduledReportSubscription> readset) throws DalException {
      ScheduledReportSubscription proto = new ScheduledReportSubscription();

      proto.setKeyScheduledReportId(keyScheduledReportId);
      proto.setKeyUserId(keyUserId);

      ScheduledReportSubscription result = getQueryEngine().querySingle(
            ScheduledReportSubscriptionEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { ScheduledReportSubscriptionEntity.class };
   }

   public int insert(ScheduledReportSubscription proto) throws DalException {
      return getQueryEngine().insertSingle(
            ScheduledReportSubscriptionEntity.INSERT,
            proto);
   }
   
   public int updateByPK(ScheduledReportSubscription proto, Updateset<ScheduledReportSubscription> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            ScheduledReportSubscriptionEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
