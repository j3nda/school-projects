package model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.service.repository.crud.storage.SqlOracleStorage;
import resources.ResourceManager;


public class PredmetDao
{
        private static final String tableName = "predmet";
        private static final List<String> pkColumns = new ArrayList<>(Arrays.asList("id"));
        private static final List<String> dataColumns = new ArrayList<>(Arrays.asList("nazev"));
        private static final List<String> allColumns = new ArrayList<>(Arrays.asList("id", "nazev"));

        private static final SqlOracleStorage mys = new SqlOracleStorage(ResourceManager.getSqlDataSource(), null);
        /**
         * Vyhledá entitu podle PK a vrátí ji, pokud existuje.
         * @param id
         * @return PredmetEntity
         */
        public static PredmetEntity seek(int id)
        {   //SqlOracleStorage mys = new SqlOracleStorage(ResourceManager.getSqlDataSource(), null);
            final List<Integer> pkVal = new ArrayList<>(Arrays.asList(id));

            final String q = "SELECT " + commaColumns(allColumns) + " FROM " + tableName + " WHERE " + makeWhereConditions(pkColumns, pkVal);

            ResultSet rs = mys.Query(q);
            try {
            if (rs.next())
            {   final PredmetEntity result = new PredmetEntity();
                result.setId(rs.getInt(1));
                result.setNazev(rs.getString(2));
                return result;
            }
            } catch (SQLException ex) { Logger.getLogger(PredmetDao.class.getName()).log(Level.SEVERE, null, ex); }
            return null;
        }

        /**
         * Vloží nový přemět.
         * @param predmet
         * @return boolean true, když se povedlo
         */
        public static boolean insert(PredmetEntity predmet)
        {   //SqlOracleStorage mys = new SqlOracleStorage(ResourceManager.getSqlDataSource(), null);
            final ArrayList<Object> vals = new ArrayList<>(Arrays.asList((Object)predmet.getId(), predmet.getNazev()));
        
            final String q = "INSERT INTO " + tableName + "(" + commaColumns(allColumns) + ") VALUES (" + commaValues(vals) + ")";

            int rc = mys.ExecuteUpdate(q);
            return rc == 1;
        }

        /** Aktualizuje entitu.
         * @param predmet
         * @return boolean true, když se povedlo
         */
        public static boolean update(PredmetEntity predmet)
        {   //SqlOracleStorage mys = new SqlOracleStorage(ResourceManager.getSqlDataSource(), null);
            final List<Integer> pkVal = new ArrayList<>(Arrays.asList(predmet.getId()));
            final ArrayList<Object> vals = new ArrayList<>(Arrays.asList((Object)predmet.getNazev()));

            final String q = "UPDATE " + tableName + " SET " + makeSet(dataColumns, vals) + " WHERE " + makeWhereConditions(pkColumns, pkVal);

            int rc = mys.ExecuteUpdate(q);
            return rc == 1;
        }

        /** Odstraní záznam entity z DB.
         * @param id
         * @return boolean true, když se povedlo
         */
        public static boolean delete(int id)
        {   //SqlOracleStorage mys = new SqlOracleStorage(ResourceManager.getSqlDataSource(), null);
            final List<Integer> pkVal = new ArrayList<>(Arrays.asList(id));

            final String q = "DELETE FROM " + tableName + " WHERE " + makeWhereConditions(pkColumns, pkVal);

            int rc = mys.ExecuteUpdate(q);
            return rc == 1;
        }

        private static String commaColumns(List<String> cols)
        {   String result = "";
            for (String colName : cols)
                result += ", " + colName;
            // FIXME ošetřit prázdné vstupní cols
            return result.substring(2);
        }

        // dvojici cols, vals nahradit společnou třídou (PrimaryKey)
        private static String makeWhereConditions(List<String> cols, List<Integer> vals)
        {   String result = "";
            for (int i=0; i<cols.size(); i++)
                result += " and " + cols.get(i) + " = " + vals.get(i).toString();
            // FIXME ošetřit prázdné vstupní cols
            return result.substring(5);
        }

        // vlastně je to stejné jako commaColumns
        private static String commaValues(List<Object> vals)
        {   String result = "";
            for (Object val : vals)
            {   if (val instanceof String) result += ", '" + val + "'";
                else result += ", " + val.toString();
            }

            return result.substring(2);
        }

        private static String makeSet(List<String> cols, List<Object> vals)
        {   String result = "";
            for (int i=0; i<cols.size(); i++)
            {   result += ", " + cols.get(i) + " = ";
                if (vals.get(i) instanceof String) result += "'" + vals.get(i) + "'";
                    else result += vals.get(i).toString();
            }

            return result.substring(2);
        }

}