package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Projet.
 */
@Document(collection = "projet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("stucture")
    private String stucture;

    @Field("informations")
    private String informations;

    @Field("cP")
    private Collaborateurs cP;

    @Field("dP")
    private Collaborateurs dP;

    @Field("collaborateurs")
    @JsonIgnoreProperties(value = { "localisation", "projets" }, allowSetters = true)
    private Set<Collaborateurs> collaborateurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Projet id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Projet nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStucture() {
        return this.stucture;
    }

    public Projet stucture(String stucture) {
        this.setStucture(stucture);
        return this;
    }

    public void setStucture(String stucture) {
        this.stucture = stucture;
    }

    public String getInformations() {
        return this.informations;
    }

    public Projet informations(String informations) {
        this.setInformations(informations);
        return this;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }

    public Collaborateurs getCP() {
        return this.cP;
    }

    public void setCP(Collaborateurs collaborateurs) {
        this.cP = collaborateurs;
    }

    public Projet cP(Collaborateurs collaborateurs) {
        this.setCP(collaborateurs);
        return this;
    }

    public Collaborateurs getDP() {
        return this.dP;
    }

    public void setDP(Collaborateurs collaborateurs) {
        this.dP = collaborateurs;
    }

    public Projet dP(Collaborateurs collaborateurs) {
        this.setDP(collaborateurs);
        return this;
    }

    public Set<Collaborateurs> getCollaborateurs() {
        return this.collaborateurs;
    }

    public void setCollaborateurs(Set<Collaborateurs> collaborateurs) {
        if (this.collaborateurs != null) {
            this.collaborateurs.forEach(i -> i.removeProjet(this));
        }
        if (collaborateurs != null) {
            collaborateurs.forEach(i -> i.addProjet(this));
        }
        this.collaborateurs = collaborateurs;
    }

    public Projet collaborateurs(Set<Collaborateurs> collaborateurs) {
        this.setCollaborateurs(collaborateurs);
        return this;
    }

    public Projet addCollaborateurs(Collaborateurs collaborateurs) {
        this.collaborateurs.add(collaborateurs);
        collaborateurs.getProjets().add(this);
        return this;
    }

    public Projet removeCollaborateurs(Collaborateurs collaborateurs) {
        this.collaborateurs.remove(collaborateurs);
        collaborateurs.getProjets().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projet)) {
            return false;
        }
        return id != null && id.equals(((Projet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", stucture='" + getStucture() + "'" +
            ", informations='" + getInformations() + "'" +
            "}";
    }
}
