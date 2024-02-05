package com.hmzhkj.common.swagger.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("swagger")
public class SwaggerProperties
{

    private Boolean enabled;


    private String basePackage = "";


    private List<String> basePath = new ArrayList<>();


    private List<String> excludePath = new ArrayList<>();


    private String title = "";


    private String description = "";


    private String version = "";


    private String license = "";


    private String licenseUrl = "";


    private String termsOfServiceUrl = "";


    private String host = "";


    private Contact contact = new Contact();


    private Authorization authorization = new Authorization();

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getBasePackage()
    {
        return basePackage;
    }

    public void setBasePackage(String basePackage)
    {
        this.basePackage = basePackage;
    }

    public List<String> getBasePath()
    {
        return basePath;
    }

    public void setBasePath(List<String> basePath)
    {
        this.basePath = basePath;
    }

    public List<String> getExcludePath()
    {
        return excludePath;
    }

    public void setExcludePath(List<String> excludePath)
    {
        this.excludePath = excludePath;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getLicense()
    {
        return license;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }

    public String getLicenseUrl()
    {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl)
    {
        this.licenseUrl = licenseUrl;
    }

    public String getTermsOfServiceUrl()
    {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl)
    {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public Authorization getAuthorization()
    {
        return authorization;
    }

    public void setAuthorization(Authorization authorization)
    {
        this.authorization = authorization;
    }

    public static class Contact
    {

        private String name = "";

        private String url = "";

        private String email = "";

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getUrl()
        {
            return url;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }
    }

    public static class Authorization
    {

        private String name = "";


        private String authRegex = "^.*$";


        private List<AuthorizationScope> authorizationScopeList = new ArrayList<>();

        private List<String> tokenUrlList = new ArrayList<>();

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getAuthRegex()
        {
            return authRegex;
        }

        public void setAuthRegex(String authRegex)
        {
            this.authRegex = authRegex;
        }

        public List<AuthorizationScope> getAuthorizationScopeList()
        {
            return authorizationScopeList;
        }

        public void setAuthorizationScopeList(List<AuthorizationScope> authorizationScopeList)
        {
            this.authorizationScopeList = authorizationScopeList;
        }

        public List<String> getTokenUrlList()
        {
            return tokenUrlList;
        }

        public void setTokenUrlList(List<String> tokenUrlList)
        {
            this.tokenUrlList = tokenUrlList;
        }
    }

    public static class AuthorizationScope
    {

        private String scope = "";


        private String description = "";

        public String getScope()
        {
            return scope;
        }

        public void setScope(String scope)
        {
            this.scope = scope;
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }
    }
}