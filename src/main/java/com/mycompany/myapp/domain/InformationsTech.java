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

    @Field("pc_dom")
    private String pcDom;

    @Field("pc_dg_fi_p")
    private String pcDGFiP;

    @Field("adresse_ssg")
    private String adresseSSG;

    @Field("adresse_dg_fi_p")
    private String adresseDGFiP;

    @Field("collaborateur")
    private Collaborateurs collaborateur;

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

    public String getPcDom() {
        return this.pcDom;
    }

    public InformationsTech pcDom(String pcDom) {
        this.setPcDom(pcDom);
        return this;
    }

    public void setPcDom(String pcDom) {
        this.pcDom = pcDom;
    }

    public String getPcDGFiP() {
        return this.pcDGFiP;
    }

    public InformationsTech pcDGFiP(String pcDGFiP) {
        this.setPcDGFiP(pcDGFiP);
        return this;
    }

    public void setPcDGFiP(String pcDGFiP) {
        this.pcDGFiP = pcDGFiP;
    }

    public String getAdresseSSG() {
        return this.adresseSSG;
    }

    public InformationsTech adresseSSG(String adresseSSG) {
        this.setAdresseSSG(adresseSSG);
        return this;
    }

    public void setAdresseSSG(String adresseSSG) {
        this.adresseSSG = adresseSSG;
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

    public Collaborateurs getCollaborateur() {
        return this.collaborateur;
    }

    public void setCollaborateur(Collaborateurs collaborateurs) {
        this.collaborateur = collaborateurs;
    }

    public InformationsTech collaborateur(Collaborateurs collaborateurs) {
        this.setCollaborateur(collaborateurs);
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
            ", pcDom='" + getPcDom() + "'" +
            ", pcDGFiP='" + getPcDGFiP() + "'" +
            ", adresseSSG='" + getAdresseSSG() + "'" +
            ", adresseDGFiP='" + getAdresseDGFiP() + "'" +
            "}";
    }
}
