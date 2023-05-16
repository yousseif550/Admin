package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Materiel.
 */
@Document(collection = "materiel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Materiel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("utilisation")
    private String utilisation;

    @Field("modele")
    private String modele;

    @Field("asset")
    private String asset;

    @Field("date_attribution")
    private LocalDate dateAttribution;

    @Field("date_rendu")
    private LocalDate dateRendu;

    @Field("actif")
    private Boolean actif;

    @Field("is_hs")
    private Boolean isHs;

    @Field("cle_anti_vol")
    private String cleAntiVol;

    @Field("adress_mac")
    private String adressMAC;

    @Field("station_dgfip")
    private String stationDgfip;

    @Field("ipdfip")
    private String ipdfip;

    @Field("i_p_teletravail")
    private String iPTeletravail;

    @Field("bios")
    private String bios;

    @Field("maj_bios")
    private Boolean majBios;

    @Field("commentaire")
    private String commentaire;

    @Field("objet")
    private Typemateriel objet;

    @Field("localisation")
    private Localisation localisation;

    @Field("collaborateur")
    private Collaborateurs collaborateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Materiel id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtilisation() {
        return this.utilisation;
    }

    public Materiel utilisation(String utilisation) {
        this.setUtilisation(utilisation);
        return this;
    }

    public void setUtilisation(String utilisation) {
        this.utilisation = utilisation;
    }

    public String getModele() {
        return this.modele;
    }

    public Materiel modele(String modele) {
        this.setModele(modele);
        return this;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getAsset() {
        return this.asset;
    }

    public Materiel asset(String asset) {
        this.setAsset(asset);
        return this;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public LocalDate getDateAttribution() {
        return this.dateAttribution;
    }

    public Materiel dateAttribution(LocalDate dateAttribution) {
        this.setDateAttribution(dateAttribution);
        return this;
    }

    public void setDateAttribution(LocalDate dateAttribution) {
        this.dateAttribution = dateAttribution;
    }

    public LocalDate getDateRendu() {
        return this.dateRendu;
    }

    public Materiel dateRendu(LocalDate dateRendu) {
        this.setDateRendu(dateRendu);
        return this;
    }

    public void setDateRendu(LocalDate dateRendu) {
        this.dateRendu = dateRendu;
    }

    public Boolean getActif() {
        return this.actif;
    }

    public Materiel actif(Boolean actif) {
        this.setActif(actif);
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public Boolean getIsHs() {
        return this.isHs;
    }

    public Materiel isHs(Boolean isHs) {
        this.setIsHs(isHs);
        return this;
    }

    public void setIsHs(Boolean isHs) {
        this.isHs = isHs;
    }

    public String getCleAntiVol() {
        return this.cleAntiVol;
    }

    public Materiel cleAntiVol(String cleAntiVol) {
        this.setCleAntiVol(cleAntiVol);
        return this;
    }

    public void setCleAntiVol(String cleAntiVol) {
        this.cleAntiVol = cleAntiVol;
    }

    public String getAdressMAC() {
        return this.adressMAC;
    }

    public Materiel adressMAC(String adressMAC) {
        this.setAdressMAC(adressMAC);
        return this;
    }

    public void setAdressMAC(String adressMAC) {
        this.adressMAC = adressMAC;
    }

    public String getStationDgfip() {
        return this.stationDgfip;
    }

    public Materiel stationDgfip(String stationDgfip) {
        this.setStationDgfip(stationDgfip);
        return this;
    }

    public void setStationDgfip(String stationDgfip) {
        this.stationDgfip = stationDgfip;
    }

    public String getIpdfip() {
        return this.ipdfip;
    }

    public Materiel ipdfip(String ipdfip) {
        this.setIpdfip(ipdfip);
        return this;
    }

    public void setIpdfip(String ipdfip) {
        this.ipdfip = ipdfip;
    }

    public String getiPTeletravail() {
        return this.iPTeletravail;
    }

    public Materiel iPTeletravail(String iPTeletravail) {
        this.setiPTeletravail(iPTeletravail);
        return this;
    }

    public void setiPTeletravail(String iPTeletravail) {
        this.iPTeletravail = iPTeletravail;
    }

    public String getBios() {
        return this.bios;
    }

    public Materiel bios(String bios) {
        this.setBios(bios);
        return this;
    }

    public void setBios(String bios) {
        this.bios = bios;
    }

    public Boolean getMajBios() {
        return this.majBios;
    }

    public Materiel majBios(Boolean majBios) {
        this.setMajBios(majBios);
        return this;
    }

    public void setMajBios(Boolean majBios) {
        this.majBios = majBios;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Materiel commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Typemateriel getObjet() {
        return this.objet;
    }

    public void setObjet(Typemateriel typemateriel) {
        this.objet = typemateriel;
    }

    public Materiel objet(Typemateriel typemateriel) {
        this.setObjet(typemateriel);
        return this;
    }

    public Localisation getLocalisation() {
        return this.localisation;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public Materiel localisation(Localisation localisation) {
        this.setLocalisation(localisation);
        return this;
    }

    public Collaborateurs getCollaborateur() {
        return this.collaborateur;
    }

    public void setCollaborateur(Collaborateurs collaborateurs) {
        this.collaborateur = collaborateurs;
    }

    public Materiel collaborateur(Collaborateurs collaborateurs) {
        this.setCollaborateur(collaborateurs);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Materiel)) {
            return false;
        }
        return id != null && id.equals(((Materiel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Materiel{" +
            "id=" + getId() +
            ", utilisation='" + getUtilisation() + "'" +
            ", modele='" + getModele() + "'" +
            ", asset='" + getAsset() + "'" +
            ", dateAttribution='" + getDateAttribution() + "'" +
            ", dateRendu='" + getDateRendu() + "'" +
            ", actif='" + getActif() + "'" +
            ", isHs='" + getIsHs() + "'" +
            ", cleAntiVol='" + getCleAntiVol() + "'" +
            ", adressMAC='" + getAdressMAC() + "'" +
            ", stationDgfip='" + getStationDgfip() + "'" +
            ", ipdfip='" + getIpdfip() + "'" +
            ", iPTeletravail='" + getiPTeletravail() + "'" +
            ", bios='" + getBios() + "'" +
            ", majBios='" + getMajBios() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
