package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Historique.
 */
@Document(collection = "historique")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Historique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("p_c")
    private String pC;

    @Field("zone")
    private String zone;

    @Field("date_mouvement")
    private LocalDate dateMouvement;

    @Field("ancienProprietaire")
    private Collaborateurs ancienProprietaire;

    @Field("nouveauProprietaire")
    private Collaborateurs nouveauProprietaire;

    @Field("materiel")
    private Materiel materiel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Historique id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpC() {
        return this.pC;
    }

    public Historique pC(String pC) {
        this.setpC(pC);
        return this;
    }

    public void setpC(String pC) {
        this.pC = pC;
    }

    public String getZone() {
        return this.zone;
    }

    public Historique zone(String zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public LocalDate getDateMouvement() {
        return this.dateMouvement;
    }

    public Historique dateMouvement(LocalDate dateMouvement) {
        this.setDateMouvement(dateMouvement);
        return this;
    }

    public void setDateMouvement(LocalDate dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public Collaborateurs getAncienProprietaire() {
        return this.ancienProprietaire;
    }

    public void setAncienProprietaire(Collaborateurs collaborateurs) {
        this.ancienProprietaire = collaborateurs;
    }

    public Historique ancienProprietaire(Collaborateurs collaborateurs) {
        this.setAncienProprietaire(collaborateurs);
        return this;
    }

    public Collaborateurs getNouveauProprietaire() {
        return this.nouveauProprietaire;
    }

    public void setNouveauProprietaire(Collaborateurs collaborateurs) {
        this.nouveauProprietaire = collaborateurs;
    }

    public Historique nouveauProprietaire(Collaborateurs collaborateurs) {
        this.setNouveauProprietaire(collaborateurs);
        return this;
    }

    public Materiel getMateriel() {
        return this.materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public Historique materiel(Materiel materiel) {
        this.setMateriel(materiel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Historique)) {
            return false;
        }
        return id != null && id.equals(((Historique) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Historique{" +
            "id=" + getId() +
            ", pC='" + getpC() + "'" +
            ", zone='" + getZone() + "'" +
            ", dateMouvement='" + getDateMouvement() + "'" +
            "}";
    }
}
