package br.dayanelima.voudebike.data.database;

/**
 * Callback interface for writing into the Database
 */
public interface IDataBaseWriteCallback {

    void dataWritten(boolean success);

}
