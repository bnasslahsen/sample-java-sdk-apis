package com.oracle.oci.sdk.sample.model;

import com.oracle.bmc.OCID;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author bnasslahsen
 */
public class OCIDUtils {

	public static String getResourceType(String ocid) {
		if (OCID.isValid(ocid)) {
			String[] ocids = ocid.split("\\.");
			if (ArrayUtils.isNotEmpty(ocids) && ocids.length == 5) {
				return ocids[1];
			}
		}
		return null;
	}
}
