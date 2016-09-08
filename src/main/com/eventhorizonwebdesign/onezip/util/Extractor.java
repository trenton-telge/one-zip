package com.eventhorizonwebdesign.onezip.util;

import com.eventhorizonwebdesign.jfail.JFail;
import com.github.junrar.Archive;
import com.github.junrar.impl.FileVolumeManager;
import com.github.junrar.rarfile.FileHeader;
import net.lingala.zip4j.core.ZipFile;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarInputStream;

import java.io.*;

/**
 * Created by Trenton on 8/23/2016.
 */
public class Extractor {

    public static boolean smartExtract(File archive, File destination){
        if (!destination.mkdirs()){
            return false;
        }
        switch(FilenameUtils.getExtension(archive.toString())){
            case "zip":
            case "zipx":
                return extractZip(archive, destination);
            case "rar":
                return extractRar(archive, destination);
            case "tar":
                return extractTar(archive, destination);
            case "7z":
                return extract7Zip(archive, destination);
            case "iso":
                return extractIso(archive, destination);
            case "gz":
            case "tgz":
                return extractTGZ(archive, destination);
            default:
                return false;
        }
    }

    public static boolean extractZip(File archive, File destination){
        try {
            ZipFile z = new ZipFile(archive.toString());
            z.extractAll(destination.toString());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            JFail.handleError(e, true);
            return false;
        }
    }

    public static boolean extractRar(File archive, File destination){
        Archive a;
        try {
            a = new Archive(new FileVolumeManager(archive));
        } catch (Exception e) {
            e.printStackTrace();
            JFail.handleError(e, true);
            return false;
        }
        a.getMainHeader().print();
        FileHeader fh = a.nextFileHeader();
        while (fh != null) {
            try {
                File out = new File(destination.toString() + System.getProperty("file.separator") + fh.getFileNameString().trim());
                FileOutputStream os = new FileOutputStream(out);
                a.extractFile(fh, os);
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                JFail.handleError(e, true);
                return false;
            }
            fh = a.nextFileHeader();
        }
        return true;
    }

    public static boolean extractTar(File archive, File destination){
        try {
            TarInputStream tis = new TarInputStream(new BufferedInputStream(new FileInputStream(archive)));
            TarEntry entry;

            while ((entry = tis.getNextEntry()) != null) {
                int count;
                byte data[] = new byte[2048];
                FileOutputStream fos = new FileOutputStream(destination.toString() + "/" + entry.getName());
                BufferedOutputStream dest = new BufferedOutputStream(fos);

                while ((count = tis.read(data)) != -1) {
                    dest.write(data, 0, count);
                }

                dest.flush();
                dest.close();
            }

            tis.close();
        } catch (Exception e){
            e.printStackTrace();
            JFail.handleError(e, true);
            return false;
        }
        return true;
    }

    public static boolean extract7Zip(File archive, File destination){
        try {
            SevenZFile sevenZFile = new SevenZFile(archive);
            SevenZArchiveEntry entry = sevenZFile.getNextEntry();
            while (entry != null) {
                System.out.println(entry.getName());
                FileOutputStream out = new FileOutputStream(destination.toString() + System.getProperty("file.separator") + entry.getName());
                byte[] content = new byte[(int) entry.getSize()];
                sevenZFile.read(content, 0, content.length);
                out.write(content);
                out.close();
                entry = sevenZFile.getNextEntry();
            }
            sevenZFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            JFail.handleError(e, true);
            return false;
        }
        return true;
    }

    public static boolean extractIso(File archive, File destination){
        try {
            FileSystemManager fsManager = VFS.getManager();
            FileObject isoFile = fsManager.resolveFile("iso:" + archive.toString());
            FileObject destObj = fsManager.createVirtualFileSystem(destination.getAbsolutePath());
            destObj.copyFrom(isoFile, new AllFileSelector());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JFail.handleError(e, true);
            return false;
        }
    }

    public static boolean extractTGZ(File archive, File destination){
        try{
            final TarGZipUnArchiver ua = new TarGZipUnArchiver();
            ua.setSourceFile(archive);
            ua.setDestDirectory(destination);
            ua.extract();
        } catch (Exception e){
            e.printStackTrace();
            JFail.handleError(e, true);
            return false;
        }
        return true;
    }
}
