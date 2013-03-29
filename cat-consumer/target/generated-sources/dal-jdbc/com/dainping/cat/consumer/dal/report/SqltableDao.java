package com.dainping.cat.consumer.dal.report;

import java.util.List;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.AbstractDao;
import org.unidal.dal.jdbc.Readset;
import org.unidal.dal.jdbc.Updateset;

public class SqltableDao extends AbstractDao {
   public Sqltable createLocal() {
      Sqltable proto = new Sqltable();

      return proto;
   }

   public int deleteByPK(Sqltable proto) throws DalException {
      return getQueryEngine().deleteSingle(
            SqltableEntity.DELETE_BY_PK,
            proto);
   }
   
   public List<Sqltable> findAllByDomain(String domain, Readset<Sqltable> readset) throws DalException {
      Sqltable proto = new Sqltable();

      proto.setDomain(domain);

      List<Sqltable> result = getQueryEngine().queryMultiple(
            SqltableEntity.FIND_ALL_BY_DOMAIN, 
            proto,
            readset);
      
      return result;
   }
   
   public Sqltable findByPK(int keyId, Readset<Sqltable> readset) throws DalException {
      Sqltable proto = new Sqltable();

      proto.setKeyId(keyId);

      Sqltable result = getQueryEngine().querySingle(
            SqltableEntity.FIND_BY_PK, 
            proto,
            readset);
      
      return result;
   }
   
   @Override
   protected Class<?>[] getEntityClasses() {
      return new Class<?>[] { SqltableEntity.class };
   }

   public int insert(Sqltable proto) throws DalException {
      return getQueryEngine().insertSingle(
            SqltableEntity.INSERT,
            proto);
   }
   
   public int updateByPK(Sqltable proto, Updateset<Sqltable> updateset) throws DalException {
      return getQueryEngine().updateSingle(
            SqltableEntity.UPDATE_BY_PK,
            proto,
            updateset);
   }
   
}
