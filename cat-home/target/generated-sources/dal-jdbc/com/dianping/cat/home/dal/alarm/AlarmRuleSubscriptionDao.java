package com.dianping.cat.home.dal.alarm;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class AlarmRuleSubscriptionDao extends AbstractDao {
   public AlarmRuleSubscription createLocal() {
      AlarmRuleSubscription proto = new AlarmRuleSubscription();

      return proto;
   }

   public int deleteByPK(AlarmRuleSubscription proto) throws DalException {
      return getQueryEngine().deleteSingle(
            AlarmRuleSubscriptionEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<AlarmRuleSubscription> findByUserId(int userId, Readset<AlarmRuleSubscription> readset) throws DalException {
      AlarmRuleSubscription proto = new AlarmRuleSubscription();

      proto.setUserId(userId);

      List<AlarmRuleSubscription> result = getQueryEngine().queryMultiple(
            AlarmRuleSubscriptionEntity.FIND_BY_USER_ID, 
            proto,
            readset);
      
      return result;
   }
   
   public List<AlarmRuleSubscription> findByAlarmRuleId(int alarmRuleId, Readset<AlarmRuleSubscription> readset) throws DalException {
      AlarmRuleSubscription proto = new AlarmRuleSubscription();

      proto.setAlarmRuleId(alarmRuleId);

      List<AlarmRuleSubscription> result = getQueryEngine().queryMultiple(
            AlarmRuleSubscriptionEntity.FIND_BY_ALARM_RULE_ID, 
            proto,
            readset);
      
      return result;
   }
   
   public AlarmRuleSubscription findByPK(int keyAlarmRuleId, int keyUserId, Readset<AlarmRuleSubscription> readset) throws DalException {
      AlarmRuleSubscription proto = new AlarmRuleSubscription();

      proto.setKeyAlarmRuleId(keyAlarmRuleId);
      proto.setKeyUserId(keyUserId);

      AlarmRuleSubscription result = getQueryEngine().querySingle(
            AlarmRuleSubscriptionEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { AlarmRuleSubscriptionEntity.class };
   }

   public int insert(AlarmRuleSubscription proto) throws DalException {
      return getQueryEngine().insertSingle(
            AlarmRuleSubscriptionEntity.INSERT,
            proto);
   }
   
   public int updateByPK(AlarmRuleSubscription proto, Updateset<AlarmRuleSubscription> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            AlarmRuleSubscriptionEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
