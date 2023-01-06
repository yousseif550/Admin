package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A NumeroInventaire.
 */
@Document(collection = "numero_inventaire")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NumeroInventaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("zone")
    private String zone;

    @Field("valeur")
    private Long valeur;

    @Field("disponible")
    private Boolean disponible;

    @Field("ancien_materiel")
    private String ancienMateriel;

    @Field("date_modification")
    private LocalDate dateModification;

    @Field("materielActuel")
    private Materiel materielActuel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public NumeroInventaire id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZone() {
        return this.zone;
    }

    public NumeroInventaire zone(String zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Long getValeur() {
        return this.valeur;
    }

    public NumeroInventaire valeur(Long valeur) {
        this.setValeur(valeur);
        return this;
    }

    public void setValeur(Long valeur) {
        this.valeur = valeur;
    }

    public Boolean getDisponible() {
        return this.disponible;
    }

    public NumeroInventaire disponible(Boolean disponible) {
        this.setDisponible(disponible);
        return this;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public String getAncienMateriel() {
        return this.ancienMateriel;
    }

    public NumeroInventaire ancienMateriel(String ancienMateriel) {
        this.setAncienMateriel(ancienMateriel);
        return this;
    }

    public void setAncienMateriel(String ancienMateriel) {
        this.ancienMateriel = ancienMateriel;
    }

    public LocalDate getDateModification() {
        return this.dateModification;
    }

    public NumeroInventaire dateModification(LocalDate dateModification) {
        this.setDateModification(dateModification);
        return this;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public Materiel getMaterielActuel() {
        return this.materielActuel;
    }

    public void setMaterielActuel(Materiel materiel) {
        this.materielActuel = materiel;
    }

    public NumeroInventaire materielActuel(Materiel materiel) {
        this.setMaterielActuel(materiel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumeroInventaire)) {
            return false;
        }
        return id != null && id.equals(((NumeroInventaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NumeroInventaire{" +
            "id=" + getId() +
            ", zone='" + getZone() + "'" +
            ", valeur=" + getValeur() +
            ", disponible='" + getDisponible() + "'" +
            ", ancienMateriel='" + getAncienMateriel() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            "}";
    }
}
