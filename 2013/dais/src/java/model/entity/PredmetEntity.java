package model.entity;


import core.model.entity.IEntity;


public class PredmetEntity implements IEntity
{
	private int id;
	private String nazev;


	@Override
	public void reset()
	{
		id    = 0;
		nazev = null;
	}


	public void setId(int id) { this.id = id; }
	public void setNazev(String nazev) { this.nazev = nazev; }

	public int    getId()    { return id; }
	public String getNazev() { return nazev; }


}

