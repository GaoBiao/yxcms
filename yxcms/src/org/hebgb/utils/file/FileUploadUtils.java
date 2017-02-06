package org.hebgb.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hebgb.utils.file.exception.FileExtExecption;
import org.hebgb.utils.file.exception.FileSizeException;

public class FileUploadUtils {
	private String fileTypes;
	private String rootDir;
	private String savePath;
	public static String baseUrl;
	private int maxFileSize;
	private int byteSize = 1024;

	protected String getRealPath(String s) {
		String separator = "/";
		if ("/".equalsIgnoreCase(separator)) {
			s = s.replace('\\', '/');
		} else {
			s = s.replace('/', '\\');
		}
		return s;
	}

	protected FileOutputStream getOutputStream(String fileName) throws IOException {
		fileName = getRealPath(fileName);
		File file = new File(fileName);
		// 创建文件目录
		File dir = file.getParentFile();
		if (dir != null && !dir.exists()) {
			dir.mkdirs();
		}
		// 判断文件是否存在，存在则先删除
		if (file.exists()) {
			file.delete();
		}
		return new FileOutputStream(fileName);
	}

	protected void validateFileType(String ext) throws FileExtExecption {
		if (StringUtils.isNotEmpty(getFileTypes())) {
			String[] exts = getFileTypes().split(",");
			if (!ArrayUtils.contains(exts, ext)) {
				throw new FileExtExecption("文件格式错误");
			}
		}
	}

	/**
	 * 保存上传文件，返回保存后的相对路径
	 * 
	 * @param in
	 * @param fileName
	 * @param folderDir
	 * @param rename
	 * @return
	 * @throws IOException
	 */
	public String save(FileInputStream in, String fileName, String folderDir, boolean rename) throws IOException {
		String filePath = getSavePath() + folderDir + "/";
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		// 验证文件格式
		validateFileType(ext);
		// 验证文件大小
		if (in.available() > maxFileSize) {
			throw new FileSizeException("文件超出限制大小");
		}
		if (rename) {
			filePath += UUID.randomUUID() + "." + ext;
		} else {
			filePath += fileName;
		}
		FileOutputStream out = getOutputStream(rootDir + filePath);
		byte[] b = new byte[byteSize];
		while (in.read(b) != -1) {
			out.write(b);
		}
		out.flush();
		in.close();
		out.close();
		return filePath;
	}

	public String getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir.endsWith("/") ? rootDir : rootDir + "/";
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		if (savePath.startsWith("/")) {
			savePath = savePath.substring(1);
		}
		if (!savePath.endsWith("/")) {
			savePath += "/";
		}
		this.savePath = savePath;
	}

	public void setBaseUrl(String baseUrl) {
		FileUploadUtils.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
	}

	public int getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public int getByteSize() {
		return byteSize;
	}

	public void setByteSize(int byteSize) {
		this.byteSize = byteSize;
	}
}
