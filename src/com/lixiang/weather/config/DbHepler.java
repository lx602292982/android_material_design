package com.lixiang.weather.config;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.lixiang.weather.support.database.DbUtils;
import com.lixiang.weather.support.database.db.sqlite.Selector;
import com.lixiang.weather.support.database.db.sqlite.WhereBuilder;
import com.lixiang.weather.support.database.exception.DbException;
import com.lixiang.weather.support.database.util.LogUtils;

public class DbHepler {
//	private String TAG = "db";
	private static String dbName = "qcb.db";
	private DbUtils db;

	public DbHepler(Context context) {
		this(context, dbName);
	}

	public DbHepler(Context context, String dbName) {
		db = DbUtils.create(context, dbName);
	}

	public <T> T findFirst(Class<T> entityType) {
		try {
			return db.findFirst(entityType);
		} catch (DbException e) {
			LogUtils.e( "[findFirst]----" + e.getMessage());
		}
		return null;
	}

	public <T> T findById(Class<T> entityType, Object idValue) {
		try {
			return db.findById(entityType, idValue);
		} catch (DbException e) {
			LogUtils.e( "[findById]----" + e.getMessage());
		}
		return null;
	}

	public <T> List<T> findAll(Class<T> entityType) {
		List<T> list = null;
		try {
			list = db.findAll(entityType);
		} catch (DbException e) {
			LogUtils.e( "[findAll]----" + e.getMessage());
		}
		return list!=null?list:new ArrayList<T>();
	}

	public <T> List<T> findAll(Selector selector) {
		List<T> list = null;
		try {
			list = db.findAll(selector);
		} catch (DbException e) {
			LogUtils.e( "[findAll]----" + e.getMessage());
		}
		return list!=null?list:new ArrayList<T>();
	}

	public void save(Object entity) {
		try {
			db.save(entity);
		} catch (DbException e) {
			LogUtils.e( "[save]----" + e.getMessage());
		}
	}

	public void saveAll(List<?> entities) {
		try {
			db.saveAll(entities);
		} catch (DbException e) {
			LogUtils.e( "[saveAll]----" + e.getMessage());
		}
	}

	public void saveOrUpdate(Object entity) {
		try {
			db.saveOrUpdate(entity);
		} catch (DbException e) {
			LogUtils.e( "[saveOrUpdate]----" + e.getMessage());
		}
	}

	public void saveOrUpdateAll(List<?> entities) {
		try {
			db.saveOrUpdateAll(entities);
		} catch (DbException e) {
			LogUtils.e( "[saveOrUpdateAll]----" + e.getMessage());
		}
	}

	public void update(Object entity, String... updateColumnNames) {
		try {
			db.update(entity, updateColumnNames);
		} catch (DbException e) {
			LogUtils.e( "[update]----" + e.getMessage());
		}
	}

	public void update(Object entity, WhereBuilder whereBuilder,
			String... updateColumnNames) {
		try {
			db.update(entity, whereBuilder, updateColumnNames);
			db.update(entity, whereBuilder, updateColumnNames);
		} catch (DbException e) {
			LogUtils.e( "[update]----" + e.getMessage());
		}
	}
	public void updateAll(List<?> entities, String... updateColumnNames) {
		try {
			db.updateAll(entities, updateColumnNames);
		} catch (DbException e) {
			LogUtils.e( "[updateAll]----" + e.getMessage());
		}
	}
	public void delete(Object entity) {
		try {
			db.delete(entity);
		} catch (DbException e) {
			LogUtils.e( "[delete]----" + e.getMessage());
		}
	}
	
	 public void deleteById(Class<?> entityType, Object idValue){
		 try {
				db.deleteById(entityType, idValue);
			} catch (DbException e) {
				LogUtils.e( "[delete]----" + e.getMessage());
			}
	    }
	public void delete(Class<?> entityType, WhereBuilder whereBuilder) {
		try {
			db.delete(entityType, whereBuilder);
		} catch (DbException e) {
			LogUtils.e( "[delete]----" + e.getMessage());
		}
	}
	
	public void deleteAll(Class<?> entityType) {
		try {
			db.deleteAll(entityType);
		} catch (DbException e) {
			LogUtils.e( "[deleteAll]----" + e.getMessage());
		}
	}
	public void deleteAll(List<?> entities) {
		try {
			db.deleteAll(entities);
		} catch (DbException e) {
			LogUtils.e( "[deleteAll]----" + e.getMessage());
		}
    }
	public void dropDb() {
		try {
			db.dropDb();
		} catch (DbException e) {
			LogUtils.e( "[dropDb]----" + e.getMessage());
		}
	}

	public void dropTable(Class<?> entityType) {
		try {
			db.dropTable(entityType);
		} catch (DbException e) {
			LogUtils.e( "[dropTable]----" + e.getMessage());
		}
	}

	public long count(Class<?> entityType) {
		long count = 0;
		try {
			count = db.count(entityType);
		} catch (DbException e) {
			LogUtils.e( "[count]----" + e.getMessage());
		}
		return count;
	}
}
