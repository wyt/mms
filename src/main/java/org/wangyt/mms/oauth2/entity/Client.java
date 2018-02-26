package org.wangyt.mms.oauth2.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "oauth2_client")
@GenericGenerator(name = "idGenerator", strategy = "identity")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private Long id;
    
    @Column(name="client_name")
    private String clientName;
    
    @Column(name="client_id")
    private String clientId;
    
    @Column(name="client_secret")
    private String clientSecret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        if (id != null ? !id.equals(client.id) : client.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", clientName='" + clientName + '\'' + ", clientId='"
                + clientId + '\'' + ", clientSecret='" + clientSecret + '\'' + '}';
    }
    
}
