import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDiscount } from 'app/shared/model/discount.model';
import { DiscountService } from './discount.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-discount-update',
    templateUrl: './discount-update.component.html'
})
export class DiscountUpdateComponent implements OnInit {
    private _discount: IDiscount;
    isSaving: boolean;

    products: IProduct[];
    endDateDp: any;
    startDateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private discountService: DiscountService,
        private productService: ProductService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ discount }) => {
            this.discount = discount;
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
        if (this.discount.id !== undefined) {
            this.subscribeToSaveResponse(this.discountService.update(this.discount));
        } else {
            this.subscribeToSaveResponse(this.discountService.create(this.discount));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDiscount>>) {
        result.subscribe((res: HttpResponse<IDiscount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get discount() {
        return this._discount;
    }

    set discount(discount: IDiscount) {
        this._discount = discount;
    }
}
