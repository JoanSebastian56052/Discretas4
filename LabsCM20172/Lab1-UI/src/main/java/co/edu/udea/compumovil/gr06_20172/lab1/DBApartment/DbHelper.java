package co.edu.udea.compumovil.gr06_20172.lab1.DBApartment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr06_20172.lab1.Model.Apartment;
import co.edu.udea.compumovil.gr06_20172.lab1.Model.Usuario;

/**
 * Created by Viviana Londoño on 22/08/2017.
 */

public class DbHelper extends SQLiteOpenHelper{
    private static final String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {
        super(context, DBAppApartment.DB_NAME, null, DBAppApartment.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        //Crear la tabla users
        sql = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s BLOB)",
                DBAppApartment.TABLE_USERS, TableColumnsUser.NOMBRE, TableColumnsUser.APELLIDO, TableColumnsUser.CONTRASEÑA,
                TableColumnsUser.EMAIL, TableColumnsUser.CIUDAD, TableColumnsUser.TELEFONO, TableColumnsUser.DIRECCION,
                TableColumnsUser.DATE, TableColumnsUser.GENERO, TableColumnsUser.FOTO);

        //Sentencia para crear la tabla
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);

        //Crear la tabla events
        sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, " +
                        "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s BLOB)",
                DBAppApartment.TABLE_APARTMENTS, TableColumnsApartments.ID, TableColumnsApartments.NOMBRE,
                TableColumnsApartments.TIPO, TableColumnsApartments.DESCRIPCION, TableColumnsApartments.AREA,
                TableColumnsApartments.DIRECCION, TableColumnsApartments.VALOR,
                TableColumnsApartments.FOTO);

        //Sentencia para crear la tabla
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;

        sql = String.format("DROP TABLE IF EXISTS %s", DBAppApartment.TABLE_USERS);
        // Sentencia para borrar la tabla
        Log.d(TAG, "onUpdrage with SQL: " + sql);
        db.execSQL(sql);

        sql = String.format("DROP TABLE IF EXISTS %s", DBAppApartment.TABLE_APARTMENTS);
        // Sentencia para borrar la tabla
        Log.d(TAG, "onUpdrage with SQL: " + sql);
        db.execSQL(sql);

        onCreate(db);
    }

    public void insertar(String tabla, ContentValues valores) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Se guarda la fila en la base de datos
        try {
            db.insertWithOnConflict(tabla, null, valores, SQLiteDatabase.CONFLICT_IGNORE);
            Log.d(TAG, "Insertado en la base de datos");
        } catch (Exception ex) {
            Log.e(TAG, "Error al insertar en la base de datos");
        } finally {
            db.close();
        }
    }

    public ArrayList<Apartment> consultarApartamentos() {
        String[] campos = new String[]{TableColumnsApartments.NOMBRE,
                TableColumnsApartments.TIPO, TableColumnsApartments.DESCRIPCION, TableColumnsApartments.AREA,
                TableColumnsApartments.DIRECCION, TableColumnsApartments.VALOR,
                TableColumnsApartments.FOTO};
        ArrayList<Apartment> retornoConsulta = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            Cursor c = db.query(DBAppApartment.TABLE_APARTMENTS, campos, null, null, null, null, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    Apartment apartamento = new Apartment();
                    apartamento.setNombre(c.getString(c.getColumnIndex(TableColumnsApartments.NOMBRE)));
                    apartamento.setDescripcion(c.getString(c.getColumnIndex(TableColumnsApartments.DESCRIPCION)));
                    apartamento.setTipo(c.getString(c.getColumnIndex(TableColumnsApartments.TIPO)));
                    apartamento.setArea(c.getString(c.getColumnIndex(TableColumnsApartments.AREA)));
                    apartamento.setDireccion(c.getString(c.getColumnIndex(TableColumnsApartments.DIRECCION)));
                    apartamento.setValor(c.getString(c.getColumnIndex(TableColumnsApartments.VALOR)));
                    apartamento.setFoto(c.getBlob(c.getColumnIndex(TableColumnsApartments.FOTO)));
                    retornoConsulta.add(apartamento);
                } while (c.moveToNext());
                Log.d(TAG, "Se ha consultado en la base de datos");
            }else{
                Log.d(TAG,"No hay registro");
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error al consultar en la base de datos");
        } finally {
            db.close();
        }
        return retornoConsulta;
    }

    public Usuario consultarUsuario(String user) {
        String[] campos = new String[]{TableColumnsUser.EMAIL, TableColumnsUser.FOTO};
        String[] argumentos = new String[]{user};
        String consulta = TableColumnsUser.EMAIL + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Usuario usuario = null;
        try {
            Cursor c = db.query(DBAppApartment.TABLE_USERS, campos, consulta, argumentos, null, null, null);
            //Nos aseguramos de que existe al menos {un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    usuario = new Usuario();
                    usuario.setUsuario(c.getString(c.getColumnIndex(TableColumnsUser.NOMBRE)));
                    usuario.setApellido(c.getString(c.getColumnIndex(TableColumnsUser.APELLIDO)));
                    usuario.setEmail(c.getString(c.getColumnIndex(TableColumnsUser.EMAIL)));
                    usuario.setDireccion(c.getString(c.getColumnIndex(TableColumnsUser.DIRECCION)));
                    usuario.setCiudad(c.getString(c.getColumnIndex(TableColumnsUser.CIUDAD)));
                    usuario.setGenero(c.getString(c.getColumnIndex(TableColumnsUser.GENERO)));
                    usuario.setTelefono(c.getString(c.getColumnIndex(TableColumnsUser.TELEFONO)));
                    usuario.setFoto(c.getBlob(c.getColumnIndex(TableColumnsUser.FOTO)));
                    usuario.setFechaNacimiento(c.getString(c.getColumnIndex(TableColumnsUser.DATE)));
                } while (c.moveToNext());
                Log.d(TAG, "Se ha consultado en la base de datos");
            }else{
                Log.d(TAG,"No hay registro");
            }
            c.close();
        } catch (Exception ex) {
            Log.e(TAG, "Error al consultar en la base de datos");
        } finally {
            db.close();
        }
        return usuario;
    }

    public boolean consultarUsuarioInicio(String user, String pass){
        String[] campos = new String[]{TableColumnsUser.EMAIL};
        String[] argumentos = new String[]{user, pass};
        String consulta = TableColumnsUser.EMAIL + "=? AND " + TableColumnsUser.CONTRASEÑA + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        boolean retorno = false;
        try {
            Cursor c = db.query(DBAppApartment.TABLE_USERS, campos, consulta, argumentos, null, null, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    retorno = true;
                } while (c.moveToNext());
                Log.d(TAG, "Se ha consultado en la base de datos");
            }else{
                Log.d(TAG,"No hay existe el registro");
                retorno = false;
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error al consultar en la base de datos");
        } finally {
            db.close();
        }
        return retorno;
    }

    public boolean consultarUsuarioRegistro(String user){
        String[] campos = new String[]{TableColumnsUser.EMAIL};
        String[] argumentos = new String[]{user};
        String consulta = TableColumnsUser.EMAIL + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        boolean retorno = false;
        try {
            Cursor c = db.query(DBAppApartment.TABLE_USERS, campos, consulta, argumentos, null, null, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    retorno = true;
                } while (c.moveToNext());
                Log.d(TAG, "Se ha consultado en la base de datos");
            }else{
                Log.d(TAG,"No hay existe el registro");
                retorno = false;
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error al consultar en la base de datos");
        } finally {
            db.close();
        }
        return retorno;
    }


}
