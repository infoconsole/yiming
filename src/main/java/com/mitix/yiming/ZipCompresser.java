package com.mitix.yiming;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 
 * 创建于:2014-3-13<br>
 * 版权所有(C) 2014 深圳市银之杰科技股份有限公司<br>
 * zip压缩工具类，压缩方法建议new对象使用
 * 
 * @author honglvhang
 * @version 1.0.0.0
 */
public class ZipCompresser {
	/**
	 * 把文档归档到zipFile 支持该路径为一个文件或者一个目录，当ffile为一个目录时，则会压缩目录下所有的文件内容
	 * 
	 * 
	 * @param ffile
	 *            原始文档
	 * @param zipFile
	 *            归档文件目录
	 * @throws Exception
	 *             异常
	 */
	public void compress(String ffile, String zipFile) throws Exception {
		File file = new File(ffile);
		if (!file.exists()) {
			throw new RuntimeException("原始文档不存在。。。");
		}
		File[] ffs = null;
		// 原始文档为文件类型不为文件夹类型
		if (!file.isDirectory()) {
			ffs = new File[1];
			ffs[0] = file;
		} else {
			// 原始文档为文件夹类型
			ffs = file.listFiles();
			if (ffs.length == 0) {
				throw new RuntimeException("原始文档为文件夹类型，目录下无文件。。。");
			}
		}
		this.compress(ffs, zipFile);
	}

	/**
	 * 把文档归档到zipFile ffs为文档数组 当ffs数组中有和zipFile同样的目录，原归档文件和压缩文件是同一个路径的同一文件，则跳过压缩
	 * 例如D:\AA\AA.ZIP压缩到D:\AA\AA.ZIP 目前如果根目录为空文件夹则返回原始文档空异常
	 * 1.目前遗留问题，如果多级目录，有子目录的文件夹为空则无法压缩进入文件夹
	 * 
	 * @param ffs
	 *            原始文档数组
	 * @param zipFile
	 *            归档文件目录
	 * @throws Exception
	 *             异常
	 */
	public void compress(File[] ffs, String zipFile) throws Exception {
		if (ffs == null || ffs.length == 0) {
			throw new RuntimeException("原始文档不存在。。。");
		}
		if (!(zipFile.toUpperCase().endsWith(".ZIP"))) {
			throw new RuntimeException("zipFile=【" + zipFile + "】不是一个标准的zip目录。。。");
		}
		File zfile = new File(zipFile);
		if (!zfile.getParentFile().exists()) {
			if (!zfile.getParentFile().mkdirs()) {
				throw new RuntimeException("创建归档父目录失败zipFile=【" + zipFile + "】。。。");
			}
		}
		ZipArchiveOutputStream zaos = null;
		try {
			zaos = new ZipArchiveOutputStream(zfile);
			zaos.setUseZip64(Zip64Mode.AsNeeded);
			for (File ff : ffs) {
				// 单独调用传入文件数组的时候，外部可能为空，进行了修正
				if (ff != null) {
					// 如果压缩的还是一个文件夹，则进行递归
					if (ff.isDirectory()) {
						recCompress(ff, ff.getName(), zipFile, zaos);
					} else {
						// 防止把自己打包到文件中
						if (!zfile.getPath().equals(ff.getPath())) {
							ZipArchiveEntry zae = new ZipArchiveEntry(ff,
									ff.getName());
							zaos.putArchiveEntry(zae);
							InputStream ins = null;
							try {
								ins = new FileInputStream(ff);
								IOUtil.copy(ins, zaos);
								zaos.closeArchiveEntry();
							} catch (Exception e) {
								throw new RuntimeException(e);
							} finally {
								IOUtil.closeQuietly(ins);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.closeQuietly(zaos);
		}
	}

	/**
	 * 递归解压缩子文件夹
	 * 
	 * @param file
	 * @param zname
	 * @param zipFile
	 * @param zaos
	 * @throws Exception
	 */
	private void recCompress(File file, String zname, String zipFile,
			ZipArchiveOutputStream zaos) throws Exception {
		// 原始文档为文件夹类型
		File[] ffs = file.listFiles();
		// 空文件夹不压缩
		if (ffs.length == 0) {
			return;
		}
		File zfile = new File(zipFile);
		try {
			for (File ff : ffs) {
				// 如果压缩的还是一个文件夹，则进行递归
				if (ff.isDirectory()) {
					recCompress(ff, zname + "/" + ff.getName(), zipFile, zaos);
				} else {
					// 防止把自己打包到文件中
					if (!zfile.getPath().equals(ff.getPath())) {
						ZipArchiveEntry zae = new ZipArchiveEntry(ff, zname
								+ "/" + ff.getName());
						zaos.putArchiveEntry(zae);
						InputStream ins = null;
						try {
							ins = new FileInputStream(ff);
							IOUtil.copy(ins, zaos);
							zaos.closeArchiveEntry();
						} catch (Exception e) {
							throw new RuntimeException(e);
						} finally {
							IOUtil.closeQuietly(ins);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解压缩归档文件
	 * 
	 * @param absoluteZipFilePath
	 *            文件路径字符串
	 * @param absoluteSaveFilePath
	 *            文件保存目录
	 * @throws Exception
	 *             异常
	 */
	public static void decompress(String absoluteZipFilePath,
			String absoluteSaveFilePath) throws Exception {
		if (!(absoluteZipFilePath.toUpperCase().endsWith(".ZIP"))) {
			throw new RuntimeException("absoluteZipFilePath=【"
					+ absoluteZipFilePath + "】不是一个标准的zip目录。。。");
		}
		File zipfile = new File(absoluteZipFilePath);
		if (!zipfile.exists()) {
			throw new RuntimeException("归档文件不存在。。。");
		}
		File savefile = new File(absoluteSaveFilePath + File.separator);
		if (!savefile.exists()) {
			if (!savefile.mkdirs()) {
				throw new RuntimeException("创建解压缩目录失败savefile=【" + savefile
						+ "】。。。");
			}
		}
		decompress(zipfile, savefile);
	}

	/**
	 * 解压缩归档文件
	 * 
	 * @param zipfile
	 *            压缩文件file
	 * @param savefile
	 *            解压缩文件file
	 * @throws Exception
	 *             异常
	 */
	public static void decompress(File zipfile, File savefile) throws Exception {
		// can read Zip archives
		ZipArchiveInputStream zais = null;
		try {
			zais = new ZipArchiveInputStream(new FileInputStream(zipfile));
			ZipArchiveEntry ziparchiveentry = null;
			while ((ziparchiveentry = zais.getNextZipEntry()) != null) {
				FileOutputStream os = null;
				try {
					// 带分隔符的目录处理
					if (ziparchiveentry.isDirectory()) {
						File nfile = new File(savefile + File.separator
								+ ziparchiveentry.getName());
						nfile.mkdirs();
					} else {
						String fileLname = ziparchiveentry.getName().substring(
								0,
								ziparchiveentry.getName().lastIndexOf("/") + 1);
						File nfile = new File(savefile + File.separator
								+ fileLname);
						if (!nfile.exists()) {
							nfile.mkdirs();
						}
						String entryLname = ziparchiveentry.getName()
								.substring(
										ziparchiveentry.getName().lastIndexOf(
												"/") + 1);
						os = new FileOutputStream(new File(nfile
								+ File.separator + entryLname));
						IOUtil.copy(zais, os);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					IOUtil.closeQuietly(os);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.closeQuietly(zais);
		}
	}

}
