package com.eventhorizonwebdesign.onezip.util;

import com.eventhorizonwebdesign.jfail.JFail;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Trenton on 8/25/2016.
 */
public class Compressor {

    public static boolean smartCompress(List<File> inputFiles, File destination, String type){
        if (!destination.mkdirs()){
            return false;
        }
        switch(type){
            case "zip":
                return compressZip(inputFiles, destination);
            case "zipx":
                return compressZipx(inputFiles, destination);
            case "rar":
                return compressRar(inputFiles, destination);
            case "tar":
                return compressTar(inputFiles, destination);
            case "7z":
                return compress7Zip(inputFiles, destination);
            case "iso":
                return compressIso(inputFiles, destination);
            case "gz":
            case "tgz":
                return compressTGZ(inputFiles, destination);
            default:
                return false;
        }
    }
    public static boolean compressZip(List<File> inputFiles, File destination){
        try {
            OutputStream zip_output = new FileOutputStream(destination);
            ArchiveOutputStream logical_zip = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, zip_output);
            for (File f : inputFiles) {
                logical_zip.putArchiveEntry(new ZipArchiveEntry(f.getName()));
                IOUtils.copy(new FileInputStream(f), logical_zip);
                logical_zip.closeArchiveEntry();
            }
            logical_zip.finish();
            zip_output.close();
        } catch (Exception e){
            e.printStackTrace();
            JFail.handleError(e, true);
            return false;
        }
        return true;
    }
}
