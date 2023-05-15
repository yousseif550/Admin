import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMateriel } from 'app/entities/materiel/materiel.model';
import { ICollaborateurs } from '../collaborateurs.model';
import { MyObject } from '../collaborateurs.model';
import { CollaborateursService } from '../service/collaborateurs.service';
import { HttpResponse } from '@angular/common/http';
import { Obj } from '@popperjs/core';

@Component({
  selector: 'jhi-collaborateurs-detail',
  templateUrl: './collaborateurs-detail.component.html',
})
export class CollaborateursDetailComponent implements OnInit {
  collaborateurs: ICollaborateurs | null = null;
  materiels: IMateriel | null = null;
  inventaire: MyObject | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private collaborateurService: CollaborateursService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaborateurs, materiels, inventaire }) => {
      this.collaborateurs = collaborateurs;
      this.materiels = materiels;
      this.inventaire = inventaire;
    });

    const id = this.activatedRoute.snapshot.paramMap.get('id');
    let myObject: any = {};

    if (id !== null) {
      this.collaborateurService.getMaterielsForCollaborateur().subscribe((res: HttpResponse<IMateriel[]> | any) => {
        for (let i = 0; i < i + 1; i++) {
          if (res.body !== null) {
            if (res.body[i].collaborateur) {
              if (res.body[i].collaborateur?.id == id) {
                this.materiels = res.body[i];
                console.log('material', this.materiels);

                let myVariable: any | MyObject = {};

                myVariable = {
                  id: this.materiels?.id,
                  asset: this.materiels?.asset,
                  objet: this.materiels?.objet,
                };

                if (typeof myVariable === 'object' && myVariable !== null) {
                  const half = myVariable as MyObject;
                  // Maintenant TypeScript sait que myObject est un objet avec une propriété 'id'

                  myObject = {
                    ...myObject,
                    [i]: half, // Ajout de la nouvelle propriété
                  };

                  this.inventaire = myObject;
                  let tableau: any = []; // initialise le tableau vide
                  tableau.push(this.inventaire);
                  console.log('TABBBB', tableau);
                  if (this.inventaire !== null) {
                    console.log('half', this.inventaire);
                  }
                }
              }
            }
          }
        }
      });
    }
  }

  previousState(): void {
    window.history.back();
  }
}
