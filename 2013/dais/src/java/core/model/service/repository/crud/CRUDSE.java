package core.model.service.repository.crud;


import core.model.service.repository.crud.storage.StorageDataRow;
import java.util.LinkedList;


abstract public class CRUDSE extends CRUD implements ICRUDSEL
{
	abstract protected LinkedList<StorageDataRow> processSearch(SearchConditions searchConditions);


	protected LinkedList<StorageDataRow> afterSearch(LinkedList<StorageDataRow> processSearchData, SearchConditions searchConditions)
	{
		setDataRow(null, StateCRUD.UPDATE_BEFORE);
		return processSearchData;
	}


	public LinkedList<StorageDataRow> Search(SearchConditions searchConditions)
	{
		if (searchConditions instanceof SearchConditions) {
			if (!searchConditions.isSet()) {
				throw new RuntimeException("Invalid search-conditions! SearchConditions.set() first!");
			}
			return afterSearch(processSearch(searchConditions), searchConditions);
		}
		throw new RuntimeException("Invalid search-conditions! it isn't SearchConditions instance! try: SearchConditions.set() first!");
	}


	@Override
	public boolean Exists()
	{
		if (getState().ordinal() >= StateCRUD.READ.ordinal() && getState().ordinal() < StateCRUD.DELETED.ordinal()) {
			return true;
		}
		return Exists(getPrimaryKey());
	}


	@Override
	public boolean Exists(PrimaryKey pkey)
	{
		if (pkey instanceof PrimaryKey) {
			StorageDataRow rowData = Read(pkey);
			if (!rowData.isEmpty()) {
				return true;
			}
			setDataRow(null, StateCRUD.NONE);
			return false;
		}
		throw new RuntimeException("Invalid primary-key! it isn't PrimaryKey instance! try: PrimaryKey.set() first!");
	}


}
