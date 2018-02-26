package org.wangyt.mms.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author WANG YONG TAO
 * 
 */
@Entity
@Table(name = "mms_oauth2_aut")
@GenericGenerator(name = "idGenerator", strategy = "uuid")
public class OAuth2AUT implements Serializable {

    private static final long serialVersionUID = -6979916037837561894L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", length = 32)
    private String id;

    /** 第三方应用key. */
    @Column(unique=true)
    private String clientId = "";

    /** 第三方应用名称 */
    @Column
    private String clientName = "";

    /** 第三方应用secret. */
    @Column
    private String clientSecret = "";

    /** 第三方应用域名. */
    @Column
    private String clientDomain = "";

    /** 资源所有者ID,即用户标识. */
    @Column
    private String resourceOwner = "";

    /** 授权类型. */
    @Column
    private String grantType = "";

    /** 第三方应用是否被认证服务器(AUTHORIZATION SERVER)授权. */
    private short asAuthorized = 0;

    /** 第三方应用是否已被资源拥有者(RESOURCE OWNER)即用户授权. */
    @Column
    private short roAuthorized = 0;

    /** 授权码. */
    @Column
    private String autCode = "";

    /** 访问令牌. */
    @Column
    private String accessToken = "";

    /** 令牌失效时间. */
    @Column
    private Timestamp atExpirationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientDomain() {
        return clientDomain;
    }

    public void setClientDomain(String clientDomain) {
        this.clientDomain = clientDomain;
    }

    public String getResourceOwner() {
        return resourceOwner;
    }

    public void setResourceOwner(String resourceOwner) {
        this.resourceOwner = resourceOwner;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public short getAsAuthorized() {
        return asAuthorized;
    }

    public void setAsAuthorized(short asAuthorized) {
        this.asAuthorized = asAuthorized;
    }

    public short getRoAuthorized() {
        return roAuthorized;
    }

    public void setRoAuthorized(short roAuthorized) {
        this.roAuthorized = roAuthorized;
    }

    public String getAutCode() {
        return autCode;
    }

    public void setAutCode(String autCode) {
        this.autCode = autCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Timestamp getAtExpirationTime() {
        return atExpirationTime;
    }

    public void setAtExpirationTime(Timestamp atExpirationTime) {
        this.atExpirationTime = atExpirationTime;
    }

}
