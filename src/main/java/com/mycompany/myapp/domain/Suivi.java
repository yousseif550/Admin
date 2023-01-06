package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Etat;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Suivi.
 */
@Document(collection = "suivi")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Suivi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("envoi_kit_accueil")
    private Etat envoiKitAccueil;

    @Field("document_signer")
    private Etat documentSigner;

    @Field("commande_pc_dom")
    private Etat commandePCDom;

    @Field("compte_ssg")
    private Etat compteSSG;

    @Field("liste_ntic")
    private Etat listeNTIC;

    @Field("acces_teams")
    private Etat accesTeams;

    @Field("acces_pulse_dg_fi_p")
    private Etat accesPulseDGFiP;

    @Field("profil_pc_dom")
    private Etat profilPCDom;

    @Field("commander_pcdg_fi_p")
    private Etat commanderPCDGFiP;

    @Field("creation_balpdg_fi_p")
    private Etat creationBALPDGFiP;

    @Field("creation_compte_ad")
    private Etat creationCompteAD;

    @Field("soclage_pc")
    private Etat soclagePC;

    @Field("dmocss_ip_tt")
    private Etat dmocssIpTT;

    @Field("installation_logiciel")
    private Etat installationLogiciel;

    @Field("commentaires")
    private String commentaires;

    @Field("collaborateur")
    private Collaborateurs collaborateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Suivi id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Etat getEnvoiKitAccueil() {
        return this.envoiKitAccueil;
    }

    public Suivi envoiKitAccueil(Etat envoiKitAccueil) {
        this.setEnvoiKitAccueil(envoiKitAccueil);
        return this;
    }

    public void setEnvoiKitAccueil(Etat envoiKitAccueil) {
        this.envoiKitAccueil = envoiKitAccueil;
    }

    public Etat getDocumentSigner() {
        return this.documentSigner;
    }

    public Suivi documentSigner(Etat documentSigner) {
        this.setDocumentSigner(documentSigner);
        return this;
    }

    public void setDocumentSigner(Etat documentSigner) {
        this.documentSigner = documentSigner;
    }

    public Etat getCommandePCDom() {
        return this.commandePCDom;
    }

    public Suivi commandePCDom(Etat commandePCDom) {
        this.setCommandePCDom(commandePCDom);
        return this;
    }

    public void setCommandePCDom(Etat commandePCDom) {
        this.commandePCDom = commandePCDom;
    }

    public Etat getCompteSSG() {
        return this.compteSSG;
    }

    public Suivi compteSSG(Etat compteSSG) {
        this.setCompteSSG(compteSSG);
        return this;
    }

    public void setCompteSSG(Etat compteSSG) {
        this.compteSSG = compteSSG;
    }

    public Etat getListeNTIC() {
        return this.listeNTIC;
    }

    public Suivi listeNTIC(Etat listeNTIC) {
        this.setListeNTIC(listeNTIC);
        return this;
    }

    public void setListeNTIC(Etat listeNTIC) {
        this.listeNTIC = listeNTIC;
    }

    public Etat getAccesTeams() {
        return this.accesTeams;
    }

    public Suivi accesTeams(Etat accesTeams) {
        this.setAccesTeams(accesTeams);
        return this;
    }

    public void setAccesTeams(Etat accesTeams) {
        this.accesTeams = accesTeams;
    }

    public Etat getAccesPulseDGFiP() {
        return this.accesPulseDGFiP;
    }

    public Suivi accesPulseDGFiP(Etat accesPulseDGFiP) {
        this.setAccesPulseDGFiP(accesPulseDGFiP);
        return this;
    }

    public void setAccesPulseDGFiP(Etat accesPulseDGFiP) {
        this.accesPulseDGFiP = accesPulseDGFiP;
    }

    public Etat getProfilPCDom() {
        return this.profilPCDom;
    }

    public Suivi profilPCDom(Etat profilPCDom) {
        this.setProfilPCDom(profilPCDom);
        return this;
    }

    public void setProfilPCDom(Etat profilPCDom) {
        this.profilPCDom = profilPCDom;
    }

    public Etat getCommanderPCDGFiP() {
        return this.commanderPCDGFiP;
    }

    public Suivi commanderPCDGFiP(Etat commanderPCDGFiP) {
        this.setCommanderPCDGFiP(commanderPCDGFiP);
        return this;
    }

    public void setCommanderPCDGFiP(Etat commanderPCDGFiP) {
        this.commanderPCDGFiP = commanderPCDGFiP;
    }

    public Etat getCreationBALPDGFiP() {
        return this.creationBALPDGFiP;
    }

    public Suivi creationBALPDGFiP(Etat creationBALPDGFiP) {
        this.setCreationBALPDGFiP(creationBALPDGFiP);
        return this;
    }

    public void setCreationBALPDGFiP(Etat creationBALPDGFiP) {
        this.creationBALPDGFiP = creationBALPDGFiP;
    }

    public Etat getCreationCompteAD() {
        return this.creationCompteAD;
    }

    public Suivi creationCompteAD(Etat creationCompteAD) {
        this.setCreationCompteAD(creationCompteAD);
        return this;
    }

    public void setCreationCompteAD(Etat creationCompteAD) {
        this.creationCompteAD = creationCompteAD;
    }

    public Etat getSoclagePC() {
        return this.soclagePC;
    }

    public Suivi soclagePC(Etat soclagePC) {
        this.setSoclagePC(soclagePC);
        return this;
    }

    public void setSoclagePC(Etat soclagePC) {
        this.soclagePC = soclagePC;
    }

    public Etat getDmocssIpTT() {
        return this.dmocssIpTT;
    }

    public Suivi dmocssIpTT(Etat dmocssIpTT) {
        this.setDmocssIpTT(dmocssIpTT);
        return this;
    }

    public void setDmocssIpTT(Etat dmocssIpTT) {
        this.dmocssIpTT = dmocssIpTT;
    }

    public Etat getInstallationLogiciel() {
        return this.installationLogiciel;
    }

    public Suivi installationLogiciel(Etat installationLogiciel) {
        this.setInstallationLogiciel(installationLogiciel);
        return this;
    }

    public void setInstallationLogiciel(Etat installationLogiciel) {
        this.installationLogiciel = installationLogiciel;
    }

    public String getCommentaires() {
        return this.commentaires;
    }

    public Suivi commentaires(String commentaires) {
        this.setCommentaires(commentaires);
        return this;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public Collaborateurs getCollaborateur() {
        return this.collaborateur;
    }

    public void setCollaborateur(Collaborateurs collaborateurs) {
        this.collaborateur = collaborateurs;
    }

    public Suivi collaborateur(Collaborateurs collaborateurs) {
        this.setCollaborateur(collaborateurs);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suivi)) {
            return false;
        }
        return id != null && id.equals(((Suivi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Suivi{" +
            "id=" + getId() +
            ", envoiKitAccueil='" + getEnvoiKitAccueil() + "'" +
            ", documentSigner='" + getDocumentSigner() + "'" +
            ", commandePCDom='" + getCommandePCDom() + "'" +
            ", compteSSG='" + getCompteSSG() + "'" +
            ", listeNTIC='" + getListeNTIC() + "'" +
            ", accesTeams='" + getAccesTeams() + "'" +
            ", accesPulseDGFiP='" + getAccesPulseDGFiP() + "'" +
            ", profilPCDom='" + getProfilPCDom() + "'" +
            ", commanderPCDGFiP='" + getCommanderPCDGFiP() + "'" +
            ", creationBALPDGFiP='" + getCreationBALPDGFiP() + "'" +
            ", creationCompteAD='" + getCreationCompteAD() + "'" +
            ", soclagePC='" + getSoclagePC() + "'" +
            ", dmocssIpTT='" + getDmocssIpTT() + "'" +
            ", installationLogiciel='" + getInstallationLogiciel() + "'" +
            ", commentaires='" + getCommentaires() + "'" +
            "}";
    }
}
