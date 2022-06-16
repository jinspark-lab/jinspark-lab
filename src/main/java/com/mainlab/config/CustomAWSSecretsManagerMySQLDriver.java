package com.mainlab.config;

import com.amazonaws.secretsmanager.caching.SecretCache;
import com.amazonaws.secretsmanager.caching.SecretCacheConfiguration;
import com.amazonaws.secretsmanager.sql.AWSSecretsManagerDriver;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.util.StringUtils;
import com.mysql.jdbc.Driver;

import java.sql.SQLException;

/**
 * This class is replicated version of:
 * https://github.com/aws/aws-secretsmanager-jdbc/blob/master/src/main/java/com/amazonaws/secretsmanager/sql/AWSSecretsManagerMySQLDriver.java
 */
public final class CustomAWSSecretsManagerMySQLDriver extends AWSSecretsManagerDriver {

    /**
     * The MySQL error code for when a user logs in using an invalid password.
     *
     * See <a href="https://dev.mysql.com/doc/refman/5.5/en/error-messages-server.html">MySQL error codes</a>.
     */
    public static final int ACCESS_DENIED_FOR_USER_USING_PASSWORD_TO_DATABASE = 1045;

    /**
     * Set to mysql.
     */
    public static final String SUBPREFIX = "mysql";

    static {
        try {
            com.mysql.cj.jdbc.Driver driver = new Driver();
            CustomAWSSecretsManagerMySQLDriver.register(new CustomAWSSecretsManagerMySQLDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructs the driver setting the properties from the properties file using system properties as defaults.
     * Instantiates the secret cache with default options.
     */
    public CustomAWSSecretsManagerMySQLDriver() {
        super();
    }

    /**
     * Constructs the driver setting the properties from the properties file using system properties as defaults.
     * Uses the passed in SecretCache.
     *
     * @param cache                                             Secret cache to use to retrieve secrets
     */
    public CustomAWSSecretsManagerMySQLDriver(SecretCache cache) {
        super(cache);
    }

    /**
     * Constructs the driver setting the properties from the properties file using system properties as defaults.
     * Instantiates the secret cache with the passed in client builder.
     *
     * @param builder                                           Builder used to instantiate cache
     */
    public CustomAWSSecretsManagerMySQLDriver(AWSSecretsManagerClientBuilder builder) {
        super(builder);
    }

    /**
     * Constructs the driver setting the properties from the properties file using system properties as defaults.
     * Instantiates the secret cache with the provided AWS Secrets Manager client.
     *
     * @param client                                            AWS Secrets Manager client to instantiate cache
     */
    public CustomAWSSecretsManagerMySQLDriver(AWSSecretsManager client) {
        super(client);
    }

    /**
     * Constructs the driver setting the properties from the properties file using system properties as defaults.
     * Instantiates the secret cache with the provided cache configuration.
     *
     * @param cacheConfig                                       Cache configuration to instantiate cache
     */
    public CustomAWSSecretsManagerMySQLDriver(SecretCacheConfiguration cacheConfig) {
        super(cacheConfig);
    }

    @Override
    public String getPropertySubprefix() {
        return SUBPREFIX;
    }

    @Override
    public boolean isExceptionDueToAuthenticationError(Exception e) {
        if (e instanceof SQLException) {
            SQLException sqle = (SQLException) e;
            int errorCode = sqle.getErrorCode();
            return errorCode == ACCESS_DENIED_FOR_USER_USING_PASSWORD_TO_DATABASE;
        }
        return false;
    }

    @Override
    public String constructUrlFromEndpointPortDatabase(String endpoint, String port, String dbname) {
        String url = "jdbc:mysql://" + endpoint;
        if (!StringUtils.isNullOrEmpty(port)) {
            url += ":" + port;
        }
        if (!StringUtils.isNullOrEmpty(dbname)) {
            url += "/" + dbname;
        }
        return url;
    }

    @Override
    public String getDefaultDriverClass() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver", false, this.getClass().getClassLoader());
            return "com.mysql.cj.jdbc.Driver";
        } catch (ClassNotFoundException e) {
            return "com.mysql.jdbc.Driver";
        }
    }
}
