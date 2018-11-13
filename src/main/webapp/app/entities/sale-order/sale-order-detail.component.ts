import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISaleOrder } from 'app/shared/model/sale-order.model';

@Component({
    selector: 'jhi-sale-order-detail',
    templateUrl: './sale-order-detail.component.html'
})
export class SaleOrderDetailComponent implements OnInit {
    saleOrder: ISaleOrder;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ saleOrder }) => {
            this.saleOrder = saleOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
