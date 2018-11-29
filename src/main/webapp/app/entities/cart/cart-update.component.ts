import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICart } from 'app/shared/model/cart.model';
import { CartService } from './cart.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
    selector: 'jhi-cart-update',
    templateUrl: './cart-update.component.html'
})
export class CartUpdateComponent implements OnInit {
    private _cart: ICart;
    isSaving: boolean;

    products: IProduct[];

    customers: ICustomer[];
    dateCreated: string;
    dateUpdated: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private cartService: CartService,
        private productService: ProductService,
        private customerService: CustomerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cart }) => {
            this.cart = cart;
        });
        this.productService.query().subscribe(
            (res: HttpResponse<IProduct[]>) => {
                this.products = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.customerService.query().subscribe(
            (res: HttpResponse<ICustomer[]>) => {
                this.customers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.cart.dateCreated = moment(this.dateCreated, DATE_TIME_FORMAT);
        this.cart.dateUpdated = moment(this.dateUpdated, DATE_TIME_FORMAT);
        if (this.cart.id !== undefined) {
            this.subscribeToSaveResponse(this.cartService.update(this.cart));
        } else {
            this.subscribeToSaveResponse(this.cartService.create(this.cart));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICart>>) {
        result.subscribe((res: HttpResponse<ICart>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }
    get cart() {
        return this._cart;
    }

    set cart(cart: ICart) {
        this._cart = cart;
        this.dateCreated = moment(cart.dateCreated).format(DATE_TIME_FORMAT);
        this.dateUpdated = moment(cart.dateUpdated).format(DATE_TIME_FORMAT);
    }
}
