package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ExtracDMOCSS.
 */
@Document(collection = "extrac_dmocss")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExtracDMOCSS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("adresse_physique_dg_fi_p")
    private String adressePhysiqueDGFiP;

    @Field("bureau_actuel")
    private String bureauActuel;

    @Field("bureau_deplacement")
    private String bureauDeplacement;

    @Field("date")
    private LocalDate date;

    @Field("ip_pc_dg_fi_p")
    private String ipPcDGFiP;

    @Field("ip_vpn_ipsec")
    private String ipVpnIPSEC;

    @Field("collaborateur")
    private Collaborateurs collaborateur;

    @Field("materiel")
    private Materiel materiel;

    @Field("localisation")
    private Localisation localisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ExtracDMOCSS id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdressePhysiqueDGFiP() {
        return this.adressePhysiqueDGFiP;
    }

    public ExtracDMOCSS adressePhysiqueDGFiP(String adressePhysiqueDGFiP) {
        this.setAdressePhysiqueDGFiP(adressePhysiqueDGFiP);
        return this;
    }

    public void setAdressePhysiqueDGFiP(String adressePhysiqueDGFiP) {
        this.adressePhysiqueDGFiP = adressePhysiqueDGFiP;
    }

    public String getBureauActuel() {
        return this.bureauActuel;
    }

    public ExtracDMOCSS bureauActuel(String bureauActuel) {
        this.setBureauActuel(bureauActuel);
        return this;
    }

    public void setBureauActuel(String bureauActuel) {
        this.bureauActuel = bureauActuel;
    }

    public String getBureauDeplacement() {
        return this.bureauDeplacement;
    }

    public ExtracDMOCSS bureauDeplacement(String bureauDeplacement) {
        this.setBureauDeplacement(bureauDeplacement);
        return this;
    }

    public void setBureauDeplacement(String bureauDeplacement) {
        this.bureauDeplacement = bureauDeplacement;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public ExtracDMOCSS date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getIpPcDGFiP() {
        return this.ipPcDGFiP;
    }

    public ExtracDMOCSS ipPcDGFiP(String ipPcDGFiP) {
        this.setIpPcDGFiP(ipPcDGFiP);
        return this;
    }

    public void setIpPcDGFiP(String ipPcDGFiP) {
        this.ipPcDGFiP = ipPcDGFiP;
    }

    public String getIpVpnIPSEC() {
        return this.ipVpnIPSEC;
    }

    public ExtracDMOCSS ipVpnIPSEC(String ipVpnIPSEC) {
        this.setIpVpnIPSEC(ipVpnIPSEC);
        return this;
    }

    public void setIpVpnIPSEC(String ipVpnIPSEC) {
        this.ipVpnIPSEC = ipVpnIPSEC;
    }

    public Collaborateurs getCollaborateur() {
        return this.collaborateur;
    }

    public void setCollaborateur(Collaborateurs collaborateurs) {
        this.collaborateur = collaborateurs;
    }

    public ExtracDMOCSS collaborateur(Collaborateurs collaborateurs) {
        this.setCollaborateur(collaborateurs);
        return this;
    }

    public Materiel getMateriel() {
        return this.materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public ExtracDMOCSS materiel(Materiel materiel) {
        this.setMateriel(materiel);
        return this;
    }

    public Localisation getLocalisation() {
        return this.localisation;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public ExtracDMOCSS localisation(Localisation localisation) {
        this.setLocalisation(localisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtracDMOCSS)) {
            return false;
        }
        return id != null && id.equals(((ExtracDMOCSS) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExtracDMOCSS{" +
            "id=" + getId() +
            ", adressePhysiqueDGFiP='" + getAdressePhysiqueDGFiP() + "'" +
            ", bureauActuel='" + getBureauActuel() + "'" +
            ", bureauDeplacement='" + getBureauDeplacement() + "'" +
            ", date='" + getDate() + "'" +
            ", ipPcDGFiP='" + getIpPcDGFiP() + "'" +
            ", ipVpnIPSEC='" + getIpVpnIPSEC() + "'" +
            "}";
    }
}
