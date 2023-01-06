import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExtracDMOCSS } from '../extrac-dmocss.model';

@Component({
  selector: 'jhi-extrac-dmocss-detail',
  templateUrl: './extrac-dmocss-detail.component.html',
})
export class ExtracDMOCSSDetailComponent implements OnInit {
  extracDMOCSS: IExtracDMOCSS | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extracDMOCSS }) => {
      this.extracDMOCSS = extracDMOCSS;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
