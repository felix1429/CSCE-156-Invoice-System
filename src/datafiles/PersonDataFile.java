package datafiles;

import datafiles.DataFile;

import java.io.IOException;

public class PersonDataFile extends DataFile {

    public PersonDataFile (String filePath) throws IOException {
        super(filePath);
        this.JSONName = "persons";
    }
}
