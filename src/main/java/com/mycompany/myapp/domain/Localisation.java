package com.mycompany.myapp.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Localisation.
 */
@Document(collection = "localisation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Localisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("batiment")
    private String batiment;

    @Field("bureau_origine")
    private String bureauOrigine;

    @Field("bureau_stockage")
    private String bureauStockage;

    @Field("site")
    private String site;

    @Field("ville")
    private String ville;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Localisation id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatiment() {
        return this.batiment;
    }

    public Localisation batiment(String batiment) {
        this.setBatiment(batiment);
        return this;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public String getBureauOrigine() {
        return this.bureauOrigine;
    }

    public Localisation bureauOrigine(String bureauOrigine) {
        this.setBureauOrigine(bureauOrigine);
        return this;
    }

    public void setBureauOrigine(String bureauOrigine) {
        this.bureauOrigine = bureauOrigine;
    }

    public String getBureauStockage() {
        return this.bureauStockage;
    }

    public Localisation bureauStockage(String bureauStockage) {
        this.setBureauStockage(bureauStockage);
        return this;
    }

    public void setBureauStockage(String bureauStockage) {
        this.bureauStockage = bureauStockage;
    }

    public String getSite() {
        return this.site;
    }

    public Localisation site(String site) {
        this.setSite(site);
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getVille() {
        return this.ville;
    }

    public Localisation ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Localisation)) {
            return false;
        }
        return id != null && id.equals(((Localisation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Localisation{" +
            "id=" + getId() +
            ", batiment='" + getBatiment() + "'" +
            ", bureauOrigine='" + getBureauOrigine() + "'" +
            ", bureauStockage='" + getBureauStockage() + "'" +
            ", site='" + getSite() + "'" +
            ", ville='" + getVille() + "'" +
            "}";
    }
}
