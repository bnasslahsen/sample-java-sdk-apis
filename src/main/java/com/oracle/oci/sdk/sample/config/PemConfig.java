package com.oracle.oci.sdk.sample.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import com.google.common.base.Supplier;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.AbstractOpenApiResource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author bnasslahsen
 */
@ConfigurationProperties(prefix = "spotlight")
@Configuration
public class PemConfig {

	@Value("${spotlight.fingerprint}")
	private String fingerprint;

	@Value("${spotlight.tenancy}")
	private String tenancy;

	@Value("${spotlight.userId}")
	private String userId;

	@Value("${spotlight.region}")
	private String region;

	@Value("${spotlight.privateKeyPath}")
	private String privateKeyPath;

	@Value("${spotlight.privateKeyName}")
	private String privateKeyName;

	private String ociPrivateKeyPath;

	private static final Logger LOGGER = LoggerFactory.getLogger(PemConfig.class);

	@PostConstruct
	public void init() {
		Path directoryPath = Paths.get(privateKeyPath);
		if (Files.notExists(directoryPath))
			throw new IllegalStateException("Pem path does not exist: " + privateKeyPath);
		ociPrivateKeyPath = Paths.get(directoryPath.toAbsolutePath().toString(), privateKeyName).toString();
	}

	public AuthenticationDetailsProvider getAuthenticationDetailsProvider() {
		Supplier<InputStream> privateKeySupplier = () -> getInputStream(ociPrivateKeyPath);
		return SimpleAuthenticationDetailsProvider.builder()
				.tenantId(tenancy)
				.userId(userId)
				.fingerprint(fingerprint)
				.region(Region.fromRegionId(region))
				.privateKeySupplier(privateKeySupplier)
				.build();
	}

	private InputStream getInputStream(String ociPrivateKeyPath) {
		InputStream is = null;
		LOGGER.debug("Private key location - " + ociPrivateKeyPath);
		try {
			is = new FileInputStream(ociPrivateKeyPath);
		}
		catch (FileNotFoundException ex) {
			LOGGER.debug("Problem accessing OCI private key at " + ociPrivateKeyPath + " - " + ex.getMessage());
		}
		return is;
	}

	public String getRegion() {
		return region;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getTenancy() {
		return tenancy;
	}

	public void setTenancy(String tenancy) {
		this.tenancy = tenancy;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPrivateKeyPath() {
		return privateKeyPath;
	}

	public void setPrivateKeyPath(String privateKeyPath) {
		this.privateKeyPath = privateKeyPath;
	}

	public String getPrivateKeyName() {
		return privateKeyName;
	}

	public void setPrivateKeyName(String privateKeyName) {
		this.privateKeyName = privateKeyName;
	}

	public String getOciPrivateKeyPath() {
		return ociPrivateKeyPath;
	}

	public void setOciPrivateKeyPath(String ociPrivateKeyPath) {
		this.ociPrivateKeyPath = ociPrivateKeyPath;
	}
}
