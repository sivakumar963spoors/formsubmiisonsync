package com.effort.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.effort.dao.MediaDao;
import com.effort.entity.Media;
import com.effort.settings.Constants;
import com.effort.settings.ConstantsExtra;
import com.effort.util.Api;
import com.effort.util.Base64;
import com.effort.util.Log;
@Service
public class MediaManager {
	@Autowired
	private Constants constants;
	@Autowired
	private ConstantsExtra constantsExtra;
	
	@Autowired
	private MediaDao  mediaDao;
	public Long saveMedia(String mimeType, String extension, String fielStream, long empId, long companyId,
			boolean safeCheck)
			throws FileNotFoundException, IOException {
		try {
			String localPath = constants.getMediaStoragePath() + File.separator + companyId + File.separator + empId ;
			String dbLocalPath = File.separator + companyId + File.separator + empId;
			String fileName =  Api.generateRandomGuId()+"_"+System.currentTimeMillis();
			
			File destinationDir = new File(localPath);
			
			Log.info(getClass(), "saveMediasForWorkApi() localPath  "+localPath);
			
			if (!destinationDir.exists()) {
			    destinationDir.mkdirs();
			}
			
			File file = new File(destinationDir,  File.separator+fileName+extension);
			
			Log.info(getClass(), "saveMediasForWorkApi() localPath nxt "+File.separator+fileName+extension);
			Base64 decoder = new Base64();
			byte[] imageBytes = decoder.decode(fielStream);
			FileOutputStream osf = new FileOutputStream(file);
			osf.write(imageBytes);
			osf.flush();
			osf.close();
			
			 String serverSidechecksum = "";
			 try{
				 serverSidechecksum = Api.getCheckSum(file);
			 } 
			 catch (Exception e) {
				 Log.ignore(this.getClass(), e);
			 }
			 String fileExtension = extension.substring(1).toLowerCase();
			 String supportedImgExtns = constants.getSupportingImgExtn();
			 if(Arrays.asList(supportedImgExtns.split(",")).contains(fileExtension)) 
			 {
				if(safeCheck && !ImageDocumentSanitizer.madeSafe(file))
				{
					file.delete();
					return null;
				}
			}
			Media media = new Media();
			media.setCompanyId(companyId);
			media.setEmpId(empId);
			media.setMimeType(mimeType);
			media.setLocalPath(dbLocalPath+File.separator+fileName+extension);
			media.setFileName(fileName+extension);
			 
			mediaDao.saveMedia(media);
			mediaDao.saveMediaChecksum(media, serverSidechecksum);
			 
			return media.getId();
		}
		catch(Exception ex) {
			Log.info(getClass(), "saveMediasForWorkApi() Got Exception:- "+ex.toString());
			ex.printStackTrace();
		}
		return null;
	}

}
