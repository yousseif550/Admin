package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Mouvement.
 */
@Document(collection = "mouvement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Mouvement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("date")
    private LocalDate date;

    @Field("type")
    private String type;

    @Field("source")
    private String source;

    @Field("destination")
    private String destination;

    @Field("user")
    private String user;

    @Field("commentaire")
    private String commentaire;

    @Field("asset")
    private Materiel asset;

    @Field("localisation")
    private Localisation localisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Mouvement id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Mouvement date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return this.type;
    }

    public Mouvement type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return this.source;
    }

    public Mouvement source(String source) {
        this.setSource(source);
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return this.destination;
    }

    public Mouvement destination(String destination) {
        this.setDestination(destination);
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUser() {
        return this.user;
    }

    public Mouvement user(String user) {
        this.setUser(user);
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Mouvement commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Materiel getAsset() {
        return this.asset;
    }

    public void setAsset(Materiel materiel) {
        this.asset = materiel;
    }

    public Mouvement asset(Materiel materiel) {
        this.setAsset(materiel);
        return this;
    }

    public Localisation getLocalisation() {
        return this.localisation;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public Mouvement localisation(Localisation localisation) {
        this.setLocalisation(localisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mouvement)) {
            return false;
        }
        return id != null && id.equals(((Mouvement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mouvement{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            ", source='" + getSource() + "'" +
            ", destination='" + getDestination() + "'" +
            ", user='" + getUser() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
