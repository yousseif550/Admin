package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Collaborateurs.
 */
@Document(collection = "collaborateurs")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Collaborateurs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("identifiant")
    private Long identifiant;

    @Field("tel")
    private Long tel;

    @Field("prestataire")
    private Boolean prestataire;

    @Field("is_actif")
    private Boolean isActif;

    @Field("date_entree")
    private LocalDate dateEntree;

    @Field("date_sortie")
    private LocalDate dateSortie;

    @Field("materiel")
    @JsonIgnoreProperties(value = { "numeroInventaire", "localisation", "collaborateurs" }, allowSetters = true)
    private Set<Materiel> materiels = new HashSet<>();

    @Field("ticket")
    @JsonIgnoreProperties(value = { "beneficiaire", "proprietaire" }, allowSetters = true)
    private Set<Ticket> tickets = new HashSet<>();

    @Field("projets")
    @JsonIgnoreProperties(value = { "collaborateurs" }, allowSetters = true)
    private Set<Projet> projets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Collaborateurs id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Collaborateurs nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getIdentifiant() {
        return this.identifiant;
    }

    public Collaborateurs identifiant(Long identifiant) {
        this.setIdentifiant(identifiant);
        return this;
    }

    public void setIdentifiant(Long identifiant) {
        this.identifiant = identifiant;
    }

    public Long getTel() {
        return this.tel;
    }

    public Collaborateurs tel(Long tel) {
        this.setTel(tel);
        return this;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }

    public Boolean getPrestataire() {
        return this.prestataire;
    }

    public Collaborateurs prestataire(Boolean prestataire) {
        this.setPrestataire(prestataire);
        return this;
    }

    public void setPrestataire(Boolean prestataire) {
        this.prestataire = prestataire;
    }

    public Boolean getIsActif() {
        return this.isActif;
    }

    public Collaborateurs isActif(Boolean isActif) {
        this.setIsActif(isActif);
        return this;
    }

    public void setIsActif(Boolean isActif) {
        this.isActif = isActif;
    }

    public LocalDate getDateEntree() {
        return this.dateEntree;
    }

    public Collaborateurs dateEntree(LocalDate dateEntree) {
        this.setDateEntree(dateEntree);
        return this;
    }

    public void setDateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
    }

    public LocalDate getDateSortie() {
        return this.dateSortie;
    }

    public Collaborateurs dateSortie(LocalDate dateSortie) {
        this.setDateSortie(dateSortie);
        return this;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Set<Materiel> getMateriels() {
        return this.materiels;
    }

    public void setMateriels(Set<Materiel> materiels) {
        if (this.materiels != null) {
            this.materiels.forEach(i -> i.setCollaborateurs(null));
        }
        if (materiels != null) {
            materiels.forEach(i -> i.setCollaborateurs(this));
        }
        this.materiels = materiels;
    }

    public Collaborateurs materiels(Set<Materiel> materiels) {
        this.setMateriels(materiels);
        return this;
    }

    public Collaborateurs addMateriel(Materiel materiel) {
        this.materiels.add(materiel);
        materiel.setCollaborateurs(this);
        return this;
    }

    public Collaborateurs removeMateriel(Materiel materiel) {
        this.materiels.remove(materiel);
        materiel.setCollaborateurs(null);
        return this;
    }

    public Set<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        if (this.tickets != null) {
            this.tickets.forEach(i -> i.setProprietaire(null));
        }
        if (tickets != null) {
            tickets.forEach(i -> i.setProprietaire(this));
        }
        this.tickets = tickets;
    }

    public Collaborateurs tickets(Set<Ticket> tickets) {
        this.setTickets(tickets);
        return this;
    }

    public Collaborateurs addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        ticket.setProprietaire(this);
        return this;
    }

    public Collaborateurs removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        ticket.setProprietaire(null);
        return this;
    }

    public Set<Projet> getProjets() {
        return this.projets;
    }

    public void setProjets(Set<Projet> projets) {
        if (this.projets != null) {
            this.projets.forEach(i -> i.removeCollaborateurs(this));
        }
        if (projets != null) {
            projets.forEach(i -> i.addCollaborateurs(this));
        }
        this.projets = projets;
    }

    public Collaborateurs projets(Set<Projet> projets) {
        this.setProjets(projets);
        return this;
    }

    public Collaborateurs addProjet(Projet projet) {
        this.projets.add(projet);
        projet.getCollaborateurs().add(this);
        return this;
    }

    public Collaborateurs removeProjet(Projet projet) {
        this.projets.remove(projet);
        projet.getCollaborateurs().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collaborateurs)) {
            return false;
        }
        return id != null && id.equals(((Collaborateurs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Collaborateurs{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", identifiant=" + getIdentifiant() +
            ", tel=" + getTel() +
            ", prestataire='" + getPrestataire() + "'" +
            ", isActif='" + getIsActif() + "'" +
            ", dateEntree='" + getDateEntree() + "'" +
            ", dateSortie='" + getDateSortie() + "'" +
            "}";
    }
}
