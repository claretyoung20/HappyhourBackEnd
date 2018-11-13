import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProductImage } from 'app/shared/model/product-image.model';
import { ProductImageService } from './product-image.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-product-image-update',
    templateUrl: './product-image-update.component.html'
})
export class ProductImageUpdateComponent implements OnInit {
    private _productImage: IProductImage;
    isSaving: boolean;

    products: IProduct[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private productImageService: ProductImageService,
        private productService: ProductService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productImage }) => {
            this.productImage = productImage;
        });
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productImage.id !== undefined) {
            this.subscribeToSaveResponse(this.productImageService.update(this.productImage));
        } else {
            this.subscribeToSaveResponse(this.productImageService.create(this.productImage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProductImage>>) {
        result.subscribe((res: HttpResponse<IProductImage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
    get productImage() {
        return this._productImage;
    }

    set productImage(productImage: IProductImage) {
        this._productImage = productImage;
    }
}
