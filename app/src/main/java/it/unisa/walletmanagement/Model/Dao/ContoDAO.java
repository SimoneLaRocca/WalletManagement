package it.unisa.walletmanagement.Model.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import it.unisa.walletmanagement.Model.Entity.Conto;
import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.Model.Storage.DatabaseHelper;
import it.unisa.walletmanagement.Model.Storage.SchemaDB;

public class ContoDAO {
    private DatabaseHelper databaseHelper;

    public ContoDAO(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public boolean insertConto(String nome, float saldo){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        if(nome.equals("") || saldo < 0){
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(SchemaDB.Conto.COLUMN_NOME, nome);
        contentValues.put(SchemaDB.Conto.COLUMN_SALDO, saldo);
        sqLiteDatabase.insert(SchemaDB.Conto.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return true;
    }

    public boolean deleteConto(String nome){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(SchemaDB.Conto.TABLE_NAME, SchemaDB.Conto.COLUMN_NOME+"=?", new String[]{nome});
        sqLiteDatabase.close();
        return true;
    }

    public Conto doRetrieveByName(String nome){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Conto conto = new Conto();
        ArrayList<Movimento> movimenti = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String query = null;

        // record tabella conto
        query = "SELECT * FROM "+SchemaDB.Conto.TABLE_NAME+
                " WHERE "+SchemaDB.Conto.COLUMN_NOME+" = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{nome});
        if(cursor.getCount() <= 0){
            return null;
        }
        cursor.moveToFirst();
        conto.setNome(cursor.getString(cursor.getColumnIndexOrThrow(SchemaDB.Conto.COLUMN_NOME)));
        conto.setSaldo(cursor.getFloat(cursor.getColumnIndexOrThrow(SchemaDB.Conto.COLUMN_SALDO)));

        // record tabella movimento
        query = "SELECT * FROM "+SchemaDB.Movimento.TABLE_NAME+
                " WHERE "+SchemaDB.Movimento.COLUMN_NOME_CONTO+" = ?";
        cursor = sqLiteDatabase.rawQuery(query, new String[]{nome});
        while (cursor.moveToNext()){
            Movimento m = new Movimento();
            m.setId(cursor.getInt(cursor.getColumnIndexOrThrow(SchemaDB.Movimento.COLUMN_ID)));
            m.setNome(cursor.getString(cursor.getColumnIndexOrThrow(SchemaDB.Movimento.COLUMN_NOME)));
            m.setImporto(cursor.getFloat(cursor.getColumnIndexOrThrow(SchemaDB.Movimento.COLUMN_IMPORTO)));
            m.setTipo(cursor.getInt(cursor.getColumnIndexOrThrow(SchemaDB.Movimento.COLUMN_TIPO)));
            m.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(SchemaDB.Movimento.COLUMN_CATEGORIA)));
            // data
            try {
                String text_date = cursor.getString(cursor.getColumnIndexOrThrow(SchemaDB.Movimento.COLUMN_DATA));
                Date date = simpleDateFormat.parse(text_date);
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                m.setData(calendar);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            movimenti.add(m);
        }
        conto.setMovimenti(movimenti);
        sqLiteDatabase.close();
        cursor.close();
        return conto;
    }

    public List<Conto> doRetrieveAll(){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        ArrayList<Conto> conti = new ArrayList<>();
        String query = "SELECT * FROM "+SchemaDB.Conto.TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        if(cursor.getCount() <= 0){
            return null;
        }
        while (cursor.moveToNext()){
            Conto c = new Conto();
            c.setNome(cursor.getString(cursor.getColumnIndexOrThrow(SchemaDB.Conto.COLUMN_NOME)));
            c.setSaldo(cursor.getFloat(cursor.getColumnIndexOrThrow(SchemaDB.Conto.COLUMN_SALDO)));
            c.setMovimenti(null);
            conti.add(c);
        }
        sqLiteDatabase.close();
        cursor.close();
        return conti;
    }

    public List<Conto> doRetrieveAllWithCurrentBalance(){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        ArrayList<Conto> conti = new ArrayList<>();
        String query = "SELECT * FROM "+SchemaDB.Conto.TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        if(cursor.getCount() <= 0){
            return null;
        }
        while (cursor.moveToNext()){
            Conto c = new Conto();
            c.setNome(cursor.getString(cursor.getColumnIndexOrThrow(SchemaDB.Conto.COLUMN_NOME)));
            c.setSaldo(cursor.getFloat(cursor.getColumnIndexOrThrow(SchemaDB.Conto.COLUMN_SALDO)));
            c.setMovimenti(null);
            conti.add(c);
        }

        // restituisce la somma dei movimenti per un dato conto
        query = "SELECT " +
                "COALESCE((SELECT SUM(" + SchemaDB.Movimento.COLUMN_IMPORTO + ") " +
                "FROM " + SchemaDB.Movimento.TABLE_NAME + " " +
                "WHERE " + SchemaDB.Movimento.COLUMN_TIPO + " = 1 AND " + SchemaDB.Movimento.COLUMN_NOME_CONTO + " = ?), 0) - " +
                "COALESCE((SELECT SUM(" + SchemaDB.Movimento.COLUMN_IMPORTO + ") " +
                "FROM " + SchemaDB.Movimento.TABLE_NAME + " " +
                "WHERE " + SchemaDB.Movimento.COLUMN_TIPO + " = 0 AND " + SchemaDB.Movimento.COLUMN_NOME_CONTO + " = ?), 0) AS sum";
        for(Conto c : conti){
            cursor = sqLiteDatabase.rawQuery(query, new String[]{c.getNome(), c.getNome()});
            if(cursor.moveToNext()){
                c.setSaldo(c.getSaldo() + cursor.getFloat(cursor.getColumnIndexOrThrow("sum")));
            }
        }

        sqLiteDatabase.close();
        cursor.close();
        return conti;
    }

    public int doCount(){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        String query = "SELECT COUNT("+SchemaDB.Conto.COLUMN_NOME+") AS tot FROM "+SchemaDB.Conto.TABLE_NAME;
        int value = 0;

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        if(cursor.moveToNext()){
            value = cursor.getInt(cursor.getColumnIndexOrThrow("tot"));
        }

        sqLiteDatabase.close();
        cursor.close();
        return value;
    }
}
