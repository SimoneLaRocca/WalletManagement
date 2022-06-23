package it.unisa.walletmanagement.Model.Storage;

import android.provider.BaseColumns;

public class SchemaDB {

    public SchemaDB() {
    }

    public static abstract class Conto implements BaseColumns {
        public static final String TABLE_NAME = "conto";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_SALDO = "saldo";
    }

    public static abstract class Movimento implements BaseColumns {
        public static final String TABLE_NAME = "movimento";
        public static final String COLUMN_ID = "id_movimento";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_IMPORTO = "importo";
        public static final String COLUMN_TIPO = "tipo";
        public static final String COLUMN_DATA = "data";
        public static final String COLUMN_CATEGORIA = "categoria";
        public static final String COLUMN_NOME_CONTO = "nome_conto";
    }
}
