package com.kpi.dao;

import java.util.List;

import com.kpi.beans.Objectif;

public interface ObjectifDao {
	public List<Objectif> listeObjectifs () throws DaoException;
}
