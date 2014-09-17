package model.service.repository.mapper;


import core.model.service.repository.crud.CRUDSEL;
import core.model.service.repository.crud.PrimaryKey;
import core.model.service.repository.crud.storage.StorageDataRow;
import core.model.service.repository.crud.storage.StorageInfo;
import java.util.Date;
import javax.sql.DataSource;
import model.entity.UcitelEntity;
import model.service.repository.crud.storage.StorageFactory;
import resources.ResourceManager;


public class UcitelMapper extends BaseMapper
{


	@Override
	public void initialize()
	{
		StorageFactory storage = new StorageFactory((DataSource)ResourceManager.getSqlDataSource(), ResourceManager.getSqlDataSourceType());

		PrimaryKey pkey = storage.getPrimaryKeyObjectInstance();
		pkey.add("id");

		CRUDSEL crud = storage.getStorageObjectInstance();
		crud.setStorageInfo(new StorageInfo("ucitel", pkey));

		setCRUD(crud);

		super.initialize();
	}


	@Override
	protected StorageDataRow mapEntityToCRUD(String name)
	{
		UcitelEntity entity   = (UcitelEntity)getEntity();
		StorageDataRow dataRow = new StorageDataRow();

		dataRow.set("id",             (Integer)entity.getId());
		dataRow.set("jmeno",          (String)entity.getJmeno());
		dataRow.set("prijmeni",       (String)entity.getPrijmeni());
		dataRow.set("datum_narozeni", (String)entity.getDatumNarozeni().toString()); // TODO: format: YYYY-MM-DD, but it depends on DB-ENGINE! so, i'm fucked!@#$%^&*()
		dataRow.set("pohlavi",
			(String)(entity.getPohlavi() == UcitelEntity.POHLAVI.MUZ
				? "M"
				: "Z"
			)
		);

		return dataRow;
	}


	@Override
	protected StorageDataRow mapEntityToCRUD_asPrimaryKey(String name)
	{
		UcitelEntity entity    = (UcitelEntity)getEntity();
		StorageDataRow dataRow = new StorageDataRow();

		dataRow.set("id", (Integer)entity.getId()); // TODO: auto_increment due DB-SERVER (MySQL, Oracle, ...)

		return dataRow;
	}


	@Override
	protected UcitelEntity mapCrudToEntity(String name)
	{
		UcitelEntity entity    = new UcitelEntity();
		StorageDataRow dataRow = crud.getDataRow();

		entity.setId((Integer)dataRow.get("id"));
		entity.setJmeno((String)dataRow.get("jmeno"));
		entity.setPrijmeni((String)dataRow.get("prijmeni"));
		entity.setDatumNarozeni((Date)dataRow.get("datum_narozeni")); // TODO: format: YYYY-MM-DD, but it depends on DB-ENGINE! so, i'm fucked!@#$%^&*()
		entity.setPohlavi(
			((String)dataRow.get("pohlavi") == "M"
				? UcitelEntity.POHLAVI.MUZ
				: UcitelEntity.POHLAVI.ZENA
			)
		);

		return entity;
	}



}
