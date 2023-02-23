import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { ICollaborateurs } from '../collaborateurs.model';
import { CollaborateursService } from '../service/collaborateurs.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-collaborateurs-detail',
  templateUrl: './collaborateurs-detail.component.html',
})
export class CollaborateursDetailComponent implements OnInit {
  collaborateurs: ICollaborateurs | null = null;
  materiels: IMateriel | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private collaborateurService: CollaborateursService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaborateurs, materiels }) => {
      this.collaborateurs = collaborateurs;
      this.materiels = materiels;
    });

    const id = this.activatedRoute.snapshot.paramMap.get('id');

    if (id !== null) {
      this.collaborateurService.getMaterielsForCollaborateur(id).subscribe((res: HttpResponse<IMateriel[]>) => {
        for (let i = 0; i < i + 1; i++) {
          if (res.body !== null) {
            /* console.log('AA',res.body[i].collaborateur?.id,'ID',id);
            console.log('MAT',res.body[i]) */

            if (res.body[i].collaborateur?.id == id) {
              this.materiels = res.body[i];
              console.log('MYCOLLAB', this.materiels);
              console.log('COLLAB', this.collaborateurs);
            }
          }
        }

        //if (this.collaborateurs && 'id' in this.collaborateurs) {
        //this.collaborateurs = res.body[0].collaborateur;
        //this.materiels = res.body.map((materiel) => materiel.materiel);
        // Utilisez myCollaborateurs ici
        //} else {
        // this.collaborateurs est null ou ne contient pas la structure ICollaborateurs
        //}
      });
    }
  }

  previousState(): void {
    window.history.back();
  }
}
