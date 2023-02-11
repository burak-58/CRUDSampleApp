package io.antmedia.db;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.result.UpdateResult;

import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import dev.morphia.query.filters.Filters;
import dev.morphia.query.updates.UpdateOperators;
import io.antmedia.datastore.db.MongoStore;
import io.antmedia.datastore.db.types.Broadcast;
import io.antmedia.datastore.db.types.VoD;
import io.antmedia.db.types.CRUDUser;

public class CRUDMongoStore {


	private Datastore userDatastore;
	private MongoClient mongoClient;

	protected static Logger logger = LoggerFactory.getLogger(CRUDMongoStore.class);

	public CRUDMongoStore(String host, String username, String password, String dbName) {
		String uri = MongoStore.getMongoConnectionUri(host, username, password);
		mongoClient = MongoClients.create(uri);
		userDatastore = Morphia.createDatastore(mongoClient, dbName);
		userDatastore.getMapper().mapPackage("io.antmedia.db.types");
		userDatastore.ensureIndexes();
	}



	public boolean saveUser(CRUDUser user) {
		try {
			synchronized(this) {
				userDatastore.save(user);
			}
			return true;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		return false;
	}



	public CRUDUser getUser(String name) {
		synchronized(this) {
			try {
				return userDatastore.find(CRUDUser.class).filter(Filters.eq("name", name)).first();
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			}
		}
		return null;
	}
	
	public boolean deleteUser(String name) {
		synchronized(this) {
			try {
				Query<CRUDUser> query = userDatastore.find(CRUDUser.class).filter(Filters.eq("name", name));
				return query.delete().getDeletedCount() == 1;
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			}
		}
		return false;
	}

	public boolean updateUser(String name, CRUDUser user) {
		synchronized(this) {
			try {
				Query<CRUDUser> query = userDatastore.find(CRUDUser.class).filter(Filters.eq("name", name));
				UpdateResult result = query.update(UpdateOperators.set("email", user.getEmail())).execute();

				return result.getMatchedCount() == 1;
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return false;
	}
}
