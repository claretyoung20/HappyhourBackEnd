import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHappyOrder } from 'app/shared/model/happy-order.model';

@Component({
    selector: 'jhi-happy-order-detail',
    templateUrl: './happy-order-detail.component.html'
})
export class HappyOrderDetailComponent implements OnInit {
    happyOrder: IHappyOrder;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ happyOrder }) => {
            this.happyOrder = happyOrder;
        });
    }

    previousState() {
        window.history.back();
    }
}
