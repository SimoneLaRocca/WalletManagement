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

import it.unisa.walletmanagement.Model.Entity.Movimento;
import it.unisa.walletmanagement.Model.Storage.DatabaseHelper;
import it.unisa.walletmanagement.Model.Storage.SchemaDB;

public class MovimentoDAO {
    private DatabaseHelper databaseHelper;

    public MovimentoDAO(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public boolean insertMovimento(String nome, float importo, int tipo, GregorianCalendar data, String categoria, String nome_conto){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String text_date = simpleDateFormat.format(data.getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(SchemaDB.Movimento.COLUMN_NOME, nome);
        contentValues.put(SchemaDB.Movimento.COLUMN_IMPORTO, importo);
        contentValues.put(SchemaDB.Movimento.COLUMN_TIPO, tipo);
        contentValues.put(SchemaDB.Movimento.COLUMN_CATEGORIA, categoria);
        contentValues.put(SchemaDB.Movimento.COLUMN_DATA, text_date);
        contentValues.put(SchemaDB.Movimento.COLUMN_NOME_CONTO, nome_conto);
        sqLiteDatabase.insert(SchemaDB.Movimento.TABLE_NAME, null, contentValues);

        return true;
    }

    public boolean insertMovimento(Movimento movimento, String nome_conto){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String text_date = simpleDateFormat.format(movimento.getData().getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(SchemaDB.Movimento.COLUMN_NOME, movimento.getNome());
        contentValues.put(SchemaDB.Movimento.COLUMN_IMPORTO, movimento.getImporto());
        contentValues.put(SchemaDB.Movimento.COLUMN_TIPO, movimento.getTipo());
        contentValues.put(SchemaDB.Movimento.COLUMN_CATEGORIA, movimento.getCategoria());
        contentValues.put(SchemaDB.Movimento.COLUMN_DATA, text_date);
        contentValues.put(SchemaDB.Movimento.COLUMN_NOME_CONTO, nome_conto);
        sqLiteDatabase.insert(SchemaDB.Movimento.TABLE_NAME, null, contentValues);

        return true;
    }

    // usato per cambiare un movimento da un conto a un altro
    public boolean updateMovimento(Movimento movimento, String nome_conto){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String text_date = simpleDateFormat.format(movimento.getData().getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(SchemaDB.Movimento.COLUMN_NOME, movimento.getNome());
        contentValues.put(SchemaDB.Movimento.COLUMN_IMPORTO, movimento.getImporto());
        contentValues.put(SchemaDB.Movimento.COLUMN_TIPO, movimento.getTipo());
        contentValues.put(SchemaDB.Movimento.COLUMN_CATEGORIA, movimento.getCategoria());
        contentValues.put(SchemaDB.Movimento.COLUMN_DATA, text_date);
        contentValues.put(SchemaDB.Movimento.COLUMN_NOME_CONTO, nome_conto);
        sqLiteDatabase.insert(SchemaDB.Movimento.TABLE_NAME, null, contentValues);
        String where = SchemaDB.Movimento.COLUMN_ID + "=?";
        String whereArgs[]= {String.valueOf(movimento.getId())};

        sqLiteDatabase.update(SchemaDB.Movimento.TABLE_NAME, contentValues, where, whereArgs);
        return true;
    }

    public boolean updateMovimento(Movimento movimento){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String text_date = simpleDateFormat.format(movimento.getData().getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(SchemaDB.Movimento.COLUMN_NOME, movimento.getNome());
        contentValues.put(SchemaDB.Movimento.COLUMN_IMPORTO, movimento.getImporto());
        contentValues.put(SchemaDB.Movimento.COLUMN_TIPO, movimento.getTipo());
        contentValues.put(SchemaDB.Movimento.COLUMN_CATEGORIA, movimento.getCategoria());
        contentValues.put(SchemaDB.Movimento.COLUMN_DATA, text_date);
        //contentValues.put(SchemaDB.Movimento.COLUMN_NOME_CONTO, nome_conto);
        sqLiteDatabase.insert(SchemaDB.Movimento.TABLE_NAME, null, contentValues);
        String where = SchemaDB.Movimento.COLUMN_ID + "=?";
        String whereArgs[]= {String.valueOf(movimento.getId())};

        sqLiteDatabase.update(SchemaDB.Movimento.TABLE_NAME, contentValues, where, whereArgs);
        return true;
    }

    public boolean deleteMovimento(int id){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(SchemaDB.Movimento.TABLE_NAME,
                SchemaDB.Movimento.COLUMN_ID+"=?",
                new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return true;
    }

    public List<Movimento> doRetrieveAll(){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        ArrayList<Movimento> movimenti = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String query = "SELECT * FROM "+SchemaDB.Movimento.TABLE_NAME;

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{});
        if(cursor.getCount() <= 0){
            return null;
        }
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
        sqLiteDatabase.close();
        cursor.close();
        return movimenti;
    }

    public List<Movimento> doRetrieveByType(int tipo){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        ArrayList<Movimento> movimenti = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String query = "SELECT * FROM "+SchemaDB.Movimento.TABLE_NAME+
                " WHERE "+SchemaDB.Movimento.COLUMN_TIPO+" = ?";

        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(tipo)});
        if(cursor.getCount() <= 0){
            return null;
        }
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
        sqLiteDatabase.close();
        cursor.close();
        return movimenti;
    }
}
