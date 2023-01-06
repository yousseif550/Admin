package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Etat;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Ticket.
 */
@Document(collection = "ticket")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type")
    private String type;

    @Field("statut")
    private Etat statut;

    @Field("date_creation")
    private LocalDate dateCreation;

    @Field("date_fin")
    private LocalDate dateFin;

    @Field("beneficiaire")
    private Collaborateurs beneficiaire;

    @Field("proprietaire")
    @JsonIgnoreProperties(value = { "materiels", "tickets", "projets" }, allowSetters = true)
    private Collaborateurs proprietaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Ticket id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Ticket type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Etat getStatut() {
        return this.statut;
    }

    public Ticket statut(Etat statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(Etat statut) {
        this.statut = statut;
    }

    public LocalDate getDateCreation() {
        return this.dateCreation;
    }

    public Ticket dateCreation(LocalDate dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Ticket dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Collaborateurs getBeneficiaire() {
        return this.beneficiaire;
    }

    public void setBeneficiaire(Collaborateurs collaborateurs) {
        this.beneficiaire = collaborateurs;
    }

    public Ticket beneficiaire(Collaborateurs collaborateurs) {
        this.setBeneficiaire(collaborateurs);
        return this;
    }

    public Collaborateurs getProprietaire() {
        return this.proprietaire;
    }

    public void setProprietaire(Collaborateurs collaborateurs) {
        this.proprietaire = collaborateurs;
    }

    public Ticket proprietaire(Collaborateurs collaborateurs) {
        this.setProprietaire(collaborateurs);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return id != null && id.equals(((Ticket) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", statut='" + getStatut() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
