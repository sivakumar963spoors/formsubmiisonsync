package com.effort.manager;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.effort.exception.EffortError;
import com.effort.util.Api;
import com.effort.util.Log;
import com.effort.util.UrlSigner;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class SyncValidationManager {

	
	
	
public void validateSyncSignrature(long empId,String apiLevel,String signature,String jsonString,HttpServletRequest request) throws EffortError {
		
		String logText = "validateSyncSignrature() // empId = "+empId+" apiLevel = "+apiLevel;
		Log.info(getClass(), logText+" starts ...");
		if (!Api.isEmptyString(apiLevel) && Integer.parseInt(apiLevel) >= 15) {
			System.out.println(request.getRequestURL().toString() + "?" + request.getQueryString());
			String requestedUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
			String serverSignatureKey = "";
			String  keyString = "LWFKFv5GyJPYvgOMT2n0PvMiTXg=";
			Log.info(getClass(), logText+" requestedUrl = "+requestedUrl+" jsonString = "+jsonString);
			try {
				serverSignatureKey = UrlSigner.getSignedUrl(requestedUrl, jsonString, keyString);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            Log.info(getClass(), logText+" keyString = "+keyString+" signature = "+signature+" serverSignatureKey = "+serverSignatureKey);
			if (Api.isEmptyString(signature)
					|| (!Api.isEmptyString(signature) && !signature.equalsIgnoreCase(serverSignatureKey))) {
				Log.info(getClass(), logText+ " validateSyncSignrature failed ");
				throw new EffortError(7005, "Signature validation failed: expected " + serverSignatureKey + " but got " + signature, HttpStatus.PRECONDITION_FAILED);

			}
		}
	}
}
