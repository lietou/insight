package com.dainping.cat.home.dal.user;

import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class DpAdminLoginDao extends AbstractDao {
   public DpAdminLogin createLocal() {
      DpAdminLogin proto = new DpAdminLogin();

      return proto;
   }

   public int deleteByPK(DpAdminLogin proto) throws DalException {
      return getQueryEngine().deleteSingle(
            DpAdminLoginEntity.DELETE_BY_PK,
            proto);
   }
   
   public DpAdminLogin findByPK(int keyLoginId, Readset<DpAdminLogin> readset) throws DalException {
      DpAdminLogin proto = new DpAdminLogin();

      proto.setKeyLoginId(keyLoginId);

      DpAdminLogin result = getQueryEngine().querySingle(
            DpAdminLoginEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   public DpAdminLogin findByEmail(String email, String password, Readset<DpAdminLogin> readset) throws DalException {
      DpAdminLogin proto = new DpAdminLogin();

      proto.setEmail(email);
      proto.setPassword(password);

      DpAdminLogin result = getQueryEngine().querySingle(
            DpAdminLoginEntity.FIND_BY_EMAIL, 
            proto,
            readset);
      
      return result;
   }
   
   public DpAdminLogin findByLoginName(String loginName, String password, Readset<DpAdminLogin> readset) throws DalException {
      DpAdminLogin proto = new DpAdminLogin();

      proto.setLoginName(loginName);
      proto.setPassword(password);

      DpAdminLogin result = getQueryEngine().querySingle(
            DpAdminLoginEntity.FIND_BY_LOGIN_NAME, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { DpAdminLoginEntity.class };
   }

   public int insert(DpAdminLogin proto) throws DalException {
      return getQueryEngine().insertSingle(
            DpAdminLoginEntity.INSERT,
            proto);
   }
   
   public int updateByPK(DpAdminLogin proto, Updateset<DpAdminLogin> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            DpAdminLoginEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
