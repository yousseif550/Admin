import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInformationsTech } from '../informations-tech.model';

@Component({
  selector: 'jhi-informations-tech-detail',
  templateUrl: './informations-tech-detail.component.html',
})
export class InformationsTechDetailComponent implements OnInit {
  informationsTech: IInformationsTech | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ informationsTech }) => {
      this.informationsTech = informationsTech;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
