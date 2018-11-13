import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductType } from 'app/shared/model/product-type.model';

@Component({
    selector: 'jhi-product-type-detail',
    templateUrl: './product-type-detail.component.html'
})
export class ProductTypeDetailComponent implements OnInit {
    productType: IProductType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productType }) => {
            this.productType = productType;
        });
    }

    previousState() {
        window.history.back();
    }
}
