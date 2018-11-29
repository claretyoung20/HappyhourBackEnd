import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductImage } from 'app/shared/model/product-image.model';

@Component({
    selector: 'jhi-product-image-detail',
    templateUrl: './product-image-detail.component.html'
})
export class ProductImageDetailComponent implements OnInit {
    productImage: IProductImage;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productImage }) => {
            this.productImage = productImage;
        });
    }

    previousState() {
        window.history.back();
    }
}
