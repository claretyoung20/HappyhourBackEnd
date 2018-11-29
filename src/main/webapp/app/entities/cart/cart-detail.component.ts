import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICart } from 'app/shared/model/cart.model';

@Component({
    selector: 'jhi-cart-detail',
    templateUrl: './cart-detail.component.html'
})
export class CartDetailComponent implements OnInit {
    cart: ICart;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cart }) => {
            this.cart = cart;
        });
    }

    previousState() {
        window.history.back();
    }
}
