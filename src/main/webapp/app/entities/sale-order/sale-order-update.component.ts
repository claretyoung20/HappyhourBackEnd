import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISaleOrder } from 'app/shared/model/sale-order.model';
import { SaleOrderService } from './sale-order.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { IHappyOrder } from 'app/shared/model/happy-order.model';
import { HappyOrderService } from 'app/entities/happy-order';

@Component({
    selector: 'jhi-sale-order-update',
    templateUrl: './sale-order-update.component.html'
})
export class SaleOrderUpdateComponent implements OnInit {
    private _saleOrder: ISaleOrder;
    isSaving: boolean;

    products: IProduct[];

    happyorders: IHappyOrder[];
    dateCreatedDp: any;
    dateUpdatedDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private saleOrderService: SaleOrderService,
        private productService: ProductService,
        private happyOrderService: HappyOrderService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ saleOrder }) => {
            this.saleOrder = saleOrder;
        });
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.happyOrderService.query().subscribe(
            (res: HttpResponse<IHappyOrder[]>) => {
                this.happyorders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.saleOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.saleOrderService.update(this.saleOrder));
        } else {
            this.subscribeToSaveResponse(this.saleOrderService.create(this.saleOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISaleOrder>>) {
        result.subscribe((res: HttpResponse<ISaleOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackHappyOrderById(index: number, item: IHappyOrder) {
        return item.id;
    }
    get saleOrder() {
        return this._saleOrder;
    }

    set saleOrder(saleOrder: ISaleOrder) {
        this._saleOrder = saleOrder;
    }
}
