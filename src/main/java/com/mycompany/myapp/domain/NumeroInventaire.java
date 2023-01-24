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

    @Field("type")
    private String type;

    @Field("disponible")
    private Boolean disponible;

    @Field("ancien_materiel")
    private String ancienMateriel;

    @Field("date_modification")
    private LocalDate dateModification;

    @Field("commentaire")
    private String commentaire;

    @Field("materielActuel")
    private Materiel materielActuel;

    @Field("ancienProprietaire")
    private Collaborateurs ancienProprietaire;

    @Field("nouveauProprietaire")
    private Collaborateurs nouveauProprietaire;

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

    public String getType() {
        return this.type;
    }

    public NumeroInventaire type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCommentaire() {
        return this.commentaire;
    }

    public NumeroInventaire commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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

    public Collaborateurs getAncienProprietaire() {
        return this.ancienProprietaire;
    }

    public void setAncienProprietaire(Collaborateurs collaborateurs) {
        this.ancienProprietaire = collaborateurs;
    }

    public NumeroInventaire ancienProprietaire(Collaborateurs collaborateurs) {
        this.setAncienProprietaire(collaborateurs);
        return this;
    }

    public Collaborateurs getNouveauProprietaire() {
        return this.nouveauProprietaire;
    }

    public void setNouveauProprietaire(Collaborateurs collaborateurs) {
        this.nouveauProprietaire = collaborateurs;
    }

    public NumeroInventaire nouveauProprietaire(Collaborateurs collaborateurs) {
        this.setNouveauProprietaire(collaborateurs);
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
            ", type='" + getType() + "'" +
            ", disponible='" + getDisponible() + "'" +
            ", ancienMateriel='" + getAncienMateriel() + "'" +
            ", dateModification='" + getDateModification() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
