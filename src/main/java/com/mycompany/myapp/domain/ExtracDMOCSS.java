package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Etat;
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

    @Field("date")
    private LocalDate date;

    @Field("bureau_deplacement")
    private String bureauDeplacement;

    @Field("ip_pc_dgfip")
    private String ipPcDgfip;

    @Field("ip_vpn_ipsec")
    private String ipVpnIPSEC;

    @Field("ip_teletravail")
    private String ipTeletravail;

    @Field("statut")
    private Etat statut;

    @Field("num_version")
    private String numVersion;

    @Field("collaborateur")
    private Collaborateurs collaborateur;

    @Field("materiel")
    private Materiel materiel;

    @Field("bureauActuel")
    private Localisation bureauActuel;

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

    public String getIpPcDgfip() {
        return this.ipPcDgfip;
    }

    public ExtracDMOCSS ipPcDgfip(String ipPcDgfip) {
        this.setIpPcDgfip(ipPcDgfip);
        return this;
    }

    public void setIpPcDgfip(String ipPcDgfip) {
        this.ipPcDgfip = ipPcDgfip;
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

    public String getIpTeletravail() {
        return this.ipTeletravail;
    }

    public ExtracDMOCSS ipTeletravail(String ipTeletravail) {
        this.setIpTeletravail(ipTeletravail);
        return this;
    }

    public void setIpTeletravail(String ipTeletravail) {
        this.ipTeletravail = ipTeletravail;
    }

    public Etat getStatut() {
        return this.statut;
    }

    public ExtracDMOCSS statut(Etat statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(Etat statut) {
        this.statut = statut;
    }

    public String getNumVersion() {
        return this.numVersion;
    }

    public ExtracDMOCSS numVersion(String numVersion) {
        this.setNumVersion(numVersion);
        return this;
    }

    public void setNumVersion(String numVersion) {
        this.numVersion = numVersion;
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

    public Localisation getBureauActuel() {
        return this.bureauActuel;
    }

    public void setBureauActuel(Localisation localisation) {
        this.bureauActuel = localisation;
    }

    public ExtracDMOCSS bureauActuel(Localisation localisation) {
        this.setBureauActuel(localisation);
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
            ", date='" + getDate() + "'" +
            ", bureauDeplacement='" + getBureauDeplacement() + "'" +
            ", ipPcDgfip='" + getIpPcDgfip() + "'" +
            ", ipVpnIPSEC='" + getIpVpnIPSEC() + "'" +
            ", ipTeletravail='" + getIpTeletravail() + "'" +
            ", statut='" + getStatut() + "'" +
            ", numVersion='" + getNumVersion() + "'" +
            "}";
    }
}
