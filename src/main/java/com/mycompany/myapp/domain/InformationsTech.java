package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A InformationsTech.
 */
@Document(collection = "informations_tech")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InformationsTech implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("ipdfip_connexion")
    private String ipdfipConnexion;

    @Field("ipfix_dmocss")
    private String ipfixDMOCSS;

    @Field("adress_mac")
    private String adressMAC;

    @Field("i_p_teletravail")
    private String iPTeletravail;

    @Field("adresse_dg_fi_p")
    private String adresseDGFiP;

    @Field("pcDGFiP")
    private Materiel pcDGFiP;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public InformationsTech id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpdfipConnexion() {
        return this.ipdfipConnexion;
    }

    public InformationsTech ipdfipConnexion(String ipdfipConnexion) {
        this.setIpdfipConnexion(ipdfipConnexion);
        return this;
    }

    public void setIpdfipConnexion(String ipdfipConnexion) {
        this.ipdfipConnexion = ipdfipConnexion;
    }

    public String getIpfixDMOCSS() {
        return this.ipfixDMOCSS;
    }

    public InformationsTech ipfixDMOCSS(String ipfixDMOCSS) {
        this.setIpfixDMOCSS(ipfixDMOCSS);
        return this;
    }

    public void setIpfixDMOCSS(String ipfixDMOCSS) {
        this.ipfixDMOCSS = ipfixDMOCSS;
    }

    public String getAdressMAC() {
        return this.adressMAC;
    }

    public InformationsTech adressMAC(String adressMAC) {
        this.setAdressMAC(adressMAC);
        return this;
    }

    public void setAdressMAC(String adressMAC) {
        this.adressMAC = adressMAC;
    }

    public String getiPTeletravail() {
        return this.iPTeletravail;
    }

    public InformationsTech iPTeletravail(String iPTeletravail) {
        this.setiPTeletravail(iPTeletravail);
        return this;
    }

    public void setiPTeletravail(String iPTeletravail) {
        this.iPTeletravail = iPTeletravail;
    }

    public String getAdresseDGFiP() {
        return this.adresseDGFiP;
    }

    public InformationsTech adresseDGFiP(String adresseDGFiP) {
        this.setAdresseDGFiP(adresseDGFiP);
        return this;
    }

    public void setAdresseDGFiP(String adresseDGFiP) {
        this.adresseDGFiP = adresseDGFiP;
    }

    public Materiel getPcDGFiP() {
        return this.pcDGFiP;
    }

    public void setPcDGFiP(Materiel materiel) {
        this.pcDGFiP = materiel;
    }

    public InformationsTech pcDGFiP(Materiel materiel) {
        this.setPcDGFiP(materiel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InformationsTech)) {
            return false;
        }
        return id != null && id.equals(((InformationsTech) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformationsTech{" +
            "id=" + getId() +
            ", ipdfipConnexion='" + getIpdfipConnexion() + "'" +
            ", ipfixDMOCSS='" + getIpfixDMOCSS() + "'" +
            ", adressMAC='" + getAdressMAC() + "'" +
            ", iPTeletravail='" + getiPTeletravail() + "'" +
            ", adresseDGFiP='" + getAdresseDGFiP() + "'" +
            "}";
    }
}
